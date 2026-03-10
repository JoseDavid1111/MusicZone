# MusicZone

MusicZone is a full-stack web application designed to simulate the core functionality of a digital music streaming platform. Inspired by the visual interfaces of services like Netflix, it allows users to explore a vast catalog of songs, manage personalized playlists, and search for their favorite artists—all through a structured, decoupled architecture.

Overview
The project focuses on the "Music Management" problem, providing a robust solution for organizing musical data. Built with a Three-Tier Architecture, it leverages a Spring Boot REST API and a dynamic frontend to deliver a seamless user experience.

Key Features:

* Secure Authentication: User login system with credential validation against a relational database.
* Netflix-Inspired Catalog: Browse songs with a focus on visual metadata (covers and details).
* Advanced Search: Efficiently find tracks by Title or Artist.
* Playlist Management (CRUD):
* Create, rename, and delete custom playlists.
   * Add or remove songs dynamically from any list.
* Responsive UI: A clean interface built with standard web technologies.
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
-------------------------------------------
## 📋 RequirementsFunctional (Highlights)

* RF1-RF4: Secure login, validation, and error handling.
* RF5-RF7: Full catalog visualization and search filters.
* RF8-RF12: Complete Playlist lifecycle management.

Non-Functional

* Decoupled API-first approach.
* Relational data integrity.
* Cloud-ready configuration.
----------------------------------------------------------
## 📂 Project Structure

```text
MusicZone/
├── backend/           # Spring Boot Application
│   └── src/           # API Logic & Database Config
├── frontend/          # Web Interface
│   ├── css/           # Netflix-style styling
│   ├── js/            # API consumption & DOM logic
│   └── index.html     # Main entry point
└── README.md

-----------------------------------------------------------------
⚙️ Installation & Setup

   1. Clone the repository:
   
