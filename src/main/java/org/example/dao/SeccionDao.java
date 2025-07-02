// src/main/java/org/example/dao/SeccionDao.java
package org.example.dao;

import org.example.config.DatabaseConfig;
import org.example.model.Seccion;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * DAO para la tabla 'secciones'.
 */
public class SeccionDao {

    public Optional<Seccion> selectById(int id) {
        String sql = "SELECT id_seccion, nombre_seccion FROM secciones WHERE id_seccion = ?";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(new Seccion(
                            rs.getInt("id_seccion"),
                            rs.getString("nombre_seccion")
                    ));
                }
            }
        } catch (SQLException e) {
            System.err.println("Error al seleccionar sección por ID: " + e.getMessage());
        }
        return Optional.empty();
    }

    public List<Seccion> selectAll() {
        List<Seccion> secciones = new ArrayList<>();
        String sql = "SELECT id_seccion, nombre_seccion FROM secciones";
        try (Connection conn = DatabaseConfig.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                secciones.add(new Seccion(
                        rs.getInt("id_seccion"),
                        rs.getString("nombre_seccion")
                ));
            }
        } catch (SQLException e) {
            System.err.println("Error al seleccionar todas las secciones: " + e.getMessage());
        }
        return secciones;
    }
}