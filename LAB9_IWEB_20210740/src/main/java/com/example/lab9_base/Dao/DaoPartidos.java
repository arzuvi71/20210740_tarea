package com.example.lab9_base.Dao;

import com.example.lab9_base.Bean.Partido;
import com.example.lab9_base.Bean.Arbitro;
import com.example.lab9_base.Bean.Estadio;
import com.example.lab9_base.Bean.Seleccion;
import com.example.lab9_base.Bean.Jugador;
import java.util.ArrayList;
import java.sql.*;

public class DaoPartidos extends DaoBase {
    public ArrayList<Partido> listaDePartidos() {

        ArrayList<Partido> partidos = new ArrayList<>();

        try{

            Connection connection = getConnection();

            String sql = "SELECT p.idPartido, p.fecha, p.numeroJornada, "
                    + "sl.idSeleccion AS seleccionLocalId, sl.nombre AS seleccionLocalNombre, "
                    + "sv.idSeleccion AS seleccionVisitanteId, sv.nombre AS seleccionVisitanteNombre, "
                    + "a.idArbitro, a.nombre AS arbitroNombre, a.pais AS arbitroPais, "
                    + "e.idEstadio, e.nombre AS estadioNombre "
                    + "FROM partido p "
                    + "JOIN seleccion sl ON p.seleccionLocal = sl.idSeleccion "
                    + "JOIN seleccion sv ON p.seleccionVisitante = sv.idSeleccion "
                    + "JOIN arbitro a ON p.arbitro = a.idArbitro "
                    + "JOIN estadio e ON sl.estadio_idEstadio = e.idEstadio";

            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet resultSet = statement.executeQuery();


            // Procesamos el ResultSet
            while (resultSet.next()) {
                Partido partido = new Partido();
                partido.setIdPartido(resultSet.getInt("idPartido"));
                partido.setFecha(resultSet.getString("fecha"));
                partido.setNumeroJornada(resultSet.getInt("numeroJornada"));

                Seleccion seleccionLocal = new Seleccion();
                seleccionLocal.setIdSeleccion(resultSet.getInt("seleccionLocalId"));
                seleccionLocal.setNombre(resultSet.getString("seleccionLocalNombre"));
                partido.setSeleccionLocal(seleccionLocal);

                Seleccion seleccionVisitante = new Seleccion();
                seleccionVisitante.setIdSeleccion(resultSet.getInt("seleccionVisitanteId"));
                seleccionVisitante.setNombre(resultSet.getString("seleccionVisitanteNombre"));
                partido.setSeleccionVisitante(seleccionVisitante);

                Arbitro arbitro = new Arbitro();
                arbitro.setIdArbitro(resultSet.getInt("idArbitro"));
                arbitro.setNombre(resultSet.getString("arbitroNombre"));
                arbitro.setPais(resultSet.getString("arbitroPais"));
                partido.setArbitro(arbitro);

                Estadio estadio = new Estadio();
                estadio.setIdEstadio(resultSet.getInt("idEstadio"));
                estadio.setNombre(resultSet.getString("estadioNombre"));
                seleccionLocal.setEstadio(estadio);

                partidos.add(partido);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return partidos;
    }

    public boolean redundanciaPartido(Partido partido) {

        try{
            Connection connection = getConnection();

            String sql="select count(*) from partido\n" +
                    "where fecha = ? and \n" +
                    "(seleccionLocal= ? and seleccionVisitante=?)";

            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, partido.getFecha());
            statement.setInt(2, partido.getSeleccionLocal().getIdSeleccion());
            statement.setInt(3, partido.getSeleccionVisitante().getIdSeleccion());

            ResultSet resultSet = statement.executeQuery();
            //Verificamos si existe el partido
            if (resultSet.next()) {
                return resultSet.getInt(1) > 0;
            }

        }catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    public boolean crearPartido(Partido partido) {

        if(redundanciaPartido(partido)){
            return false;
        }

        try{
            Connection connection = getConnection();

            String sql = "INSERT INTO partido (numeroJornada, fecha,seleccionLocal,seleccionVisitante, arbitro) " +
                    "VALUES (?, ?, ?, ?, ?)";

            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1,partido.getNumeroJornada());
            statement.setString(2, partido.getFecha());
            statement.setInt(3, partido.getSeleccionLocal().getIdSeleccion());
            statement.setInt(4, partido.getSeleccionVisitante().getIdSeleccion());
            statement.setInt(5, partido.getArbitro().getIdArbitro());

            //Verificamos si se realizo correctamente la actualización de la base de datos
            statement.executeUpdate();
            return true;

        }catch (SQLException e) {
            e.printStackTrace();
            return false;
        }

    }

    public ArrayList<Seleccion> listarSelecciones() {
        ArrayList<Seleccion> selecciones = new ArrayList<>();

        try{
            Connection connection = getConnection();
            String sql = "SELECT idSeleccion, nombre FROM seleccion";

            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet resultSet = statement.executeQuery();

            // Procesar los resultados
            while (resultSet.next()) {
                Seleccion seleccion = new Seleccion();
                seleccion.setIdSeleccion(resultSet.getInt("idSeleccion"));
                seleccion.setNombre(resultSet.getString("nombre"));
                selecciones.add(seleccion);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return selecciones;
    }

    public ArrayList<Arbitro> listarArbitros() {
        ArrayList<Arbitro> arbitros = new ArrayList<>();

        try{
            Connection connection = getConnection();
            String sql = "SELECT idArbitro, nombre FROM seleccion";

            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet resultSet = statement.executeQuery();

            // Procesar los resultados
            while (resultSet.next()) {
                Arbitro arbitro = new Arbitro();
                arbitro.setIdArbitro(resultSet.getInt("idArbitro"));
                arbitro.setNombre(resultSet.getString("nombre"));
                arbitros.add(arbitro);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return arbitros;
    }
}
