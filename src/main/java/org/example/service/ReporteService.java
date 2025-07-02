// src/main/java/org/example/service/ReporteService.java
package org.example.service;

import org.example.dao.ReporteDao;
import org.example.model.Reporte;
import java.util.List;
import java.util.Optional;

/**
 * Servicio para la lógica de negocio de los reportes.
 * Utiliza ReporteDao para interactuar con la base de datos.
 */
public class ReporteService {
    private final ReporteDao reporteDao;

    public ReporteService(ReporteDao reporteDao) {
        this.reporteDao = reporteDao;
    }

    public Optional<Reporte> createReporte(Reporte reporte) {
        // Aquí podrías añadir validaciones de negocio antes de insertar
        return reporteDao.insert(reporte);
    }

    public Optional<Reporte> getReporteById(int id) {
        return reporteDao.selectById(id);
    }

    public List<Reporte> getAllReportes() {
        return reporteDao.selectAll();
    }

    public boolean updateReporte(Reporte reporte) {
        // Aquí podrías añadir validaciones de negocio antes de actualizar
        return reporteDao.update(reporte);
    }

    public boolean deleteReporte(int id) {
        // Aquí podrías añadir lógica para verificar dependencias antes de eliminar
        return reporteDao.delete(id);
    }
}
