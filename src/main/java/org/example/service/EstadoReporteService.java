// src/main/java/org/example/service/EstadoReporteService.java
package org.example.service;

import org.example.dao.EstadoReporteDao;
import org.example.model.EstadoReporte;
import java.util.List;
import java.util.Optional;

/**
 * Servicio para la lógica de negocio de los estadoreportes.
 * Utiliza EstadoReporteDao para interactuar con la base de datos.
 */
public class EstadoReporteService {
    private final EstadoReporteDao estadoReporteDao;

    public EstadoReporteService(EstadoReporteDao estadoReporteDao) {
        this.estadoReporteDao = estadoReporteDao;
    }

    public Optional<EstadoReporte> getEstadoReporteById(int id) {
        return estadoReporteDao.selectById(id);
    }

    public List<EstadoReporte> getAllEstadoReportes() {
        return estadoReporteDao.selectAll();
    }

}
