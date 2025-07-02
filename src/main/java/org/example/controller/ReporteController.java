// src/main/java/org/example/controller/ReporteController.java
package org.example.controller;

import io.javalin.http.Context;
import io.javalin.http.HttpStatus;
import org.example.model.Reporte;
import org.example.service.ReporteService;
import java.util.List;
import java.util.Optional;

/**
 * Controlador para manejar las solicitudes HTTP de la entidad 'Reporte'.
 */
public class ReporteController {
    private final ReporteService reporteService;

    public ReporteController(ReporteService reporteService) {
        this.reporteService = reporteService;
    }

    /**
     * POST /reports - Crea un nuevo reporte.
     * Espera un JSON en el cuerpo de la solicitud que se mapeará a un objeto Reporte.
     */
    public void createReporte(Context ctx) {
        try {
            Reporte newReporte = ctx.bodyAsClass(Reporte.class);
            Optional<Reporte> createdReporte = reporteService.createReporte(newReporte);
            if (createdReporte.isPresent()) {
                ctx.status(HttpStatus.CREATED).json(createdReporte.get()); // 201 Created
            } else {
                ctx.status(HttpStatus.BAD_REQUEST).result("No se pudo crear el reporte. Verifique los datos (ej. IDs de FK válidos)."); // 400 Bad Request
            }
        } catch (Exception e) {
            ctx.status(HttpStatus.BAD_REQUEST).result("Error al procesar la solicitud: " + e.getMessage()); // 400 Bad Request
            System.err.println("Error al crear reporte: " + e.getMessage());
        }
    }

    /**
     * GET /reports/{id} - Obtiene un reporte por su ID.
     */
    public void getReporteById(Context ctx) {
        try {
            int id = ctx.pathParamAsClass("id", Integer.class).get();
            Optional<Reporte> reporte = reporteService.getReporteById(id);
            if (reporte.isPresent()) {
                ctx.json(reporte.get());
            } else {
                ctx.status(HttpStatus.NOT_FOUND).result("Reporte no encontrado."); // 404 Not Found
            }
        } catch (Exception e) {
            ctx.status(HttpStatus.BAD_REQUEST).result("ID de reporte inválido."); // 400 Bad Request
            System.err.println("Error al obtener reporte por ID: " + e.getMessage());
        }
    }

    /**
     * GET /reports - Obtiene todos los reportes.
     */
    public void getAllReportes(Context ctx) {
        List<Reporte> reportes = reporteService.getAllReportes();
        ctx.json(reportes);
    }

    /**
     * PUT /reports/{id} - Actualiza un reporte existente.
     * Espera un JSON en el cuerpo de la solicitud que se mapeará a un objeto Reporte.
     */
    public void updateReporte(Context ctx) {
        try {
            int id = ctx.pathParamAsClass("id", Integer.class).get();
            Reporte reporteToUpdate = ctx.bodyAsClass(Reporte.class);
            reporteToUpdate.setIdReporte(id); // Aseguramos que el ID del objeto coincida con el de la URL

            if (reporteService.updateReporte(reporteToUpdate)) {
                ctx.status(HttpStatus.OK).result("Reporte actualizado exitosamente."); // 200 OK
            } else {
                ctx.status(HttpStatus.NOT_FOUND).result("Reporte no encontrado o no se pudo actualizar."); // 404 Not Found o 304 Not Modified
            }
        } catch (Exception e) {
            ctx.status(HttpStatus.BAD_REQUEST).result("Error al procesar la solicitud de actualización: " + e.getMessage()); // 400 Bad Request
            System.err.println("Error al actualizar reporte: " + e.getMessage());
        }
    }

    /**
     * DELETE /reports/{id} - Elimina un reporte por su ID.
     */
    public void deleteReporte(Context ctx) {
        try {
            int id = ctx.pathParamAsClass("id", Integer.class).get();
            if (reporteService.deleteReporte(id)) {
                ctx.status(HttpStatus.NO_CONTENT); // 204 No Content (éxito sin cuerpo de respuesta)
            } else {
                ctx.status(HttpStatus.NOT_FOUND).result("Reporte no encontrado o no se pudo eliminar."); // 404 Not Found
            }
        } catch (Exception e) {
            ctx.status(HttpStatus.BAD_REQUEST).result("ID de reporte inválido."); // 400 Bad Request
            System.err.println("Error al eliminar reporte: " + e.getMessage());
        }
    }
}
