// src/main/java/org/example/Main.java
package org.example;

import io.javalin.Javalin;
// JavalinConfig no es necesario importarlo explícitamente si se usa 'config' en el lambda
import io.javalin.http.Context; // Importar Context para el middleware
import io.javalin.http.HttpStatus; // Importar HttpStatus para los estados HTTP
import io.javalin.plugin.bundled.CorsPlugin; // Importar CorsPlugin
// CorsContainer ya no es necesario en Javalin 6+ para esta configuración de CORS
// import io.javalin.plugin.bundled.CorsContainer;
import org.example.controller.*;
import org.example.dao.*;
import org.example.service.*;

public class Main {
    public static void main(String[] args) {
        // Inicialización de DAOs
        UserDao userDao = new UserDao();
        CalleDao calleDao = new CalleDao();
        SeccionDao seccionDao = new SeccionDao();
        NivelUrgenciaDao nivelUrgenciaDao = new NivelUrgenciaDao();
        TipoReporteDao tipoReporteDao = new TipoReporteDao();
        EstadoReporteDao estadoReporteDao = new EstadoReporteDao();
        ReporteDao reporteDao = new ReporteDao();
        MensajeAdminDao mensajeAdminDao = new MensajeAdminDao();

        // Inicialización de Servicios, inyectando los DAOs
        UserService userService = new UserService(userDao);
        CalleService calleService = new CalleService(calleDao);
        SeccionService seccionService = new SeccionService(seccionDao);
        NivelUrgenciaService nivelUrgenciaService = new NivelUrgenciaService(nivelUrgenciaDao);
        TipoReporteService tipoReporteService = new TipoReporteService(tipoReporteDao);
        EstadoReporteService estadoReporteService = new EstadoReporteService(estadoReporteDao);
        ReporteService reporteService = new ReporteService(reporteDao);
        MensajeAdminService mensajeAdminService = new MensajeAdminService(mensajeAdminDao);

        // Inicialización de Controladores, inyectando los Servicios
        UserController userController = new UserController(userService);
        CalleController calleController = new CalleController(calleService);
        SeccionController seccionController = new SeccionController(seccionService);
        NivelUrgenciaController nivelUrgenciaController = new NivelUrgenciaController(nivelUrgenciaService);
        TipoReporteController tipoReporteController = new TipoReporteController(tipoReporteService);
        EstadoReporteController estadoReporteController = new EstadoReporteController(estadoReporteService);
        ReporteController reporteController = new ReporteController(reporteService);
        MensajeAdminController mensajeAdminController = new MensajeAdminController(mensajeAdminService);


        // Crear la aplicación Javalin
        // Declarar 'app' con tipo explícito 'Javalin' para evitar problemas de inferencia
        Javalin app = Javalin.create(config -> {
            // Configuración de logging: en Javalin 6+, 'showLpg' se ha movido o renombrado.
            // Para habilitar el logging de desarrollo, puedes usar:
            config.showLpg = true; // Esto es para logging básico de Javalin en consola.
            // Si slf4j-simple está configurado, Javalin lo usará automáticamente.

            // Configuración para permitir CORS (Cross-Origin Resource Sharing)
            // Esto permite que tu frontend (si está en un dominio/puerto diferente)
            // pueda hacer solicitudes a esta API.
            config.plugins.register(new CorsPlugin(cors -> {
                cors.add(it -> {
                    it.anyHost(); // Permite solicitudes desde cualquier origen
                });
            }));

            // Configuración para manejo de excepciones globales y errores 404
            // Usamos Javalin.currentJavalin() para referirnos a la instancia de 'app'
            // dentro de este bloque de configuración
            config.router.apiBuilder(() -> {
                // Manejo de NumberFormatException para IDs inválidos en rutas
                Javalin.currentJavalin().exception(NumberFormatException.class, (e, ctx) -> {
                    ctx.status(HttpStatus.BAD_REQUEST).result("ID inválido. Debe ser un número.");
                    System.err.println("Error de formato de ID: " + e.getMessage());
                });

                // Manejo de cualquier otra excepción no capturada
                Javalin.currentJavalin().exception(Exception.class, (e, ctx) -> {
                    ctx.status(HttpStatus.INTERNAL_SERVER_ERROR).result("Ocurrió un error interno en el servidor.");
                    System.err.println("Error interno del servidor: " + e.getMessage());
                    e.printStackTrace(); // Imprime el stack trace para depuración
                });

                // Manejo de rutas no encontradas (404)
                Javalin.currentJavalin().error(HttpStatus.NOT_FOUND.getCode(), ctx -> {
                    ctx.result("Recurso no encontrado. Verifique la URL.");
                });
            });
        });

        // Definir las rutas de la API

        // Rutas para Usuarios (CRUD completo)
        app.post("/users", userController::createUser);
        app.get("/users", userController::getAllUsers);
        app.get("/users/{id}", userController::getUserById);
        app.put("/users/{id}", userController::updateUser);
        app.delete("/users/{id}", userController::deleteUser);

        // Rutas para Reportes (CRUD completo)
        app.post("/reports", reporteController::createReporte);
        app.get("/reports", reporteController::getAllReportes);
        app.get("/reports/{id}", reporteController::getReporteById);
        app.put("/reports/{id}", reporteController::updateReporte);
        app.delete("/reports/{id}", reporteController::deleteReporte);

        // Rutas para Calles (solo lectura)
        app.get("/calles", calleController::getAllCalles);
        app.get("/calles/{id}", calleController::getCalleById);

        // Rutas para Secciones (solo lectura)
        app.get("/secciones", seccionController::getAllSecciones);
        app.get("/secciones/{id}", seccionController::getSeccionById);

        // Rutas para Niveles de Urgencia (solo lectura)
        app.get("/niveles-urgencia", nivelUrgenciaController::getAllNivelesUrgencia);
        app.get("/niveles-urgencia/{id}", nivelUrgenciaController::getNivelUrgenciaById);

        // Rutas para Tipos de Reporte (solo lectura)
        app.get("/tipos-reporte", tipoReporteController::getAllTiposReporte);
        app.get("/tipos-reporte/{id}", tipoReporteController::getTipoReporteById);

        // Rutas para Estados de Reporte (solo lectura)
        app.get("/estados-reporte", estadoReporteController::getAllEstadosReporte);
        app.get("/estados-reporte/{id}", estadoReporteController::getEstadoReporteById);

        // Rutas para Mensajes de Administrador (solo lectura)
        app.get("/admin-messages", mensajeAdminController::getAllMensajesAdmin);
        app.get("/admin-messages/{id}", mensajeAdminController::getMensajeAdminById);


        // Iniciar la aplicación en el puerto 7070
        app.start(7070);

        System.out.println("Servidor Javalin iniciado en http://localhost:7070");
        System.out.println("\n--- Endpoints de Usuarios (CRUD) ---");
        System.out.println("  POST   /users       - Crear nuevo usuario (body: JSON User sin id_usuario)");
        System.out.println("  GET    /users       - Obtener todos los usuarios");
        System.out.println("  GET    /users/{id}  - Obtener usuario por ID");
        System.out.println("  PUT    /users/{id}  - Actualizar usuario (body: JSON User con id_usuario)");
        System.out.println("  DELETE /users/{id}  - Eliminar usuario por ID");

        System.out.println("\n--- Endpoints de Reportes (CRUD) ---");
        System.out.println("  POST   /reports       - Crear nuevo reporte (body: JSON Reporte sin id_reporte, con FKs)");
        System.out.println("  GET    /reports       - Obtener todos los reportes");
        System.out.println("  GET    /reports/{id}  - Obtener reporte por ID");
        System.out.println("  PUT    /reports/{id}  - Actualizar reporte (body: JSON Reporte con id_reporte, con FKs)");
        System.out.println("  DELETE /reports/{id}  - Eliminar reporte por ID");

        System.out.println("\n--- Endpoints de Tablas de Catálogo (Solo Lectura) ---");
        System.out.println("  GET    /calles");
        System.out.println("  GET    /calles/{id}");
        System.out.println("  GET    /secciones");
        System.out.println("  GET    /secciones/{id}");
        System.out.println("  GET    /niveles-urgencia");
        System.out.println("  GET    /niveles-urgencia/{id}");
        System.out.println("  GET    /tipos-reporte");
        System.out.println("  GET    /tipos-reporte/{id}");
        System.out.println("  GET    /estados-reporte");
        System.out.println("  GET    /estados-reporte/{id}");
        System.out.println("  GET    /admin-messages");
        System.out.println("  GET    /admin-messages/{id}");
    }
}
