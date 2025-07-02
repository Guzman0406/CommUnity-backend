// src/main/java/org/example/service/TipoReporteService.java
package org.example.service;

import org.example.dao.TipoReporteDao;
import org.example.model.TipoReporte;
import java.util.List;
import java.util.Optional;

/**
 * Servicio para la lógica de negocio de los tiporeportes.
 * Utiliza TipoReporteDao para interactuar con la base de datos.
 */
public class TipoReporteService {
    private final TipoReporteDao tipoReporteDao;

    public TipoReporteService(TipoReporteDao tipoReporteDao) {
        this.tipoReporteDao = tipoReporteDao;
    }

    public Optional<TipoReporte> getTipoReporteById(int id) {
        return tipoReporteDao.selectById(id);
    }

    public List<TipoReporte> getAllTipoReportes() {
        return tipoReporteDao.selectAll();
    }

}
