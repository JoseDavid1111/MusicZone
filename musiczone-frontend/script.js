/* ═══════════════════════════════════════════════
   MUSICZONE — script.js
   Conectado a: http://localhost:8081
   ═══════════════════════════════════════════════ */

const API = 'http://localhost:8081';

// ─── ESTADO GLOBAL ────────────────────────────
let usuarioActual = null;       // { id, nombreUsuario, correo }
let todasLasCanciones = [];     // cache de canciones
let cancionParaAgregar = null;  // id playlist activa en modal

// ═══════════════════════════════════════════════
//  AUTENTICACIÓN
// ═══════════════════════════════════════════════

async function hacerLogin() {
  const nombreUsuario = document.getElementById('login-usuario').value.trim();
  const password      = document.getElementById('login-password').value.trim();
  const errorEl       = document.getElementById('login-error');

  ocultarEl(errorEl);

  if (!nombreUsuario || !password) {
    mostrarError(errorEl, 'Por favor completa todos los campos.');
    return;
  }

  try {
    const res = await fetch(`${API}/auth/login`, {
      method: 'POST',
      headers: { 'Content-Type': 'application/json' },
      body: JSON.stringify({ nombreUsuario, password })
    });

    const data = await res.json();

    if (res.ok && data.exito) {
      usuarioActual = data.datos;
      localStorage.setItem('musiczone_usuario', JSON.stringify(usuarioActual));
      entrarALaApp();
    } else {
      mostrarError(errorEl, data.mensaje || 'Credenciales inválidas.');
    }
  } catch (e) {
    mostrarError(errorEl, 'No se pudo conectar con el servidor. ¿Está corriendo el backend?');
  }
}

async function hacerRegistro() {
  const nombreUsuario = document.getElementById('reg-usuario').value.trim();
  const correo        = document.getElementById('reg-correo').value.trim();
  const password      = document.getElementById('reg-password').value.trim();
  const errorEl       = document.getElementById('reg-error');
  const exitoEl       = document.getElementById('reg-exito');

  ocultarEl(errorEl);
  ocultarEl(exitoEl);

  if (!nombreUsuario || !correo || !password) {
    mostrarError(errorEl, 'Por favor completa todos los campos.');
    return;
  }

  try {
    const res = await fetch(`${API}/auth/registrar`, {
      method: 'POST',
      headers: { 'Content-Type': 'application/json' },
      body: JSON.stringify({ nombreUsuario, correo, password })
    });

    const data = await res.json();

    if (res.ok) {
      mostrarExito(exitoEl, '¡Cuenta creada! Ahora puedes iniciar sesión.');
      document.getElementById('reg-usuario').value = '';
      document.getElementById('reg-correo').value  = '';
      document.getElementById('reg-password').value = '';
      setTimeout(() => mostrarTab('login'), 1800);
    } else {
      mostrarError(errorEl, data.mensaje || 'No se pudo crear la cuenta.');
    }
  } catch (e) {
    mostrarError(errorEl, 'No se pudo conectar con el servidor.');
  }
}

function cerrarSesion() {
  usuarioActual = null;
  localStorage.removeItem('musiczone_usuario');
  document.getElementById('pantalla-app').classList.remove('activa');
  document.getElementById('pantalla-login').classList.add('activa');
  mostrarTab('login');
}

function entrarALaApp() {
  document.getElementById('pantalla-login').classList.remove('activa');
  document.getElementById('pantalla-app').classList.add('activa');
  document.getElementById('nombre-usuario-header').textContent = `♪ ${usuarioActual.nombreUsuario}`;
  document.getElementById('saludo-bienvenida').textContent = `Bienvenido, ${usuarioActual.nombreUsuario} 👋`;

  // Cargar datos iniciales
  cargarCancionesInicio();
  cargarPlaylistsSidebar();
}

// ═══════════════════════════════════════════════
//  CANCIONES
// ═══════════════════════════════════════════════

async function cargarTodasLasCanciones() {
  if (todasLasCanciones.length > 0) return todasLasCanciones;

  try {
    const res  = await fetch(`${API}/canciones`);
    const data = await res.json();
    todasLasCanciones = data.datos || [];
    return todasLasCanciones;
  } catch (e) {
    mostrarToast('Error al cargar canciones', 'error');
    return [];
  }
}

async function cargarCancionesInicio() {
  const canciones = await cargarTodasLasCanciones();
  const container = document.getElementById('canciones-inicio');
  // Muestra las primeras 8
  renderizarCanciones(canciones.slice(0, 8), container);
}

