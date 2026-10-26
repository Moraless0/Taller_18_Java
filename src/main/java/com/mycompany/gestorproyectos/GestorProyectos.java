package com.mycompany.gestorproyectos;

import com.mycompany.gestorproyectos.controller.AsignacionController;
import com.mycompany.gestorproyectos.controller.EmpleadoController;
import com.mycompany.gestorproyectos.controller.ProyectoController;
import com.mycompany.gestorproyectos.util.DatabaseConnection;
import com.mycompany.gestorproyectos.view.AsignacionView;
import com.mycompany.gestorproyectos.view.EmpleadoView;
import com.mycompany.gestorproyectos.view.MenuView;
import com.mycompany.gestorproyectos.view.ProyectoView;

public class GestorProyectos {

    public static void main(String[] args) {
        try {
            MenuView menuView = new MenuView();
            EmpleadoController empleadoController = new EmpleadoController();
            ProyectoController proyectoController = new ProyectoController();
            AsignacionController asignacionController = new AsignacionController();

            EmpleadoView empleadoView = new EmpleadoView(menuView);
            ProyectoView proyectoView = new ProyectoView(menuView);
            AsignacionView asignacionView = new AsignacionView(menuView);

            boolean running = true;

            while (running) {
                int opcion = menuView.mostrarMenuPrincipal();

                switch (opcion) {
                    case 1:
                        menuEmpleados(menuView, empleadoController, empleadoView);
                        break;
                    case 2:
                        menuProyectos(menuView, proyectoController, proyectoView);
                        break;
                    case 3:
                        menuAsignaciones(menuView, asignacionController, asignacionView);
                        break;
                    case 4:
                        running = false;
                        menuView.mostrarMensaje("Saliendo del sistema...");
                        break;
                    default:
                        menuView.mostrarMensaje("Opción inválida. Intente nuevamente.");
                }
            }

            menuView.cerrar();
            DatabaseConnection.closeConnection();
        } catch (Exception e) {
            System.err.println("Error fatal: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private static void menuEmpleados(MenuView menuView, EmpleadoController controller, EmpleadoView view) {
        boolean enSubmenu = true;
        while (enSubmenu) {
            int opcion = menuView.mostrarMenuEmpleados();

            switch (opcion) {
                case 1:
                    String[] datos = view.solicitarDatosEmpleado();
                    String resultado = controller.registrarEmpleado(datos[0], datos[1], datos[2]);
                    view.mostrarResultado(resultado);
                    break;
                case 2:
                    view.mostrarResultado(controller.listarEmpleados());
                    break;
                case 3:
                    int id = view.solicitarIdEmpleado();
                    view.mostrarResultado(controller.buscarEmpleadoPorId(id));
                    break;
                case 4:
                    id = view.solicitarIdEmpleado();
                    String[] datosActualizacion = view.solicitarDatosActualizacionEmpleado();
                    view.mostrarResultado(controller.actualizarEmpleado(id, datosActualizacion[0], datosActualizacion[1], datosActualizacion[2]));
                    break;
                case 5:
                    id = view.solicitarIdEmpleado();
                    view.mostrarResultado(controller.eliminarEmpleado(id));
                    break;
                case 6:
                    enSubmenu = false;
                    break;
                default:
                    menuView.mostrarMensaje("Opción inválida. Intente nuevamente.");
            }
        }
    }

    private static void menuProyectos(MenuView menuView, ProyectoController controller, ProyectoView view) {
        boolean enSubmenu = true;
        while (enSubmenu) {
            int opcion = menuView.mostrarMenuProyectos();

            switch (opcion) {
                case 1:
                    String[] datos = view.solicitarDatosProyecto();
                    String resultado = controller.registrarProyecto(datos[0], datos[1], datos[2], datos[3], datos[4]);
                    view.mostrarResultado(resultado);
                    break;
                case 2:
                    view.mostrarResultado(controller.listarProyectos());
                    break;
                case 3:
                    int id = view.solicitarIdProyecto();
                    view.mostrarResultado(controller.buscarProyectoPorId(id));
                    break;
                case 4:
                    id = view.solicitarIdProyecto();
                    String[] datosActualizacion = view.solicitarDatosActualizacionProyecto();
                    view.mostrarResultado(controller.actualizarProyecto(id, datosActualizacion[0], datosActualizacion[1], datosActualizacion[2], datosActualizacion[3], datosActualizacion[4]));
                    break;
                case 5:
                    id = view.solicitarIdProyecto();
                    view.mostrarResultado(controller.eliminarProyecto(id));
                    break;
                case 6:
                    enSubmenu = false;
                    break;
                default:
                    menuView.mostrarMensaje("Opción inválida. Intente nuevamente.");
            }
        }
    }

    private static void menuAsignaciones(MenuView menuView, AsignacionController controller, AsignacionView view) {
        boolean enSubmenu = true;
        while (enSubmenu) {
            int opcion = menuView.mostrarMenuAsignaciones();

            switch (opcion) {
                case 1:
                    Object[] datos = view.solicitarDatosAsignacion();
                    String resultado = controller.asignarEmpleadoAProyecto(
                        (int) datos[0],
                        (int) datos[1],
                        (String) datos[2],
                        (String) datos[3],
                        (double) datos[4]
                    );
                    view.mostrarResultado(resultado);
                    break;
                case 2:
                    int proyectoId = view.solicitarIdProyecto();
                    view.mostrarResultado(controller.consultarIntegrantesProyecto(proyectoId));
                    break;
                case 3:
                    int empleadoId = view.solicitarIdEmpleado();
                    view.mostrarResultado(controller.consultarProyectosEmpleado(empleadoId));
                    break;
                case 4:
                    Object[] datosModificacion = view.solicitarDatosModificacionAsignacion();
                    view.mostrarResultado(controller.modificarAsignacion(
                        (int) datosModificacion[0],
                        (int) datosModificacion[1],
                        (String) datosModificacion[2],
                        (double) datosModificacion[3]
                    ));
                    break;
                case 5:
                    Object[] datosEliminacion = view.solicitarDatosEliminacionAsignacion();
                    view.mostrarResultado(controller.retirarEmpleadoDeProyecto(
                        (int) datosEliminacion[0],
                        (int) datosEliminacion[1]
                    ));
                    break;
                case 6:
                    enSubmenu = false;
                    break;
                default:
                    menuView.mostrarMensaje("Opción inválida. Intente nuevamente.");
            }
        }
    }
}
