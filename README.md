# MusicZone

-----------------------------------
## 👤 Participantes
* Angela Lozano Pulido
* Gabriel Alfonso Vera
* Jose David Meneses 

MusicZone es una aplicación web full-stack diseñada para simular la funcionalidad principal de una plataforma de streaming de música digital. Inspirada en las interfaces visuales de servicios como Spotify, permite a los usuarios explorar un amplio catálogo de canciones, gestionar listas de reproducción personalizadas y buscar a sus artistas favoritos, todo ello a través de una arquitectura estructurada y desacoplada.

El proyecto se centra en la gestión musical, proporcionando una solución robusta para organizar datos musicales. Desarrollada con una arquitectura de tres capas, aprovecha una API REST de Spring Boot y un frontend dinámico para ofrecer una experiencia de usuario fluida.

Características principales:

* Autenticación segura: Sistema de inicio de sesión con validación de credenciales en una base de datos no relacional.
* Catálogo inspirado en Spotify: Explora canciones con énfasis en información y en su guardado.
* Búsqueda avanzada: Encuentra canciones fácilmente por título o artista.
* Gestión de listas de reproducción (CRUD):
* Crea, renombra, busca y elimina listas de reproducción personalizadas.
   * Agrega o elimina canciones dinámicamente de cualquier lista.
* Interfaz de usuario adaptable: Una interfaz limpia desarrollada con tecnologías web estándar.
-----------------------------------
## 🛠️ Tech StackBackend
* Framework: Spring Boot (Java), Reacr Vite
* Languages: React, Java, JavaScript
  
* Architecture: REST API (Controller-Service-Repository pattern)
  
* Security: Se proyeca utiliar tokens con jwt para asegurar las credenciales y evitar exponerlas en los viajes de los json, principalemnte en la parte de login
  
Database: Se contempla usar 2 ramas:
  * Supabase: Se guardaran los archivos MP3 de las canciones
  * MongoDB | Atlas: Se guardará la base de datos no relacional con las siguientes colecciones
    <img width="539" height="420" alt="image" src="https://github.com/user-attachments/assets/5e56cb09-3290-4061-95bb-4bd7ed9778c5" />

* Flujo de funcionamiento: Teniendo en cuenta los nuevos cambios, se proyecta:
  <img width="571" height="364" alt="image" src="https://github.com/user-attachments/assets/9de34c16-2fd8-4cbf-be3e-75575255ca3c" />


----------------------------------------------------------
## 📂 Project Structure

```text
MusicZone/
├── musiczone-backend/           # Spring Boot Application
│   └── src/           # API Logic & Database Config
├── musiczone-react/          # Web Interface
│   ├── node_modules/           # styling
│   ├── src/            # API consumption & DOM logic
│   └── index.html     # Main entry point
|   └── package-lock.json
|   └── package.json
|   └── vite.confing.js  
└── README.md

-----------------------------------------------------------------
⚙️ Installation & Setup

  1. Clone the repository:
     git clone https://github.com/JoseDavid1111/MusicZone.git
     cd MusicZone
