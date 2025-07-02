// src/main/java/org/example/dao/UserDao.java
package org.example.dao;

import org.example.config.DatabaseConfig;
import org.example.model.User;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * DAO (Data Access Object) para la tabla 'usuarios'.
 * Contiene métodos para realizar operaciones CRUD en la base de datos.
 */
public class UserDao {

    /**
     * Inserta un nuevo usuario en la base de datos.
     * @param user El objeto User a insertar.
     * @return El objeto User insertado con su ID generado, o Optional.empty() si falla.
     */
    public Optional<User> insert(User user) {
        String sql = "INSERT INTO usuarios (nombre, contraseña, apellido_paterno, apellido_materno, telefono, es_admin) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            pstmt.setString(1, user.getNombre());
            pstmt.setString(2, user.getPassword()); // En una app real, usar hash de contraseña
            pstmt.setString(3, user.getApellidoPaterno());
            pstmt.setString(4, user.getApellidoMaterno());
            pstmt.setString(5, user.getTelefono());
            pstmt.setBoolean(6, user.isEsAdmin());

            int affectedRows = pstmt.executeUpdate();

            if (affectedRows > 0) {
                try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        user.setIdUsuario(generatedKeys.getInt(1));
                        return Optional.of(user);
                    }
                }
            }
        } catch (SQLException e) {
            System.err.println("Error al insertar usuario: " + e.getMessage());
        }
        return Optional.empty();
    }

    /**
     * Obtiene un usuario por su ID.
     * @param id El ID del usuario.
     * @return Un Optional que contiene el usuario si se encuentra, o vacío si no.
     */
    public Optional<User> selectById(int id) {
        String sql = "SELECT id_usuario, nombre, contraseña, apellido_paterno, apellido_materno, telefono, es_admin FROM usuarios WHERE id_usuario = ?";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, id);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(new User(
                            rs.getInt("id_usuario"),
                            rs.getString("nombre"),
                            rs.getString("contraseña"),
                            rs.getString("apellido_paterno"),
                            rs.getString("apellido_materno"),
                            rs.getString("telefono"),
                            rs.getBoolean("es_admin")
                    ));
                }
            }
        } catch (SQLException e) {
            System.err.println("Error al seleccionar usuario por ID: " + e.getMessage());
        }
        return Optional.empty();
    }

    /**
     * Obtiene todos los usuarios de la base de datos.
     * @return Una lista de objetos User.
     */
    public List<User> selectAll() {
        List<User> users = new ArrayList<>();
        String sql = "SELECT id_usuario, nombre, contraseña, apellido_paterno, apellido_materno, telefono, es_admin FROM usuarios";
        try (Connection conn = DatabaseConfig.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                users.add(new User(
                        rs.getInt("id_usuario"),
                        rs.getString("nombre"),
                        rs.getString("contraseña"),
                        rs.getString("apellido_paterno"),
                        rs.getString("apellido_materno"),
                        rs.getString("telefono"),
                        rs.getBoolean("es_admin")
                ));
            }
        } catch (SQLException e) {
            System.err.println("Error al seleccionar todos los usuarios: " + e.getMessage());
        }
        return users;
    }

    /**
     * Actualiza un usuario existente en la base de datos.
     * @param user El objeto User con los datos actualizados.
     * @return true si la actualización fue exitosa, false en caso contrario.
     */
    public boolean update(User user) {
        String sql = "UPDATE usuarios SET nombre = ?, contraseña = ?, apellido_paterno = ?, apellido_materno = ?, telefono = ?, es_admin = ? WHERE id_usuario = ?";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, user.getNombre());
            pstmt.setString(2, user.getPassword());
            pstmt.setString(3, user.getApellidoPaterno());
            pstmt.setString(4, user.getApellidoMaterno());
            pstmt.setString(5, user.getTelefono());
            pstmt.setBoolean(6, user.isEsAdmin());
            pstmt.setInt(7, user.getIdUsuario());

            int affectedRows = pstmt.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            System.err.println("Error al actualizar usuario: " + e.getMessage());
        }
        return false;
    }

    /**
     * Elimina un usuario de la base de datos por su ID.
     * @param id El ID del usuario a eliminar.
     * @return true si la eliminación fue exitosa, false en caso contrario.
     */
    public boolean delete(int id) {
        String sql = "DELETE FROM usuarios WHERE id_usuario = ?";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, id);
            int affectedRows = pstmt.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            System.err.println("Error al eliminar usuario: " + e.getMessage());
        }
        return false;
    }
}