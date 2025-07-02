// src/main/java/org/example/service/SeccionService.java
package org.example.service;

import org.example.dao.SeccionDao;
import org.example.model.Seccion;
import java.util.List;
import java.util.Optional;

/**
 * Servicio para la lógica de negocio de los seccions.
 * Utiliza SeccionDao para interactuar con la base de datos.
 */
public class SeccionService {
    private final SeccionDao seccionDao;

    public SeccionService(SeccionDao seccionDao) {
        this.seccionDao = seccionDao;
    }

    public Optional<Seccion> getSeccionById(int id) {
        return seccionDao.selectById(id);
    }

    public List<Seccion> getAllSeccions() {
        return seccionDao.selectAll();
    }

}
