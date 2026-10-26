-- Seed Data for Gestor de Proyectos Empresariales
USE gestor_proyectos;

-- Insert sample employees
INSERT INTO empleados (nombre, correo, cargo) VALUES
('Juan Pérez', 'juan.perez@empresa.com', 'Desarrollador Senior'),
('María López', 'maria.lopez@empresa.com', 'Diseñadora UX'),
('Carlos García', 'carlos.garcia@empresa.com', 'Arquitecto de Software'),
('Ana Martínez', 'ana.martinez@empresa.com', 'Analista de Negocios'),
('Pedro Sánchez', 'pedro.sanchez@empresa.com', 'Tester QA');

-- Insert sample projects
INSERT INTO proyectos (nombre, descripcion, fecha_inicio, fecha_fin, estado) VALUES
('Sistema ERP', 'Sistema de planificación de recursos empresariales', '2024-01-15', '2024-12-31', 'EN_CURSO'),
('App Móvil Bancaria', 'Aplicación móvil para banca online', '2024-03-01', '2024-09-30', 'EN_CURSO'),
('Portal de E-commerce', 'Plataforma de comercio electrónico B2B', '2024-02-01', '2024-08-31', 'FINALIZADO'),
('Sistema de CRM', 'Gestión de relaciones con clientes', '2024-06-01', '2025-03-31', 'PLANIFICADO'),
('Dashboard Analítico', 'Panel de control para análisis de datos', '2024-04-01', '2024-10-31', 'EN_CURSO');

-- Insert sample assignments
INSERT INTO empleado_proyecto (empleado_id, proyecto_id, rol, fecha_asignacion, horas_semanales) VALUES
(1, 1, 'Líder Técnico', '2024-01-15', 40),
(2, 1, 'Diseñadora UI/UX', '2024-01-20', 35),
(3, 1, 'Arquitecto', '2024-01-15', 30),
(1, 2, 'Desarrollador Mobile', '2024-03-01', 38),
(2, 2, 'Diseñadora UX', '2024-03-05', 25),
(4, 2, 'Analista', '2024-03-01', 20),
(1, 3, 'Desarrollador Backend', '2024-02-01', 40),
(3, 3, 'Arquitecto', '2024-02-01', 25),
(5, 3, 'Tester', '2024-02-10', 30),
(4, 4, 'Analista de Requisitos', '2024-06-01', 35),
(3, 4, 'Arquitecto', '2024-06-05', 20),
(1, 5, 'Desarrollador Full Stack', '2024-04-01', 40),
(2, 5, 'Diseñadora UI', '2024-04-05', 30),
(5, 5, 'QA Engineer', '2024-04-10', 35);
