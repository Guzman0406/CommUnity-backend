// src/main/java/org/example/controller/NivelUrgenciaController.java
package org.example.controller;

import io.javalin.http.Context;
import io.javalin.http.HttpStatus;
import org.example.model.NivelUrgencia;
import org.example.service.NivelUrgenciaService;
import java.util.List;
import java.util.Optional;

/**
 * Controlador para manejar las solicitudes HTTP de la entidad 'NivelUrgencia'.
 */
public class NivelUrgenciaController {
    private final NivelUrgenciaService nivelUrgenciaService;

    public NivelUrgenciaController(NivelUrgenciaService nivelUrgenciaService) {
        this.nivelUrgenciaService = nivelUrgenciaService;
    }

    /**
     * GET /niveles-urgencia/{id} - Obtiene un nivelUrgencia por su ID.
     */
    public void getNivelUrgenciaById(Context ctx) {
        try {
            int id = ctx.pathParamAsClass("id", Integer.class).get();
            Optional<NivelUrgencia> nivelUrgencia = nivelUrgenciaService.getNivelUrgenciaById(id);
            if (nivelUrgencia.isPresent()) {
                ctx.json(nivelUrgencia.get());
            } else {
                ctx.status(HttpStatus.NOT_FOUND).result("NivelUrgencia no encontrado."); // 404 Not Found
            }
        } catch (Exception e) {
            ctx.status(HttpStatus.BAD_REQUEST).result("ID de nivelUrgencia inválido."); // 400 Bad Request
            System.err.println("Error al obtener nivelUrgencia por ID: " + e.getMessage());
        }
    }

    /**
     * GET /niveles-urgencia - Obtiene todos los nivelurgencias.
     */
    public void getAllNivelUrgencias(Context ctx) {
        List<NivelUrgencia> nivelurgencias = nivelUrgenciaService.getAllNivelUrgencias();
        ctx.json(nivelurgencias);
    }

}
