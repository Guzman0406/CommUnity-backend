// src/main/java/org/example/dao/TipoReporteDao.java
package org.example.dao;

import org.example.config.DatabaseConfig;
import org.example.model.TipoReporte;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * DAO para la tabla 'tipo_reporte'.
 */
public class TipoReporteDao {

    public Optional<TipoReporte> selectById(int id) {
        String sql = "SELECT id_tipo, nombre_tipo, id_nivel_urgencia FROM tipo_reporte WHERE id_tipo = ?";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(new TipoReporte(
                            rs.getInt("id_tipo"),
                            rs.getString("nombre_tipo"),
                            rs.getInt("id_nivel_urgencia")
                    ));
                }
            }
        } catch (SQLException e) {
            System.err.println("Error al seleccionar tipo de reporte por ID: " + e.getMessage());
        }
        return Optional.empty();
    }

    public List<TipoReporte> selectAll() {
        List<TipoReporte> tipos = new ArrayList<>();
        String sql = "SELECT id_tipo, nombre_tipo, id_nivel_urgencia FROM tipo_reporte";
        try (Connection conn = DatabaseConfig.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                tipos.add(new TipoReporte(
                        rs.getInt("id_tipo"),
                        rs.getString("nombre_tipo"),
                        rs.getInt("id_nivel_urgencia")
                ));
            }
        } catch (SQLException e) {
            System.err.println("Error al seleccionar todos los tipos de reporte: " + e.getMessage());
        }
        return tipos;
    }
}