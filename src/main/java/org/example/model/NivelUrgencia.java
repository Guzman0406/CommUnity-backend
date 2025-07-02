// src/main/java/org/example/model/NivelUrgencia.java
package org.example.model;

/**
 * Modelo para la tabla 'niveles_urgencia'.
 */
public class NivelUrgencia {
    private int idNivel;
    private String nombreNivel;

    public NivelUrgencia(int idNivel, String nombreNivel) {
        this.idNivel = idNivel;
        this.nombreNivel = nombreNivel;
    }

    public NivelUrgencia(String nombreNivel) {
        this.nombreNivel = nombreNivel;
    }

    // Getters
    public int getIdNivel() { return idNivel; }
    public String getNombreNivel() { return nombreNivel; }

    // Setters
    public void setIdNivel(int idNivel) { this.idNivel = idNivel; }
    public void setNombreNivel(String nombreNivel) { this.nombreNivel = nombreNivel; }
}