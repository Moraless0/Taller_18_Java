package com.mycompany.gestorproyectos.controller;

import com.mycompany.gestorproyectos.dao.EmpleadoDAO;
import com.mycompany.gestorproyectos.model.Empleado;
import com.mycompany.gestorproyectos.util.InputValidator;
import java.sql.SQLException;
import java.util.List;

public class EmpleadoController {
    private EmpleadoDAO empleadoDAO;

    public EmpleadoController() {
        this.empleadoDAO = new EmpleadoDAO();
    }

    public String registrarEmpleado(String nombre, String correo, String cargo) {
        try {
            if (!InputValidator.isNotEmpty(nombre)) {
                return "Error: El nombre es obligatorio.";
            }
            if (!InputValidator.isNotEmpty(correo)) {
                return "Error: El correo es obligatorio.";
            }
            if (!InputValidator.isNotEmpty(cargo)) {
                return "Error: El cargo es obligatorio.";
            }
            if (!InputValidator.isValidEmail(correo)) {
                return "Error: El formato del correo no es válido.";
            }
            if (empleadoDAO.existsByEmail(correo)) {
                return "Error: Ya existe un empleado con ese correo.";
            }

            Empleado empleado = new Empleado(nombre, correo, cargo);
            empleadoDAO.insert(empleado);
            return "Empleado registrado exitosamente con ID: " + empleado.getId();
        } catch (SQLException e) {
            return "Error al registrar empleado: " + e.getMessage();
        }
    }

    public String listarEmpleados() {
        try {
            List<Empleado> empleados = empleadoDAO.findAll();
            if (empleados.isEmpty()) {
                return "No hay empleados registrados.";
            }
            StringBuilder sb = new StringBuilder("=== LISTA DE EMPLEADOS ===\n");
            for (Empleado emp : empleados) {
                sb.append(emp).append("\n");
            }
            return sb.toString();
        } catch (SQLException e) {
            return "Error al listar empleados: " + e.getMessage();
        }
    }

    public String buscarEmpleadoPorId(int id) {
        try {
            if (!InputValidator.isValidId(id)) {
                return "Error: ID inválido.";
            }
            Empleado empleado = empleadoDAO.findById(id);
            if (empleado == null) {
                return "No se encontró empleado con ID: " + id;
            }
            return "=== EMPLEADO ENCONTRADO ===\n" + empleado.toString();
        } catch (SQLException e) {
            return "Error al buscar empleado: " + e.getMessage();
        }
    }

    public String actualizarEmpleado(int id, String nombre, String correo, String cargo) {
        try {
            if (!InputValidator.isValidId(id)) {
                return "Error: ID inválido.";
            }
            if (!InputValidator.isNotEmpty(nombre)) {
                return "Error: El nombre es obligatorio.";
            }
            if (!InputValidator.isNotEmpty(correo)) {
                return "Error: El correo es obligatorio.";
            }
            if (!InputValidator.isNotEmpty(cargo)) {
                return "Error: El cargo es obligatorio.";
            }
            if (!InputValidator.isValidEmail(correo)) {
                return "Error: El formato del correo no es válido.";
            }
            if (!empleadoDAO.existsById(id)) {
                return "Error: No existe empleado con ID: " + id;
            }
            if (empleadoDAO.existsByEmailExcludingId(correo, id)) {
                return "Error: Ya existe otro empleado con ese correo.";
            }

            Empleado empleado = new Empleado(id, nombre, correo, cargo);
            empleadoDAO.update(empleado);
            return "Empleado actualizado exitosamente.";
        } catch (SQLException e) {
            return "Error al actualizar empleado: " + e.getMessage();
        }
    }

    public String eliminarEmpleado(int id) {
        try {
            if (!InputValidator.isValidId(id)) {
                return "Error: ID inválido.";
            }
            if (!empleadoDAO.existsById(id)) {
                return "Error: No existe empleado con ID: " + id;
            }
            if (empleadoDAO.hasActiveAssignments(id)) {
                return "Error: No se puede eliminar el empleado porque tiene asignaciones activas.";
            }

            empleadoDAO.delete(id);
            return "Empleado eliminado exitosamente.";
        } catch (SQLException e) {
            return "Error al eliminar empleado: " + e.getMessage();
        }
    }
}