async function cargarSeccionCanciones() {
  const canciones = await cargarTodasLasCanciones();
  renderizarCanciones(canciones, document.getElementById('lista-canciones'));
}

function renderizarCanciones(canciones, container) {
  if (!canciones || canciones.length === 0) {
    container.innerHTML = estadoVacio('🎵', 'No hay canciones disponibles');
    return;
  }

  container.innerHTML = canciones.map(c => `
    <div class="card-cancion">
      <div class="cancion-titulo">${c.titulo}</div>
      <div class="cancion-artista">🎤 ${c.artista || 'Artista desconocido'}</div>
      ${c.genero ? `<span class="cancion-genero">${c.genero}</span>` : ''}
      <div class="cancion-acciones">
        <button class="btn-agregar-playlist" onclick="abrirModalAgregarCancion(${c.id})">
          + Agregar a playlist
        </button>
      </div>
    </div>
  `).join('');
}

async function filtrarCanciones() {
  const titulo = document.getElementById('buscar-titulo').value.trim();
  if (!titulo) {
    const canciones = await cargarTodasLasCanciones();
    renderizarCanciones(canciones, document.getElementById('lista-canciones'));
    return;
  }

  try {
    const res  = await fetch(`${API}/canciones/buscar?titulo=${encodeURIComponent(titulo)}`);
    const data = await res.json();
    renderizarCanciones(data.datos || [], document.getElementById('lista-canciones'));
  } catch (e) {
    mostrarToast('Error al buscar', 'error');
  }
}

async function filtrarPorArtista() {
  const artista = document.getElementById('buscar-artista-campo').value.trim();
  if (!artista) {
    const canciones = await cargarTodasLasCanciones();
    renderizarCanciones(canciones, document.getElementById('lista-canciones'));
    return;
  }

  try {
    const res  = await fetch(`${API}/canciones/buscar-por-artista?artista=${encodeURIComponent(artista)}`);
    const data = await res.json();
    renderizarCanciones(data.datos || [], document.getElementById('lista-canciones'));
  } catch (e) {
    mostrarToast('Error al buscar', 'error');
  }
}

async function buscarGlobal() {
  const q = document.getElementById('buscador-global').value.trim();
  if (!q) return;

  // Ir a sección canciones y filtrar
  mostrarSeccion('canciones');
  document.getElementById('buscar-titulo').value = q;
  filtrarCanciones();
}

// ═══════════════════════════════════════════════
//  ARTISTAS
// ═══════════════════════════════════════════════

async function cargarArtistas() {
  try {
    const res  = await fetch(`${API}/artistas`);
    const data = await res.json();
    const artistas = data.datos || [];
    const container = document.getElementById('lista-artistas');

    if (artistas.length === 0) {
      container.innerHTML = estadoVacio('🎤', 'No hay artistas disponibles');
      return;
    }

    container.innerHTML = artistas.map(a => `
      <div class="card-artista">
        <div class="artista-avatar">🎤</div>
        <div class="artista-nombre">${a.nombre}</div>
        ${a.genero ? `<div class="artista-genero">${a.genero}</div>` : ''}
        ${a.pais   ? `<div class="artista-pais">📍 ${a.pais}</div>`  : ''}
      </div>
    `).join('');
  } catch (e) {
    mostrarToast('Error al cargar artistas', 'error');
  }
}

// ═══════════════════════════════════════════════
//  PLAYLISTS
// ═══════════════════════════════════════════════

async function cargarPlaylists() {
  if (!usuarioActual) return;

  try {
    const res  = await fetch(`${API}/playlists/usuario/${usuarioActual.id}`);
    const data = await res.json();
    return data.datos || [];
  } catch (e) {
    mostrarToast('Error al cargar playlists', 'error');
    return [];
  }
}

async function cargarSeccionPlaylists() {
  const playlists = await cargarPlaylists();
  const container = document.getElementById('lista-playlists');

  if (!playlists || playlists.length === 0) {
    container.innerHTML = estadoVacio('📋', 'Aún no tienes playlists. ¡Crea una!');
    return;
  }

  container.innerHTML = playlists.map(p => `
    <div class="card-playlist" onclick="verDetallePlaylist(${p.id})">
      <div class="playlist-icono">📋</div>
      <div class="playlist-nombre">${p.nombre}</div>
      ${p.descripcion ? `<div class="playlist-desc">${p.descripcion}</div>` : ''}
      <div class="playlist-acciones" onclick="event.stopPropagation()">
        <button class="btn-editar" onclick="abrirModalEditarPlaylist(${p.id}, '${escapar(p.nombre)}', '${escapar(p.descripcion || '')}')">✏️ Editar</button>
        <button class="btn-eliminar" onclick="eliminarPlaylist(${p.id})">🗑️ Eliminar</button>
      </div>
    </div>
  `).join('');
}

