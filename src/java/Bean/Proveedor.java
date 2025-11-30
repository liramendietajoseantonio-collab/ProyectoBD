/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Bean;

import java.sql.Connection;
import java.sql.PreparedStatement;

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
}
