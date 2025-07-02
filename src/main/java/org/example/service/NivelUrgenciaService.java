// src/main/java/org/example/service/NivelUrgenciaService.java
package org.example.service;

import org.example.dao.NivelUrgenciaDao;
import org.example.model.NivelUrgencia;
import java.util.List;
import java.util.Optional;

/**
 * Servicio para la lógica de negocio de los nivelurgencias.
 * Utiliza NivelUrgenciaDao para interactuar con la base de datos.
 */
public class NivelUrgenciaService {
    private final NivelUrgenciaDao nivelUrgenciaDao;

    public NivelUrgenciaService(NivelUrgenciaDao nivelUrgenciaDao) {
        this.nivelUrgenciaDao = nivelUrgenciaDao;
    }

    public Optional<NivelUrgencia> getNivelUrgenciaById(int id) {
        return nivelUrgenciaDao.selectById(id);
    }

    public List<NivelUrgencia> getAllNivelUrgencias() {
        return nivelUrgenciaDao.selectAll();
    }

}
