import { useNavigate, useLocation } from 'react-router-dom'
import { useEffect, useState } from 'react'
import { useAuth } from '../../context/AuthContext'
import { playlistService } from '../../services/api'

export default function Sidebar() {
  const navigate = useNavigate()
  const location = useLocation()
  const { usuario } = useAuth()
  const [playlists, setPlaylists] = useState([])

  useEffect(() => {
    if (usuario) {
      playlistService.listarPorUsuario(usuario.nombreUsuario)
        .then(r => setPlaylists(r.datos || []))
        .catch(() => {})
    }
  }, [usuario?.nombreUsuario, location.pathname])

  const navItems = [
    { path: '/app',        icono: '🏠', label: 'Inicio' },
    { path: '/canciones',  icono: '🎵', label: 'Canciones' },
    { path: '/artistas',   icono: '🎤', label: 'Artistas' },
    { path: '/playlists',  icono: '📋', label: 'Mis Playlists' },
  ]

  const esActivo = (path) => location.pathname === path

  return (
    <nav style={{
      width: 'var(--sidebar-w)',
      background: 'var(--bg-sidebar)',
      borderRight: '1px solid var(--borde)',
      position: 'fixed',
      top: 'var(--header-h)', bottom: 0,
      overflow: 'auto',
      padding: '20px 10px',
      display: 'flex', flexDirection: 'column', gap: 4,
    }}>
      <p style={{
        fontSize: 10, fontWeight: 700,
        textTransform: 'uppercase', letterSpacing: 1.5,
        color: 'var(--texto-3)', padding: '0 12px',
        marginBottom: 4, marginTop: 8,
      }}>
        Menú
      </p>

      {navItems.map(item => (
        <button
          key={item.path}
          onClick={() => navigate(item.path)}
          style={{
            width: '100%', display: 'flex',
            alignItems: 'center', gap: 10,
            padding: '11px 14px',
            border: 'none', borderRadius: 'var(--radio)',
            background: esActivo(item.path) ? 'var(--acento-dim)' : 'transparent',
            color: esActivo(item.path) ? 'var(--acento)' : 'var(--texto-2)',
            fontWeight: esActivo(item.path) ? 600 : 400,
            fontSize: 14, cursor: 'pointer',
            transition: 'var(--transition)', textAlign: 'left',
          }}
          onMouseEnter={e => { if (!esActivo(item.path)) { e.currentTarget.style.background = 'var(--bg-card)'; e.currentTarget.style.color = 'var(--texto-1)' } }}
          onMouseLeave={e => { if (!esActivo(item.path)) { e.currentTarget.style.background = 'transparent'; e.currentTarget.style.color = 'var(--texto-2)' } }}
        >
          <span style={{ minWidth: 24, fontSize: 16 }}>{item.icono}</span> {item.label}
        </button>
      ))}

      {playlists.length > 0 && (
        <>
          <p style={{
            fontSize: 10, fontWeight: 700,
            textTransform: 'uppercase', letterSpacing: 1.5,
            color: 'var(--texto-3)', padding: '0 12px',
            marginTop: 20, marginBottom: 4,
          }}>
            Mis listas
          </p>
          {playlists.map(p => (
            <button
              key={p.id}
              onClick={() => navigate(`/playlists/${p.id}`)}
              style={{
                width: '100%', padding: '9px 14px',
                border: 'none', borderRadius: 'var(--radio)',
                background: location.pathname === `/playlists/${p.id}` ? 'var(--acento-dim)' : 'transparent',
                color: location.pathname === `/playlists/${p.id}` ? 'var(--acento)' : 'var(--texto-2)',
                fontSize: 13, cursor: 'pointer',
                textAlign: 'left', transition: 'var(--transition)',
                whiteSpace: 'nowrap', overflow: 'hidden', textOverflow: 'ellipsis',
              }}
              onMouseEnter={e => { e.currentTarget.style.background = 'var(--bg-card)'; e.currentTarget.style.color = 'var(--texto-1)' }}
              onMouseLeave={e => { e.currentTarget.style.background = 'transparent'; e.currentTarget.style.color = 'var(--texto-2)' }}
            >
              📋 {p.nombre}
            </button>
          ))}
        </>
      )}
    </nav>
  )
}
