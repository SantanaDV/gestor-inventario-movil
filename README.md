# Gestor-inventario
Desarrollar una aplicación multiplataforma (móvil y escritorio) para la gestión de inventario y tareas en un almacén. Se utilizará Spring Boot para el backend y Android para la aplicación móvil. Se requiere integración con Git para la colaboración.


## 2. Funcionalidades Principales

* Común (Móvil y Escritorio)
    * Vista de almacén:
        * Mostrar estanterías y productos almacenados mediante una interfaz visual donde se representen los estantes y la cantidad de productos en cada uno.
        * Permitir a los empleados solicitar productos cuando necesiten reabastecimiento.
        * Opción para añadir nuevos productos con datos como nombre, cantidad, ubicación y fecha de ingreso.
        * Notificaciones de alerta cuando un producto está por debajo del umbral mínimo de stock.
        * Base de datos centralizada que garantizará la sincronización de datos en tiempo real entre la aplicación móvil y la de escritorio.

* Aplicación Móvil (Android)
    * Lectura de datos mediante tecnologías avanzadas:
        * QR y código de barras: Para leer información sobre productos y ubicaciones.
    * Funciones principales relacionadas con QR y códigos de barras:
        * Registrar nuevos productos escaneando etiquetas QR.
        * Añadir o desactivar productos al escanear su código y modificar su estado en la base de datos.
        * Escanear el QR de una estantería para obtener un listado de productos almacenados en ella junto con sus cantidades.

* Aplicación de Escritorio
    * Gestor de almacén:
        * Administrar productos, ubicaciones y estanterías con una interfaz gráfica intuitiva.
        * Ver alertas en tiempo real sobre productos con bajo stock.
        * Control de entradas y salidas de productos con historial de movimientos.
    * Gestor de tareas:
        * Creación, edición y eliminación de tareas de forma sencilla.
        * Asignación de tareas a empleados con seguimiento del responsable.
        * Estados de tareas definidos: "Por hacer", "En proceso", "Finalizada".
        * Historial de tareas para garantizar un registro detallado del trabajo realizado.

## 3. Tecnologías a Utilizar

* Backend: Spring Boot
* Base de Datos: PostgreSQL / MySQL
* Aplicación Móvil: Android (Java/Kotlin)
* Aplicación de Escritorio: Java Spring Boot / JavaScript / HTML / CSS…
* Control de Versiones: Git (GitHub/GitLab/Bitbucket)


## 4. División del Trabajo
* Equipo Backend
  * Diseño y creación de la base de datos.
  * Desarrollo de API REST en Spring Boot.
  * Implementación de la lógica de negocio para gestión de inventario y tareas.
  * Integración con las aplicaciones móvil y escritorio.

* Equipo Móvil
    * Desarrollo de la aplicación en Android.
    * Implementación de la lectura NFC, QR y código de barras.
    * Conexión con la API REST para sincronización de datos.

* Equipo Escritorio
    * Desarrollo de la aplicación de escritorio.
    * Creación del gestor de almacén y tareas.
    * Implementación de la vista de alertas.
    * Integración con la API REST para sincronización de datos.



## 6. Plazos y Entregables (ORIENTATIVOS)
   - Semana 1: Definición de requisitos, diseño de BD y configuración del repositorio Git.
   - Semana 2-3: Desarrollo del backend (API REST) y conexión inicial con BD.
   - Semana 4-5: Implementación de la aplicación móvil (UI y funcionalidades básicas).
   - Semana 6-7: Implementación de la aplicación de escritorio (UI y funcionalidades básicas).
   - Semana 8-9: Integración de aplicaciones con el backend y pruebas.
   - Semana 10: Ajustes finales y despliegue.
