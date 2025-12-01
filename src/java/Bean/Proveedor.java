/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Bean;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class Proveedor {
    private int idProveedor;
    private String numeroIdentificacion;
    private String nombre;
    private String nombreContacto;
    private String direccion;
    private String telefono;
    private String correoElectronico;
    private String celular;
    
    // Atributo para la respuesta
    private String respuesta;
    
    // Getters y Setters
    public int getIdProveedor() { return idProveedor; }
    public void setIdProveedor(int idProveedor) { this.idProveedor = idProveedor; }
    
    public String getNumeroIdentificacion() { return numeroIdentificacion; }
    public void setNumeroIdentificacion(String numeroIdentificacion) { this.numeroIdentificacion = numeroIdentificacion; }
    
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    
    public String getNombreContacto() { return nombreContacto; }
    public void setNombreContacto(String nombreContacto) { this.nombreContacto = nombreContacto; }
    
    public String getDireccion() { return direccion; }
    public void setDireccion(String direccion) { this.direccion = direccion; }
    
    public String getTelefono() { return telefono; }
    public void setTelefono(String telefono) { this.telefono = telefono; }
    
    public String getCorreoElectronico() { return correoElectronico; }
    public void setCorreoElectronico(String correoElectronico) { this.correoElectronico = correoElectronico; }
    
    public String getCelular() { return celular; }
    public void setCelular(String celular) { this.celular = celular; }
    
    public String getRespuesta() { return respuesta; }
    public void setRespuesta(String respuesta) { this.respuesta = respuesta; }
    
    /**
     * Da de alta un nuevo Proveedor.
     */
    public void alta() {
        try (Connection cn = Conexion.conectar()) {
            String sql = "INSERT INTO Proveedores (numero_identificacion, nombre, nombre_contacto, direccion, telefono, correo_electronico, celular) VALUES (?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement ps = cn.prepareStatement(sql);
            ps.setString(1, this.numeroIdentificacion);
            ps.setString(2, this.nombre);
            ps.setString(3, this.nombreContacto);
            ps.setString(4, this.direccion);
            ps.setString(5, this.telefono);
            ps.setString(6, this.correoElectronico);
            ps.setString(7, this.celular);
            ps.executeUpdate();
            respuesta = "Proveedor registrado exitosamente.";
        } catch (Exception e) {
            respuesta = "Error en alta: " + e.getMessage();
        }
    }
    
    public void bajaLogica() {
    try (Connection cn = Conexion.conectar()) {
        String sql = "UPDATE Proveedores SET activo = 0 WHERE numero_identificacion = ?";
        PreparedStatement ps = cn.prepareStatement(sql);
        ps.setString(1, this.numeroIdentificacion);
        int filasAfectadas = ps.executeUpdate();
        if (filasAfectadas > 0) {
            respuesta = "Proveedor dado de baja exitosamente.";
        } else {
            respuesta = "No se encontró el proveedor con número de identificación: " + this.numeroIdentificacion;
        }
    } catch (Exception e) {
        respuesta = "Error en baja: " + e.getMessage();
    }
    }
    
    public void consulta() {
    try (Connection cn = Conexion.conectar()) {
        String sql = "SELECT * FROM Proveedores WHERE numero_identificacion = ? AND activo = 1";
        PreparedStatement ps = cn.prepareStatement(sql);
        ps.setString(1, this.numeroIdentificacion);
        ResultSet rs = ps.executeQuery();
        if (rs.next()) {
            this.idProveedor = rs.getInt("id_proveedor");
            this.nombre = rs.getString("nombre");
            this.nombreContacto = rs.getString("nombre_contacto");
            this.direccion = rs.getString("direccion");
            this.telefono = rs.getString("telefono");
            this.correoElectronico = rs.getString("correo_electronico");
            this.celular = rs.getString("celular");
            respuesta = "<h2>Proveedor encontrado</h2>" +
                       "<b>Número de Identificación:</b> " + this.numeroIdentificacion + "<br>" +
                       "<b>Nombre:</b> " + this.nombre + "<br>" +
                       "<b>Nombre de Contacto:</b> " + this.nombreContacto + "<br>" +
                       "<b>Dirección:</b> " + this.direccion + "<br>" +
                       "<b>Teléfono:</b> " + this.telefono + "<br>" +
                       "<b>Celular:</b> " + this.celular + "<br>" +
                       "<b>Email:</b> " + this.correoElectronico;
        } else {
            respuesta = "No se encontró el proveedor con número de identificación: " + this.numeroIdentificacion + " o está dado de baja.";
        }
    } catch (Exception e) {
        respuesta = "Error en consulta: " + e.getMessage();
    }
}
    
    /**
     * Consulta un Proveedor por número de identificación para modificar (genera formulario con datos).
     */
    public void consultaParaModificar() {
        try (Connection cn = Conexion.conectar()) {
            String sql = "SELECT * FROM Proveedores WHERE numero_identificacion = ? AND activo = 1";
            PreparedStatement ps = cn.prepareStatement(sql);
            ps.setString(1, this.numeroIdentificacion);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                this.idProveedor = rs.getInt("id_proveedor");
                this.nombre = rs.getString("nombre");
                this.nombreContacto = rs.getString("nombre_contacto");
                this.direccion = rs.getString("direccion");
                this.telefono = rs.getString("telefono");
                this.correoElectronico = rs.getString("correo_electronico");
                this.celular = rs.getString("celular");
                
                respuesta = "<h1>Modificar Proveedor (Paso 2: Actualizar)</h1>";
                respuesta += "<form action='ProveedorControl' method='post'>";
                respuesta += "<b>Número de Identificación: " + this.numeroIdentificacion + "</b><br>";
                respuesta += "<input type='hidden' name='numero_identificacion' value='" + this.numeroIdentificacion + "'>";
                respuesta += "Nombre: <input type='text' name='nombre' value='" + this.nombre + "'><br>";
                respuesta += "Nombre de Contacto: <input type='text' name='nombre_contacto' value='" + this.nombreContacto + "'><br>";
                respuesta += "Dirección: <input type='text' name='direccion' value='" + this.direccion + "'><br>";
                respuesta += "Teléfono: <input type='text' name='telefono' value='" + this.telefono + "'><br>";
                respuesta += "Celular: <input type='text' name='celular' value='" + this.celular + "'><br>";
                respuesta += "Email: <input type='text' name='correo' value='" + this.correoElectronico + "'><br><br>";
                respuesta += "<input type='submit' name='boton' value='Modificar Proveedor'>";
                respuesta += "</form>";
            } else {
                respuesta = "No se encontró el proveedor con número de identificación: '" + this.numeroIdentificacion + "' o está dado de baja.";
            }
        } catch (Exception e) {
            respuesta = "Error en consulta para modificar proveedor: " + e.getMessage();
            e.printStackTrace();
        }
    }
    
    /**
     * Modifica los datos de un Proveedor existente.
     */
    public void modifica() {
        try (Connection cn = Conexion.conectar()) {
            String sql = "UPDATE Proveedores SET nombre = ?, nombre_contacto = ?, direccion = ?, telefono = ?, correo_electronico = ?, celular = ? WHERE numero_identificacion = ?";
            PreparedStatement ps = cn.prepareStatement(sql);
            ps.setString(1, this.nombre);
            ps.setString(2, this.nombreContacto);
            ps.setString(3, this.direccion);
            ps.setString(4, this.telefono);
            ps.setString(5, this.correoElectronico);
            ps.setString(6, this.celular);
            ps.setString(7, this.numeroIdentificacion);
            int filas = ps.executeUpdate();
            if (filas > 0) {
                respuesta = "Datos del proveedor modificados exitosamente";
            } else {
                respuesta = "No se encontró el número de identificación para modificar.";
            }
        } catch (Exception e) {
            respuesta = "Error en modificación: " + e.getMessage();
            e.printStackTrace();
        }
    }
}
