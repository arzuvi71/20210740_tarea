//Servlet de Arbitro
package com.example.lab9_base.Controller;

import com.example.lab9_base.Bean.Arbitro;
import com.example.lab9_base.Dao.DaoArbitros;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import java.io.IOException;
import java.util.ArrayList;

@WebServlet(name = "ArbitroServlet", urlPatterns = {"/ArbitroServlet"})
public class ArbitroServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action") == null ? "lista" : request.getParameter("action");
        DaoArbitros daoArbitros=new DaoArbitros();
        ArrayList<String> opciones = new ArrayList<>();
        opciones.add("nombre");
        opciones.add("pais");


        switch (action) {

            case "buscar":
                String criterioBusqueda = request.getParameter("criterioBusqueda");
                String valorBusqueda = request.getParameter("valorBusqueda");
                ArrayList<Arbitro> resultados = new ArrayList<>();

                if (criterioBusqueda != null && valorBusqueda != null && !valorBusqueda.isEmpty()) {
                    if ("nombre".equals(criterioBusqueda)) {
                        resultados = daoArbitros.busquedaNombre(valorBusqueda);
                    } else if ("pais".equals(criterioBusqueda)) {
                        resultados = daoArbitros.busquedaPais(valorBusqueda);
                    }
                }

                request.setAttribute("listaArbitros", resultados);
                RequestDispatcher view = request.getRequestDispatcher("/arbitros/list.jsp");
                view.forward(request, response);
                break;

            case "guardar":
                /*
                Inserte su código aquí
                */
                break;

        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String action = request.getParameter("action") == null ? "lista" : request.getParameter("action");
        RequestDispatcher view;
        DaoArbitros daoArbitros = new DaoArbitros();
        ArrayList<String> paises = new ArrayList<>();
        paises.add("Peru");
        paises.add("Chile");
        paises.add("Argentina");
        paises.add("Paraguay");
        paises.add("Uruguay");
        paises.add("Colombia");

        ArrayList<String> opciones = new ArrayList<>();
        opciones.add("nombre");
        opciones.add("pais");


        switch (action) {
            case "lista":
                ArrayList<Arbitro> listaArbitros = daoArbitros.listarArbitros();
                request.setAttribute("listaArbitros", listaArbitros);
                view = request.getRequestDispatcher("/arbitros/list.jsp");
                view.forward(request, response);
                break;
            case "crear":
                request.setAttribute("paises", paises);
                view = request.getRequestDispatcher("/arbitros/form.jsp");
                view.forward(request, response);
                break;
            case "borrar":
                /*
                Inserte su código aquí
                */
                break;
        }
    }
}
