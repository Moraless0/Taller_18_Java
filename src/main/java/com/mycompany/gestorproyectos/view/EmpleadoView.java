package com.mycompany.gestorproyectos.view;

public class EmpleadoView {
    private MenuView menuView;

    public EmpleadoView(MenuView menuView) {
        this.menuView = menuView;
    }

    public String[] solicitarDatosEmpleado() {
        System.out.println("\n--- REGISTRAR EMPLEADO ---");
        System.out.print("Nombre: ");
        String nombre = menuView.leerCadena();
        System.out.print("Correo: ");
        String correo = menuView.leerCadena();
        System.out.print("Cargo: ");
        String cargo = menuView.leerCadena();
        return new String[]{nombre, correo, cargo};
    }

    public int solicitarIdEmpleado() {
        System.out.print("Ingrese el ID del empleado: ");
        return menuView.leerEntero();
    }

    public String[] solicitarDatosActualizacionEmpleado() {
        System.out.println("\n--- ACTUALIZAR EMPLEADO ---");
        System.out.print("Nuevo Nombre: ");
        String nombre = menuView.leerCadena();
        System.out.print("Nuevo Correo: ");
        String correo = menuView.leerCadena();
        System.out.print("Nuevo Cargo: ");
        String cargo = menuView.leerCadena();
        return new String[]{nombre, correo, cargo};
    }

    public void mostrarResultado(String resultado) {
        menuView.mostrarMensaje(resultado);
    }
}
