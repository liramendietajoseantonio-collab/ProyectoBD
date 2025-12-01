package Bean;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class Venta {
    private int idVenta;
    private int idCliente;
    private int idProducto;
    private int cantidad;
    private double precioUnitario;
    private double total;
    private String fechaVenta;
    private String respuesta;
    
    // Getters y Setters
    public int getIdVenta() {
        return idVenta; 
    }
    public void setIdVenta(int idVenta) {
        this.idVenta = idVenta; 
    }
    
    public int getIdCliente() { 
        return idCliente;
    }
    public void setIdCliente(int idCliente) { 
        this.idCliente = idCliente; 
    }
    
    public int getIdProducto() { 
        return idProducto; 
    }
    public void setIdProducto(int idProducto) { 
        this.idProducto = idProducto; 
    }
    
    public int getCantidad() { 
        return cantidad;
    }
    public void setCantidad(int cantidad) { 
        this.cantidad = cantidad; 
    }
    
    public double getPrecioUnitario() { 
        return precioUnitario; 
    }
    public void setPrecioUnitario(double precioUnitario) { 
        this.precioUnitario = precioUnitario; 
    }
    
    public double getTotal() { 
        return total; 
    }
    public void setTotal(double total) { 
        this.total = total;
    }
    
    public String getFechaVenta() { 
        return fechaVenta;
    }
    public void setFechaVenta(String fechaVenta) { 
        this.fechaVenta = fechaVenta;
    }
    
    public String getRespuesta() {
        return respuesta;
    }
    public void setRespuesta(String respuesta) { 
        this.respuesta = respuesta; 
    }
    
    /**
     * Da de alta una nueva Venta.
     */
    public void alta() {
        try (Connection cn = Conexion.conectar()) {
            this.total = this.cantidad * this.precioUnitario;
            
            String sql = "INSERT INTO Ventas (id_cliente, id_producto, cantidad, precio_unitario, total) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement ps = cn.prepareStatement(sql);
            ps.setInt(1, this.idCliente);
            ps.setInt(2, this.idProducto);
            ps.setInt(3, this.cantidad);
            ps.setDouble(4, this.precioUnitario);
            ps.setDouble(5, this.total);
            ps.executeUpdate();
            respuesta = "Venta registrada exitosamente.";
        } catch (Exception e) {
            respuesta = "Error en alta de venta: " + e.getMessage();
        }
    }

    /**
     * Cancela una venta (elimina el registro).
     */
    public void cancelar() {
        try (Connection cn = Conexion.conectar()) {
            String sql = "DELETE FROM Ventas WHERE id_venta = ?";
            PreparedStatement ps = cn.prepareStatement(sql);
            ps.setInt(1, this.idVenta);
            int filasAfectadas = ps.executeUpdate();
            if (filasAfectadas > 0) {
                respuesta = "Venta cancelada exitosamente.";
            } else {
                respuesta = "No se encontró la venta con ID: " + this.idVenta;
            }
        } catch (Exception e) {
            respuesta = "Error al cancelar venta: " + e.getMessage();
        }
    }
    
    /**
     * Consulta una Venta por ID.
     */
    public void consulta() {
        try (Connection cn = Conexion.conectar()) {
            String sql = "SELECT * FROM Ventas WHERE id_venta = ?";
            PreparedStatement ps = cn.prepareStatement(sql);
            ps.setInt(1, this.idVenta);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                this.idCliente = rs.getInt("id_cliente");
                this.idProducto = rs.getInt("id_producto");
                this.cantidad = rs.getInt("cantidad");
                this.precioUnitario = rs.getDouble("precio_unitario");
                this.total = rs.getDouble("total");
                this.fechaVenta = rs.getString("fecha_venta");
                
                respuesta = "<h2>Venta encontrada</h2>" +
                           "<b>ID Venta:</b> " + this.idVenta + "<br>" +
                           "<b>ID Cliente:</b> " + this.idCliente + "<br>" +
                           "<b>ID Producto:</b> " + this.idProducto + "<br>" +
                           "<b>Cantidad:</b> " + this.cantidad + "<br>" +
                           "<b>Precio Unitario:</b> $" + this.precioUnitario + "<br>" +
                           "<b>Total:</b> $" + this.total + "<br>" +
                           "<b>Fecha:</b> " + this.fechaVenta;
            } else {
                respuesta = "No se encontró la venta con ID: " + this.idVenta;
            }
        } catch (Exception e) {
            respuesta = "Error en consulta: " + e.getMessage();
        }
    }

    /**
     * Consulta una Venta por ID para modificar.
     */
    public void consultaParaModificar() {
        try (Connection cn = Conexion.conectar()) {
            String sql = "SELECT * FROM Ventas WHERE id_venta = ?";
            PreparedStatement ps = cn.prepareStatement(sql);
            ps.setInt(1, this.idVenta);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                this.idCliente = rs.getInt("id_cliente");
                this.idProducto = rs.getInt("id_producto");
                this.cantidad = rs.getInt("cantidad");
                this.precioUnitario = rs.getDouble("precio_unitario");
                this.total = rs.getDouble("total");
                
                respuesta = "<h1>Modificar Venta (Paso 2: Actualizar)</h1>";
                respuesta += "<form action='VentaControl' method='post'>";
                respuesta += "<b>ID Venta: " + this.idVenta + "</b><br>";
                respuesta += "<input type='hidden' name='id_venta' value='" + this.idVenta + "'>";
                respuesta += "ID Cliente: <input type='number' name='id_cliente' value='" + this.idCliente + "'><br>";
                respuesta += "ID Producto: <input type='number' name='id_producto' value='" + this.idProducto + "'><br>";
                respuesta += "Cantidad: <input type='number' name='cantidad' value='" + this.cantidad + "'><br>";
                respuesta += "Precio Unitario: <input type='number' name='precio_unitario' value='" + this.precioUnitario + "'><br><br>";
                respuesta += "<input type='submit' name='boton' value='Modificar Venta'>";
                respuesta += "</form>";
            } else {
                respuesta = "No se encontró la venta con ID: " + this.idVenta;
            }
        } catch (Exception e) {
            respuesta = "Error en consulta para modificar: " + e.getMessage();
        }
    }
    
    /**
     * Modifica los datos de una Venta existente.
     */
    public void modifica() {
        try (Connection cn = Conexion.conectar()) {
            this.total = this.cantidad * this.precioUnitario;
            
            String sql = "UPDATE Ventas SET id_cliente = ?, id_producto = ?, cantidad = ?, precio_unitario = ?, total = ? WHERE id_venta = ?";
            PreparedStatement ps = cn.prepareStatement(sql);
            ps.setInt(1, this.idCliente);
            ps.setInt(2, this.idProducto);
            ps.setInt(3, this.cantidad);
            ps.setDouble(4, this.precioUnitario);
            ps.setDouble(5, this.total);
            ps.setInt(6, this.idVenta);
            int filas = ps.executeUpdate();
            if (filas > 0) {
                respuesta = "Venta modificada exitosamente";
            } else {
                respuesta = "No se encontró la venta para modificar.";
            }
        } catch (Exception e) {
            respuesta = "Error en modificación: " + e.getMessage();
        }
    }

    /**
     * LEFT JOIN: Ventas con Clientes
     * Muestra todas las ventas con el nombre del cliente
     */
    public void consultaVentasConClientes() {
        try (Connection cn = Conexion.conectar()) {
            String sql = "SELECT v.id_venta, v.cantidad, v.precio_unitario, v.total, v.fecha_venta, " +
                        "c.nombre, c.rfc " +
                        "FROM Ventas v " +
                        "LEFT JOIN Clientes c ON v.id_cliente = c.id_cliente";
            
            PreparedStatement ps = cn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            
            respuesta = "<h2>Ventas con Clientes (LEFT JOIN)</h2>";
            respuesta += "<table border='1' cellpadding='5'>";
            respuesta += "<tr><th>ID Venta</th><th>Cliente</th><th>RFC</th><th>Cantidad</th><th>Precio Unit.</th><th>Total</th><th>Fecha</th></tr>";
            
            while (rs.next()) {
                respuesta += "<tr>";
                respuesta += "<td>" + rs.getInt("id_venta") + "</td>";
                respuesta += "<td>" + rs.getString("nombre") + "</td>";
                respuesta += "<td>" + rs.getString("rfc") + "</td>";
                respuesta += "<td>" + rs.getInt("cantidad") + "</td>";
                respuesta += "<td>$" + rs.getDouble("precio_unitario") + "</td>";
                respuesta += "<td>$" + rs.getDouble("total") + "</td>";
                respuesta += "<td>" + rs.getString("fecha_venta") + "</td>";
                respuesta += "</tr>";
            }
            respuesta += "</table>";
            
        } catch (Exception e) {
            respuesta = "Error en consulta LEFT JOIN: " + e.getMessage();
        }
    }
    
    /**
     * RIGHT JOIN: Productos con Ventas
     * Muestra todos los productos, hayan sido vendidos o no
     */
    public void consultaProductosConVentas() {
        try (Connection cn = Conexion.conectar()) {
            String sql = "SELECT p.clave_producto, p.nombre_producto, p.precio, " +
                        "v.id_venta, v.cantidad, v.total " +
                        "FROM Ventas v " +
                        "RIGHT JOIN Productos p ON v.id_producto = p.id_producto";
            
            PreparedStatement ps = cn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            
            respuesta = "<h2>Productos con Ventas (RIGHT JOIN)</h2>";
            respuesta += "<table border='1' cellpadding='5'>";
            respuesta += "<tr><th>Clave</th><th>Producto</th><th>Precio</th><th>ID Venta</th><th>Cantidad</th><th>Total</th></tr>";
            
            while (rs.next()) {
                respuesta += "<tr>";
                respuesta += "<td>" + rs.getString("clave_producto") + "</td>";
                respuesta += "<td>" + rs.getString("nombre_producto") + "</td>";
                respuesta += "<td>$" + rs.getDouble("precio") + "</td>";
                respuesta += "<td>" + (rs.getObject("id_venta") != null ? rs.getInt("id_venta") : "SIN VENTA") + "</td>";
                respuesta += "<td>" + (rs.getObject("cantidad") != null ? rs.getInt("cantidad") : "-") + "</td>";
                respuesta += "<td>" + (rs.getObject("total") != null ? "$" + rs.getDouble("total") : "-") + "</td>";
                respuesta += "</tr>";
            }
            respuesta += "</table>";
            
        } catch (Exception e) {
            respuesta = "Error en consulta RIGHT JOIN: " + e.getMessage();
        }
    }

    /**
     * FULL JOIN: Clientes con Ventas (completo)
     * Muestra todos los clientes y todas las ventas
     */
    public void consultaClientesVentasCompleto() {
        try (Connection cn = Conexion.conectar()) {
            String sql = "SELECT c.rfc, c.nombre, c.tipo_cliente, " +
                        "v.id_venta, v.cantidad, v.total, v.fecha_venta " +
                        "FROM Clientes c " +
                        "FULL JOIN Ventas v ON c.id_cliente = v.id_cliente";
            
            PreparedStatement ps = cn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            
            respuesta = "<h2>Clientes y Ventas Completo (FULL JOIN)</h2>";
            respuesta += "<table border='1' cellpadding='5'>";
            respuesta += "<tr><th>RFC</th><th>Cliente</th><th>Tipo</th><th>ID Venta</th><th>Cantidad</th><th>Total</th><th>Fecha</th></tr>";
            
            while (rs.next()) {
                respuesta += "<tr>";
                respuesta += "<td>" + (rs.getString("rfc") != null ? rs.getString("rfc") : "-") + "</td>";
                respuesta += "<td>" + (rs.getString("nombre") != null ? rs.getString("nombre") : "-") + "</td>";
                respuesta += "<td>" + (rs.getString("tipo_cliente") != null ? rs.getString("tipo_cliente") : "-") + "</td>";
                respuesta += "<td>" + (rs.getObject("id_venta") != null ? rs.getInt("id_venta") : "SIN VENTA") + "</td>";
                respuesta += "<td>" + (rs.getObject("cantidad") != null ? rs.getInt("cantidad") : "-") + "</td>";
                respuesta += "<td>" + (rs.getObject("total") != null ? "$" + rs.getDouble("total") : "-") + "</td>";
                respuesta += "<td>" + (rs.getString("fecha_venta") != null ? rs.getString("fecha_venta") : "-") + "</td>";
                respuesta += "</tr>";
            }
            respuesta += "</table>";
            
        } catch (Exception e) {
            respuesta = "Error en consulta FULL JOIN: " + e.getMessage();
        }
    }
}
