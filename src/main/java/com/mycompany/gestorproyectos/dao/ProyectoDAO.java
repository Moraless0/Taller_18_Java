package com.mycompany.gestorproyectos.dao;

import com.mycompany.gestorproyectos.model.Proyecto;
import com.mycompany.gestorproyectos.util.DatabaseConnection;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProyectoDAO {
    public void insert(Proyecto proyecto) throws SQLException {
        String sql = "INSERT INTO proyectos (nombre, descripcion, fecha_inicio, fecha_fin, estado) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            pstmt.setString(1, proyecto.getNombre());
            pstmt.setString(2, proyecto.getDescripcion());
            pstmt.setDate(3, new java.sql.Date(proyecto.getFechaInicio().getTime()));
            pstmt.setDate(4, new java.sql.Date(proyecto.getFechaFin().getTime()));
            pstmt.setString(5, proyecto.getEstado());
            pstmt.executeUpdate();
            try (ResultSet rs = pstmt.getGeneratedKeys()) {
                if (rs.next()) {
                    proyecto.setId(rs.getInt(1));
                }
            }
        }
    }

    public List<Proyecto> findAll() throws SQLException {
        List<Proyecto> proyectos = new ArrayList<>();
        String sql = "SELECT id, nombre, descripcion, fecha_inicio, fecha_fin, estado FROM proyectos";
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                proyectos.add(new Proyecto(
                    rs.getInt("id"),
                    rs.getString("nombre"),
                    rs.getString("descripcion"),
                    rs.getDate("fecha_inicio"),
                    rs.getDate("fecha_fin"),
                    rs.getString("estado")
                ));
            }
        }
        return proyectos;
    }

    public Proyecto findById(int id) throws SQLException {
        String sql = "SELECT id, nombre, descripcion, fecha_inicio, fecha_fin, estado FROM proyectos WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return new Proyecto(
                        rs.getInt("id"),
                        rs.getString("nombre"),
                        rs.getString("descripcion"),
                        rs.getDate("fecha_inicio"),
                        rs.getDate("fecha_fin"),
                        rs.getString("estado")
                    );
                }
            }
        }
        return null;
    }

    public void update(Proyecto proyecto) throws SQLException {
        String sql = "UPDATE proyectos SET nombre = ?, descripcion = ?, fecha_inicio = ?, fecha_fin = ?, estado = ? WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, proyecto.getNombre());
            pstmt.setString(2, proyecto.getDescripcion());
            pstmt.setDate(3, new java.sql.Date(proyecto.getFechaInicio().getTime()));
            pstmt.setDate(4, new java.sql.Date(proyecto.getFechaFin().getTime()));
            pstmt.setString(5, proyecto.getEstado());
            pstmt.setInt(6, proyecto.getId());
            pstmt.executeUpdate();
        }
    }

    public void delete(int id) throws SQLException {
        String sql = "DELETE FROM proyectos WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            pstmt.executeUpdate();
        }
    }

    public boolean existsById(int id) throws SQLException {
        String sql = "SELECT COUNT(*) FROM proyectos WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0;
                }
            }
        }
        return false;
    }

    public boolean hasActiveAssignments(int id) throws SQLException {
        String sql = "SELECT COUNT(*) FROM empleado_proyecto WHERE proyecto_id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0;
                }
            }
        }
        return false;
    }
}
