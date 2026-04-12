-- ============================================================
--  MusicZone — Script completo de Base de Datos Relacional
-- ============================================================

--  0. PREPARACIÓN
-- DROP DATABASE IF EXISTS musiczone;
-- CREATE DATABASE musiczone CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
USE musiczone;

--  1. TABLA: users
DROP TABLE IF EXISTS usuario;
CREATE TABLE usuario (
    id_usuario         BIGINT       NOT NULL AUTO_INCREMENT,
    nombre_usuario   VARCHAR(50)  NOT NULL,
    password   VARCHAR(255) NOT NULL,   -- Hash BCrypt (mínimo 60 chars)
    correo      VARCHAR(100) NOT NULL,
    active     BOOLEAN      NOT NULL DEFAULT TRUE,
    fecha_creacion TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP,

    CONSTRAINT pk_usuario        PRIMARY KEY (id_usuario),
    CONSTRAINT uq_nombre_usuario  UNIQUE (nombre_usuario),
    CONSTRAINT uq_usuario_correo  UNIQUE (correo)
);

--  2. TABLA: artist
DROP TABLE IF EXISTS artista;
CREATE TABLE artista (
    id_artista      BIGINT       NOT NULL AUTO_INCREMENT,
    nombre    VARCHAR(150) NOT NULL,
    genero   VARCHAR(80),
    pais VARCHAR(80),
    bio     TEXT,
    perfil_url VARCHAR(500),

    CONSTRAINT pk_artista PRIMARY KEY (id_artista)
);

--  3. TABLA: albums
DROP TABLE IF EXISTS album;
CREATE TABLE album (
    id_album     BIGINT       NOT NULL AUTO_INCREMENT,
    titulo        VARCHAR(200) NOT NULL,
    id_artista    BIGINT       NOT NULL,
    year_lanzamiento YEAR,
    portada_url    VARCHAR(500),

    CONSTRAINT pk_album        PRIMARY KEY (id_album),
    CONSTRAINT fk_album_artista FOREIGN KEY (id_artista)
        REFERENCES artista(id_artista)
        ON UPDATE CASCADE
        ON DELETE RESTRICT
);

--  4. TABLA: cancion
DROP TABLE IF EXISTS cancion;
CREATE TABLE cancion (
    id_cancion       BIGINT       NOT NULL AUTO_INCREMENT,
    titulo            VARCHAR(200) NOT NULL,
    id_artista        BIGINT       NOT NULL,
    id_album         BIGINT,                -- NULL = single sin álbum
    duracion_segundos INT,
    numero_track     INT,
    genero            VARCHAR(80),
    year_lanzamiento     YEAR,

    CONSTRAINT pk_cancion        PRIMARY KEY (id_cancion),
    CONSTRAINT fk_cancion_artista FOREIGN KEY (id_artista)
        REFERENCES artista(id_artista)
        ON UPDATE CASCADE
        ON DELETE RESTRICT,
    CONSTRAINT fk_cancion_album  FOREIGN KEY (id_album)
        REFERENCES album(id_album)
        ON UPDATE CASCADE
        ON DELETE SET NULL
);

-- ─────────────────────────────────────────────────────────────
--  5. TABLA: playlists
DROP TABLE IF EXISTS playlist;
CREATE TABLE playlist (
    id_playlist          BIGINT       NOT NULL AUTO_INCREMENT,
    nombre        VARCHAR(150) NOT NULL,
    id_usuario     BIGINT       NOT NULL,
    descripcion TEXT,
    fecha_creacion  TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP,
    fecha_actualizacion  TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP
                             ON UPDATE CURRENT_TIMESTAMP,

    CONSTRAINT pk_playlist      PRIMARY KEY (id_playlist),
    CONSTRAINT fk_playlist_usuario FOREIGN KEY (id_usuario)
        REFERENCES usuario(id_usuario)
        ON UPDATE CASCADE
        ON DELETE CASCADE   -- Si se borra el usuario, se borran sus playlists
);

