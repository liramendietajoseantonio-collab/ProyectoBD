/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Control;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import Bean.Cliente;
/**
 *
 * @author garri
 */
@WebServlet(name = "ClienteControl", urlPatterns = {"/ClienteControl"})
public class ClienteControl extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet ClienteControl</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet ClienteControl at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
   public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String accion = request.getParameter("boton");
        Cliente c = new Cliente();
        
        switch (accion) {
            case "Registrar Cliente":
                c.setRfc(request.getParameter("rfc"));
                c.setNombre(request.getParameter("nombre"));
                c.setDireccion(request.getParameter("direccion"));
                c.setTelefono(request.getParameter("telefono"));
                c.setCorreoElectronico(request.getParameter("correo"));
                c.setCelular(request.getParameter("celular"));
                c.setTipoCliente(request.getParameter("tipo"));
                c.alta();
                break;
                
            case "Dar de Baja cliente":
                c.setRfc(request.getParameter("rfc"));
                c.bajaLogica();
                break;
                
            case "Consultar Cliente":
                c.setRfc(request.getParameter("rfc"));
                c.consulta();
                break;
                
            case "Buscar para Modificar":
                c.setRfc(request.getParameter("rfc"));
                c.consultaParaModificar();
                break;
                
            case "Modificar Cliente":
                c.setRfc(request.getParameter("rfc"));
                c.setNombre(request.getParameter("nombre"));
                c.setDireccion(request.getParameter("direccion"));
                c.setTelefono(request.getParameter("telefono"));
                c.setCorreoElectronico(request.getParameter("correo"));
                c.setCelular(request.getParameter("celular"));
                c.setTipoCliente(request.getParameter("tipo"));
                c.modifica();
                break;
                
            default:
                c.setRespuesta("Error: Acción desconocida (" + accion + ").");
                break;
        }
        
        // Enviar a la respuesta
        request.setAttribute("respuesta", c.getRespuesta());
        request.getRequestDispatcher("respuesta.jsp").forward(request, response);
    }

    @Override
    public String getServletInfo() {
        return "Controlador de altas, bajas y consultas de clientes";
    }
}