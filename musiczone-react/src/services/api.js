// ═══════════════════════════════════════════
//  MUSICZONE — Capa de servicios API
//  Base URL: http://localhost:8081
// ═══════════════════════════════════════════

const BASE = 'http://localhost:8081'

// Obtiene el token guardado en localStorage
function getToken() {
  return localStorage.getItem('token')
}

// Si hay token lo agrega al header Authorization automáticamente
async function request(path, options = {}) {
  const token = getToken()

  const res = await fetch(`${BASE}${path}`, {
    headers: {
      'Content-Type': 'application/json',
      ...(token ? { Authorization: `Bearer ${token}` } : {}),
    },
    ...options,
  })
  const data = await res.json()
  if (!res.ok) throw new Error(data.mensaje || 'Error en la petición')
  return data
}

// ── AUTH ─────────────────────────────────
export const authService = {
  login: async (nombreUsuario, password) => {
    const data = await request('/auth/login', {
      method: 'POST',
      body: JSON.stringify({ nombreUsuario, password }),
    })
    // Guarda el token automáticamente al hacer login
    if (data.datos?.token) {
      localStorage.setItem('token', data.datos.token)
    }
    return data
  },

  registrar: (nombreUsuario, correo, password) =>
    request('/auth/registrar', {
      method: 'POST',
      body: JSON.stringify({ nombreUsuario, correo, password }),
    }),

  logout: () => {
    localStorage.removeItem('token')
  },
}

// ── CANCIONES ────────────────────────────
export const cancionService = {
  listarTodas: () => request('/canciones'),

  buscarPorTitulo: (titulo) =>
    request(`/canciones/buscar?titulo=${encodeURIComponent(titulo)}`),

  buscarPorArtista: (artista) =>
    request(`/canciones/buscar-por-artista?artista=${encodeURIComponent(artista)}`),
}

// ── ARTISTAS ─────────────────────────────
export const artistaService = {
  listarTodos: () => request('/artistas'),
  buscarPorNombre: (nombre) =>
    request(`/artistas/buscar?nombre=${encodeURIComponent(nombre)}`),
}

// ── PLAYLISTS ────────────────────────────
export const playlistService = {
  listarPorUsuario: (idUsuario) =>
    request(`/playlists/usuario/${idUsuario}`),

  verDetalle: (id) => request(`/playlists/${id}`),

  crear: (nombre, descripcion, idUsuario) =>
    request('/playlists', {
      method: 'POST',
      body: JSON.stringify({ nombre, descripcion, idUsuario }),
    }),

  actualizar: (id, nombre, descripcion, idUsuario) =>
    request(`/playlists/${id}`, {
      method: 'PUT',
      body: JSON.stringify({ nombre, descripcion, idUsuario }),
    }),

  eliminar: (id) =>
    request(`/playlists/${id}`, { method: 'DELETE' }),

  agregarCancion: (idPlaylist, idCancion) =>
    request(`/playlists/${idPlaylist}/canciones`, {
      method: 'POST',
      body: JSON.stringify({ idCancion }),
    }),

  quitarCancion: (idPlaylist, idCancion) =>
    request(`/playlists/${idPlaylist}/canciones/${idCancion}`, {
      method: 'DELETE',
    }),
}
