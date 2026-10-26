package com.mycompany.gestorproyectos.controller;

import com.mycompany.gestorproyectos.dao.ProyectoDAO;
import com.mycompany.gestorproyectos.model.Proyecto;
import com.mycompany.gestorproyectos.util.DateUtil;
import com.mycompany.gestorproyectos.util.InputValidator;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.Date;
import java.util.List;

public class ProyectoController {
    private ProyectoDAO proyectoDAO;

    public ProyectoController() {
        this.proyectoDAO = new ProyectoDAO();
    }

    public String registrarProyecto(String nombre, String descripcion, String fechaInicioStr, String fechaFinStr, String estado) {
        try {
            if (!InputValidator.isNotEmpty(nombre)) {
                return "Error: El nombre del proyecto es obligatorio.";
            }
            if (!InputValidator.isValidProjectState(estado)) {
                return "Error: El estado debe ser PLANIFICADO, EN_CURSO o FINALIZADO.";
            }

            Date fechaInicio = DateUtil.parseDate(fechaInicioStr);
            Date fechaFin = DateUtil.parseDate(fechaFinStr);

            if (!DateUtil.isValidDateRange(fechaInicio, fechaFin)) {
                return "Error: La fecha de finalización no puede ser anterior a la fecha de inicio.";
            }

            Proyecto proyecto = new Proyecto(nombre, descripcion, fechaInicio, fechaFin, estado);
            proyectoDAO.insert(proyecto);
            return "Proyecto registrado exitosamente con ID: " + proyecto.getId();
        } catch (ParseException e) {
            return "Error en el formato de fecha. Use el formato: " + DateUtil.getDateFormatPattern();
        } catch (SQLException e) {
            return "Error al registrar proyecto: " + e.getMessage();
        }
    }

    public String listarProyectos() {
        try {
            List<Proyecto> proyectos = proyectoDAO.findAll();
            if (proyectos.isEmpty()) {
                return "No hay proyectos registrados.";
            }
            StringBuilder sb = new StringBuilder("=== LISTA DE PROYECTOS ===\n");
            for (Proyecto proj : proyectos) {
                sb.append(proj).append("\n");
            }
            return sb.toString();
        } catch (SQLException e) {
            return "Error al listar proyectos: " + e.getMessage();
        }
    }

    public String buscarProyectoPorId(int id) {
        try {
            if (!InputValidator.isValidId(id)) {
                return "Error: ID inválido.";
            }
            Proyecto proyecto = proyectoDAO.findById(id);
            if (proyecto == null) {
                return "No se encontró proyecto con ID: " + id;
            }
            return "=== PROYECTO ENCONTRADO ===\n" + proyecto.toString();
        } catch (SQLException e) {
            return "Error al buscar proyecto: " + e.getMessage();
        }
    }

    public String actualizarProyecto(int id, String nombre, String descripcion, String fechaInicioStr, String fechaFinStr, String estado) {
        try {
            if (!InputValidator.isValidId(id)) {
                return "Error: ID inválido.";
            }
            if (!InputValidator.isNotEmpty(nombre)) {
                return "Error: El nombre del proyecto es obligatorio.";
            }
            if (!InputValidator.isValidProjectState(estado)) {
                return "Error: El estado debe ser PLANIFICADO, EN_CURSO o FINALIZADO.";
            }
            if (!proyectoDAO.existsById(id)) {
                return "Error: No existe proyecto con ID: " + id;
            }

            Date fechaInicio = DateUtil.parseDate(fechaInicioStr);
            Date fechaFin = DateUtil.parseDate(fechaFinStr);

            if (!DateUtil.isValidDateRange(fechaInicio, fechaFin)) {
                return "Error: La fecha de finalización no puede ser anterior a la fecha de inicio.";
            }

            Proyecto proyecto = new Proyecto(id, nombre, descripcion, fechaInicio, fechaFin, estado);
            proyectoDAO.update(proyecto);
            return "Proyecto actualizado exitosamente.";
        } catch (ParseException e) {
            return "Error en el formato de fecha. Use el formato: " + DateUtil.getDateFormatPattern();
        } catch (SQLException e) {
            return "Error al actualizar proyecto: " + e.getMessage();
        }
    }

    public String eliminarProyecto(int id) {
        try {
            if (!InputValidator.isValidId(id)) {
                return "Error: ID inválido.";
            }
            if (!proyectoDAO.existsById(id)) {
                return "Error: No existe proyecto con ID: " + id;
            }
            if (proyectoDAO.hasActiveAssignments(id)) {
                return "Error: No se puede eliminar el proyecto porque tiene asignaciones activas.";
            }

            proyectoDAO.delete(id);
            return "Proyecto eliminado exitosamente.";
        } catch (SQLException e) {
            return "Error al eliminar proyecto: " + e.getMessage();
        }
    }
}
