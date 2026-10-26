CREATE DATABASE IF NOT EXISTS gestor_proyectos;
USE gestor_proyectos;

CREATE TABLE IF NOT EXISTS empleados (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    correo VARCHAR(100) NOT NULL UNIQUE,
    cargo VARCHAR(50) NOT NULL
);

CREATE TABLE IF NOT EXISTS proyectos (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    descripcion TEXT,
    fecha_inicio DATE NOT NULL,
    fecha_fin DATE NOT NULL,
    estado VARCHAR(20) NOT NULL CHECK (estado IN ('PLANIFICADO', 'EN_CURSO', 'FINALIZADO'))
);

CREATE TABLE IF NOT EXISTS empleado_proyecto (
    empleado_id INT NOT NULL,
    proyecto_id INT NOT NULL,
    rol VARCHAR(50) NOT NULL,
    fecha_asignacion DATE NOT NULL,
    horas_semanales DECIMAL(4,1) NOT NULL CHECK (horas_semanales > 0 AND horas_semanales <= 40),
    PRIMARY KEY (empleado_id, proyecto_id),
    FOREIGN KEY (empleado_id) REFERENCES empleados(id) ON DELETE RESTRICT,
    FOREIGN KEY (proyecto_id) REFERENCES proyectos(id) ON DELETE RESTRICT
);
