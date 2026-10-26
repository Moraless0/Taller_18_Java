package com.mycompany.gestorproyectos.controller;

import com.mycompany.gestorproyectos.dao.EmpleadoDAO;
import com.mycompany.gestorproyectos.dao.EmpleadoProyectoDAO;
import com.mycompany.gestorproyectos.dao.ProyectoDAO;
import com.mycompany.gestorproyectos.model.EmpleadoProyecto;
import com.mycompany.gestorproyectos.util.DateUtil;
import com.mycompany.gestorproyectos.util.InputValidator;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.Date;
import java.util.List;

public class AsignacionController {
    private EmpleadoProyectoDAO asignacionDAO;
    private EmpleadoDAO empleadoDAO;
    private ProyectoDAO proyectoDAO;

    public AsignacionController() {
        this.asignacionDAO = new EmpleadoProyectoDAO();
        this.empleadoDAO = new EmpleadoDAO();
        this.proyectoDAO = new ProyectoDAO();
    }

    public String asignarEmpleadoAProyecto(int empleadoId, int proyectoId, String rol, String fechaAsignacionStr, double horasSemanales) {
        try {
            if (!InputValidator.isValidId(empleadoId)) {
                return "Error: ID de empleado inválido.";
            }
            if (!InputValidator.isValidId(proyectoId)) {
                return "Error: ID de proyecto inválido.";
            }
            if (!InputValidator.isNotEmpty(rol)) {
                return "Error: El rol es obligatorio.";
            }
            if (!InputValidator.isValidHours(horasSemanales)) {
                return "Error: Las horas semanales deben ser mayores que 0 y no superar 40.";
            }
            if (!empleadoDAO.existsById(empleadoId)) {
                return "Error: No existe empleado con ID: " + empleadoId;
            }
            if (!proyectoDAO.existsById(proyectoId)) {
                return "Error: No existe proyecto con ID: " + proyectoId;
            }
            if (asignacionDAO.exists(empleadoId, proyectoId)) {
                return "Error: El empleado ya está asignado a este proyecto.";
            }

            Date fechaAsignacion = DateUtil.parseDate(fechaAsignacionStr);
            EmpleadoProyecto asignacion = new EmpleadoProyecto(empleadoId, proyectoId, rol, fechaAsignacion, horasSemanales);
            asignacionDAO.insert(asignacion);
            return "Asignación registrada exitosamente.";
        } catch (ParseException e) {
            return "Error en el formato de fecha. Use el formato: " + DateUtil.getDateFormatPattern();
        } catch (SQLException e) {
            return "Error al registrar asignación: " + e.getMessage();
        }
    }

    public String consultarIntegrantesProyecto(int proyectoId) {
        try {
            if (!InputValidator.isValidId(proyectoId)) {
                return "Error: ID de proyecto inválido.";
            }
            if (!proyectoDAO.existsById(proyectoId)) {
                return "Error: No existe proyecto con ID: " + proyectoId;
            }

            List<EmpleadoProyecto> asignaciones = asignacionDAO.findByProyectoId(proyectoId);
            if (asignaciones.isEmpty()) {
                return "El proyecto no tiene integrantes asignados.";
            }
            StringBuilder sb = new StringBuilder("=== INTEGRANTES DEL PROYECTO ===\n");
            for (EmpleadoProyecto asignacion : asignaciones) {
                sb.append(asignacion).append("\n");
            }
            return sb.toString();
        } catch (SQLException e) {
            return "Error al consultar integrantes: " + e.getMessage();
        }
    }

    public String consultarProyectosEmpleado(int empleadoId) {
        try {
            if (!InputValidator.isValidId(empleadoId)) {
                return "Error: ID de empleado inválido.";
            }
            if (!empleadoDAO.existsById(empleadoId)) {
                return "Error: No existe empleado con ID: " + empleadoId;
            }

            List<EmpleadoProyecto> asignaciones = asignacionDAO.findByEmpleadoId(empleadoId);
            if (asignaciones.isEmpty()) {
                return "El empleado no tiene proyectos asignados.";
            }
            StringBuilder sb = new StringBuilder("=== PROYECTOS DEL EMPLEADO ===\n");
            for (EmpleadoProyecto asignacion : asignaciones) {
                sb.append(asignacion).append("\n");
            }
            return sb.toString();
        } catch (SQLException e) {
            return "Error al consultar proyectos: " + e.getMessage();
        }
    }

    public String modificarAsignacion(int empleadoId, int proyectoId, String rol, double horasSemanales) {
        try {
            if (!InputValidator.isValidId(empleadoId)) {
                return "Error: ID de empleado inválido.";
            }
            if (!InputValidator.isValidId(proyectoId)) {
                return "Error: ID de proyecto inválido.";
            }
            if (!InputValidator.isNotEmpty(rol)) {
                return "Error: El rol es obligatorio.";
            }
            if (!InputValidator.isValidHours(horasSemanales)) {
                return "Error: Las horas semanales deben ser mayores que 0 y no superar 40.";
            }
            if (!asignacionDAO.exists(empleadoId, proyectoId)) {
                return "Error: No existe la asignación especificada.";
            }

            EmpleadoProyecto asignacion = new EmpleadoProyecto(empleadoId, proyectoId, rol, null, horasSemanales);
            asignacionDAO.update(asignacion);
            return "Asignación modificada exitosamente.";
        } catch (SQLException e) {
            return "Error al modificar asignación: " + e.getMessage();
        }
    }

    public String retirarEmpleadoDeProyecto(int empleadoId, int proyectoId) {
        try {
            if (!InputValidator.isValidId(empleadoId)) {
                return "Error: ID de empleado inválido.";
            }
            if (!InputValidator.isValidId(proyectoId)) {
                return "Error: ID de proyecto inválido.";
            }
            if (!asignacionDAO.exists(empleadoId, proyectoId)) {
                return "Error: No existe la asignación especificada.";
            }

            asignacionDAO.delete(empleadoId, proyectoId);
            return "Empleado retirado del proyecto exitosamente.";
        } catch (SQLException e) {
            return "Error al retirar empleado: " + e.getMessage();
        }
    }
}
