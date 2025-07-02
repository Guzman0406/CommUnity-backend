// src/main/java/org/example/model/MensajeAdmin.java
package org.example.model;

import java.time.LocalDateTime;

/**
 * Modelo para la tabla 'mensajes_admin'.
 */
public class MensajeAdmin {
    private int idMensaje;
    private int idAdmin; // FK
    private String titulo;
    private String contenido;
    private LocalDateTime fechaMensaje;

    // Constructor completo
    public MensajeAdmin(int idMensaje, int idAdmin, String titulo, String contenido, LocalDateTime fechaMensaje) {
        this.idMensaje = idMensaje;
        this.idAdmin = idAdmin;
        this.titulo = titulo;
        this.contenido = contenido;
        this.fechaMensaje = fechaMensaje;
    }

    // Constructor para crear (sin ID, fecha se maneja en DB)
    public MensajeAdmin(int idAdmin, String titulo, String contenido) {
        this.idAdmin = idAdmin;
        this.titulo = titulo;
        this.contenido = contenido;
        this.fechaMensaje = null; // Se establecerá en la base de datos
    }

    // Getters
    public int getIdMensaje() { return idMensaje; }
    public int getIdAdmin() { return idAdmin; }
    public String getTitulo() { return titulo; }
    public String getContenido() { return contenido; }
    public LocalDateTime getFechaMensaje() { return fechaMensaje; }

    // Setters
    public void setIdMensaje(int idMensaje) { this.idMensaje = idMensaje; }
    public void setIdAdmin(int idAdmin) { this.idAdmin = idAdmin; }
    public void setTitulo(String titulo) { this.titulo = titulo; }
    public void setContenido(String contenido) { this.contenido = contenido; }
    public void setFechaMensaje(LocalDateTime fechaMensaje) { this.fechaMensaje = fechaMensaje; }
}