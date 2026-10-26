package com.mycompany.gestorproyectos.dao;

import com.mycompany.gestorproyectos.model.Empleado;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

public class EmpleadoDAOTest {
    private Connection connection;
    private EmpleadoDAO empleadoDAO;

    @BeforeEach
    public void setUp() throws SQLException {
        connection = DriverManager.getConnection("jdbc:h2:mem:testdb;DB_CLOSE_DELAY=-1", "sa", "");
        createTables();
        empleadoDAO = new EmpleadoDAO();
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
    public void testInsertEmpleado() throws SQLException {
        Empleado empleado = new Empleado("Juan Pérez", "juan@example.com", "Desarrollador");
        empleadoDAO.insert(empleado);

        assertTrue(empleado.getId() > 0);
        Empleado encontrado = empleadoDAO.findById(empleado.getId());
        assertNotNull(encontrado);
        assertEquals("Juan Pérez", encontrado.getNombre());
        assertEquals("juan@example.com", encontrado.getCorreo());
        assertEquals("Desarrollador", encontrado.getCargo());
    }

    @Test
    public void testInsertEmpleadoWithDuplicateEmail() throws SQLException {
        Empleado empleado1 = new Empleado("Juan Pérez", "juan@example.com", "Desarrollador");
        empleadoDAO.insert(empleado1);

        Empleado empleado2 = new Empleado("Maria López", "juan@example.com", "Diseñador");
        assertThrows(SQLException.class, () -> empleadoDAO.insert(empleado2));
    }

    @Test
    public void testFindAllEmpleados() throws SQLException {
        empleadoDAO.insert(new Empleado("Juan Pérez", "juan@example.com", "Desarrollador"));
        empleadoDAO.insert(new Empleado("Maria López", "maria@example.com", "Diseñador"));

        List<Empleado> empleados = empleadoDAO.findAll();
        assertEquals(2, empleados.size());
    }

    @Test
    public void testFindById() throws SQLException {
        Empleado empleado = new Empleado("Juan Pérez", "juan@example.com", "Desarrollador");
        empleadoDAO.insert(empleado);

        Empleado encontrado = empleadoDAO.findById(empleado.getId());
        assertNotNull(encontrado);
        assertEquals(empleado.getId(), encontrado.getId());
    }

    @Test
    public void testFindByIdNotFound() throws SQLException {
        Empleado encontrado = empleadoDAO.findById(999);
        assertNull(encontrado);
    }

    @Test
    public void testUpdateEmpleado() throws SQLException {
        Empleado empleado = new Empleado("Juan Pérez", "juan@example.com", "Desarrollador");
        empleadoDAO.insert(empleado);

        empleado.setNombre("Juan Carlos Pérez");
        empleado.setCargo("Senior Developer");
        empleadoDAO.update(empleado);

        Empleado actualizado = empleadoDAO.findById(empleado.getId());
        assertEquals("Juan Carlos Pérez", actualizado.getNombre());
        assertEquals("Senior Developer", actualizado.getCargo());
    }

    @Test
    public void testDeleteEmpleado() throws SQLException {
        Empleado empleado = new Empleado("Juan Pérez", "juan@example.com", "Desarrollador");
        empleadoDAO.insert(empleado);

        empleadoDAO.delete(empleado.getId());

        Empleado eliminado = empleadoDAO.findById(empleado.getId());
        assertNull(eliminado);
    }

    @Test
    public void testDeleteEmpleadoWithAssignments() throws SQLException {
        Empleado empleado = new Empleado("Juan Pérez", "juan@example.com", "Desarrollador");
        empleadoDAO.insert(empleado);

        try (Statement stmt = connection.createStatement()) {
            stmt.execute("INSERT INTO empleado_proyecto (empleado_id, proyecto_id, rol, fecha_asignacion, horas_semanales) " +
                "VALUES (" + empleado.getId() + ", 1, 'Desarrollador', CURRENT_DATE, 40)");
        }

        assertThrows(SQLException.class, () -> empleadoDAO.delete(empleado.getId()));
    }

    @Test
    public void testExistsById() throws SQLException {
        Empleado empleado = new Empleado("Juan Pérez", "juan@example.com", "Desarrollador");
        empleadoDAO.insert(empleado);

        assertTrue(empleadoDAO.existsById(empleado.getId()));
        assertFalse(empleadoDAO.existsById(999));
    }

    @Test
    public void testExistsByEmail() throws SQLException {
        Empleado empleado = new Empleado("Juan Pérez", "juan@example.com", "Desarrollador");
        empleadoDAO.insert(empleado);

        assertTrue(empleadoDAO.existsByEmail("juan@example.com"));
        assertFalse(empleadoDAO.existsByEmail("noexiste@example.com"));
    }

    @Test
    public void testExistsByEmailExcludingId() throws SQLException {
        Empleado empleado1 = new Empleado("Juan Pérez", "juan@example.com", "Desarrollador");
        empleadoDAO.insert(empleado1);

        Empleado empleado2 = new Empleado("Maria López", "maria@example.com", "Diseñador");
        empleadoDAO.insert(empleado2);

        assertTrue(empleadoDAO.existsByEmailExcludingId("juan@example.com", empleado2.getId()));
        assertFalse(empleadoDAO.existsByEmailExcludingId("juan@example.com", empleado1.getId()));
    }

    @Test
    public void testHasActiveAssignments() throws SQLException {
        Empleado empleado = new Empleado("Juan Pérez", "juan@example.com", "Desarrollador");
        empleadoDAO.insert(empleado);

        assertFalse(empleadoDAO.hasActiveAssignments(empleado.getId()));

        try (Statement stmt = connection.createStatement()) {
            stmt.execute("INSERT INTO empleado_proyecto (empleado_id, proyecto_id, rol, fecha_asignacion, horas_semanales) " +
                "VALUES (" + empleado.getId() + ", 1, 'Desarrollador', CURRENT_DATE, 40)");
        }

        assertTrue(empleadoDAO.hasActiveAssignments(empleado.getId()));
    }
}
