<%-- 
    Document   : listarProductos
    Created on : 30-nov-2025, 19:42:48
    Author     : garri
--%>
<%@page import="java.sql.*"%>
<%@page import="Bean.Conexion"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Catálogo de Productos</title>
        <link rel="stylesheet" href="css/style.css">
    </head>
     <body>
        <h1>Catálogo de Productos</h1>
        
        <table border="1">
            <thead>
                <tr>
                    <th>Clave</th>
                    <th>Nombre</th>
                    <th>Precio</th>
                    <th>Características</th>
                    <th>Marca</th>
                    <th>Existencia</th>
                </tr>
            </thead>
            <tbody>
                <%
                    Connection cn = null;
                    PreparedStatement ps = null;
                    ResultSet rs = null;
                    try {
                        cn = Conexion.conectar();
                        String sql = "SELECT * FROM Productos";
                        ps = cn.prepareStatement(sql);
                        rs = ps.executeQuery();
                        
                        while (rs.next()) {
                            out.println("<tr>");
                            out.println("<td>" + rs.getString("clave_producto") + "</td>");
                            out.println("<td>" + rs.getString("nombre_producto") + "</td>");
                            out.println("<td>$" + rs.getDouble("precio") + "</td>");
                            out.println("<td>" + rs.getString("caracteristicas") + "</td>");
                            out.println("<td>" + rs.getString("marca") + "</td>");
                            out.println("<td>" + rs.getInt("existencia") + "</td>");
                            out.println("</tr>");
                        }
                    } catch (Exception e) {
                        out.println("<tr><td colspan='6'>Error: " + e.getMessage() + "</td></tr>");
                    } finally {
                        if (rs != null) try { rs.close(); } catch (Exception e) {}
                        if (ps != null) try { ps.close(); } catch (Exception e) {}
                        if (cn != null) try { cn.close(); } catch (Exception e) {}
                    }
                %>
            </tbody>
        </table>
        
        <br>
        <a href="Usuarios.html">Volver al menú</a>
    </body>
</html>
