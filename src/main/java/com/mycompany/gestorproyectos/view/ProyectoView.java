package com.mycompany.gestorproyectos.view;

public class ProyectoView {
    private MenuView menuView;

    public ProyectoView(MenuView menuView) {
        this.menuView = menuView;
    }

    public String[] solicitarDatosProyecto() {
        System.out.println("\n--- REGISTRAR PROYECTO ---");
        System.out.print("Nombre: ");
        String nombre = menuView.leerCadena();
        System.out.print("Descripción: ");
        String descripcion = menuView.leerCadena();
        System.out.print("Fecha Inicio (dd/MM/yyyy): ");
        String fechaInicio = menuView.leerCadena();
        System.out.print("Fecha Fin (dd/MM/yyyy): ");
        String fechaFin = menuView.leerCadena();
        System.out.println("Estados disponibles: PLANIFICADO, EN_CURSO, FINALIZADO");
        System.out.print("Estado: ");
        String estado = menuView.leerCadena();
        return new String[]{nombre, descripcion, fechaInicio, fechaFin, estado};
    }

    public int solicitarIdProyecto() {
        System.out.print("Ingrese el ID del proyecto: ");
        return menuView.leerEntero();
    }

    public String[] solicitarDatosActualizacionProyecto() {
        System.out.println("\n--- ACTUALIZAR PROYECTO ---");
        System.out.print("Nuevo Nombre: ");
        String nombre = menuView.leerCadena();
        System.out.print("Nueva Descripción: ");
        String descripcion = menuView.leerCadena();
        System.out.print("Nueva Fecha Inicio (dd/MM/yyyy): ");
        String fechaInicio = menuView.leerCadena();
        System.out.print("Nueva Fecha Fin (dd/MM/yyyy): ");
        String fechaFin = menuView.leerCadena();
        System.out.println("Estados disponibles: PLANIFICADO, EN_CURSO, FINALIZADO");
        System.out.print("Nuevo Estado: ");
        String estado = menuView.leerCadena();
        return new String[]{nombre, descripcion, fechaInicio, fechaFin, estado};
    }

    public void mostrarResultado(String resultado) {
        menuView.mostrarMensaje(resultado);
    }
}
