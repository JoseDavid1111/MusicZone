import { useEffect, useState } from 'react'
import { useNavigate } from 'react-router-dom'
import { useAuth } from '../context/AuthContext'
import { useToast } from '../context/ToastContext'
import { playlistService } from '../services/api'
import { Spinner, EstadoVacio, Modal, Campo, BtnPrimario, BtnSecundario } from '../components/ui'

export default function Playlists() {
  const { usuario } = useAuth()
  const { exito, error } = useToast()
  const navigate = useNavigate()

  const [playlists, setPlaylists] = useState([])
  const [cargando, setCargando] = useState(true)
  const [modalCrear, setModalCrear] = useState(false)
  const [modalEditar, setModalEditar] = useState(null)
  const [playlistPorEliminar, setPlaylistPorEliminar] = useState(null)

  const [nombre, setNombre] = useState('')
  const [descripcion, setDescripcion] = useState('')

  const cargar = () => {
    setCargando(true)
    playlistService.listarPorUsuario(usuario.nombreUsuario)
      .then(d => setPlaylists(d.datos || []))
      .catch(() => {})
      .finally(() => setCargando(false))
  }

  useEffect(() => { cargar() }, [usuario.nombreUsuario])

  const crear = async () => {
    if (!nombre.trim()) return
    try {
      await playlistService.crear(nombre, descripcion, usuario.nombreUsuario)
      exito('Playlist creada ✓')
      setModalCrear(false)
      setNombre('')
      setDescripcion('')
      cargar()
    } catch (e) { error(e.message) }
  }

  const guardarEdicion = async () => {
    if (!nombre.trim()) return
    try {
      await playlistService.actualizar(modalEditar.id, nombre, descripcion, usuario.nombreUsuario)
      exito('Playlist actualizada ✓')
      setModalEditar(null)
      setNombre('')
      setDescripcion('')
      cargar()
    } catch (e) { error(e.message) }
  }

  const eliminar = async () => {
    if (!playlistPorEliminar) return
    try {
      await playlistService.eliminar(playlistPorEliminar.id)
      exito('Playlist eliminada')
      setPlaylistPorEliminar(null)
      cargar()
    } catch (e) { error(e.message) }
  }

  const abrirEditar = (p) => {
    setModalEditar(p)
    setNombre(p.nombre)
    setDescripcion(p.descripcion || '')
  }

  return (
    <div style={{ display: 'flex', flexDirection: 'column', gap: 24, animation: 'fadeUp 0.4s ease' }}>
      <div style={{ display: 'flex', alignItems: 'center', justifyContent: 'space-between', gap: 12, flexWrap: 'wrap' }}>
        <h2 style={{ fontFamily: 'var(--font-display)', fontSize: 32, letterSpacing: 1 }}>
          Mis Playlists
        </h2>
        <button
          onClick={() => { setModalCrear(true); setNombre(''); setDescripcion('') }}
          style={{
            background: 'var(--acento)', color: '#000',
            border: 'none', borderRadius: 'var(--radio)',
            padding: '10px 20px', fontWeight: 700,
            fontSize: 13, cursor: 'pointer',
            transition: 'var(--transition)',
          }}
        >
          + Nueva playlist
        </button>
      </div>

      {cargando ? <Spinner /> : playlists.length === 0
        ? <EstadoVacio icono="📋" texto="Aún no tienes playlists. ¡Crea una!" />
        : (
          <div style={{
            display: 'grid',
            gridTemplateColumns: 'repeat(auto-fill, minmax(240px, 1fr))',
            gap: 14,
          }}>
            {playlists.map(p => (
              <div key={p.id} style={{
                background: 'var(--bg-card)',
                border: '1px solid var(--borde)',
                borderRadius: 'var(--radio)',
                padding: 22,
                display: 'flex', flexDirection: 'column', gap: 10,
                transition: 'var(--transition)',
              }}
                onMouseEnter={e => { e.currentTarget.style.background = 'var(--bg-card-hover)'; e.currentTarget.style.transform = 'translateY(-2px)' }}
                onMouseLeave={e => { e.currentTarget.style.background = 'var(--bg-card)'; e.currentTarget.style.transform = 'translateY(0)' }}
              >
                <div style={{ fontSize: 32, cursor: 'pointer' }} onClick={() => navigate(`/playlists/${p.id}`)}>📋</div>
                <div style={{ fontWeight: 700, fontSize: 16, cursor: 'pointer' }} onClick={() => navigate(`/playlists/${p.id}`)}>
                  {p.nombre}
                </div>
                {p.descripcion && <div style={{ fontSize: 13, color: 'var(--texto-2)' }}>{p.descripcion}</div>}
                <div style={{ display: 'flex', gap: 8, marginTop: 4 }}>
                  <button onClick={() => abrirEditar(p)} style={{
                    flex: 1, padding: '8px',
                    background: 'transparent', color: 'var(--texto-2)',
                    border: '1px solid var(--borde-fuerte)',
                    borderRadius: 8, cursor: 'pointer', fontSize: 12, fontWeight: 600,
                  }}>
                    Editar
                  </button>
                  <button onClick={() => setPlaylistPorEliminar(p)} style={{
                    flex: 1, padding: '8px',
                    background: 'var(--rojo-dim)', color: 'var(--rojo)',
                    border: '1px solid var(--rojo)',
                    borderRadius: 8, cursor: 'pointer', fontSize: 12, fontWeight: 600,
                  }}>
                    Eliminar
                  </button>
                </div>
              </div>
            ))}
          </div>
        )
      }

      <Modal visible={modalCrear} onClose={() => setModalCrear(false)} titulo="Nueva Playlist">
        <Campo label="Nombre" value={nombre} onChange={setNombre} placeholder="Mi playlist favorita" />
        <Campo label="Descripción (opcional)" value={descripcion} onChange={setDescripcion} placeholder="Una descripción..." />
        <div style={{ display: 'flex', gap: 10, justifyContent: 'flex-end' }}>
          <BtnSecundario onClick={() => setModalCrear(false)}>Cancelar</BtnSecundario>
          <BtnPrimario onClick={crear} style={{ width: 'auto', padding: '12px 24px' }}>Crear</BtnPrimario>
        </div>
      </Modal>

      <Modal visible={!!modalEditar} onClose={() => setModalEditar(null)} titulo="Editar Playlist">
        <Campo label="Nombre" value={nombre} onChange={setNombre} placeholder="Nombre de la playlist" />
        <Campo label="Descripción" value={descripcion} onChange={setDescripcion} placeholder="Descripción..." />
        <div style={{ display: 'flex', gap: 10, justifyContent: 'flex-end' }}>
          <BtnSecundario onClick={() => setModalEditar(null)}>Cancelar</BtnSecundario>
          <BtnPrimario onClick={guardarEdicion} style={{ width: 'auto', padding: '12px 24px' }}>Guardar</BtnPrimario>
        </div>
      </Modal>

      <Modal
        visible={!!playlistPorEliminar}
        onClose={() => setPlaylistPorEliminar(null)}
        titulo="Eliminar playlist"
      >
        <p style={{ color: 'var(--texto-2)', lineHeight: 1.5 }}>
          ¿Seguro que quieres eliminar "{playlistPorEliminar?.nombre}"? Esta acción no se puede deshacer.
        </p>
        <div style={{ display: 'flex', gap: 10, justifyContent: 'flex-end' }}>
          <BtnSecundario onClick={() => setPlaylistPorEliminar(null)}>Cancelar</BtnSecundario>
          <button
            onClick={eliminar}
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
            Eliminar
          </button>
        </div>
      </Modal>
    </div>
  )
}
