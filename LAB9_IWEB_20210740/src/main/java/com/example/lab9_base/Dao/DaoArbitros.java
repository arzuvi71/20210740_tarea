package com.example.lab9_base.Dao;

import com.example.lab9_base.Bean.Arbitro;
import com.example.lab9_base.Bean.Partido;

import java.util.ArrayList;
import java.sql.*;

public class DaoArbitros extends DaoBase {
    public ArrayList<Arbitro> listarArbitros() {
        ArrayList<Arbitro> arbitros = new ArrayList<>();

        try{
            Connection connection = getConnection();
            String sql="select * from arbitro";

            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet resultSet = statement.executeQuery();

            // Procesamos el ResultSet
            while (resultSet.next()) {
                Arbitro arbitro = new Arbitro();
                arbitro.setIdArbitro(resultSet.getInt("idArbitro"));
                arbitro.setNombre(resultSet.getString("nombre"));
                arbitro.setPais(resultSet.getString("pais"));
                arbitros.add(arbitro);
            }

        }catch (SQLException e) {
            e.printStackTrace();
        }

        return arbitros;
    }

    public void crearArbitro(Arbitro arbitro) {

        try{
            Connection connection = getConnection();

            String sql = "INSERT INTO arbitro (nombre, pais) VALUES (?, ?)";

            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(2, arbitro.getNombre());
            statement.setString(1, arbitro.getPais());

            //Verificamos si se realizo correctamente la actualización de la base de datos
            statement.executeUpdate();

        }catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public ArrayList<Arbitro> busquedaPais(String pais) {

        ArrayList<Arbitro> arbitros = new ArrayList<>();
        String sql = "SELECT * FROM arbitro WHERE pais LIKE ?";

        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setString(1, "%" + pais + "%");
            try (ResultSet resultSet= preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    Arbitro  arbitro= new Arbitro();
                    arbitro.setIdArbitro(resultSet.getInt("idArbitro"));
                    arbitro.setNombre(resultSet.getString("nombre"));
                    arbitro.setPais(resultSet.getString("pais"));
                    arbitros.add(arbitro);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return arbitros;
    }

    public ArrayList<Arbitro> busquedaNombre(String nombre) {

        ArrayList<Arbitro> arbitros = new ArrayList<>();
        String sql = "SELECT * FROM arbitro WHERE nombre LIKE ?";

        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setString(1, "%" + nombre + "%");
            try (ResultSet resultSet= preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    Arbitro  arbitro= new Arbitro();
                    arbitro.setIdArbitro(resultSet.getInt("idArbitro"));
                    arbitro.setNombre(resultSet.getString("nombre"));
                    arbitro.setPais(resultSet.getString("pais"));
                    arbitros.add(arbitro);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return arbitros;
    }


    public Arbitro buscarArbitro(int id) {
        Arbitro arbitro = null;//En caso no se halle valores se devuelve null
        String sql = "SELECT * FROM arbitro WHERE idArbitro = ?";

        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setInt(1, id);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    arbitro = new Arbitro();
                    arbitro.setIdArbitro(resultSet.getInt("idArbitro"));
                    arbitro.setNombre(resultSet.getString("nombre"));
                    arbitro.setPais(resultSet.getString("pais"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return arbitro;
    }

    public void borrarArbitro(int id) {
        /*
        Inserte su código aquí
        */
    }
}
