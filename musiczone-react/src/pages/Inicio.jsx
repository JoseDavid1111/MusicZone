import { useEffect, useState } from 'react'
import { useOutletContext } from 'react-router-dom'
import { useAuth } from '../context/AuthContext'
import { cancionService, playlistService } from '../services/api'
import { Spinner, EstadoVacio } from '../components/ui'
import TarjetaCancion from '../components/TarjetaCancion'

export default function Inicio() {
  const { usuario } = useAuth()
  const { reproducirCancion } = useOutletContext()
  const [canciones, setCanciones] = useState([])
  const [playlists, setPlaylists] = useState([])
  const [cargando, setCargando] = useState(true)

  useEffect(() => {
    Promise.all([
      cancionService.listarTodas(),
      playlistService.listarPorUsuario(usuario.nombreUsuario),
    ]).then(([c, p]) => {
      setCanciones((c.datos || []).slice(0, 8))
      setPlaylists(p.datos || [])
    }).catch(() => {}).finally(() => setCargando(false))
  }, [usuario.nombreUsuario])

  return (
    <div style={{ display: 'flex', flexDirection: 'column', gap: 32, animation: 'fadeUp 0.4s ease' }}>
      <div style={{
        background: 'linear-gradient(135deg, var(--acento-dim), transparent)',
        border: '1px solid var(--acento-dim)',
        borderRadius: 'var(--radio-lg)',
        padding: '32px 36px',
      }}>
        <h2 style={{
          fontFamily: 'var(--font-display)',
          fontSize: 42, letterSpacing: 1,
          marginBottom: 6,
        }}>
          Bienvenido, {usuario?.nombreUsuario} 👋
        </h2>
        <p style={{ color: 'var(--texto-2)', fontSize: 15 }}>
          Tu música, tu mundo. Tienes {playlists.length} playlist{playlists.length !== 1 ? 's' : ''} creada{playlists.length !== 1 ? 's' : ''}.
        </p>
      </div>

      <div>
        <h3 style={{
          fontFamily: 'var(--font-display)',
          fontSize: 26, letterSpacing: 1, marginBottom: 18,
        }}>
          Canciones destacadas
        </h3>

        {cargando ? <Spinner /> : canciones.length === 0
          ? <EstadoVacio icono="♪" texto="No hay canciones disponibles" />
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
                  onReproducir={reproducirCancion}
                />
              ))}
            </div>
          )
        }
      </div>
    </div>
  )
}