--  6. TABLA: playlist_songs  (tabla puente N:M)
DROP TABLE IF EXISTS cancion_playlist;
CREATE TABLE cancion_playlist (
    id_playlist BIGINT    NOT NULL,
    id_cancion     BIGINT    NOT NULL,
    posicion    INT,                        -- Orden dentro de la playlist
    fecha_agregada    TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,

    CONSTRAINT pk_cancion_playlist    PRIMARY KEY (id_playlist, id_cancion),
    CONSTRAINT uq_playlist_posicion UNIQUE (id_playlist, posicion),
    CONSTRAINT fk_ps_playlist       FOREIGN KEY (id_playlist)
        REFERENCES playlist(id_playlist)
        ON UPDATE CASCADE
        ON DELETE CASCADE,  -- Si se borra la playlist, se borran sus canciones
    CONSTRAINT fk_ps_cancion           FOREIGN KEY (id_cancion)
        REFERENCES cancion(id_cancion)
        ON UPDATE CASCADE
        ON DELETE CASCADE
);

-- ─────────────────────────────────────────────────────────────
--  7. ÍNDICES ADICIONALES
--     Mejoran el rendimiento en búsquedas frecuentes (RNF8)
-- ─────────────────────────────────────────────────────────────
CREATE INDEX idx_cancion_titulo     ON cancion(titulo);
CREATE INDEX idx_cancion_genero     ON cancion(genero);
CREATE INDEX idx_artista_nombre    ON artista(nombre);
CREATE INDEX idx_playlist_usuario  ON playlist(id_usuario);
CREATE INDEX idx_album_artista   ON album(id_artista);


-- ============================================================
--  DATOS DE PRUEBA (SEED DATA)
--  Orden: usuario → artista → album → cancion → playlists
--         → playlist_
-- ============================================================

-- ─────────────────────────────────────────────────────────────
--  USUARIOS
--  Contraseñas hasheadas con BCrypt (factor 10):
--    admin123  → hash del primer registro
-- ─────────────────────────────────────────────────────────────
INSERT INTO usuario (nombre_usuario, password, correo) VALUES
('admin',
 '$2a$10$TGbEZJmskT9KPoSp4arWkOD6/XFB2m6p0XqNAuF11E9C7Tl19xKxS',
 'admin@musiczone.com');

('test',
 '$2a$10$onlCW1yjS3zMziUVBsiJJ.fRaiSPlKbDGw1J0XgS0sw0henGAYbwi',
 'test@test.com');


-- ─────────────────────────────────────────────────────────────
--  ARTISTAS
-- ─────────────────────────────────────────────────────────────
INSERT INTO artista (nombre, genero, pais, bio) VALUES
('The Beatles',      'Rock',        'Reino Unido', 'Banda legendaria de Liverpool formada en 1960. Redefinieron la música popular del siglo XX.'),
('Queen',            'Rock',        'Reino Unido', 'Banda de rock británica liderada por Freddie Mercury, conocida por su teatralidad y virtuosismo.'),
('Bad Bunny',        'Reggaeton',   'Puerto Rico', 'Artista urbano puertorriqueño, uno de los más escuchados del mundo en la última década.'),
('Taylor Swift',     'Pop',         'Estados Unidos', 'Cantautora y compositora multipremiada, conocida por sus álbumes narrativos y re-grabaciones.'),
('Carlos Vives',     'Vallenato',   'Colombia',    'Máximo exponente del vallenato moderno, ganador de múltiples Grammy y Grammy Latino.'),
('Daft Punk',        'Electronic',  'Francia',     'Dúo de música electrónica parisino, pioneros del French House y la música dance de los 90s.');

-- ─────────────────────────────────────────────────────────────
--  ÁLBUMES
-- ─────────────────────────────────────────────────────────────
INSERT INTO album (titulo, id_artista, year_lanzamiento, portada_url) VALUES
-- The Beatles
('Abbey Road',               1, 1969, 'https://upload.wikimedia.org/wikipedia/en/4/42/Beatles_-_Abbey_Road.jpg'),
('Let It Be',                1, 1970, NULL),
-- Queen
('A Night at the Opera',     2, 1975, NULL),
('Greatest Hits',            2, 1981, NULL),
-- Bad Bunny
('Un Verano Sin Ti',         3, 2022, NULL),
('YHLQMDLG',                 3, 2020, NULL),
-- Taylor Swift
('1989',                     4, 2014, NULL),
('Midnights',                4, 2022, NULL),
-- Carlos Vives
('Clásicos de la Provincia', 5, 1993, NULL),
-- Daft Punk
('Random Access Memories',   6, 2013, NULL);

