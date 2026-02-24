# Plataforma de Gestión de Bandas Musicales

Sistema web para la gestión de bandas musicales que permite administrar músicos e instrumentos, así como visualizar canciones según el rol del usuario.  
Incluye una aplicación móvil destinada exclusivamente a la visualización de contenido.

---

## Descripción del Proyecto

Este proyecto fue desarrollado como parte de un curso de Ingeniería de Software.

La plataforma permite a los líderes de banda administrar su banda, mientras que músicos y visitantes pueden acceder a las canciones según los permisos definidos.

El alcance del sistema fue estrictamente definido y validado en reuniones con el cliente.  
No se implementaron funcionalidades fuera de los requerimientos aprobados.

---

## Integrantes del Equipo

- Armas Arteaga Angel Zaid
- Barrera Briones Davor Sadrak
- Campuzano Hernández Ulises
- Gonzalez Morales Bernardo Jesé
- Ríos Bello Angeles Dennise

---

## Roles del Sistema

### Visitante

- No requiere autenticación.
- Puede visualizar canciones públicas.
- Puede filtrar canciones por:
    - Nombre de la banda
    - Tono
    - BPM (velocidad)

No puede modificar información.

---

### Músico

- Usuario autenticado.
- Pertenece a una sola banda.

Permisos:
- Visualizar canciones públicas.
- Visualizar canciones privadas de su banda.

Restricciones:
- No puede registrar canciones.
- No puede editar contenido.
- No puede administrar músicos.
- No puede administrar instrumentos.

---

### Líder

- Usuario autenticado.
- Administra una sola banda.

Permisos:
- Registrar, editar y eliminar músicos.
- Restablecer contraseñas de músicos.
- Registrar, editar y eliminar instrumentos.
- Registrar, editar y eliminar canciones.
- Definir visibilidad de canciones (pública o privada).
- Gestionar secciones de las canciones.

Restricciones:
- No puede transferir liderazgo.
- No puede administrar otras bandas.

---

## Aplicación Web y Aplicación Móvil

### Aplicación Web

Es el núcleo del sistema y permite la administración completa de:

- Banda
- Músicos
- Instrumentos
- Canciones

### Aplicación Móvil

Está destinada exclusivamente a la visualización.

Permite:
- Visualizar canciones públicas.
- Visualizar canciones de la banda correspondiente (según rol).

No permite:
- Registro
- Edición
- Eliminación
- Administración

---

## Alcance del Proyecto

El sistema se apega estrictamente a los requerimientos validados con el cliente.  
No se implementaron funcionalidades adicionales fuera del alcance aprobado.