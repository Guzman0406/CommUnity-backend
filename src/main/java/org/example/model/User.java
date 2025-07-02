// src/main/java/org/example/model/User.java
package org.example.model;

/**
 * Modelo para la tabla 'usuarios'.
 */
public class User {
    private int idUsuario;
    private String nombre;
    private String password; // En una app real, esto sería un hash
    private String apellidoPaterno;
    private String apellidoMaterno;
    private String telefono;
    private boolean esAdmin;

    // Constructor completo
    public User(int idUsuario, String nombre, String password, String apellidoPaterno, String apellidoMaterno, String telefono, boolean esAdmin) {
        this.idUsuario = idUsuario;
        this.nombre = nombre;
        this.password = password;
        this.apellidoPaterno = apellidoPaterno;
        this.apellidoMaterno = apellidoMaterno;
        this.telefono = telefono;
        this.esAdmin = esAdmin;
    }

    // Constructor para crear (sin ID, ya que es AUTO_INCREMENT)
    public User(String nombre, String password, String apellidoPaterno, String apellidoMaterno, String telefono, boolean esAdmin) {
        this.nombre = nombre;
        this.password = password;
        this.apellidoPaterno = apellidoPaterno;
        this.apellidoMaterno = apellidoMaterno;
        this.telefono = telefono;
        this.esAdmin = esAdmin;
    }

    // Getters
    public int getIdUsuario() { return idUsuario; }
    public String getNombre() { return nombre; }
    public String getPassword() { return password; }
    public String getApellidoPaterno() { return apellidoPaterno; }
    public String getApellidoMaterno() { return apellidoMaterno; }
    public String getTelefono() { return telefono; }
    public boolean isEsAdmin() { return esAdmin; }

    // Setters (necesarios para Jackson al deserializar JSON)
    public void setIdUsuario(int idUsuario) { this.idUsuario = idUsuario; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public void setPassword(String password) { this.password = password; }
    public void setApellidoPaterno(String apellidoPaterno) { this.apellidoPaterno = apellidoPaterno; }
    public void setApellidoMaterno(String apellidoMaterno) { this.apellidoMaterno = apellidoMaterno; }
    public void setTelefono(String telefono) { this.telefono = telefono; }
    public void setEsAdmin(boolean esAdmin) { this.esAdmin = esAdmin; }

    @Override
    public String toString() {
        return "User{" +
                "idUsuario=" + idUsuario +
                ", nombre='" + nombre + '\'' +
                ", apellidoPaterno='" + apellidoPaterno + '\'' +
                ", telefono='" + telefono + '\'' +
                ", esAdmin=" + esAdmin +
                '}';
    }
}