async function cargarPlaylistsSidebar() {
  const playlists = await cargarPlaylists();
  const container = document.getElementById('lista-playlists-sidebar');

  if (!playlists || playlists.length === 0) {
    container.innerHTML = `<p style="padding:8px 14px;font-size:12px;color:var(--texto-3)">Sin playlists aún</p>`;
    return;
  }

  container.innerHTML = playlists.map(p => `
    <button class="nav-playlist-item" onclick="verDetallePlaylist(${p.id})">
      📋 ${p.nombre}
    </button>
  `).join('');
}

async function crearPlaylist() {
  const nombre      = document.getElementById('nueva-playlist-nombre').value.trim();
  const descripcion = document.getElementById('nueva-playlist-desc').value.trim();
  const errorEl     = document.getElementById('modal-playlist-error');

  ocultarEl(errorEl);

  if (!nombre) {
    mostrarError(errorEl, 'El nombre es obligatorio.');
    return;
  }

  try {
    const res = await fetch(`${API}/playlists`, {
      method: 'POST',
      headers: { 'Content-Type': 'application/json' },
      body: JSON.stringify({ nombre, descripcion, idUsuario: usuarioActual.id })
    });

    if (res.ok) {
      cerrarModal('modal-crear-playlist');
      document.getElementById('nueva-playlist-nombre').value = '';
      document.getElementById('nueva-playlist-desc').value   = '';
      mostrarToast('Playlist creada ✓', 'exito');
      cargarSeccionPlaylists();
      cargarPlaylistsSidebar();
    } else {
      const data = await res.json();
      mostrarError(errorEl, data.mensaje || 'No se pudo crear la playlist.');
    }
  } catch (e) {
    mostrarError(errorEl, 'Error al conectar con el servidor.');
  }
}

async function eliminarPlaylist(id) {
  if (!confirm('¿Eliminar esta playlist?')) return;

  try {
    const res = await fetch(`${API}/playlists/${id}`, { method: 'DELETE' });
    if (res.ok) {
      mostrarToast('Playlist eliminada', 'exito');
      cargarSeccionPlaylists();
      cargarPlaylistsSidebar();
    }
  } catch (e) {
    mostrarToast('Error al eliminar', 'error');
  }
}

async function guardarEdicionPlaylist() {
  const id          = document.getElementById('editar-playlist-id').value;
  const nombre      = document.getElementById('editar-playlist-nombre').value.trim();
  const descripcion = document.getElementById('editar-playlist-desc').value.trim();
  const errorEl     = document.getElementById('modal-editar-error');

  ocultarEl(errorEl);

  if (!nombre) {
    mostrarError(errorEl, 'El nombre es obligatorio.');
    return;
  }

  try {
    const res = await fetch(`${API}/playlists/${id}`, {
      method: 'PUT',
      headers: { 'Content-Type': 'application/json' },
      body: JSON.stringify({ nombre, descripcion, idUsuario: usuarioActual.id })
    });

    if (res.ok) {
      cerrarModal('modal-editar-playlist');
      mostrarToast('Playlist actualizada ✓', 'exito');
      cargarSeccionPlaylists();
      cargarPlaylistsSidebar();
    } else {
      const data = await res.json();
      mostrarError(errorEl, data.mensaje || 'Error al actualizar.');
    }
  } catch (e) {
    mostrarError(errorEl, 'Error al conectar con el servidor.');
  }
}

