import { useEffect, useState } from 'react'
import { artistaService } from '../services/api'
import { Spinner, EstadoVacio } from '../components/ui'

export default function Artistas() {
  const [artistas, setArtistas] = useState([])
  const [cargando, setCargando] = useState(true)

  useEffect(() => {
    artistaService.listarTodos()
      .then(d => setArtistas(d.datos || []))
      .catch(() => {})
      .finally(() => setCargando(false))
  }, [])

  return (
    <div style={{ display: 'flex', flexDirection: 'column', gap: 24, animation: 'fadeUp 0.4s ease' }}>
      <h2 style={{ fontFamily: 'var(--font-display)', fontSize: 32, letterSpacing: 1 }}>
        Artistas
      </h2>

      {cargando ? <Spinner /> : artistas.length === 0
        ? <EstadoVacio icono="🎤" texto="No hay artistas disponibles" />
        : (
          <div style={{
            display: 'grid',
            gridTemplateColumns: 'repeat(auto-fill, minmax(190px, 1fr))',
            gap: 14,
          }}>
            {artistas.map(a => (
              <div key={a.id} style={{
                background: 'var(--bg-card)',
                border: '1px solid var(--borde)',
                borderRadius: 'var(--radio)',
                padding: '24px 20px',
                textAlign: 'center',
                transition: 'var(--transition)',
              }}
                onMouseEnter={e => { e.currentTarget.style.background = 'var(--bg-card-hover)'; e.currentTarget.style.transform = 'translateY(-2px)'; e.currentTarget.style.boxShadow = 'var(--sombra)' }}
                onMouseLeave={e => { e.currentTarget.style.background = 'var(--bg-card)'; e.currentTarget.style.transform = 'translateY(0)'; e.currentTarget.style.boxShadow = 'none' }}
              >
                <div style={{
                  width: 64, height: 64,
                  background: 'var(--acento-dim)',
                  border: '2px solid var(--acento)',
                  borderRadius: '50%',
                  display: 'flex', alignItems: 'center', justifyContent: 'center',
                  fontSize: 28, margin: '0 auto 12px',
                }}>
                  🎤
                </div>
                <div style={{ fontWeight: 600, fontSize: 15, marginBottom: 4 }}>{a.nombre}</div>
                {a.genero && <div style={{ fontSize: 12, color: 'var(--texto-2)' }}>{a.genero}</div>}
                {a.pais && <div style={{ fontSize: 11, color: 'var(--texto-3)', marginTop: 4 }}>📍 {a.pais}</div>}
              </div>
            ))}
          </div>
        )
      }
    </div>
  )
}
