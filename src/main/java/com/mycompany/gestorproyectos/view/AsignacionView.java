package com.mycompany.gestorproyectos.view;

public class AsignacionView {
    private MenuView menuView;

    public AsignacionView(MenuView menuView) {
        this.menuView = menuView;
    }

    public Object[] solicitarDatosAsignacion() {
        System.out.println("\n--- ASIGNAR EMPLEADO A PROYECTO ---");
        System.out.print("ID del Empleado: ");
        int empleadoId = menuView.leerEntero();
        System.out.print("ID del Proyecto: ");
        int proyectoId = menuView.leerEntero();
        System.out.print("Rol: ");
        String rol = menuView.leerCadena();
        System.out.print("Fecha de Asignación (dd/MM/yyyy): ");
        String fechaAsignacion = menuView.leerCadena();
        System.out.print("Horas Semanales (1-40): ");
        double horasSemanales = menuView.leerDecimal();
        return new Object[]{empleadoId, proyectoId, rol, fechaAsignacion, horasSemanales};
    }

    public int solicitarIdProyecto() {
        System.out.print("Ingrese el ID del proyecto: ");
        return menuView.leerEntero();
    }

    public int solicitarIdEmpleado() {
        System.out.print("Ingrese el ID del empleado: ");
        return menuView.leerEntero();
    }

    public Object[] solicitarDatosModificacionAsignacion() {
        System.out.println("\n--- MODIFICAR ASIGNACIÓN ---");
        System.out.print("ID del Empleado: ");
        int empleadoId = menuView.leerEntero();
        System.out.print("ID del Proyecto: ");
        int proyectoId = menuView.leerEntero();
        System.out.print("Nuevo Rol: ");
        String rol = menuView.leerCadena();
        System.out.print("Nuevas Horas Semanales (1-40): ");
        double horasSemanales = menuView.leerDecimal();
        return new Object[]{empleadoId, proyectoId, rol, horasSemanales};
    }

    public Object[] solicitarDatosEliminacionAsignacion() {
        System.out.println("\n--- RETIRAR EMPLEADO DE PROYECTO ---");
        System.out.print("ID del Empleado: ");
        int empleadoId = menuView.leerEntero();
        System.out.print("ID del Proyecto: ");
        int proyectoId = menuView.leerEntero();
        return new Object[]{empleadoId, proyectoId};
    }

    public void mostrarResultado(String resultado) {
        menuView.mostrarMensaje(resultado);
    }
}
