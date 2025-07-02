// src/main/java/org/example/service/CalleService.java
package org.example.service;

import org.example.dao.CalleDao;
import org.example.model.Calle;
import java.util.List;
import java.util.Optional;

/**
 * Servicio para la lógica de negocio de los calles.
 * Utiliza CalleDao para interactuar con la base de datos.
 */
public class CalleService {
    private final CalleDao calleDao;

    public CalleService(CalleDao calleDao) {
        this.calleDao = calleDao;
    }

    public Optional<Calle> getCalleById(int id) {
        return calleDao.selectById(id);
    }

    public List<Calle> getAllCalles() {
        return calleDao.selectAll();
    }

}
