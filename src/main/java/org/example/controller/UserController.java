// src/main/java/org/example/controller/UserController.java
package org.example.controller;

import io.javalin.http.Context;
import io.javalin.http.HttpStatus;
import org.example.model.User;
import org.example.service.UserService;
import java.util.List;
import java.util.Optional;

/**
 * Controlador para manejar las solicitudes HTTP de la entidad 'User'.
 */
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    /**
     * POST /users - Crea un nuevo user.
     * Espera un JSON en el cuerpo de la solicitud que se mapeará a un objeto User.
     */
    public void createUser(Context ctx) {
        try {
            User newUser = ctx.bodyAsClass(User.class);
            Optional<User> createdUser = userService.createUser(newUser);
            if (createdUser.isPresent()) {
                ctx.status(HttpStatus.CREATED).json(createdUser.get()); // 201 Created
            } else {
                ctx.status(HttpStatus.BAD_REQUEST).result("No se pudo crear el user. Verifique los datos (ej. IDs de FK válidos)."); // 400 Bad Request
            }
        } catch (Exception e) {
            ctx.status(HttpStatus.BAD_REQUEST).result("Error al procesar la solicitud: " + e.getMessage()); // 400 Bad Request
            System.err.println("Error al crear user: " + e.getMessage());
        }
    }

    /**
     * GET /users/{id} - Obtiene un user por su ID.
     */
    public void getUserById(Context ctx) {
        try {
            int id = ctx.pathParamAsClass("id", Integer.class).get();
            Optional<User> user = userService.getUserById(id);
            if (user.isPresent()) {
                ctx.json(user.get());
            } else {
                ctx.status(HttpStatus.NOT_FOUND).result("User no encontrado."); // 404 Not Found
            }
        } catch (Exception e) {
            ctx.status(HttpStatus.BAD_REQUEST).result("ID de user inválido."); // 400 Bad Request
            System.err.println("Error al obtener user por ID: " + e.getMessage());
        }
    }

    /**
     * GET /users - Obtiene todos los users.
     */
    public void getAllUsers(Context ctx) {
        List<User> users = userService.getAllUsers();
        ctx.json(users);
    }

    /**
     * PUT /users/{id} - Actualiza un user existente.
     * Espera un JSON en el cuerpo de la solicitud que se mapeará a un objeto User.
     */
    public void updateUser(Context ctx) {
        try {
            int id = ctx.pathParamAsClass("id", Integer.class).get();
            User userToUpdate = ctx.bodyAsClass(User.class);
            userToUpdate.setIdUsuario(id); // Aseguramos que el ID del objeto coincida con el de la URL

            if (userService.updateUser(userToUpdate)) {
                ctx.status(HttpStatus.OK).result("User actualizado exitosamente."); // 200 OK
            } else {
                ctx.status(HttpStatus.NOT_FOUND).result("User no encontrado o no se pudo actualizar."); // 404 Not Found o 304 Not Modified
            }
        } catch (Exception e) {
            ctx.status(HttpStatus.BAD_REQUEST).result("Error al procesar la solicitud de actualización: " + e.getMessage()); // 400 Bad Request
            System.err.println("Error al actualizar user: " + e.getMessage());
        }
    }

    /**
     * DELETE /users/{id} - Elimina un user por su ID.
     */
    public void deleteUser(Context ctx) {
        try {
            int id = ctx.pathParamAsClass("id", Integer.class).get();
            if (userService.deleteUser(id)) {
                ctx.status(HttpStatus.NO_CONTENT); // 204 No Content (éxito sin cuerpo de respuesta)
            } else {
                ctx.status(HttpStatus.NOT_FOUND).result("User no encontrado o no se pudo eliminar."); // 404 Not Found
            }
        } catch (Exception e) {
            ctx.status(HttpStatus.BAD_REQUEST).result("ID de user inválido."); // 400 Bad Request
            System.err.println("Error al eliminar user: " + e.getMessage());
        }
    }
}
