package com.mycompany.gestorproyectos.dao;

import com.mycompany.gestorproyectos.model.Proyecto;
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

public class ProyectoDAOTest {
    private Connection connection;
    private ProyectoDAO proyectoDAO;

    @BeforeEach
    public void setUp() throws SQLException {
        connection = DriverManager.getConnection("jdbc:h2:mem:testdb;DB_CLOSE_DELAY=-1", "sa", "");
        createTables();
        proyectoDAO = new ProyectoDAO();
    }

    @AfterEach
    public void tearDown() throws SQLException {
        if (connection != null && !connection.isClosed()) {
            connection.close();
        }
    }

    private void createTables() throws SQLException {
        try (Statement stmt = connection.createStatement()) {
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

    @Test
    public void testInsertProyecto() throws SQLException {
        Date fechaInicio = new Date();
        Date fechaFin = new Date(fechaInicio.getTime() + 86400000 * 30);
        Proyecto proyecto = new Proyecto("Sistema ERP", "Sistema de gestión empresarial", fechaInicio, fechaFin, "PLANIFICADO");
        proyectoDAO.insert(proyecto);

        assertTrue(proyecto.getId() > 0);
        Proyecto encontrado = proyectoDAO.findById(proyecto.getId());
        assertNotNull(encontrado);
        assertEquals("Sistema ERP", encontrado.getNombre());
        assertEquals("PLANIFICADO", encontrado.getEstado());
    }

    @Test
    public void testFindAllProyectos() throws SQLException {
        Date fechaInicio = new Date();
        Date fechaFin = new Date(fechaInicio.getTime() + 86400000 * 30);
        proyectoDAO.insert(new Proyecto("Sistema ERP", "Sistema de gestión", fechaInicio, fechaFin, "PLANIFICADO"));
        proyectoDAO.insert(new Proyecto("App Móvil", "Aplicación móvil", fechaInicio, fechaFin, "EN_CURSO"));

        List<Proyecto> proyectos = proyectoDAO.findAll();
        assertEquals(2, proyectos.size());
    }

    @Test
    public void testFindById() throws SQLException {
        Date fechaInicio = new Date();
        Date fechaFin = new Date(fechaInicio.getTime() + 86400000 * 30);
        Proyecto proyecto = new Proyecto("Sistema ERP", "Sistema de gestión", fechaInicio, fechaFin, "PLANIFICADO");
        proyectoDAO.insert(proyecto);

        Proyecto encontrado = proyectoDAO.findById(proyecto.getId());
        assertNotNull(encontrado);
        assertEquals(proyecto.getId(), encontrado.getId());
    }

    @Test
    public void testFindByIdNotFound() throws SQLException {
        Proyecto encontrado = proyectoDAO.findById(999);
        assertNull(encontrado);
    }

    @Test
    public void testUpdateProyecto() throws SQLException {
        Date fechaInicio = new Date();
        Date fechaFin = new Date(fechaInicio.getTime() + 86400000 * 30);
        Proyecto proyecto = new Proyecto("Sistema ERP", "Sistema de gestión", fechaInicio, fechaFin, "PLANIFICADO");
        proyectoDAO.insert(proyecto);

        proyecto.setEstado("EN_CURSO");
        proyecto.setDescripcion("Sistema de gestión empresarial actualizado");
        proyectoDAO.update(proyecto);

        Proyecto actualizado = proyectoDAO.findById(proyecto.getId());
        assertEquals("EN_CURSO", actualizado.getEstado());
        assertEquals("Sistema de gestión empresarial actualizado", actualizado.getDescripcion());
    }

    @Test
    public void testDeleteProyecto() throws SQLException {
        Date fechaInicio = new Date();
        Date fechaFin = new Date(fechaInicio.getTime() + 86400000 * 30);
        Proyecto proyecto = new Proyecto("Sistema ERP", "Sistema de gestión", fechaInicio, fechaFin, "PLANIFICADO");
        proyectoDAO.insert(proyecto);

        proyectoDAO.delete(proyecto.getId());

        Proyecto eliminado = proyectoDAO.findById(proyecto.getId());
        assertNull(eliminado);
    }

    @Test
    public void testDeleteProyectoWithAssignments() throws SQLException {
        Date fechaInicio = new Date();
        Date fechaFin = new Date(fechaInicio.getTime() + 86400000 * 30);
        Proyecto proyecto = new Proyecto("Sistema ERP", "Sistema de gestión", fechaInicio, fechaFin, "PLANIFICADO");
        proyectoDAO.insert(proyecto);

        try (Statement stmt = connection.createStatement()) {
            stmt.execute("INSERT INTO empleado_proyecto (empleado_id, proyecto_id, rol, fecha_asignacion, horas_semanales) " +
                "VALUES (1, " + proyecto.getId() + ", 'Desarrollador', CURRENT_DATE, 40)");
        }

        assertThrows(SQLException.class, () -> proyectoDAO.delete(proyecto.getId()));
    }

    @Test
    public void testExistsById() throws SQLException {
        Date fechaInicio = new Date();
        Date fechaFin = new Date(fechaInicio.getTime() + 86400000 * 30);
        Proyecto proyecto = new Proyecto("Sistema ERP", "Sistema de gestión", fechaInicio, fechaFin, "PLANIFICADO");
        proyectoDAO.insert(proyecto);

        assertTrue(proyectoDAO.existsById(proyecto.getId()));
        assertFalse(proyectoDAO.existsById(999));
    }

    @Test
    public void testHasActiveAssignments() throws SQLException {
        Date fechaInicio = new Date();
        Date fechaFin = new Date(fechaInicio.getTime() + 86400000 * 30);
        Proyecto proyecto = new Proyecto("Sistema ERP", "Sistema de gestión", fechaInicio, fechaFin, "PLANIFICADO");
        proyectoDAO.insert(proyecto);

        assertFalse(proyectoDAO.hasActiveAssignments(proyecto.getId()));

        try (Statement stmt = connection.createStatement()) {
            stmt.execute("INSERT INTO empleado_proyecto (empleado_id, proyecto_id, rol, fecha_asignacion, horas_semanales) " +
                "VALUES (1, " + proyecto.getId() + ", 'Desarrollador', CURRENT_DATE, 40)");
        }

        assertTrue(proyectoDAO.hasActiveAssignments(proyecto.getId()));
    }
}
