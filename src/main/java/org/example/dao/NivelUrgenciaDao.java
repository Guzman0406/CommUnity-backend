// src/main/java/org/example/dao/NivelUrgenciaDao.java
package org.example.dao;

import org.example.config.DatabaseConfig;
import org.example.model.NivelUrgencia;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * DAO para la tabla 'niveles_urgencia'.
 */
public class NivelUrgenciaDao {

    public Optional<NivelUrgencia> selectById(int id) {
        String sql = "SELECT id_nivel, nombre_nivel FROM niveles_urgencia WHERE id_nivel = ?";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(new NivelUrgencia(
                            rs.getInt("id_nivel"),
                            rs.getString("nombre_nivel")
                    ));
                }
            }
        } catch (SQLException e) {
            System.err.println("Error al seleccionar nivel de urgencia por ID: " + e.getMessage());
        }
        return Optional.empty();
    }

    public List<NivelUrgencia> selectAll() {
        List<NivelUrgencia> niveles = new ArrayList<>();
        String sql = "SELECT id_nivel, nombre_nivel FROM niveles_urgencia";
        try (Connection conn = DatabaseConfig.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                niveles.add(new NivelUrgencia(
                        rs.getInt("id_nivel"),
                        rs.getString("nombre_nivel")
                ));
            }
        } catch (SQLException e) {
            System.err.println("Error al seleccionar todos los niveles de urgencia: " + e.getMessage());
        }
        return niveles;
    }
}