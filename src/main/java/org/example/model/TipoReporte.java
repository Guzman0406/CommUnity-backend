// src/main/java/org/example/model/TipoReporte.java
package org.example.model;

/**
 * Modelo para la tabla 'tipo_reporte'.
 */
public class TipoReporte {
    private int idTipo;
    private String nombreTipo;
    private int idNivelUrgencia; // FK

    public TipoReporte(int idTipo, String nombreTipo, int idNivelUrgencia) {
        this.idTipo = idTipo;
        this.nombreTipo = nombreTipo;
        this.idNivelUrgencia = idNivelUrgencia;
    }

    public TipoReporte(String nombreTipo, int idNivelUrgencia) {
        this.nombreTipo = nombreTipo;
        this.idNivelUrgencia = idNivelUrgencia;
    }

    // Getters
    public int getIdTipo() { return idTipo; }
    public String getNombreTipo() { return nombreTipo; }
    public int getIdNivelUrgencia() { return idNivelUrgencia; }

    // Setters
    public void setIdTipo(int idTipo) { this.idTipo = idTipo; }
    public void setNombreTipo(String nombreTipo) { this.nombreTipo = nombreTipo; }
    public void setIdNivelUrgencia(int idNivelUrgencia) { this.idNivelUrgencia = idNivelUrgencia; }
}