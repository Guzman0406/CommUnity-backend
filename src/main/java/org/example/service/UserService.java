// src/main/java/org/example/service/UserService.java
package org.example.service;

import org.example.dao.UserDao;
import org.example.model.User;
import java.util.List;
import java.util.Optional;

/**
 * Servicio para la lógica de negocio de los users.
 * Utiliza UserDao para interactuar con la base de datos.
 */
public class UserService {
    private final UserDao userDao;

    public UserService(UserDao userDao) {
        this.userDao = userDao;
    }

    public Optional<User> createUser(User user) {
        // Aquí podrías añadir validaciones de negocio antes de insertar
        return userDao.insert(user);
    }

    public Optional<User> getUserById(int id) {
        return userDao.selectById(id);
    }

    public List<User> getAllUsers() {
        return userDao.selectAll();
    }

    public boolean updateUser(User user) {
        // Aquí podrías añadir validaciones de negocio antes de actualizar
        return userDao.update(user);
    }

    public boolean deleteUser(int id) {
        // Aquí podrías añadir lógica para verificar dependencias antes de eliminar
        return userDao.delete(id);
    }
}
