// src/main/java/org/example/controller/CalleController.java
package org.example.controller;

import io.javalin.http.Context;
import io.javalin.http.HttpStatus;
import org.example.model.Calle;
import org.example.service.CalleService;
import java.util.List;
import java.util.Optional;

/**
 * Controlador para manejar las solicitudes HTTP de la entidad 'Calle'.
 */
public class CalleController {
    private final CalleService calleService;

    public CalleController(CalleService calleService) {
        this.calleService = calleService;
    }

    /**
     * GET /calles/{id} - Obtiene un calle por su ID.
     */
    public void getCalleById(Context ctx) {
        try {
            int id = ctx.pathParamAsClass("id", Integer.class).get();
            Optional<Calle> calle = calleService.getCalleById(id);
            if (calle.isPresent()) {
                ctx.json(calle.get());
            } else {
                ctx.status(HttpStatus.NOT_FOUND).result("Calle no encontrado."); // 404 Not Found
            }
        } catch (Exception e) {
            ctx.status(HttpStatus.BAD_REQUEST).result("ID de calle inválido."); // 400 Bad Request
            System.err.println("Error al obtener calle por ID: " + e.getMessage());
        }
    }

    /**
     * GET /calles - Obtiene todos los calles.
     */
    public void getAllCalles(Context ctx) {
        List<Calle> calles = calleService.getAllCalles();
        ctx.json(calles);
    }

}
