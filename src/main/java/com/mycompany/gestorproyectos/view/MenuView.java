package com.mycompany.gestorproyectos.view;

import java.util.Scanner;

public class MenuView {
    private Scanner scanner;

    public MenuView() {
        this.scanner = new Scanner(System.in);
    }

    public int mostrarMenuPrincipal() {
        System.out.println("\n=== GESTOR DE PROYECTOS EMPRESARIALES ===");
        System.out.println("1. Gestión de Empleados");
        System.out.println("2. Gestión de Proyectos");
        System.out.println("3. Gestión de Asignaciones");
        System.out.println("4. Salir");
        System.out.print("Seleccione una opción: ");
        return leerEntero();
    }

    public int mostrarMenuEmpleados() {
        System.out.println("\n=== GESTIÓN DE EMPLEADOS ===");
        System.out.println("1. Registrar Empleado");
        System.out.println("2. Listar Todos los Empleados");
        System.out.println("3. Buscar Empleado por ID");
        System.out.println("4. Actualizar Empleado");
        System.out.println("5. Eliminar Empleado");
        System.out.println("6. Volver al Menú Principal");
        System.out.print("Seleccione una opción: ");
        return leerEntero();
    }

    public int mostrarMenuProyectos() {
        System.out.println("\n=== GESTIÓN DE PROYECTOS ===");
        System.out.println("1. Registrar Proyecto");
        System.out.println("2. Listar Todos los Proyectos");
        System.out.println("3. Buscar Proyecto por ID");
        System.out.println("4. Actualizar Proyecto");
        System.out.println("5. Eliminar Proyecto");
        System.out.println("6. Volver al Menú Principal");
        System.out.print("Seleccione una opción: ");
        return leerEntero();
    }

    public int mostrarMenuAsignaciones() {
        System.out.println("\n=== GESTIÓN DE ASIGNACIONES ===");
        System.out.println("1. Asignar Empleado a Proyecto");
        System.out.println("2. Consultar Integrantes de un Proyecto");
        System.out.println("3. Consultar Proyectos de un Empleado");
        System.out.println("4. Modificar Asignación");
        System.out.println("5. Retirar Empleado de Proyecto");
        System.out.println("6. Volver al Menú Principal");
        System.out.print("Seleccione una opción: ");
        return leerEntero();
    }

    public void mostrarMensaje(String mensaje) {
        System.out.println(mensaje);
    }

    public int leerEntero() {
        try {
            return Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            return -1;
        }
    }

    public double leerDecimal() {
        try {
            return Double.parseDouble(scanner.nextLine());
        } catch (NumberFormatException e) {
            return -1;
        }
    }

    public String leerCadena() {
        return scanner.nextLine();
    }

    public void cerrar() {
        scanner.close();
    }
}
