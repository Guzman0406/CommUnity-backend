// src/main/java/org/example/config/DatabaseConfig.java
package org.example.config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Clase de configuración para la conexión a la base de datos MySQL.
 * Contiene los detalles de la conexión y un método para obtener una conexión.
 */
public class DatabaseConfig {
    // TODO: Reemplaza estos valores con los de tu base de datos MySQL
    private static final String DB_URL = "44.208.86.55";
    private static final String DB_USER = "usuario"; // Tu usuario de MySQL
    private static final String DB_PASSWORD = "hola123"; // Tu contraseña de MySQL

    /**
     * Establece y devuelve una nueva conexión a la base de datos.
     * @return Un objeto Connection.
     * @throws SQLException Si ocurre un error al establecer la conexión.
     */
    public static Connection getConnection() throws SQLException {
        try {
            // Cargar el driver JDBC (no estrictamente necesario desde JDBC 4.0, pero buena práctica)
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            System.err.println("Error: Driver JDBC de MySQL no encontrado.");
            throw new SQLException("Driver JDBC de MySQL no encontrado", e);
        }
        return DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
    }
}