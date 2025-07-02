// src/main/java/org/example/dao/ReporteDao.java
package org.example.dao;

import org.example.config.DatabaseConfig;
import org.example.model.Reporte;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * DAO para la tabla 'reportes'.
 */
public class ReporteDao {

    /**
     * Inserta un nuevo reporte en la base de datos.
     * @param reporte El objeto Reporte a insertar.
     * @return El objeto Reporte insertado con su ID y fechas generadas, o Optional.empty() si falla.
     */
    public Optional<Reporte> insert(Reporte reporte) {
        String sql = "INSERT INTO reportes (id_usuario, id_tipo, id_seccion, id_calle, referencias, descripcion, id_estado) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            pstmt.setInt(1, reporte.getIdUsuario());
            pstmt.setInt(2, reporte.getIdTipo());
            pstmt.setInt(3, reporte.getIdSeccion());
            pstmt.setInt(4, reporte.getIdCalle());
            pstmt.setString(5, reporte.getReferencias());
            pstmt.setString(6, reporte.getDescripcion());
            pstmt.setInt(7, reporte.getIdEstado());

            int affectedRows = pstmt.executeUpdate();

            if (affectedRows > 0) {
                try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        reporte.setIdReporte(generatedKeys.getInt(1));
                        // Recuperar las fechas generadas por la base de datos
                        Optional<Reporte> fetchedReporte = selectById(reporte.getIdReporte());
                        if(fetchedReporte.isPresent()) {
                            return fetchedReporte;
                        }
                    }
                }
            }
        } catch (SQLException e) {
            System.err.println("Error al insertar reporte: " + e.getMessage());
        }
        return Optional.empty();
    }

    /**
     * Obtiene un reporte por su ID.
     * @param id El ID del reporte.
     * @return Un Optional que contiene el reporte si se encuentra, o vacío si no.
     */
    public Optional<Reporte> selectById(int id) {
        String sql = "SELECT id_reporte, id_usuario, id_tipo, id_seccion, id_calle, referencias, descripcion, id_estado, fecha_creacion, fecha_actualizacion FROM reportes WHERE id_reporte = ?";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, id);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(new Reporte(
                            rs.getInt("id_reporte"),
                            rs.getInt("id_usuario"),
                            rs.getInt("id_tipo"),
                            rs.getInt("id_seccion"),
                            rs.getInt("id_calle"),
                            rs.getString("referencias"),
                            rs.getString("descripcion"),
                            rs.getInt("id_estado"),
                            rs.getTimestamp("fecha_creacion").toLocalDateTime(),
                            rs.getTimestamp("fecha_actualizacion").toLocalDateTime()
                    ));
                }
            }
        } catch (SQLException e) {
            System.err.println("Error al seleccionar reporte por ID: " + e.getMessage());
        }
        return Optional.empty();
    }

    /**
     * Obtiene todos los reportes de la base de datos.
     * @return Una lista de objetos Reporte.
     */
    public List<Reporte> selectAll() {
        List<Reporte> reportes = new ArrayList<>();
        String sql = "SELECT id_reporte, id_usuario, id_tipo, id_seccion, id_calle, referencias, descripcion, id_estado, fecha_creacion, fecha_actualizacion FROM reportes";
        try (Connection conn = DatabaseConfig.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                reportes.add(new Reporte(
                        rs.getInt("id_reporte"),
                        rs.getInt("id_usuario"),
                        rs.getInt("id_tipo"),
                        rs.getInt("id_seccion"),
                        rs.getInt("id_calle"),
                        rs.getString("referencias"),
                        rs.getString("descripcion"),
                        rs.getInt("id_estado"),
                        rs.getTimestamp("fecha_creacion").toLocalDateTime(),
                        rs.getTimestamp("fecha_actualizacion").toLocalDateTime()
                ));
            }
        } catch (SQLException e) {
            System.err.println("Error al seleccionar todos los reportes: " + e.getMessage());
        }
        return reportes;
    }

    /**
     * Actualiza un reporte existente en la base de datos.
     * @param reporte El objeto Reporte con los datos actualizados.
     * @return true si la actualización fue exitosa, false en caso contrario.
     */
    public boolean update(Reporte reporte) {
        String sql = "UPDATE reportes SET id_usuario = ?, id_tipo = ?, id_seccion = ?, id_calle = ?, referencias = ?, descripcion = ?, id_estado = ? WHERE id_reporte = ?";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, reporte.getIdUsuario());
            pstmt.setInt(2, reporte.getIdTipo());
            pstmt.setInt(3, reporte.getIdSeccion());
            pstmt.setInt(4, reporte.getIdCalle());
            pstmt.setString(5, reporte.getReferencias());
            pstmt.setString(6, reporte.getDescripcion());
            pstmt.setInt(7, reporte.getIdEstado());
            pstmt.setInt(8, reporte.getIdReporte());

            int affectedRows = pstmt.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            System.err.println("Error al actualizar reporte: " + e.getMessage());
        }
        return false;
    }

    /**
     * Elimina un reporte de la base de datos por su ID.
     * @param id El ID del reporte a eliminar.
     * @return true si la eliminación fue exitosa, false en caso contrario.
     */
    public boolean delete(int id) {
        String sql = "DELETE FROM reportes WHERE id_reporte = ?";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, id);
            int affectedRows = pstmt.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            System.err.println("Error al eliminar reporte: " + e.getMessage());
        }
        return false;
    }
}