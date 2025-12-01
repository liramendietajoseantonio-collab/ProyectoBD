package Control;

import Bean.Venta;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name = "VentaControl", urlPatterns = {"/VentaControl"})
public class VentaControl extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        
        String boton = request.getParameter("boton");
        Venta venta = new Venta();
        
        try (PrintWriter out = response.getWriter()) {
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Control de Ventas</title>");
            out.println("</head>");
            out.println("<body>");
            
            if (boton != null) {
                switch (boton) {
                    case "Registrar Venta":
                        int idCliente = Integer.parseInt(request.getParameter("id_cliente"));
                        int idProducto = Integer.parseInt(request.getParameter("id_producto"));
                        int cantidad = Integer.parseInt(request.getParameter("cantidad"));
                        double precioUnitario = Double.parseDouble(request.getParameter("precio_unitario"));
                        
                        venta.setIdCliente(idCliente);
                        venta.setIdProducto(idProducto);
                        venta.setCantidad(cantidad);
                        venta.setPrecioUnitario(precioUnitario);
                        venta.alta();
                        break;
                        
                    case "Dar de Baja Venta":
                        int idVentaBaja = Integer.parseInt(request.getParameter("id_venta"));
                        venta.setIdVenta(idVentaBaja);
                        venta.cancelar();
                        break;
                        
                    case "Consultar Venta":
                        int idVentaConsulta = Integer.parseInt(request.getParameter("id_venta"));
                        venta.setIdVenta(idVentaConsulta);
                        venta.consulta();
                        break;

                    case "Buscar Venta para Modificar":
                        int idVentaBuscar = Integer.parseInt(request.getParameter("id_venta"));
                        venta.setIdVenta(idVentaBuscar);
                        venta.consultaParaModificar();
                        break;
                        
                    case "Modificar Venta":
                        int idVentaMod = Integer.parseInt(request.getParameter("id_venta"));
                        int idClienteMod = Integer.parseInt(request.getParameter("id_cliente"));
                        int idProductoMod = Integer.parseInt(request.getParameter("id_producto"));
                        int cantidadMod = Integer.parseInt(request.getParameter("cantidad"));
                        double precioUnitarioMod = Double.parseDouble(request.getParameter("precio_unitario"));
                        
                        venta.setIdVenta(idVentaMod);
                        venta.setIdCliente(idClienteMod);
                        venta.setIdProducto(idProductoMod);
                        venta.setCantidad(cantidadMod);
                        venta.setPrecioUnitario(precioUnitarioMod);
                        venta.modifica();
                        break;
                        
                    case "LEFT JOIN":
                        venta.consultaVentasConClientes();
                        break;
                        
                    case "RIGHT JOIN":
                        venta.consultaProductosConVentas();
                        break;
                        
                    case "FULL JOIN":
                        venta.consultaClientesVentasCompleto();
                        break;
                        
                    default:
                        venta.setRespuesta("Opción no válida");
                }
                
                out.println(venta.getRespuesta());
            } else {
                out.println("<h2>No se recibió ninguna acción</h2>");
            }
            
            out.println("<br><br>");
            out.println("<a href='Ventas.html'>Regresar al Menú de Ventas</a>");
            out.println("</body>");
            out.println("</html>");
        }
    }
}
