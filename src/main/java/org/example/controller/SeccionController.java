// src/main/java/org/example/controller/SeccionController.java
package org.example.controller;

import io.javalin.http.Context;
import io.javalin.http.HttpStatus;
import org.example.model.Seccion;
import org.example.service.SeccionService;
import java.util.List;
import java.util.Optional;

/**
 * Controlador para manejar las solicitudes HTTP de la entidad 'Seccion'.
 */
public class SeccionController {
    private final SeccionService seccionService;

    public SeccionController(SeccionService seccionService) {
        this.seccionService = seccionService;
    }

    /**
     * GET /secciones/{id} - Obtiene un seccion por su ID.
     */
    public void getSeccionById(Context ctx) {
        try {
            int id = ctx.pathParamAsClass("id", Integer.class).get();
            Optional<Seccion> seccion = seccionService.getSeccionById(id);
            if (seccion.isPresent()) {
                ctx.json(seccion.get());
            } else {
                ctx.status(HttpStatus.NOT_FOUND).result("Seccion no encontrado."); // 404 Not Found
            }
        } catch (Exception e) {
            ctx.status(HttpStatus.BAD_REQUEST).result("ID de seccion inválido."); // 400 Bad Request
            System.err.println("Error al obtener seccion por ID: " + e.getMessage());
        }
    }

    /**
     * GET /secciones - Obtiene todos los seccions.
     */
    public void getAllSeccions(Context ctx) {
        List<Seccion> seccions = seccionService.getAllSeccions();
        ctx.json(seccions);
    }

}
