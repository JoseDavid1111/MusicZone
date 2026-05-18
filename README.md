# MusicZone

MusicZone es una aplicación web full-stack para explorar canciones, buscar artistas, iniciar sesión y gestionar playlists personales. El proyecto usa una API REST con Spring Boot, un frontend en React con Vite y una base de datos MongoDB Atlas.

## Participantes

- Angela Lozano Pulido
- Gabriel Alfonso Vera
- Jose David Meneses

## Características

- Registro e inicio de sesión con JWT.
- Catálogo de canciones con búsqueda por título o artista.
- Reproducción de canciones mediante URLs de audio almacenadas en MongoDB.
- Gestión de playlists: crear, editar, eliminar, agregar canciones y quitarlas.
- Reproducción desde playlists, incluyendo opción de canción aleatoria.
- Interfaz web en React con layout protegido para usuarios autenticados.
- Documentación de endpoints con Swagger.

## Tecnologías

- Backend: Java 17, Spring Boot, Spring Security, JWT, Spring Data MongoDB.
- Frontend: React, Vite, React Router.
- Base de datos: MongoDB Atlas.
- Almacenamiento de audios: Supabase Storage mediante URLs públicas o válidas para reproducción.

## Estructura del proyecto

```text
MusicZone/
├── musiczone-backend/      # API REST en Spring Boot
│   ├── src/main/java/      # Controladores, servicios, modelos y repositorios
│   └── src/main/resources/ # Configuración de la aplicación
├── musiczone-react/        # Frontend principal en React + Vite
│   ├── src/                # Componentes, páginas, contexto y servicios
│   ├── package.json
│   └── vite.config.js
├── musiczone-frontend/     # Versión estática anterior; no se usa para correr la app actual
└── README.md
```

## Requisitos

- Java 17 o superior.
- Node.js y npm.
- Acceso a la base de datos MongoDB configurada para el proyecto.

## Ejecución

Abrir una terminal para el backend:

```powershell
cd "...\musiczone-backend"
.\mvnw.cmd spring-boot:run
```

El backend queda disponible en:

```text
http://localhost:8081
```

Swagger:

```text
http://localhost:8081/swagger-ui.html
```

Abrir otra terminal para el frontend:

```powershell
cd "...\musiczone-react"
npm install
npm run dev
```

El frontend queda disponible en:

```text
http://localhost:5173
```

## Configuración

La conexión a MongoDB se configura en:

```text
musiczone-backend/src/main/resources/application.properties
```

