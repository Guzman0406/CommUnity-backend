// src/main/java/org/example/service/MensajeAdminService.java
package org.example.service;

import org.example.dao.MensajeAdminDao;
import org.example.model.MensajeAdmin;
import java.util.List;
import java.util.Optional;

/**
 * Servicio para la lógica de negocio de los mensajeadmins.
 * Utiliza MensajeAdminDao para interactuar con la base de datos.
 */
public class MensajeAdminService {
    private final MensajeAdminDao mensajeAdminDao;

    public MensajeAdminService(MensajeAdminDao mensajeAdminDao) {
        this.mensajeAdminDao = mensajeAdminDao;
    }

    public Optional<MensajeAdmin> getMensajeAdminById(int id) {
        return mensajeAdminDao.selectById(id);
    }

    public List<MensajeAdmin> getAllMensajeAdmins() {
        return mensajeAdminDao.selectAll();
    }

}
