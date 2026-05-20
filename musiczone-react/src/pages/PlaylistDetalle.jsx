import { useEffect, useState } from 'react'
import { useParams, useNavigate, useOutletContext } from 'react-router-dom'
import { useAuth } from '../context/AuthContext'
import { useToast } from '../context/ToastContext'
import { playlistService, cancionService } from '../services/api'
import { Spinner, EstadoVacio, Modal, BtnSecundario } from '../components/ui'

export default function PlaylistDetalle() {
  const { id } = useParams()
  const { usuario } = useAuth()
  const { exito, error } = useToast()
  const navigate = useNavigate()
  const { cancionActiva, reproducirCancion, reproducirLista } = useOutletContext()

  const [playlist, setPlaylist] = useState(null)
  const [cargando, setCargando] = useState(true)
  const [modalVisible, setModalVisible] = useState(false)
  const [cancionPorQuitar, setCancionPorQuitar] = useState(null)
  const [todasCanciones, setTodasCanciones] = useState([])
  const [busqueda, setBusqueda] = useState('')

  const cargar = () => {
    setCargando(true)
    playlistService.verDetalle(id)
      .then(d => setPlaylist(d.datos))
      .catch(() => navigate('/playlists'))
      .finally(() => setCargando(false))
  }

  useEffect(() => { cargar() }, [id])

  const abrirModal = async () => {
    const data = await cancionService.listarTodas()
    setTodasCanciones(data.datos || [])
    setModalVisible(true)
  }

  const agregar = async (idCancion) => {
    try {
      await playlistService.agregarCancion(id, idCancion)
      exito('Canción agregada ✓')
      cargar()
    } catch (e) { error(e.message) }
  }

  const quitar = async () => {
    if (!cancionPorQuitar) return
    try {
      await playlistService.quitarCancion(id, cancionPorQuitar.idCancion)
      exito('Canción quitada')
      setCancionPorQuitar(null)
      cargar()
    } catch (e) { error(e.message) }
  }

  const reproducibles = (playlist?.canciones || []).filter(c => c.urlAudio)

  const reproducirAleatoria = () => {
    if (reproducibles.length === 0) return

    const opciones = reproducibles.length > 1 && cancionActiva
      ? reproducibles.filter(c => c.idCancion !== cancionActiva.idCancion)
      : reproducibles

    reproducirCancion(opciones[Math.floor(Math.random() * opciones.length)])
  }

  const reproducirPlaylist = () => {
    reproducirLista(playlist.canciones || [])
  }

  const cancionesFiltradas = todasCanciones.filter(c =>
    c.titulo.toLowerCase().includes(busqueda.toLowerCase()) ||
    (c.artista || '').toLowerCase().includes(busqueda.toLowerCase())
  )

  if (cargando) return <Spinner />
  if (!playlist) return null

  return (
    <div style={{ display: 'flex', flexDirection: 'column', gap: 24, animation: 'fadeUp 0.4s ease' }}>
      <button
        onClick={() => navigate('/playlists')}
        style={{
          background: 'transparent', border: '1px solid var(--borde-fuerte)',
          color: 'var(--texto-2)', padding: '10px 18px',
          borderRadius: 'var(--radio)', cursor: 'pointer',
          fontSize: 14, alignSelf: 'flex-start',
          transition: 'var(--transition)',
        }}
        onMouseEnter={e => { e.currentTarget.style.color = 'var(--texto-1)' }}
        onMouseLeave={e => { e.currentTarget.style.color = 'var(--texto-2)' }}
      >
        ← Volver
      </button>

      <div style={{ display: 'flex', alignItems: 'center', justifyContent: 'space-between', flexWrap: 'wrap', gap: 12 }}>
        <div>
          <h2 style={{ fontFamily: 'var(--font-display)', fontSize: 36, letterSpacing: 1 }}>
            📋 {playlist.nombre}
          </h2>
          {playlist.descripcion && (
            <p style={{ color: 'var(--texto-2)', marginTop: 6 }}>{playlist.descripcion}</p>
          )}
        </div>
        <div style={{ display: 'flex', gap: 10, flexWrap: 'wrap' }}>
          <button
            onClick={reproducirPlaylist}
            disabled={reproducibles.length === 0}
            style={botonAccionStyle(reproducibles.length > 0, true)}
          >
            Reproducir playlist
          </button>
          <button
            onClick={reproducirAleatoria}
            disabled={reproducibles.length === 0}
            style={botonAccionStyle(reproducibles.length > 0)}
          >
            Aleatoria
          </button>
          <button
            onClick={abrirModal}
            style={{
              background: 'var(--acento)', color: '#06110b',
              border: 'none', borderRadius: 'var(--radio)',
              padding: '10px 20px', fontWeight: 700,
              fontSize: 13, cursor: 'pointer',
            }}
          >
            + Agregar canción
          </button>
        </div>
      </div>

      {(!playlist.canciones || playlist.canciones.length === 0)
        ? <EstadoVacio icono="🎵" texto="Esta playlist está vacía. ¡Agrega canciones!" />
        : (
          <div style={{ display: 'flex', flexDirection: 'column', gap: 8 }}>
            {playlist.canciones.map((c, i) => (
              <div key={c.idCancion} style={{
                display: 'flex',
                flexWrap: 'wrap',
                alignItems: 'center',
                gap: 16,
                background: 'var(--bg-card)',
                border: '1px solid var(--borde)',
                borderRadius: 'var(--radio)',
                padding: '14px 18px',
                transition: 'background 0.2s',
              }}
                onMouseEnter={e => e.currentTarget.style.background = 'var(--bg-card-hover)'}
                onMouseLeave={e => e.currentTarget.style.background = 'var(--bg-card)'}
              >
                <span style={{ fontSize: 13, color: 'var(--texto-3)', minWidth: 24, textAlign: 'right', fontWeight: 600 }}>
                  {c.posicion || i + 1}
                </span>
                <div style={{ flex: '1 1 180px', minWidth: 0 }}>
                  <div style={{ fontWeight: 600, fontSize: 14 }}>{c.titulo}</div>
                  <div style={{ fontSize: 12, color: 'var(--texto-2)', marginTop: 2 }}>{c.artista}</div>
                </div>
                <button
                  onClick={() => reproducirCancion(c)}
                  disabled={!c.urlAudio}
                  style={{
                    flex: '0 0 auto',
                    padding: '8px 14px',
                    background: c.urlAudio ? 'var(--acento)' : 'var(--bg-glass)',
                    color: c.urlAudio ? '#000' : 'var(--texto-3)',
                    border: c.urlAudio ? '1px solid var(--acento)' : '1px solid var(--borde)',
                    borderRadius: 8,
                    cursor: c.urlAudio ? 'pointer' : 'not-allowed',
                    fontSize: 12,
                    fontWeight: 700,
                  }}
                >
                  Reproducir
                </button>
                <button
                  onClick={() => setCancionPorQuitar(c)}
                  title="Quitar de playlist"
                  style={{
                    background: 'transparent', border: 'none',
                    color: 'var(--texto-3)', cursor: 'pointer',
                    fontSize: 18, padding: '4px 8px', borderRadius: 6,
                    transition: 'var(--transition)',
                  }}
                  onMouseEnter={e => { e.currentTarget.style.color = 'var(--rojo)'; e.currentTarget.style.background = 'var(--rojo-dim)' }}
                  onMouseLeave={e => { e.currentTarget.style.color = 'var(--texto-3)'; e.currentTarget.style.background = 'transparent' }}
                >
                  ×
                </button>
              </div>
            ))}
          </div>
        )
      }

      <Modal visible={modalVisible} onClose={() => setModalVisible(false)} titulo="Agregar canción">
        <input
          value={busqueda}
          onChange={e => setBusqueda(e.target.value)}
          placeholder="Buscar canción..."
          style={{
            background: 'var(--bg-base)', border: '1px solid var(--borde-fuerte)',
            borderRadius: 'var(--radio)', padding: '11px 16px',
            color: 'var(--texto-1)', fontSize: 14, outline: 'none', width: '100%',
          }}
        />
        <div style={{ maxHeight: 280, overflow: 'auto', display: 'flex', flexDirection: 'column', gap: 6 }}>
          {cancionesFiltradas.length === 0
            ? <p style={{ color: 'var(--texto-3)', textAlign: 'center', padding: 20 }}>Sin resultados</p>
            : cancionesFiltradas.map(c => (
              <div key={c.id} style={{
                display: 'flex', alignItems: 'center', justifyContent: 'space-between',
                gap: 12,
                padding: '10px 14px',
                background: 'var(--bg-base)', borderRadius: 8,
                border: '1px solid var(--borde)',
              }}>
                <div>
                  <div style={{ fontWeight: 600, fontSize: 13 }}>{c.titulo}</div>
                  <div style={{ fontSize: 12, color: 'var(--texto-2)' }}>{c.artista}</div>
                </div>
                <button
                  onClick={() => agregar(c.id)}
                  style={{
                    background: 'var(--acento-dim)', color: 'var(--acento)',
                    border: '1px solid var(--acento)',
                    borderRadius: 6, padding: '6px 14px',
                    fontSize: 12, fontWeight: 700, cursor: 'pointer',
                  }}
                >
                  + Agregar
                </button>
              </div>
            ))
          }
        </div>
        <BtnSecundario onClick={() => setModalVisible(false)} style={{ alignSelf: 'flex-end' }}>
          Cerrar
        </BtnSecundario>
      </Modal>

      <Modal
        visible={!!cancionPorQuitar}
        onClose={() => setCancionPorQuitar(null)}
        titulo="Quitar canción"
      >
        <p style={{ color: 'var(--texto-2)', lineHeight: 1.5 }}>
          ¿Seguro que quieres quitar "{cancionPorQuitar?.titulo}" de esta playlist?
        </p>
        <div style={{ display: 'flex', gap: 10, justifyContent: 'flex-end' }}>
          <BtnSecundario onClick={() => setCancionPorQuitar(null)}>Cancelar</BtnSecundario>
          <button
            onClick={quitar}
            style={{
              background: 'var(--rojo)',
              color: '#fff',
              border: 'none',
              borderRadius: 'var(--radio)',
              padding: '11px 18px',
              fontSize: 13,
              fontWeight: 700,
            }}
          >
            Quitar
          </button>
        </div>
      </Modal>
    </div>
  )
}

function botonAccionStyle(habilitado, principal = false) {
  return {
    background: habilitado
      ? principal ? 'var(--acento)' : 'var(--acento-dim)'
      : 'var(--bg-glass)',
    color: habilitado
      ? principal ? '#000' : 'var(--acento)'
      : 'var(--texto-3)',
    border: habilitado
      ? '1px solid var(--acento)'
      : '1px solid var(--borde)',
    borderRadius: 'var(--radio)',
    padding: '10px 18px',
    fontWeight: 700,
    fontSize: 13,
    cursor: habilitado ? 'pointer' : 'not-allowed',
  }
}
