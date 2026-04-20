# MusicZone

-----------------------------------
## 🛠️ Participantes
* Angela Lozano Pulido
* Gabriel Alfonso Vera
* Jose David Meneses 

MusicZone es una aplicación web full-stack diseñada para simular la funcionalidad principal de una plataforma de streaming de música digital. Inspirada en las interfaces visuales de servicios como Netflix, permite a los usuarios explorar un amplio catálogo de canciones, gestionar listas de reproducción personalizadas y buscar a sus artistas favoritos, todo ello a través de una arquitectura estructurada y desacoplada.

Descripción general: El proyecto se centra en la gestión musical, proporcionando una solución robusta para organizar datos musicales. Desarrollada con una arquitectura de tres capas, aprovecha una API REST de Spring Boot y un frontend dinámico para ofrecer una experiencia de usuario fluida.

Características principales:

* Autenticación segura: Sistema de inicio de sesión con validación de credenciales en una base de datos relacional.
* Catálogo inspirado en Netflix: Explora canciones con énfasis en metadatos visuales (portadas y detalles).
* Búsqueda avanzada: Encuentra canciones fácilmente por título o artista.
* Gestión de listas de reproducción (CRUD):
* Crea, renombra y elimina listas de reproducción personalizadas.
   * Agrega o elimina canciones dinámicamente de cualquier lista.
* Interfaz de usuario adaptable: Una interfaz limpia desarrollada con tecnologías web estándar.
-----------------------------------
## 🛠️ Tech StackBackend
* Framework: Spring Boot (Java)
* Architecture: REST API (Controller-Service-Repository pattern)
* Security: Authentication logic for protected endpoints.
* Database: Relational (MySQL/PostgreSQL)

Frontend
* Languages: HTML5, CSS3, JavaScript (Vanilla)
* UI Style: Dark mode, card-based layout (Netflix-style).

DevOps & Tools
* Version Control: Git & GitHub
* Deployment: Cloud-hosted environment (Development/Production parity).

----------------------------------------------------------
## 📂 Project Structure

```text
MusicZone/
├── backend/           # Spring Boot Application
│   └── src/           # API Logic & Database Config
├── frontend/          # Web Interface
│   ├── css/           # styling
│   ├── js/            # API consumption & DOM logic
│   └── index.html     # Main entry point
└── README.md

-----------------------------------------------------------------
⚙️ Installation & Setup

   1. Clone the repository:
   