-- ─────────────────────────────────────────────────────────────
--  CANCIONES
-- ─────────────────────────────────────────────────────────────
INSERT INTO cancion (titulo, id_artista, id_album, duracion_segundos, numero_track, genero, year_lanzamiento) VALUES
-- The Beatles — Abbey Road
('Come Together',       1, 1, 259, 1,  'Rock',      1969),
('Something',           1, 1, 182, 2,  'Rock',      1969),
('Here Comes the Sun',  1, 1, 185, 8,  'Rock',      1969),
('Let It Be',           1, 2, 243, 6,  'Rock',      1970),
('Hey Jude',            1, NULL, 431, NULL, 'Rock',  1968),   -- single

-- Queen — A Night at the Opera
('Bohemian Rhapsody',   2, 3, 354, 11, 'Rock',      1975),
('Love of My Life',     2, 3, 213, 9,  'Rock',      1975),
-- Queen — Greatest Hits
('We Will Rock You',    2, 4, 122, 1,  'Rock',      1977),
('We Are the Champions',2, 4, 179, 2,  'Rock',      1977),
('Don''t Stop Me Now',  2, 4, 209, 5,  'Rock',      1978),

-- Bad Bunny — Un Verano Sin Ti
('Moscow Mule',         3, 5, 237, 2,  'Reggaeton', 2022),
('Me Porto Bonito',     3, 5, 178, 4,  'Reggaeton', 2022),
('Tití Me Preguntó',    3, 5, 240, 6,  'Reggaeton', 2022),
-- Bad Bunny — YHLQMDLG
('Safaera',             3, 6, 290, 5,  'Reggaeton', 2020),
('Yo Perreo Sola',      3, 6, 195, 11, 'Reggaeton', 2020),

-- Taylor Swift — 1989
('Shake It Off',        4, 7, 219, 6,  'Pop',       2014),
('Blank Space',         4, 7, 231, 2,  'Pop',       2014),
('Style',               4, 7, 231, 3,  'Pop',       2014),
-- Taylor Swift — Midnights
('Anti-Hero',           4, 8, 200, 1,  'Pop',       2022),
('Lavender Haze',       4, 8, 202, 2,  'Pop',       2022),

-- Carlos Vives — Clásicos de la Provincia
('La Bicicleta',        5, 9, 227, 1,  'Vallenato', 1993),
('Frío Frío',           5, 9, 198, 3,  'Vallenato', 1993),
('El Amor de Mi Tierra',5, 9, 210, 5,  'Vallenato', 1993),

-- Daft Punk — Random Access Memories
('Get Lucky',           6, 10, 369, 8, 'Electronic',2013),
('Instant Crush',       6, 10, 337, 4, 'Electronic',2013),
('Lose Yourself to Dance',6,10, 345, 7,'Electronic',2013);

-- ─────────────────────────────────────────────────────────────
--  PLAYLISTS
-- ─────────────────────────────────────────────────────────────
INSERT INTO playlist (nombre, id_usuario, descripcion) VALUES
('Mis Clásicos',        1, 'Las mejores canciones de todos los tiempos'),
('Workout Mix',         2, 'Energía pura para entrenar'),
('Relax & Chill',       2, 'Canciones para relajarse'),

-- ─────────────────────────────────────────────────────────────
--  CANCIONES EN PLAYLISTS
-- ─────────────────────────────────────────────────────────────
INSERT INTO cancion_playlist (id_playlist, id_cancion, posicion) VALUES
-- Playlist 1: Mis Clásicos (admin)
(1, 6,  1),   -- Bohemian Rhapsody
(1, 1,  2),   -- Come Together
(1, 3,  3),   -- Here Comes the Sun
(1, 8,  4),   -- We Will Rock You
(1, 9,  5),   -- We Are the Champions
(1, 5,  6),   -- Hey Jude
(1, 24, 7),   -- Get Lucky

-- Playlist 2: Workout Mix (carlos_m)
(2, 8,  1),   -- We Will Rock You
(2, 9,  2),   -- We Are the Champions
(2, 10, 3),   -- Don't Stop Me Now
(2, 16, 4),   -- Shake It Off
(2, 12, 5),   -- Me Porto Bonito
(2, 15, 6),   -- Yo Perreo Sola

-- Playlist 3: Relax & Chill (carlos_m)
(3, 3,  1),   -- Here Comes the Sun
(3, 2,  2),   -- Something
(3, 7,  3),   -- Love of My Life
(3, 25, 4),   -- Instant Crush
(3, 22, 5),   -- Frío Frío
