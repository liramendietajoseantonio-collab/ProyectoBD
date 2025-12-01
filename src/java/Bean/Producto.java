/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Bean;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class Producto {
    private int idProducto;
    private String claveProducto;
    private String nombreProducto;
    private double precio;
    private String caracteristicas;
    private String marca;
    private int idProveedor;
    private int existencia;
    
    // Atributo para la respuesta
    private String respuesta;
    
    // Getters y Setters
    public int getIdProducto() { return idProducto; }
    public void setIdProducto(int idProducto) { this.idProducto = idProducto; }
    
    public String getClaveProducto() { return claveProducto; }
    public void setClaveProducto(String claveProducto) { this.claveProducto = claveProducto; }
    
    public String getNombreProducto() { return nombreProducto; }
    public void setNombreProducto(String nombreProducto) { this.nombreProducto = nombreProducto; }
    
    public double getPrecio() { return precio; }
    public void setPrecio(double precio) { this.precio = precio; }
    
    public String getCaracteristicas() { return caracteristicas; }
    public void setCaracteristicas(String caracteristicas) { this.caracteristicas = caracteristicas; }
    
    public String getMarca() { return marca; }
    public void setMarca(String marca) { this.marca = marca; }
    
    public int getIdProveedor() { return idProveedor; }
    public void setIdProveedor(int idProveedor) { this.idProveedor = idProveedor; }
    
    public int getExistencia() { return existencia; }
    public void setExistencia(int existencia) { this.existencia = existencia; }
    
    public String getRespuesta() { return respuesta; }
    public void setRespuesta(String respuesta) { this.respuesta = respuesta; }
    
    /**
     * Da de alta un nuevo Producto.
     */
    public void alta() {
        try (Connection cn = Conexion.conectar()) {
            String sql = "INSERT INTO Productos (clave_producto, nombre_producto, precio, caracteristicas, marca, id_proveedor, existencia) VALUES (?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement ps = cn.prepareStatement(sql);
            ps.setString(1, this.claveProducto);
            ps.setString(2, this.nombreProducto);
            ps.setDouble(3, this.precio);
            ps.setString(4, this.caracteristicas);
            ps.setString(5, this.marca);
            ps.setInt(6, this.idProveedor);
            ps.setInt(7, this.existencia);
            ps.executeUpdate();
            respuesta = "Producto registrado exitosamente.";
        } catch (Exception e) {
            respuesta = "Error en alta: " + e.getMessage();
        }
    }
    
    /**
     * Da de baja lógica a un Producto (marca como inactivo sin eliminar).
     */
    public void bajaLogica() {
        try (Connection cn = Conexion.conectar()) {
            String sql = "UPDATE Productos SET activo = 0 WHERE clave_producto = ?";
            PreparedStatement ps = cn.prepareStatement(sql);
            ps.setString(1, this.claveProducto);
            int filasAfectadas = ps.executeUpdate();
            if (filasAfectadas > 0) {
                respuesta = "Producto dado de baja exitosamente.";
            } else {
                respuesta = "No se encontró el producto con clave: " + this.claveProducto;
            }
        } catch (Exception e) {
            respuesta = "Error en baja: " + e.getMessage();
        }
    }
    
    /**
     * Consulta un Producto por clave.
     */
    public void consulta() {
        try (Connection cn = Conexion.conectar()) {
            String sql = "SELECT * FROM Productos WHERE clave_producto = ?";
            PreparedStatement ps = cn.prepareStatement(sql);
            ps.setString(1, this.claveProducto);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                this.idProducto = rs.getInt("id_producto");
                this.nombreProducto = rs.getString("nombre_producto");
                this.precio = rs.getDouble("precio");
                this.caracteristicas = rs.getString("caracteristicas");
                this.marca = rs.getString("marca");
                this.idProveedor = rs.getInt("id_proveedor");
                this.existencia = rs.getInt("existencia");
                respuesta = "<h2>Producto encontrado</h2>" +
                           "<b>Clave:</b> " + this.claveProducto + "<br>" +
                           "<b>Nombre:</b> " + this.nombreProducto + "<br>" +
                           "<b>Precio:</b> $" + this.precio + "<br>" +
                           "<b>Características:</b> " + this.caracteristicas + "<br>" +
                           "<b>Marca:</b> " + this.marca + "<br>" +
                           "<b>ID Proveedor:</b> " + this.idProveedor + "<br>" +
                           "<b>Existencia:</b> " + this.existencia;
            } else {
                respuesta = "No se encontró el producto con clave: " + this.claveProducto;
            }
        } catch (Exception e) {
            respuesta = "Error en consulta: " + e.getMessage();
        }
    }
    
    /**
     * Consulta un Producto por clave para modificar (genera formulario con datos).
     */
    public void consultaParaModificar() {
        try (Connection cn = Conexion.conectar()) {
            String sql = "SELECT * FROM Productos WHERE clave_producto = ?";
            PreparedStatement ps = cn.prepareStatement(sql);
            ps.setString(1, this.claveProducto);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                this.idProducto = rs.getInt("id_producto");
                this.nombreProducto = rs.getString("nombre_producto");
                this.precio = rs.getDouble("precio");
                this.caracteristicas = rs.getString("caracteristicas");
                this.marca = rs.getString("marca");
                this.idProveedor = rs.getInt("id_proveedor");
                this.existencia = rs.getInt("existencia");
                
                respuesta = "<h1>Modificar Producto (Paso 2: Actualizar)</h1>";
                respuesta += "<form action='ProductoControl' method='post'>";
                respuesta += "<b>Clave: " + this.claveProducto + "</b><br>";
                respuesta += "<input type='hidden' name='clave_producto' value='" + this.claveProducto + "'>";
                respuesta += "Nombre: <input type='text' name='nombre_producto' value='" + this.nombreProducto + "'><br>";
                respuesta += "Precio: <input type='number' step='0.01' name='precio' value='" + this.precio + "'><br>";
                respuesta += "Características: <textarea name='caracteristicas' rows='3' cols='40'>" + this.caracteristicas + "</textarea><br>";
                respuesta += "Marca: <input type='text' name='marca' value='" + this.marca + "'><br>";
                respuesta += "ID Proveedor: <input type='number' name='id_proveedor' value='" + this.idProveedor + "'><br>";
                respuesta += "Existencia: <input type='number' name='existencia' value='" + this.existencia + "'><br><br>";
                respuesta += "<input type='submit' name='boton' value='Modificar Producto'>";
                respuesta += "</form>";
            } else {
                respuesta = "No se encontró el producto con clave: '" + this.claveProducto + "'.";
            }
        } catch (Exception e) {
            respuesta = "Error en consulta para modificar producto: " + e.getMessage();
            e.printStackTrace();
        }
    }
    
    /**
     * Modifica los datos de un Producto existente.
     */
    public void modifica() {
        try (Connection cn = Conexion.conectar()) {
            String sql = "UPDATE Productos SET nombre_producto = ?, precio = ?, caracteristicas = ?, marca = ?, id_proveedor = ?, existencia = ? WHERE clave_producto = ?";
            PreparedStatement ps = cn.prepareStatement(sql);
            ps.setString(1, this.nombreProducto);
            ps.setDouble(2, this.precio);
            ps.setString(3, this.caracteristicas);
            ps.setString(4, this.marca);
            ps.setInt(5, this.idProveedor);
            ps.setInt(6, this.existencia);
            ps.setString(7, this.claveProducto);
            int filas = ps.executeUpdate();
            if (filas > 0) {
                respuesta = "Datos del producto modificados exitosamente";
            } else {
                respuesta = "No se encontró la clave para modificar.";
            }
        } catch (Exception e) {
            respuesta = "Error en modificación: " + e.getMessage();
            e.printStackTrace();
        }
    }
}
