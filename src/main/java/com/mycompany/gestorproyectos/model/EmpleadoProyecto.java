package com.mycompany.gestorproyectos.model;

import java.util.Date;

public class EmpleadoProyecto {
    private int empleadoId;
    private int proyectoId;
    private String rol;
    private Date fechaAsignacion;
    private double horasSemanales;

    private String empleadoNombre;
    private String empleadoCargo;
    private String proyectoNombre;

    public EmpleadoProyecto() {
    }

    public EmpleadoProyecto(int empleadoId, int proyectoId, String rol, Date fechaAsignacion, double horasSemanales) {
        this.empleadoId = empleadoId;
        this.proyectoId = proyectoId;
        this.rol = rol;
        this.fechaAsignacion = fechaAsignacion;
        this.horasSemanales = horasSemanales;
    }

    public int getEmpleadoId() {
        return empleadoId;
    }

    public void setEmpleadoId(int empleadoId) {
        this.empleadoId = empleadoId;
    }

    public int getProyectoId() {
        return proyectoId;
    }

    public void setProyectoId(int proyectoId) {
        this.proyectoId = proyectoId;
    }

    public String getRol() {
        return rol;
    }

    public void setRol(String rol) {
        this.rol = rol;
    }

    public Date getFechaAsignacion() {
        return fechaAsignacion;
    }

    public void setFechaAsignacion(Date fechaAsignacion) {
        this.fechaAsignacion = fechaAsignacion;
    }

    public double getHorasSemanales() {
        return horasSemanales;
    }

    public void setHorasSemanales(double horasSemanales) {
        this.horasSemanales = horasSemanales;
    }

    public String getEmpleadoNombre() {
        return empleadoNombre;
    }

    public void setEmpleadoNombre(String empleadoNombre) {
        this.empleadoNombre = empleadoNombre;
    }

    public String getEmpleadoCargo() {
        return empleadoCargo;
    }

    public void setEmpleadoCargo(String empleadoCargo) {
        this.empleadoCargo = empleadoCargo;
    }

    public String getProyectoNombre() {
        return proyectoNombre;
    }

    public void setProyectoNombre(String proyectoNombre) {
        this.proyectoNombre = proyectoNombre;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Empleado ID: ").append(empleadoId);
        if (empleadoNombre != null) {
            sb.append(" (").append(empleadoNombre).append(" - ").append(empleadoCargo).append(")");
        }
        sb.append(" | Proyecto ID: ").append(proyectoId);
        if (proyectoNombre != null) {
            sb.append(" (").append(proyectoNombre).append(")");
        }
        sb.append(" | Rol: ").append(rol);
        sb.append(" | Fecha Asignación: ").append(fechaAsignacion);
        sb.append(" | Horas Semanales: ").append(horasSemanales);
        return sb.toString();
    }
}
