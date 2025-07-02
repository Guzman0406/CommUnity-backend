// src/main/java/org/example/model/Seccion.java
package org.example.model;

/**
 * Modelo para la tabla 'secciones'.
 */
public class Seccion {
    private int idSeccion;
    private String nombreSeccion;

    public Seccion(int idSeccion, String nombreSeccion) {
        this.idSeccion = idSeccion;
        this.nombreSeccion = nombreSeccion;
    }

    public Seccion(String nombreSeccion) {
        this.nombreSeccion = nombreSeccion;
    }

    // Getters
    public int getIdSeccion() { return idSeccion; }
    public String getNombreSeccion() { return nombreSeccion; }

    // Setters
    public void setIdSeccion(int idSeccion) { this.idSeccion = idSeccion; }
    public void setNombreSeccion(String nombreSeccion) { this.nombreSeccion = nombreSeccion; }
}