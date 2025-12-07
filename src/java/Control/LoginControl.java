/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Control;

import Bean.Conexion;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author garri
 */
@WebServlet(name = "LoginControl", urlPatterns = {"/LoginControl"})
public class LoginControl extends HttpServlet {

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String usuario = request.getParameter("usuario");
        String pass = request.getParameter("password");
        
        try (Connection cn = Conexion.conectar()) {
            // 1. Consulta SQL
            String sql = "SELECT tipo_usuario FROM Usuarios WHERE nombre_usuario = ? AND password = ? AND estado = 1";
            PreparedStatement ps = cn.prepareStatement(sql);
            ps.setString(1, usuario);
            ps.setString(2, pass);
            ResultSet rs = ps.executeQuery();
            
            if (rs.next()) {
                String tipo = rs.getString("tipo_usuario");
                
                // 2. Crear sesión
                HttpSession session = request.getSession();
                session.setAttribute("usuario", usuario);
                session.setAttribute("tipo", tipo);
                session.setAttribute("logueado", true);
                
                // 3. Lógica de redirección
                if (tipo.equals("usuario")) {
                    // Usuario va a su página
                    response.sendRedirect("Usuarios.html");
                } else if (tipo.equals("administrador")) {
                    // Administrador va a su página
                    response.sendRedirect("Administrador.html");
                } else {
                    // Por si acaso hay un rol raro
                    response.sendRedirect("index.html");
                }
            } else {
                // Si no existe o contraseña mal
                response.sendRedirect("index.html"); // O una página de error
            }
        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect("index.html");
        }
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }
}
