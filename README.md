# Gestor de Proyectos Empresariales

Aplicación de consola en Java para gestionar proyectos y asignaciones de empleados en una empresa de desarrollo de software.

## Qué hace

- CRUD completo de empleados y proyectos
- Asignación de empleados a proyectos con roles y horas semanales
- Consulta de integrantes por proyecto y proyectos por empleado
- Validación de datos (correos únicos, fechas, estados, horas)
- Arquitectura en capas: Model, DAO, Controller, View, Config, Util

## Requisitos

- Java 26+
- MySQL 8.0+
- Maven 3.6+

## Setup

### Base de datos

Crea la base de datos:

```sql
CREATE DATABASE gestor_proyectos;
```

Ejecuta el esquema:

```bash
mysql -u root -p gestor_proyectos < src/main/resources/schema.sql
```

Opcional: carga datos de prueba

```bash
mysql -u root -p gestor_proyectos < src/main/resources/seed_data.sql
```

### Configuración

Copia el archivo de configuración:

```bash
cp src/main/resources/db.properties.example src/main/resources/db.properties
```

Edita `src/main/resources/db.properties` con tus credenciales:

```properties
db.url=jdbc:mysql://localhost:3306/gestor_proyectos
db.username=tu_usuario
db.password=tu_contraseña
```

El archivo `db.properties` está en `.gitignore` para no subir credenciales al repo.

## Ejecutar

Compilar:

```bash
mvn clean compile
```

Ejecutar:

```bash
mvn exec:java
```

O generar JAR:

```bash
mvn package
java -jar target/GestorProyectos-1.0-SNAPSHOT.jar
```

## Tests

```bash
mvn test
```

Usan H2 in-memory para no depender de MySQL en el ambiente de pruebas.

## Uso

### Menú principal

1. Gestión de Empleados
2. Gestión de Proyectos
3. Gestión de Asignaciones
4. Salir

### Empleados

- Registrar: nombre, correo, cargo
- Listar todos
- Buscar por ID
- Actualizar datos
- Eliminar (solo sin asignaciones activas)

### Proyectos

- Registrar: nombre, descripción, fechas, estado
- Listar todos
- Buscar por ID
- Actualizar datos
- Eliminar (solo sin asignaciones activas)

Estados válidos: `PLANIFICADO`, `EN_CURSO`, `FINALIZADO`

### Asignaciones

- Asignar empleado a proyecto con rol y horas
- Consultar integrantes de un proyecto
- Consultar proyectos de un empleado
- Modificar rol y horas
- Retirar empleado de proyecto

Horas semanales: 1-40

## Reglas de negocio

- Correo de empleado único
- Fecha fin >= fecha inicio
- Estado de proyecto: PLANIFICADO, EN_CURSO, FINALIZADO
- Horas semanales: 1-40
- Un empleado no puede estar asignado dos veces al mismo proyecto
- No se puede eliminar empleado/proyecto con asignaciones activas
- Eliminar asignación no borra empleado ni proyecto

## Estructura

```
src/main/java/com/mycompany/gestorproyectos/
├── config/
│   └── DatabaseConfig.java
├── model/
│   ├── Empleado.java
│   ├── Proyecto.java
│   └── EmpleadoProyecto.java
├── dao/
│   ├── EmpleadoDAO.java
│   ├── ProyectoDAO.java
│   └── EmpleadoProyectoDAO.java
├── controller/
│   ├── EmpleadoController.java
│   ├── ProyectoController.java
│   └── AsignacionController.java
├── view/
│   ├── MenuView.java
│   ├── EmpleadoView.java
│   ├── ProyectoView.java
│   └── AsignacionView.java
├── util/
│   ├── DatabaseConnection.java
│   ├── DateUtil.java
│   ├── InputValidator.java
│   └── DatabaseSeeder.java
└── GestorProyectos.java
```

## Datos de prueba

`seed_data.sql` incluye:
- 5 empleados
- 5 proyectos
- 14 asignaciones

Para cargarlos manualmente:

```bash
mysql -u root -p gestor_proyectos < src/main/resources/seed_data.sql
```

O vía DatabaseSeeder:

```bash
mvn exec:java -Dexec.mainClass="com.mycompany.gestorproyectos.util.DatabaseSeeder"
```
