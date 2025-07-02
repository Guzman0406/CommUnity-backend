// src/main/java/org/example/dao/EstadoReporteDao.java
package org.example.dao;

import org.example.config.DatabaseConfig;
import org.example.model.EstadoReporte;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * DAO para la tabla 'estados_reporte'.
 */
public class EstadoReporteDao {

    public Optional<EstadoReporte> selectById(int id) {
        String sql = "SELECT id_estado, nombre_estado FROM estados_reporte WHERE id_estado = ?";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(new EstadoReporte(
                            rs.getInt("id_estado"),
                            rs.getString("nombre_estado")
                    ));
                }
            }
        } catch (SQLException e) {
            System.err.println("Error al seleccionar estado de reporte por ID: " + e.getMessage());
        }
        return Optional.empty();
    }

    public List<EstadoReporte> selectAll() {
        List<EstadoReporte> estados = new ArrayList<>();
        String sql = "SELECT id_estado, nombre_estado FROM estados_reporte";
        try (Connection conn = DatabaseConfig.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                estados.add(new EstadoReporte(
                        rs.getInt("id_estado"),
                        rs.getString("nombre_estado")
                ));
            }
        } catch (SQLException e) {
            System.err.println("Error al seleccionar todos los estados de reporte: " + e.getMessage());
        }
        return estados;
    }
}