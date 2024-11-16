package com.example.lab9_base.Controller;

import com.example.lab9_base.Bean.Arbitro;
import com.example.lab9_base.Bean.Partido;
import com.example.lab9_base.Bean.Seleccion;
import com.example.lab9_base.Dao.DaoPartidos;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import java.io.IOException;
import java.util.ArrayList;

@WebServlet(name = "PartidoServlet", urlPatterns = {"/PartidoServlet", ""})
public class PartidoServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action") == null ? "guardar" : request.getParameter("action");
        RequestDispatcher view;

        switch (action) {

            case "guardar":
                String newNumJornada=request.getParameter("numJornada");
                String fecha=request.getParameter("fecha");
                String newSeleccionLocal=request.getParameter("seleccionLocal");
                String newSeleccionVisitante=request.getParameter("seleccionVisitante");
                String newArbitro=request.getParameter("arbitro");

                //Verificamos que se rellenen todos los campos
                if (newNumJornada==null || newNumJornada.isEmpty() || fecha==null || fecha.isEmpty() || newSeleccionLocal==null || newSeleccionLocal.isEmpty() || newSeleccionVisitante==null || newSeleccionVisitante.isEmpty() || newArbitro==null || newArbitro.isEmpty()) {
                    response.sendRedirect(request.getContextPath() + "/PartidoServlet?action=crear");
                    return;
                }
                if (newSeleccionLocal.equals(newSeleccionVisitante)) {
                    response.sendRedirect(request.getContextPath() + "/PartidoServlet?action=crear");
                    return;
                }

                DaoPartidos daoPartidos = new DaoPartidos();
                ArrayList<Seleccion> selecciones = daoPartidos.listarSelecciones();
                ArrayList<Arbitro> arbitros = daoPartidos.listarArbitros();

                boolean seleccionLocalValida = false;
                boolean seleccionVisitanteValida = false;
                boolean arbitroValido = false;

                Seleccion seleccionLocal = null;
                Seleccion seleccionVisitante = null;
                Arbitro arbitro = null;

                // Validaciones
                for (Seleccion seleccion : selecciones) {
                    if (seleccion.getNombre().equalsIgnoreCase(newSeleccionLocal)) {
                        seleccionLocal = seleccion;
                        seleccionLocalValida = true;
                        break;
                    }
                }

                for (Seleccion seleccion : selecciones) {
                    if (seleccion.getNombre().equalsIgnoreCase(newSeleccionVisitante)) {
                        seleccionVisitante=seleccion;
                        seleccionVisitanteValida = true;
                        break;
                    }
                }

                for (Arbitro arbitroNew : arbitros) {
                    if (arbitroNew.getNombre().equalsIgnoreCase(newArbitro)) {
                        arbitro = arbitroNew;
                        arbitroValido = true;
                        break;
                    }
                }

                // En caso la validacion de error
                if (!seleccionLocalValida || !seleccionVisitanteValida || !arbitroValido) {
                    response.sendRedirect(request.getContextPath() + "/PartidoServlet?action=crear");
                    return;
                }


                Partido partido = new Partido();
                partido.setNumJornada(Integer.parseInt(newNumJornada));
                partido.setFecha(fecha);
                partido.setSeleccionLocal(seleccionLocal);
                partido.setSeleccionVisitante(seleccionVisitante);
                partido.setArbitro(arbitro);
                daoPartidos.crearPartido(partido);

                response.sendRedirect(request.getContextPath() + "/PartidoServlet?action=lista");

                break;

        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String action = request.getParameter("action") == null ? "lista" : request.getParameter("action");
        RequestDispatcher view;
        DaoPartidos daoPartidos = new DaoPartidos();
        switch (action) {
            case "lista":
                ArrayList<Partido> listaPartidos = daoPartidos.listaDePartidos();
                request.setAttribute("listaPartidos", listaPartidos);
                view = request.getRequestDispatcher("index.jsp");
                view.forward(request, response);
                break;
            case "crear":
                view = request.getRequestDispatcher("partidos/form.jsp");
                view.forward(request, response);
                break;

        }

    }
}
