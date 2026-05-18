import { useEffect, useState, useCallback } from 'react'
import { useSearchParams } from 'react-router-dom'
import { useAuth } from '../context/AuthContext'
import { cancionService, playlistService } from '../services/api'
import { Spinner, EstadoVacio } from '../components/ui'
import TarjetaCancion from '../components/TarjetaCancion'
import ReproductorAudio from '../components/ReproductorAudio'

export default function Canciones() {
  const { usuario } = useAuth()
  const [searchParams] = useSearchParams()
  const [canciones, setCanciones] = useState([])
  const [playlists, setPlaylists] = useState([])
  const [cargando, setCargando] = useState(true)
  const [busqueda, setBusqueda] = useState('')
  const [busquedaArtista, setBusquedaArtista] = useState('')
  const [cancionActiva, setCancionActiva] = useState(null)
  const busquedaGlobal = searchParams.get('buscar') || ''

  useEffect(() => {
    setCargando(true)
    Promise.all([
      cancionService.listarTodas(),
      playlistService.listarPorUsuario(usuario.nombreUsuario),
    ]).then(([c, p]) => {
      const cancionesApi = c.datos || []
      const texto = busquedaGlobal.trim().toLowerCase()
      const filtradas = texto
        ? cancionesApi.filter(cancion =>
            cancion.titulo?.toLowerCase().includes(texto) ||
            cancion.artista?.toLowerCase().includes(texto)
          )
        : cancionesApi

      setCanciones(filtradas)
      setBusqueda(busquedaGlobal)
      setBusquedaArtista('')
      setPlaylists(p.datos || [])
    }).catch(() => {}).finally(() => setCargando(false))
  }, [usuario.nombreUsuario, busquedaGlobal])

  const buscarPorTitulo = useCallback(async (titulo) => {
    setBusqueda(titulo)
    setBusquedaArtista('')
    if (!titulo.trim()) {
      const data = await cancionService.listarTodas()
      setCanciones(data.datos || [])
      return
    }
    try {
      const data = await cancionService.buscarPorTitulo(titulo)
      setCanciones(data.datos || [])
    } catch { setCanciones([]) }
  }, [])

  const buscarPorArtista = useCallback(async (artista) => {
    setBusquedaArtista(artista)
    setBusqueda('')
    if (!artista.trim()) {
      const data = await cancionService.listarTodas()
      setCanciones(data.datos || [])
      return
    }
    try {
      const data = await cancionService.buscarPorArtista(artista)
      setCanciones(data.datos || [])
    } catch { setCanciones([]) }
  }, [])

  return (
    <div style={{ display: 'flex', flexDirection: 'column', gap: 24, animation: 'fadeUp 0.4s ease' }}>
      <h2 style={{ fontFamily: 'var(--font-display)', fontSize: 32, letterSpacing: 1 }}>
        Todas las canciones
      </h2>

      {/* Filtros */}
      <div style={{ display: 'flex', gap: 12, flexWrap: 'wrap' }}>
        {[
          { value: busqueda, onChange: buscarPorTitulo, placeholder: 'Buscar por título...' },
          { value: busquedaArtista, onChange: buscarPorArtista, placeholder: 'Buscar por artista...' },
        ].map((campo, i) => (
          <input
            key={i}
            value={campo.value}
            onChange={e => campo.onChange(e.target.value)}
            placeholder={campo.placeholder}
            style={{
              flex: 1, minWidth: 200,
              background: 'var(--bg-card)',
              border: '1px solid var(--borde-fuerte)',
              borderRadius: 'var(--radio)',
              padding: '11px 16px',
              color: 'var(--texto-1)',
              fontSize: 14, outline: 'none',
            }}
          />
        ))}
      </div>

      {/* Grid */}
      {cargando ? <Spinner /> : canciones.length === 0
        ? <EstadoVacio icono="🔍" texto="No se encontraron canciones" />
        : (
          <div style={{
            display: 'grid',
            gridTemplateColumns: 'repeat(auto-fill, minmax(260px, 1fr))',
            gap: 14,
          }}>
            {canciones.map(c => (
              <TarjetaCancion
                key={c.id}
                cancion={c}
                playlists={playlists}
                onReproducir={setCancionActiva}
              />
            ))}
          </div>
        )
      }
      <ReproductorAudio cancion={cancionActiva} onClose={() => setCancionActiva(null)} />
    </div>
  )
}