async function verDetallePlaylist(id) {
  mostrarSeccion('detalle-playlist');

  try {
    const res  = await fetch(`${API}/playlists/${id}`);
    const data = await res.json();
    const p    = data.datos;

    const container = document.getElementById('detalle-playlist-contenido');
    const canciones = p.canciones || [];

    container.innerHTML = `
      <div class="detalle-header">
        <h2>📋 ${p.nombre}</h2>
        <button class="btn-primario btn-sm" onclick="abrirModalAgregarCancionAPlaylist(${p.id})">+ Agregar canción</button>
      </div>
      ${p.descripcion ? `<p style="color:var(--texto-2);margin-top:-8px">${p.descripcion}</p>` : ''}
      <div class="lista-canciones-detalle">
        ${canciones.length === 0
          ? estadoVacio('🎵', 'Esta playlist está vacía. Agrega canciones!')
          : canciones.map((c, i) => `
              <div class="fila-cancion">
                <span class="fila-numero">${c.posicion || i + 1}</span>
                <div class="fila-info">
                  <div class="fila-titulo">${c.titulo}</div>
                  <div class="fila-artista">${c.artista || ''}</div>
                </div>
                <button class="btn-quitar-cancion" onclick="quitarCancionDePlaylist(${p.id}, ${c.idCancion})" title="Quitar de playlist">✕</button>
              </div>
            `).join('')
        }
      </div>
    `;
  } catch (e) {
    mostrarToast('Error al cargar la playlist', 'error');
  }
}

async function quitarCancionDePlaylist(idPlaylist, idCancion) {
  try {
    const res = await fetch(`${API}/playlists/${idPlaylist}/canciones/${idCancion}`, {
      method: 'DELETE'
    });

    if (res.ok) {
      mostrarToast('Canción quitada ✓', 'exito');
      verDetallePlaylist(idPlaylist);
    } else {
      mostrarToast('No se pudo quitar la canción', 'error');
    }
  } catch (e) {
    mostrarToast('Error al conectar', 'error');
  }
}

// ═══════════════════════════════════════════════
//  MODAL: AGREGAR CANCIÓN A PLAYLIST
// ═══════════════════════════════════════════════

async function abrirModalAgregarCancion(idCancion) {
  // Desde una card de canción — necesitamos elegir a qué playlist
  const playlists = await cargarPlaylists();

  if (!playlists || playlists.length === 0) {
    mostrarToast('Primero crea una playlist', 'error');
    return;
  }

  // Si solo hay una, preguntar directamente
  if (playlists.length === 1) {
    agregarCancionAPlaylist(playlists[0].id, idCancion);
    return;
  }

  // Mostrar modal para elegir playlist
  cancionParaAgregar = idCancion;
  const lista = document.getElementById('lista-canciones-modal');

  lista.innerHTML = playlists.map(p => `
    <div class="item-cancion-modal">
      <div class="info">
        <div class="titulo">📋 ${p.nombre}</div>
      </div>
      <button class="btn-add-modal" onclick="agregarCancionAPlaylist(${p.id}, ${idCancion})">Agregar</button>
    </div>
  `).join('');

  document.querySelector('#modal-agregar-cancion .modal-subtitulo').textContent =
    'Selecciona la playlist donde quieres agregar esta canción:';
  document.getElementById('buscar-cancion-modal').style.display = 'none';

  abrirModal('modal-agregar-cancion');
}

async function abrirModalAgregarCancionAPlaylist(idPlaylist) {
  // Desde el detalle de una playlist — elegir canción
  cancionParaAgregar = idPlaylist;
  const canciones = await cargarTodasLasCanciones();

  document.querySelector('#modal-agregar-cancion .modal-subtitulo').textContent =
    'Selecciona una canción para agregar:';
  document.getElementById('buscar-cancion-modal').style.display = 'block';

  renderizarCancionesModal(canciones, idPlaylist);
  abrirModal('modal-agregar-cancion');
}

function renderizarCancionesModal(canciones, idPlaylist) {
  const lista = document.getElementById('lista-canciones-modal');

  if (canciones.length === 0) {
    lista.innerHTML = `<p style="color:var(--texto-3);text-align:center;padding:20px">Sin resultados</p>`;
    return;
  }

  lista.innerHTML = canciones.map(c => `
    <div class="item-cancion-modal">
      <div class="info">
        <div class="titulo">${c.titulo}</div>
        <div class="artista">${c.nombreArtista || ''}</div>
      </div>
      <button class="btn-add-modal" onclick="agregarCancionAPlaylist(${idPlaylist}, ${c.id})">+ Agregar</button>
    </div>
  `).join('');
}

async function filtrarCancionesModal() {
  const q = document.getElementById('buscar-cancion-modal').value.toLowerCase();
  const canciones = await cargarTodasLasCanciones();
  const filtradas = canciones.filter(c =>
    c.titulo.toLowerCase().includes(q) ||
    (c.nombreArtista || '').toLowerCase().includes(q)
  );
  renderizarCancionesModal(filtradas, cancionParaAgregar);
}

