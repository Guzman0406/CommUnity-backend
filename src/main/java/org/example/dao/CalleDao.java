// src/main/java/org/example/dao/CalleDao.java
package org.example.dao;

import org.example.config.DatabaseConfig;
import org.example.model.Calle;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * DAO para la tabla 'calles'.
 */
public class CalleDao {

    public Optional<Calle> selectById(int id) {
        String sql = "SELECT id_calle, nombre_calle FROM calles WHERE id_calle = ?";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(new Calle(
                            rs.getInt("id_calle"),
                            rs.getString("nombre_calle")
                    ));
                }
            }
        } catch (SQLException e) {
            System.err.println("Error al seleccionar calle por ID: " + e.getMessage());
        }
        return Optional.empty();
    }

    public List<Calle> selectAll() {
        List<Calle> calles = new ArrayList<>();
        String sql = "SELECT id_calle, nombre_calle FROM calles";
        try (Connection conn = DatabaseConfig.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                calles.add(new Calle(
                        rs.getInt("id_calle"),
                        rs.getString("nombre_calle")
                ));
            }
        } catch (SQLException e) {
            System.err.println("Error al seleccionar todas las calles: " + e.getMessage());
        }
        return calles;
    }
}