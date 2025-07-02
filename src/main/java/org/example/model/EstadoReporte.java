// src/main/java/org/example/model/EstadoReporte.java
package org.example.model;

/**
 * Modelo para la tabla 'estados_reporte'.
 */
public class EstadoReporte {
    private int idEstado;
    private String nombreEstado;

    public EstadoReporte(int idEstado, String nombreEstado) {
        this.idEstado = idEstado;
        this.nombreEstado = nombreEstado;
    }

    public EstadoReporte(String nombreEstado) {
        this.nombreEstado = nombreEstado;
    }

    // Getters
    public int getIdEstado() { return idEstado; }
    public String getNombreEstado() { return nombreEstado; }

    // Setters
    public void setIdEstado(int idEstado) { this.idEstado = idEstado; }
    public void setNombreEstado(String nombreEstado) { this.nombreEstado = nombreEstado; }
}