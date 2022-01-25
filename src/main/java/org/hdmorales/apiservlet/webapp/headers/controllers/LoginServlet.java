package org.hdmorales.apiservlet.webapp.headers.controllers;

import jakarta.inject.Inject;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import org.hdmorales.apiservlet.webapp.headers.models.entities.Usuario;
import org.hdmorales.apiservlet.webapp.headers.services.*;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Optional;

@WebServlet({"/login", "/login.html"})
public class LoginServlet extends HttpServlet {

    @Inject
    private LoginService auth;

    @Inject
    private UsuarioService service;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        Optional<String> userOptional = auth.getUsername(req);

        if(userOptional.isPresent()){
            resp.setContentType("text/html;charset=UTF-8");
            try (PrintWriter out = resp.getWriter()) {
                out.println("<!DOCTYPE html>");
                out.println("<html>");
                out.println("     <head>");
                out.println("         <meta charset=\"UTF-8\">");
                out.println("         <title>Hola " + userOptional.get() + "</title>");
                out.println("     </head>");
                out.println("     <body>");
                out.println("         <h1>Hola " + userOptional.get() + " has iniciado sesion con exito</h1>");
                out.println("         <p><a href='" + req.getContextPath() + "/index.jsp'>Volver</a></p>");
                out.println("         <p><a href='" + req.getContextPath() + "/logout'>Cerrar sesion</a></p>");
                out.println("     </body>");
                out.println("</html>");
            }
        } else {
            req.setAttribute("title", req.getAttribute("title") + ": Login");
            getServletContext().getRequestDispatcher("/login.jsp").forward(req, resp);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String username = req.getParameter("username");
        String password = req.getParameter("password");

        Optional<Usuario> usuarioOptional = service.login(username, password);

        if (usuarioOptional.isPresent()) {
            HttpSession session = req.getSession();
            session.setAttribute("username", username);

            resp.sendRedirect(req.getContextPath() + "/login.html");
        } else {
            resp.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Lo sentimos no esta autorizado para ingresar a esta pagina");
        }
    }
}
