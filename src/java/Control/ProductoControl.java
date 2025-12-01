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

/**
 *
 * @author garri
 */
@WebServlet(name = "ProductoControl", urlPatterns = {"/ProductoControl"})
public class ProductoControl extends HttpServlet {

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
            out.println("<title>Servlet ProductoControl</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet ProductoControl at " + request.getContextPath() + "</h1>");
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
        Bean.Producto p = new Bean.Producto();
        
        switch (accion) {
            case "Registrar Producto":
                p.setClaveProducto(request.getParameter("clave_producto"));
                p.setNombreProducto(request.getParameter("nombre_producto"));
                p.setPrecio(Double.parseDouble(request.getParameter("precio")));
                p.setCaracteristicas(request.getParameter("caracteristicas"));
                p.setMarca(request.getParameter("marca"));
                p.setIdProveedor(Integer.parseInt(request.getParameter("id_proveedor")));
                p.setExistencia(Integer.parseInt(request.getParameter("existencia")));
                p.alta();
                break;
                
            case "Dar de Baja":
                p.setClaveProducto(request.getParameter("clave_producto"));
                p.bajaLogica();
                break;
                
            case "Consultar":
                p.setClaveProducto(request.getParameter("clave_producto"));
                p.consulta();
                break;
                
            case "Buscar para Modificar":
                p.setClaveProducto(request.getParameter("clave_producto"));
                p.consultaParaModificar();
                break;
                
            case "Modificar Producto":
                p.setClaveProducto(request.getParameter("clave_producto"));
                p.setNombreProducto(request.getParameter("nombre_producto"));
                p.setPrecio(Double.parseDouble(request.getParameter("precio")));
                p.setCaracteristicas(request.getParameter("caracteristicas"));
                p.setMarca(request.getParameter("marca"));
                p.setIdProveedor(Integer.parseInt(request.getParameter("id_proveedor")));
                p.setExistencia(Integer.parseInt(request.getParameter("existencia")));
                p.modifica();
                break;
                
            default:
                p.setRespuesta("Error: Acción desconocida (" + accion + ").");
                break;
        }
        
        // Enviar a la respuesta
        request.setAttribute("respuesta", p.getRespuesta());
        request.getRequestDispatcher("respuesta.jsp").forward(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Controlador de altas, bajas y consultas de productos";
    }// </editor-fold>

}
