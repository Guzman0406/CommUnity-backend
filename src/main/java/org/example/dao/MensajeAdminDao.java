// src/main/java/org/example/dao/MensajeAdminDao.java
package org.example.dao;

import org.example.config.DatabaseConfig;
import org.example.model.MensajeAdmin;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * DAO para la tabla 'mensajes_admin'.
 */
public class MensajeAdminDao {

    public Optional<MensajeAdmin> selectById(int id) {
        String sql = "SELECT id_mensaje, id_admin, titulo, contenido, fecha_mensaje FROM mensajes_admin WHERE id_mensaje = ?";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(new MensajeAdmin(
                            rs.getInt("id_mensaje"),
                            rs.getInt("id_admin"),
                            rs.getString("titulo"),
                            rs.getString("contenido"),
                            rs.getTimestamp("fecha_mensaje").toLocalDateTime()
                    ));
                }
            }
        } catch (SQLException e) {
            System.err.println("Error al seleccionar mensaje de admin por ID: " + e.getMessage());
        }
        return Optional.empty();
    }

    public List<MensajeAdmin> selectAll() {
        List<MensajeAdmin> mensajes = new ArrayList<>();
        String sql = "SELECT id_mensaje, id_admin, titulo, contenido, fecha_mensaje FROM mensajes_admin";
        try (Connection conn = DatabaseConfig.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                mensajes.add(new MensajeAdmin(
                        rs.getInt("id_mensaje"),
                        rs.getInt("id_admin"),
                        rs.getString("titulo"),
                        rs.getString("contenido"),
                        rs.getTimestamp("fecha_mensaje").toLocalDateTime()
                ));
            }
        } catch (SQLException e) {
            System.err.println("Error al seleccionar todos los mensajes de admin: " + e.getMessage());
        }
        return mensajes;
    }
}