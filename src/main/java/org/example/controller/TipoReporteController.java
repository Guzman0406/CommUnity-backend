// src/main/java/org/example/controller/TipoReporteController.java
package org.example.controller;

import io.javalin.http.Context;
import io.javalin.http.HttpStatus;
import org.example.model.TipoReporte;
import org.example.service.TipoReporteService;
import java.util.List;
import java.util.Optional;

/**
 * Controlador para manejar las solicitudes HTTP de la entidad 'TipoReporte'.
 */
public class TipoReporteController {
    private final TipoReporteService tipoReporteService;

    public TipoReporteController(TipoReporteService tipoReporteService) {
        this.tipoReporteService = tipoReporteService;
    }

    /**
     * GET /tipos-reporte/{id} - Obtiene un tipoReporte por su ID.
     */
    public void getTipoReporteById(Context ctx) {
        try {
            int id = ctx.pathParamAsClass("id", Integer.class).get();
            Optional<TipoReporte> tipoReporte = tipoReporteService.getTipoReporteById(id);
            if (tipoReporte.isPresent()) {
                ctx.json(tipoReporte.get());
            } else {
                ctx.status(HttpStatus.NOT_FOUND).result("TipoReporte no encontrado."); // 404 Not Found
            }
        } catch (Exception e) {
            ctx.status(HttpStatus.BAD_REQUEST).result("ID de tipoReporte inválido."); // 400 Bad Request
            System.err.println("Error al obtener tipoReporte por ID: " + e.getMessage());
        }
    }

    /**
     * GET /tipos-reporte - Obtiene todos los tiporeportes.
     */
    public void getAllTipoReportes(Context ctx) {
        List<TipoReporte> tiporeportes = tipoReporteService.getAllTipoReportes();
        ctx.json(tiporeportes);
    }

}
