// src/main/java/org/example/model/Reporte.java
package org.example.model;

import java.time.LocalDateTime;

/**
 * Modelo para la tabla 'reportes'.
 */
public class Reporte {
    private int idReporte;
    private int idUsuario; // FK
    private int idTipo; // FK
    private int idSeccion; // FK
    private int idCalle; // FK
    private String referencias;
    private String descripcion;
    private int idEstado; // FK, default 1 (Pendiente)
    private LocalDateTime fechaCreacion;
    private LocalDateTime fechaActualizacion;

    // Constructor completo
    public Reporte(int idReporte, int idUsuario, int idTipo, int idSeccion, int idCalle, String referencias, String descripcion, int idEstado, LocalDateTime fechaCreacion, LocalDateTime fechaActualizacion) {
        this.idReporte = idReporte;
        this.idUsuario = idUsuario;
        this.idTipo = idTipo;
        this.idSeccion = idSeccion;
        this.idCalle = idCalle;
        this.referencias = referencias;
        this.descripcion = descripcion;
        this.idEstado = idEstado;
        this.fechaCreacion = fechaCreacion;
        this.fechaActualizacion = fechaActualizacion;
    }

    // Constructor para crear (sin ID, y fechas se manejan en DB o al insertar)
    public Reporte(int idUsuario, int idTipo, int idSeccion, int idCalle, String referencias, String descripcion, int idEstado) {
        this.idUsuario = idUsuario;
        this.idTipo = idTipo;
        this.idSeccion = idSeccion;
        this.idCalle = idCalle;
        this.referencias = referencias;
        this.descripcion = descripcion;
        this.idEstado = idEstado;
        // Las fechas se establecerán en la base de datos o al recuperar
        this.fechaCreacion = null;
        this.fechaActualizacion = null;
    }

    // Getters
    public int getIdReporte() { return idReporte; }
    public int getIdUsuario() { return idUsuario; }
    public int getIdTipo() { return idTipo; }
    public int getIdSeccion() { return idSeccion; }
    public int getIdCalle() { return idCalle; }
    public String getReferencias() { return referencias; }
    public String getDescripcion() { return descripcion; }
    public int getIdEstado() { return idEstado; }
    public LocalDateTime getFechaCreacion() { return fechaCreacion; }
    public LocalDateTime getFechaActualizacion() { return fechaActualizacion; }

    // Setters (necesarios para Jackson y para actualizaciones)
    public void setIdReporte(int idReporte) { this.idReporte = idReporte; }
    public void setIdUsuario(int idUsuario) { this.idUsuario = idUsuario; }
    public void setIdTipo(int idTipo) { this.idTipo = idTipo; }
    public void setIdSeccion(int idSeccion) { this.idSeccion = idSeccion; }
    public void setIdCalle(int idCalle) { this.idCalle = idCalle; }
    public void setReferencias(String referencias) { this.referencias = referencias; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }
    public void setIdEstado(int idEstado) { this.idEstado = idEstado; }
    public void setFechaCreacion(LocalDateTime fechaCreacion) { this.fechaCreacion = fechaCreacion; }
    public void setFechaActualizacion(LocalDateTime fechaActualizacion) { this.fechaActualizacion = fechaActualizacion; }
}