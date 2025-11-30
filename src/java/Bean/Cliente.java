/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Bean;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class Cliente {
    private int idCliente;
    private String rfc;
    private String nombre;
    private String direccion;
    private String telefono;
    private String correoElectronico;
    private String celular;
    private String tipoCliente;
    
    // Atributo para la respuesta
    private String respuesta;
    
    // Getters y Setters
    public int getIdCliente() { return idCliente; }
    public void setIdCliente(int idCliente) { this.idCliente = idCliente; }
    
    public String getRfc() { return rfc; }
    public void setRfc(String rfc) { this.rfc = rfc; }
    
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    
    public String getDireccion() { return direccion; }
    public void setDireccion(String direccion) { this.direccion = direccion; }
    
    public String getTelefono() { return telefono; }
    public void setTelefono(String telefono) { this.telefono = telefono; }
    
    public String getCorreoElectronico() { return correoElectronico; }
    public void setCorreoElectronico(String correoElectronico) { this.correoElectronico = correoElectronico; }
    
    public String getCelular() { return celular; }
    public void setCelular(String celular) { this.celular = celular; }
    
    public String getTipoCliente() { return tipoCliente; }
    public void setTipoCliente(String tipoCliente) { this.tipoCliente = tipoCliente; }
    
    public String getRespuesta() { return respuesta; }
    public void setRespuesta(String respuesta) { this.respuesta = respuesta; }
    
    /**
     * Da de alta un nuevo Cliente.
     */
    public void alta() {
        try (Connection cn = Conexion.conectar()) {
            String sql = "INSERT INTO Clientes (rfc, nombre, direccion, telefono, correo_electronico, celular, tipo_cliente) VALUES (?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement ps = cn.prepareStatement(sql);
            ps.setString(1, this.rfc);
            ps.setString(2, this.nombre);
            ps.setString(3, this.direccion);
            ps.setString(4, this.telefono);
            ps.setString(5, this.correoElectronico);
            ps.setString(6, this.celular);
            ps.setString(7, this.tipoCliente);
            ps.executeUpdate();
            respuesta = "Cliente registrado exitosamente.";
        } catch (Exception e) {
            respuesta = "Error en alta: " + e.getMessage();
        }
    }
    /**
     * Da de baja lógica a un Cliente (marca como inactivo sin eliminar).
     */
    public void bajaLogica() {
        try (Connection cn = Conexion.conectar()) {
            String sql = "UPDATE Clientes SET activo = 0 WHERE rfc = ?";
            PreparedStatement ps = cn.prepareStatement(sql);
            ps.setString(1, this.rfc);
            int filasAfectadas = ps.executeUpdate();
            if (filasAfectadas > 0) {
                respuesta = "Cliente dado de baja exitosamente.";
            } else {
                respuesta = "No se encontró el cliente con RFC: " + this.rfc;
            }
        } catch (Exception e) {
            respuesta = "Error en baja: " + e.getMessage();
        }
    }
    
    /**
     * Consulta un Cliente por RFC.
     */
    public void consulta() {
        try (Connection cn = Conexion.conectar()) {
            String sql = "SELECT * FROM Clientes WHERE rfc = ?";
            PreparedStatement ps = cn.prepareStatement(sql);
            ps.setString(1, this.rfc);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                this.idCliente = rs.getInt("id_cliente");
                this.nombre = rs.getString("nombre");
                this.direccion = rs.getString("direccion");
                this.telefono = rs.getString("telefono");
                this.correoElectronico = rs.getString("correo_electronico");
                this.celular = rs.getString("celular");
                this.tipoCliente = rs.getString("tipo_cliente");
                respuesta = "<h2>Cliente encontrado</h2>" +
                           "<b>RFC:</b> " + this.rfc + "<br>" +
                           "<b>Nombre:</b> " + this.nombre + "<br>" +
                           "<b>Dirección:</b> " + this.direccion + "<br>" +
                           "<b>Teléfono:</b> " + this.telefono + "<br>" +
                           "<b>Celular:</b> " + this.celular + "<br>" +
                           "<b>Email:</b> " + this.correoElectronico + "<br>" +
                           "<b>Tipo de Cliente:</b> " + this.tipoCliente;
            } else {
                respuesta = "No se encontró el cliente con RFC: " + this.rfc;
            }
        } catch (Exception e) {
            respuesta = "Error en consulta: " + e.getMessage();
        }
    }
    
    /**
     * Consulta un Cliente por RFC para modificar (genera formulario con datos).
     */
    public void consultaParaModificar() {
        try (Connection cn = Conexion.conectar()) {
            String sql = "SELECT * FROM Clientes WHERE rfc = ?";
            PreparedStatement ps = cn.prepareStatement(sql);
            ps.setString(1, this.rfc);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                this.idCliente = rs.getInt("id_cliente");
                this.nombre = rs.getString("nombre");
                this.direccion = rs.getString("direccion");
                this.telefono = rs.getString("telefono");
                this.correoElectronico = rs.getString("correo_electronico");
                this.celular = rs.getString("celular");
                this.tipoCliente = rs.getString("tipo_cliente");
                
                respuesta = "<h1>Modificar Cliente (Paso 2: Actualizar)</h1>";
                respuesta += "<form action='ClienteControl' method='post'>";
                respuesta += "<b>RFC: " + this.rfc + "</b><br>";
                respuesta += "<input type='hidden' name='rfc' value='" + this.rfc + "'>";
                respuesta += "Nombre: <input type='text' name='nombre' value='" + this.nombre + "'><br>";
                respuesta += "Dirección: <input type='text' name='direccion' value='" + this.direccion + "'><br>";
                respuesta += "Teléfono: <input type='text' name='telefono' value='" + this.telefono + "'><br>";
                respuesta += "Celular: <input type='text' name='celular' value='" + this.celular + "'><br>";
                respuesta += "Email: <input type='text' name='correo' value='" + this.correoElectronico + "'><br>";
                respuesta += "Tipo de cliente: <select name='tipo'>";
                respuesta += "<option value='Regular' " + (this.tipoCliente.equals("Regular") ? "selected" : "") + ">Regular</option>";
                respuesta += "<option value='Premium' " + (this.tipoCliente.equals("Premium") ? "selected" : "") + ">Premium</option>";
                respuesta += "<option value='VIP' " + (this.tipoCliente.equals("VIP") ? "selected" : "") + ">VIP</option>";
                respuesta += "</select><br><br>";
                respuesta += "<input type='submit' name='boton' value='Modificar Cliente'>";
                respuesta += "</form>";
            } else {
                respuesta = "No se encontró el cliente con RFC: '" + this.rfc + "'.";
            }
        } catch (Exception e) {
            respuesta = "Error en consulta para modificar cliente: " + e.getMessage();
            e.printStackTrace();
        }
    }
    
    /**
     * Modifica los datos de un Cliente existente.
     */
    public void modifica() {
        try (Connection cn = Conexion.conectar()) {
            String sql = "UPDATE Clientes SET nombre = ?, direccion = ?, telefono = ?, correo_electronico = ?, celular = ?, tipo_cliente = ? WHERE rfc = ?";
            PreparedStatement ps = cn.prepareStatement(sql);
            ps.setString(1, this.nombre);
            ps.setString(2, this.direccion);
            ps.setString(3, this.telefono);
            ps.setString(4, this.correoElectronico);
            ps.setString(5, this.celular);
            ps.setString(6, this.tipoCliente);
            ps.setString(7, this.rfc);
            int filas = ps.executeUpdate();
            if (filas > 0) {
                respuesta = "Datos del cliente modificados exitosamente";
            } else {
                respuesta = "No se encontró el RFC para modificar.";
            }
        } catch (Exception e) {
            respuesta = "Error en modificación: " + e.getMessage();
            e.printStackTrace();
        }
    }
}

