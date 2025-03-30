# Proyecto CRUD de Gestión de Usuarios

## Descripción
Este es un proyecto educativo desarrollado para el **SENA - Centro de Comercio y Turismo Armenia, Regional Quindío**. Consiste en un **CRUD (Crear, Leer, Actualizar y Eliminar)** de gestión de usuarios, realizado en **Java con JSP** y utilizando **MySQL** como base de datos a través de **PhpMyAdmin**.

El proyecto tiene como objetivo proporcionar una herramienta funcional y didáctica para el aprendizaje y aplicación de conceptos en el desarrollo web con Java y bases de datos.

## Tecnologías Utilizadas
- **Lenguaje de Programación**: Java
- **Framework**: JSP y Servlets
- **Base de Datos**: MySQL
- **Herramienta de Administración de BD**: PhpMyAdmin
- **Servidor Web**: Apache Tomcat
- **IDE**: NetBeans

## Instalación y Configuración
### Requisitos Previos
Antes de ejecutar el proyecto, asegúrate de tener instalados los siguientes componentes:
- **JDK 17 o superior**
- **Apache Tomcat**
- **MySQL Server**
- **PhpMyAdmin**
- **NetBeans (recomendado) o cualquier otro IDE compatible con Java y Maven**

### Configuración de la Base de Datos
1. **Crear la base de datos**:
   - Importa el script SQL ubicado en el directorio **`Config`** del proyecto.
   - Este script creará la base de datos y poblará las tablas con datos de ejemplo.

2. **Actualizar la configuración de conexión**:
   - Verifica en el archivo **`Config/Conexion.java`** que los datos de conexión sean correctos:
     ```java
     private static final String URL = "jdbc:mysql://localhost:3306/nombre_de_tu_base_de_datos";
     private static final String USER = "tu_usuario";
     private static final String PASSWORD = "tu_contraseña";
     ```

### Ejecución del Proyecto
1. Abre el proyecto en NetBeans.
2. Asegúrate de tener Apache Tomcat configurado.
3. Ejecuta el proyecto desde NetBeans o despliega en Tomcat.
4. Accede a la aplicación a través de **http://localhost:8080/NombreDelProyecto**.

## Estructura del Proyecto
```
├── src/
│   ├── Controller/         # Controladores para la lógica de negocio
│   ├── Model/              # Clases de modelo
│   ├── ModelDAO/           # Acceso a datos
│   ├── views/              # Archivos JSP para la interfaz
├── Config/
│   ├── hardware_store.sql        # Script de la base de datos con datos de ejemplo
├── README.md               # Documentación del proyecto
├── pom.xml                 # Configuración del proyecto Maven
```

## Autores
- **Joaquín H Jiménez Rosas** - Ficha: 2879723
- **Juan David Gallego López** - Ficha: 2879723
- **David Ricardo Graffe Rodríguez** - Ficha: 2879724

## Instructor
**Carlos Alberto Fuel Tulcan**

## Licencia
@Derechos reservados **JuDaJo 2024-2025**

---
Este proyecto está diseñado para fines educativos y de aprendizaje en el contexto del SENA.

