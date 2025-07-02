// src/main/java/org/example/controller/MensajeAdminController.java
package org.example.controller;

import io.javalin.http.Context;
import io.javalin.http.HttpStatus;
import org.example.model.MensajeAdmin;
import org.example.service.MensajeAdminService;
import java.util.List;
import java.util.Optional;

/**
 * Controlador para manejar las solicitudes HTTP de la entidad 'MensajeAdmin'.
 */
public class MensajeAdminController {
    private final MensajeAdminService mensajeAdminService;

    public MensajeAdminController(MensajeAdminService mensajeAdminService) {
        this.mensajeAdminService = mensajeAdminService;
    }

    /**
     * GET /admin-messages/{id} - Obtiene un mensajeAdmin por su ID.
     */
    public void getMensajeAdminById(Context ctx) {
        try {
            int id = ctx.pathParamAsClass("id", Integer.class).get();
            Optional<MensajeAdmin> mensajeAdmin = mensajeAdminService.getMensajeAdminById(id);
            if (mensajeAdmin.isPresent()) {
                ctx.json(mensajeAdmin.get());
            } else {
                ctx.status(HttpStatus.NOT_FOUND).result("MensajeAdmin no encontrado."); // 404 Not Found
            }
        } catch (Exception e) {
            ctx.status(HttpStatus.BAD_REQUEST).result("ID de mensajeAdmin inválido."); // 400 Bad Request
            System.err.println("Error al obtener mensajeAdmin por ID: " + e.getMessage());
        }
    }

    /**
     * GET /admin-messages - Obtiene todos los mensajeadmins.
     */
    public void getAllMensajeAdmins(Context ctx) {
        List<MensajeAdmin> mensajeadmins = mensajeAdminService.getAllMensajeAdmins();
        ctx.json(mensajeadmins);
    }

}
