package com.mycompany.gestorproyectos.dao;

import com.mycompany.gestorproyectos.model.EmpleadoProyecto;
import com.mycompany.gestorproyectos.util.DatabaseConnection;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EmpleadoProyectoDAO {
    public void insert(EmpleadoProyecto asignacion) throws SQLException {
        String sql = "INSERT INTO empleado_proyecto (empleado_id, proyecto_id, rol, fecha_asignacion, horas_semanales) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, asignacion.getEmpleadoId());
            pstmt.setInt(2, asignacion.getProyectoId());
            pstmt.setString(3, asignacion.getRol());
            pstmt.setDate(4, new java.sql.Date(asignacion.getFechaAsignacion().getTime()));
            pstmt.setDouble(5, asignacion.getHorasSemanales());
            pstmt.executeUpdate();
        }
    }

    public List<EmpleadoProyecto> findByProyectoId(int proyectoId) throws SQLException {
        List<EmpleadoProyecto> asignaciones = new ArrayList<>();
        String sql = "SELECT ep.empleado_id, ep.proyecto_id, ep.rol, ep.fecha_asignacion, ep.horas_semanales, " +
                     "e.nombre as empleado_nombre, e.cargo as empleado_cargo, p.nombre as proyecto_nombre " +
                     "FROM empleado_proyecto ep " +
                     "JOIN empleados e ON ep.empleado_id = e.id " +
                     "JOIN proyectos p ON ep.proyecto_id = p.id " +
                     "WHERE ep.proyecto_id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, proyectoId);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    EmpleadoProyecto asignacion = new EmpleadoProyecto(
                        rs.getInt("empleado_id"),
                        rs.getInt("proyecto_id"),
                        rs.getString("rol"),
                        rs.getDate("fecha_asignacion"),
                        rs.getDouble("horas_semanales")
                    );
                    asignacion.setEmpleadoNombre(rs.getString("empleado_nombre"));
                    asignacion.setEmpleadoCargo(rs.getString("empleado_cargo"));
                    asignacion.setProyectoNombre(rs.getString("proyecto_nombre"));
                    asignaciones.add(asignacion);
                }
            }
        }
        return asignaciones;
    }

    public List<EmpleadoProyecto> findByEmpleadoId(int empleadoId) throws SQLException {
        List<EmpleadoProyecto> asignaciones = new ArrayList<>();
        String sql = "SELECT ep.empleado_id, ep.proyecto_id, ep.rol, ep.fecha_asignacion, ep.horas_semanales, " +
                     "e.nombre as empleado_nombre, e.cargo as empleado_cargo, p.nombre as proyecto_nombre " +
                     "FROM empleado_proyecto ep " +
                     "JOIN empleados e ON ep.empleado_id = e.id " +
                     "JOIN proyectos p ON ep.proyecto_id = p.id " +
                     "WHERE ep.empleado_id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, empleadoId);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    EmpleadoProyecto asignacion = new EmpleadoProyecto(
                        rs.getInt("empleado_id"),
                        rs.getInt("proyecto_id"),
                        rs.getString("rol"),
                        rs.getDate("fecha_asignacion"),
                        rs.getDouble("horas_semanales")
                    );
                    asignacion.setEmpleadoNombre(rs.getString("empleado_nombre"));
                    asignacion.setEmpleadoCargo(rs.getString("empleado_cargo"));
                    asignacion.setProyectoNombre(rs.getString("proyecto_nombre"));
                    asignaciones.add(asignacion);
                }
            }
        }
        return asignaciones;
    }

    public void update(EmpleadoProyecto asignacion) throws SQLException {
        String sql = "UPDATE empleado_proyecto SET rol = ?, horas_semanales = ? WHERE empleado_id = ? AND proyecto_id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, asignacion.getRol());
            pstmt.setDouble(2, asignacion.getHorasSemanales());
            pstmt.setInt(3, asignacion.getEmpleadoId());
            pstmt.setInt(4, asignacion.getProyectoId());
            pstmt.executeUpdate();
        }
    }

    public void delete(int empleadoId, int proyectoId) throws SQLException {
        String sql = "DELETE FROM empleado_proyecto WHERE empleado_id = ? AND proyecto_id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, empleadoId);
            pstmt.setInt(2, proyectoId);
            pstmt.executeUpdate();
        }
    }

    public boolean exists(int empleadoId, int proyectoId) throws SQLException {
        String sql = "SELECT COUNT(*) FROM empleado_proyecto WHERE empleado_id = ? AND proyecto_id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, empleadoId);
            pstmt.setInt(2, proyectoId);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0;
                }
            }
        }
        return false;
    }

    public int countByEmpleado(int empleadoId) throws SQLException {
        String sql = "SELECT COUNT(*) FROM empleado_proyecto WHERE empleado_id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, empleadoId);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1);
                }
            }
        }
        return 0;
    }

    public int countByProyecto(int proyectoId) throws SQLException {
        String sql = "SELECT COUNT(*) FROM empleado_proyecto WHERE proyecto_id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, proyectoId);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1);
                }
            }
        }
        return 0;
    }
}
