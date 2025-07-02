// src/main/java/org/example/controller/EstadoReporteController.java
package org.example.controller;

import io.javalin.http.Context;
import io.javalin.http.HttpStatus;
import org.example.model.EstadoReporte;
import org.example.service.EstadoReporteService;
import java.util.List;
import java.util.Optional;

/**
 * Controlador para manejar las solicitudes HTTP de la entidad 'EstadoReporte'.
 */
public class EstadoReporteController {
    private final EstadoReporteService estadoReporteService;

    public EstadoReporteController(EstadoReporteService estadoReporteService) {
        this.estadoReporteService = estadoReporteService;
    }

    /**
     * GET /estados-reporte/{id} - Obtiene un estadoReporte por su ID.
     */
    public void getEstadoReporteById(Context ctx) {
        try {
            int id = ctx.pathParamAsClass("id", Integer.class).get();
            Optional<EstadoReporte> estadoReporte = estadoReporteService.getEstadoReporteById(id);
            if (estadoReporte.isPresent()) {
                ctx.json(estadoReporte.get());
            } else {
                ctx.status(HttpStatus.NOT_FOUND).result("EstadoReporte no encontrado."); // 404 Not Found
            }
        } catch (Exception e) {
            ctx.status(HttpStatus.BAD_REQUEST).result("ID de estadoReporte inválido."); // 400 Bad Request
            System.err.println("Error al obtener estadoReporte por ID: " + e.getMessage());
        }
    }

    /**
     * GET /estados-reporte - Obtiene todos los estadoreportes.
     */
    public void getAllEstadoReportes(Context ctx) {
        List<EstadoReporte> estadoreportes = estadoReporteService.getAllEstadoReportes();
        ctx.json(estadoreportes);
    }

}