async function agregarCancionAPlaylist(idPlaylist, idCancion) {
  try {
    const res = await fetch(`${API}/playlists/${idPlaylist}/canciones`, {
      method: 'POST',
      headers: { 'Content-Type': 'application/json' },
      body: JSON.stringify({ idCancion })
    });

    if (res.ok) {
      cerrarModal('modal-agregar-cancion');
      mostrarToast('¡Canción agregada! ✓', 'exito');
      // Refrescar detalle si estamos en esa sección
      if (document.getElementById('seccion-detalle-playlist').classList.contains('activa')) {
        verDetallePlaylist(idPlaylist);
      }
    } else {
      const data = await res.json();
      mostrarToast(data.mensaje || 'No se pudo agregar', 'error');
    }
  } catch (e) {
    mostrarToast('Error al conectar', 'error');
  }
}

// ═══════════════════════════════════════════════
//  NAVEGACIÓN Y UI
// ═══════════════════════════════════════════════

function mostrarSeccion(nombre) {
  // Ocultar todas
  document.querySelectorAll('.seccion').forEach(s => s.classList.remove('activa'));
  document.querySelectorAll('.nav-btn').forEach(b => b.classList.remove('activo'));

  const nombreSeccion = nombre === 'detalle-playlist' ? 'detalle-playlist' : nombre;
  const seccion = document.getElementById(`seccion-${nombreSeccion}`);
  if (seccion) seccion.classList.add('activa');

  const navBtn = document.getElementById(`nav-${nombre}`);
  if (navBtn) navBtn.classList.add('activo');

  // Cargar datos según sección
  if (nombre === 'canciones') cargarSeccionCanciones();
  if (nombre === 'artistas')  cargarArtistas();
  if (nombre === 'playlists') cargarSeccionPlaylists();
}

function mostrarTab(tab) {
  document.getElementById('form-login').classList.add('oculto');
  document.getElementById('form-registro').classList.add('oculto');
  document.getElementById('tab-login').classList.remove('activo');
  document.getElementById('tab-registro').classList.remove('activo');

  document.getElementById(`form-${tab}`).classList.remove('oculto');
  document.getElementById(`tab-${tab}`).classList.add('activo');
}

function abrirModalCrearPlaylist() {
  abrirModal('modal-crear-playlist');
}

function abrirModalEditarPlaylist(id, nombre, desc) {
  document.getElementById('editar-playlist-id').value    = id;
  document.getElementById('editar-playlist-nombre').value = nombre;
  document.getElementById('editar-playlist-desc').value   = desc;
  abrirModal('modal-editar-playlist');
}

function abrirModal(id) {
  document.getElementById(id).classList.remove('oculto');
}

function cerrarModal(id) {
  document.getElementById(id).classList.add('oculto');
}

function cerrarModalSiOverlay(e, id) {
  if (e.target === document.getElementById(id)) cerrarModal(id);
}

// ═══════════════════════════════════════════════
//  UTILIDADES
// ═══════════════════════════════════════════════

function mostrarError(el, msg) {
  el.textContent = msg;
  el.classList.remove('oculto');
}

function mostrarExito(el, msg) {
  el.textContent = msg;
  el.classList.remove('oculto');
}

function ocultarEl(el) {
  el.classList.add('oculto');
}

function estadoVacio(icono, texto) {
  return `<div class="estado-vacio"><div class="icono">${icono}</div><p>${texto}</p></div>`;
}

function escapar(str) {
  return String(str).replace(/'/g, "\\'").replace(/"/g, '&quot;');
}

let toastTimer = null;
function mostrarToast(msg, tipo = '') {
  const toast = document.getElementById('toast');
  toast.textContent = msg;
  toast.className = `toast ${tipo}`;
  toast.classList.remove('oculto');

  if (toastTimer) clearTimeout(toastTimer);
  toastTimer = setTimeout(() => toast.classList.add('oculto'), 3000);
}

// ═══════════════════════════════════════════════
//  INICIO: verificar sesión guardada
// ═══════════════════════════════════════════════

document.addEventListener('DOMContentLoaded', () => {
  const guardado = localStorage.getItem('musiczone_usuario');

  if (guardado) {
    try {
      usuarioActual = JSON.parse(guardado);
      entrarALaApp();
    } catch {
      localStorage.removeItem('musiczone_usuario');
      document.getElementById('pantalla-login').classList.add('activa');
    }
  } else {
    document.getElementById('pantalla-login').classList.add('activa');
  }

  // Enter en los campos de login
  document.getElementById('login-password').addEventListener('keyup', e => {
    if (e.key === 'Enter') hacerLogin();
  });
});
