// src/main/java/org/example/model/Calle.java
package org.example.model;

/**
 * Modelo para la tabla 'calles'.
 */
public class Calle {
    private int idCalle;
    private String nombreCalle;

    public Calle(int idCalle, String nombreCalle) {
        this.idCalle = idCalle;
        this.nombreCalle = nombreCalle;
    }

    public Calle(String nombreCalle) {
        this.nombreCalle = nombreCalle;
    }

    // Getters
    public int getIdCalle() { return idCalle; }
    public String getNombreCalle() { return nombreCalle; }

    // Setters
    public void setIdCalle(int idCalle) { this.idCalle = idCalle; }
    public void setNombreCalle(String nombreCalle) { this.nombreCalle = nombreCalle; }
}