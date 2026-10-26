package com.mycompany.gestorproyectos.dao;

import com.mycompany.gestorproyectos.model.EmpleadoProyecto;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

public class EmpleadoProyectoDAOTest {
    private Connection connection;
    private EmpleadoProyectoDAO asignacionDAO;

    @BeforeEach
    public void setUp() throws SQLException {
        connection = DriverManager.getConnection("jdbc:h2:mem:testdb;DB_CLOSE_DELAY=-1", "sa", "");
        createTables();
        asignacionDAO = new EmpleadoProyectoDAO();
    }

    @AfterEach
    public void tearDown() throws SQLException {
        if (connection != null && !connection.isClosed()) {
            connection.close();
        }
    }

    private void createTables() throws SQLException {
        try (Statement stmt = connection.createStatement()) {
            stmt.execute("CREATE TABLE empleados (" +
                "id INT AUTO_INCREMENT PRIMARY KEY, " +
                "nombre VARCHAR(100) NOT NULL, " +
                "correo VARCHAR(100) NOT NULL UNIQUE, " +
                "cargo VARCHAR(50) NOT NULL)");
            stmt.execute("CREATE TABLE proyectos (" +
                "id INT AUTO_INCREMENT PRIMARY KEY, " +
                "nombre VARCHAR(100) NOT NULL, " +
                "descripcion TEXT, " +
                "fecha_inicio DATE NOT NULL, " +
                "fecha_fin DATE NOT NULL, " +
                "estado VARCHAR(20) NOT NULL)");
            stmt.execute("CREATE TABLE empleado_proyecto (" +
                "empleado_id INT NOT NULL, " +
                "proyecto_id INT NOT NULL, " +
                "rol VARCHAR(50) NOT NULL, " +
                "fecha_asignacion DATE NOT NULL, " +
                "horas_semanales DECIMAL(4,1) NOT NULL, " +
                "PRIMARY KEY (empleado_id, proyecto_id))");
        }
    }

    private void insertTestData() throws SQLException {
        try (Statement stmt = connection.createStatement()) {
            stmt.execute("INSERT INTO empleados (id, nombre, correo, cargo) VALUES (1, 'Juan Pérez', 'juan@example.com', 'Desarrollador')");
            stmt.execute("INSERT INTO empleados (id, nombre, correo, cargo) VALUES (2, 'Maria López', 'maria@example.com', 'Diseñador')");
            stmt.execute("INSERT INTO proyectos (id, nombre, descripcion, fecha_inicio, fecha_fin, estado) VALUES " +
                "(1, 'Sistema ERP', 'Sistema de gestión', CURRENT_DATE, CURRENT_DATE, 'PLANIFICADO')");
            stmt.execute("INSERT INTO proyectos (id, nombre, descripcion, fecha_inicio, fecha_fin, estado) VALUES " +
                "(2, 'App Móvil', 'Aplicación móvil', CURRENT_DATE, CURRENT_DATE, 'EN_CURSO')");
        }
    }

    @Test
    public void testInsertAsignacion() throws SQLException {
        insertTestData();
        Date fechaAsignacion = new Date();
        EmpleadoProyecto asignacion = new EmpleadoProyecto(1, 1, "Desarrollador", fechaAsignacion, 40.0);
        asignacionDAO.insert(asignacion);

        assertTrue(asignacionDAO.exists(1, 1));
    }

    @Test
    public void testFindByProyectoId() throws SQLException {
        insertTestData();
        Date fechaAsignacion = new Date();
        asignacionDAO.insert(new EmpleadoProyecto(1, 1, "Desarrollador", fechaAsignacion, 40.0));
        asignacionDAO.insert(new EmpleadoProyecto(2, 1, "Diseñador", fechaAsignacion, 20.0));

        List<EmpleadoProyecto> asignaciones = asignacionDAO.findByProyectoId(1);
        assertEquals(2, asignaciones.size());
    }

    @Test
    public void testFindByEmpleadoId() throws SQLException {
        insertTestData();
        Date fechaAsignacion = new Date();
        asignacionDAO.insert(new EmpleadoProyecto(1, 1, "Desarrollador", fechaAsignacion, 40.0));
        asignacionDAO.insert(new EmpleadoProyecto(1, 2, "Arquitecto", fechaAsignacion, 20.0));

        List<EmpleadoProyecto> asignaciones = asignacionDAO.findByEmpleadoId(1);
        assertEquals(2, asignaciones.size());
    }

    @Test
    public void testUpdateAsignacion() throws SQLException {
        insertTestData();
        Date fechaAsignacion = new Date();
        EmpleadoProyecto asignacion = new EmpleadoProyecto(1, 1, "Desarrollador", fechaAsignacion, 40.0);
        asignacionDAO.insert(asignacion);

        asignacion.setRol("Senior Developer");
        asignacion.setHorasSemanales(35.0);
        asignacionDAO.update(asignacion);

        List<EmpleadoProyecto> asignaciones = asignacionDAO.findByProyectoId(1);
        assertEquals("Senior Developer", asignaciones.get(0).getRol());
        assertEquals(35.0, asignaciones.get(0).getHorasSemanales());
    }

    @Test
    public void testDeleteAsignacion() throws SQLException {
        insertTestData();
        Date fechaAsignacion = new Date();
        EmpleadoProyecto asignacion = new EmpleadoProyecto(1, 1, "Desarrollador", fechaAsignacion, 40.0);
        asignacionDAO.insert(asignacion);

        asignacionDAO.delete(1, 1);

        assertFalse(asignacionDAO.exists(1, 1));
    }

    @Test
    public void testExists() throws SQLException {
        insertTestData();
        Date fechaAsignacion = new Date();
        asignacionDAO.insert(new EmpleadoProyecto(1, 1, "Desarrollador", fechaAsignacion, 40.0));

        assertTrue(asignacionDAO.exists(1, 1));
        assertFalse(asignacionDAO.exists(1, 2));
    }

    @Test
    public void testCountByEmpleado() throws SQLException {
        insertTestData();
        Date fechaAsignacion = new Date();
        asignacionDAO.insert(new EmpleadoProyecto(1, 1, "Desarrollador", fechaAsignacion, 40.0));
        asignacionDAO.insert(new EmpleadoProyecto(1, 2, "Arquitecto", fechaAsignacion, 20.0));

        assertEquals(2, asignacionDAO.countByEmpleado(1));
        assertEquals(0, asignacionDAO.countByEmpleado(3));
    }

    @Test
    public void testCountByProyecto() throws SQLException {
        insertTestData();
        Date fechaAsignacion = new Date();
        asignacionDAO.insert(new EmpleadoProyecto(1, 1, "Desarrollador", fechaAsignacion, 40.0));
        asignacionDAO.insert(new EmpleadoProyecto(2, 1, "Diseñador", fechaAsignacion, 20.0));

        assertEquals(2, asignacionDAO.countByProyecto(1));
        assertEquals(0, asignacionDAO.countByProyecto(3));
    }
}
