import { useState } from 'react'
import { useNavigate } from 'react-router-dom'
import { useAuth } from '../../context/AuthContext'

export default function Header({ onBuscar }) {
  const { usuario, logout } = useAuth()
  const navigate = useNavigate()
  const [query, setQuery] = useState('')

  const handleBuscar = (e) => {
    setQuery(e.target.value)
  }

  const enviarBusqueda = (e) => {
    e.preventDefault()
    const texto = query.trim()
    if (onBuscar) onBuscar(texto)
    navigate(texto ? `/canciones?buscar=${encodeURIComponent(texto)}` : '/canciones')
  }

  const handleLogout = () => {
    logout()
    navigate('/login')
  }

  return (
    <header style={{
      position: 'fixed', top: 0, left: 0, right: 0,
      height: 'var(--header-h)',
      background: 'rgba(8,8,16,0.85)',
      backdropFilter: 'blur(20px)',
      borderBottom: '1px solid var(--borde)',
      display: 'flex', alignItems: 'center',
      padding: '0 24px', gap: 16,
      zIndex: 100,
    }}>
      {/* Logo */}
      <div style={{ minWidth: 'var(--sidebar-w)', cursor: 'pointer' }} onClick={() => navigate('/app')}>
        <span style={{
          fontFamily: 'var(--font-display)',
          fontSize: 22, letterSpacing: 3,
          color: 'var(--acento)',
          filter: 'drop-shadow(0 0 8px var(--acento-glow))',
        }}>
          ♪ MusicZone
        </span>
      </div>

      {/* Buscador */}
      <form onSubmit={enviarBusqueda} style={{
        flex: 1, maxWidth: 480,
        display: 'flex', alignItems: 'center',
        background: 'var(--bg-card)',
        border: '1px solid var(--borde-fuerte)',
        borderRadius: 100, padding: '0 16px', gap: 10,
      }}>
        <span style={{ color: 'var(--texto-2)', fontSize: 15 }}>🔍</span>
        <input
          value={query}
          onChange={handleBuscar}
          placeholder="Buscar canciones o artistas..."
          style={{
            flex: 1, background: 'transparent',
            border: 'none', outline: 'none',
            color: 'var(--texto-1)', fontSize: 14,
            padding: '10px 0',
          }}
        />
      </form>

      {/* Usuario */}
      <div style={{ marginLeft: 'auto', display: 'flex', alignItems: 'center', gap: 12 }}>
        {usuario && (
          <span style={{
            background: 'var(--acento-dim)',
            color: 'var(--acento)',
            border: '1px solid var(--acento)',
            padding: '6px 14px', borderRadius: 100,
            fontSize: 13, fontWeight: 600,
          }}>
            ♪ {usuario.nombreUsuario}
          </span>
        )}
        <button
          onClick={handleLogout}
          style={{
            background: 'transparent',
            border: '1px solid var(--borde-fuerte)',
            color: 'var(--texto-2)',
            padding: '8px 16px', borderRadius: 100,
            fontSize: 13, cursor: 'pointer',
            transition: 'var(--transition)',
          }}
          onMouseEnter={e => { e.currentTarget.style.borderColor = 'var(--rojo)'; e.currentTarget.style.color = 'var(--rojo)' }}
          onMouseLeave={e => { e.currentTarget.style.borderColor = 'var(--borde-fuerte)'; e.currentTarget.style.color = 'var(--texto-2)' }}
        >
          Salir
        </button>
      </div>
    </header>
  )
}
