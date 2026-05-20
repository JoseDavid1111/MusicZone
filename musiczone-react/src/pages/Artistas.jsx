import { useEffect, useMemo, useState } from 'react'
import { artistaService, cancionService } from '../services/api'
import { Spinner, EstadoVacio } from '../components/ui'

function normalizarLista(valor) {
  return Array.isArray(valor) ? valor.filter(Boolean) : []
}

function nombreAlbum(album) {
  if (!album) return null
  if (typeof album === 'string') return album
  return album.titulo || album.nombre || null
}

function obtenerIniciales(nombre = '') {
  const partes = nombre.trim().split(/\s+/).filter(Boolean)
  if (partes.length === 0) return 'A'
  return partes.slice(0, 2).map(p => p[0]).join('').toUpperCase()
}

export default function Artistas() {
  const [artistas, setArtistas] = useState([])
  const [cargando, setCargando] = useState(true)
  const [artistaSeleccionado, setArtistaSeleccionado] = useState(null)
  const [cancionesArtista, setCancionesArtista] = useState([])
  const [cargandoDetalle, setCargandoDetalle] = useState(false)

  useEffect(() => {
    artistaService.listarTodos()
      .then(d => setArtistas(d.datos || []))
      .catch(() => {})
      .finally(() => setCargando(false))
  }, [])

  const albumes = useMemo(() => {
    const albumesDelArtista = normalizarLista(artistaSeleccionado?.albumes)
      .map(nombreAlbum)
      .filter(Boolean)

    const albumesDeCanciones = cancionesArtista
      .map(c => nombreAlbum(c.album))
      .filter(Boolean)

    return [...new Set([...albumesDelArtista, ...albumesDeCanciones])]
  }, [artistaSeleccionado, cancionesArtista])

  async function seleccionarArtista(artista) {
    if (artistaSeleccionado?.id === artista.id) {
      setArtistaSeleccionado(null)
      setCancionesArtista([])
      return
    }

    setCargandoDetalle(true)
    setArtistaSeleccionado(artista)
    setCancionesArtista([])

    try {
      const [detalle, canciones] = await Promise.all([
        artistaService.verDetalle(artista.id),
        cancionService.buscarPorArtista(artista.nombre),
      ])

      setArtistaSeleccionado(detalle.datos || artista)
      setCancionesArtista(canciones.datos || [])
    } catch {
      setArtistaSeleccionado(artista)
      setCancionesArtista([])
    } finally {
      setCargandoDetalle(false)
    }
  }

  return (
    <div style={{ display: 'flex', flexDirection: 'column', gap: 24, animation: 'fadeUp 0.4s ease' }}>
      <h2 style={{ fontFamily: 'var(--font-display)', fontSize: 32, letterSpacing: 1 }}>
        Artistas
      </h2>

      {artistaSeleccionado && (
        <section style={{
          background: 'var(--bg-card)',
          border: '1px solid var(--borde-fuerte)',
          borderRadius: 'var(--radio)',
          padding: 24,
          display: 'grid',
          gridTemplateColumns: 'repeat(auto-fit, minmax(260px, 1fr))',
          gap: 24,
          boxShadow: 'var(--sombra)',
        }}>
          <div style={{ display: 'flex', flexDirection: 'column', gap: 14 }}>
            <div style={{
              width: 84,
              height: 84,
              background: 'var(--acento-dim)',
              border: '2px solid var(--acento)',
              borderRadius: '50%',
              display: 'flex',
              alignItems: 'center',
              justifyContent: 'center',
              fontSize: 26,
              fontWeight: 800,
              color: 'var(--acento)',
            }}>
              {obtenerIniciales(artistaSeleccionado.nombre)}
            </div>

            <div>
              <h3 style={{
                fontFamily: 'var(--font-display)',
                fontSize: 28,
                letterSpacing: 1,
                marginBottom: 6,
              }}>
                {artistaSeleccionado.nombre}
              </h3>
              <div style={{ display: 'flex', flexWrap: 'wrap', gap: 8 }}>
                {artistaSeleccionado.genero && (
                  <span style={chipStyle}>{artistaSeleccionado.genero}</span>
                )}
                {artistaSeleccionado.pais && (
                  <span style={chipStyle}>País: {artistaSeleccionado.pais}</span>
                )}
              </div>
            </div>

            {artistaSeleccionado.perfilUrl && (
              <a
                href={artistaSeleccionado.perfilUrl}
                target="_blank"
                rel="noreferrer"
                style={{
                  width: 'fit-content',
                  color: '#000',
                  background: 'var(--acento)',
                  borderRadius: 'var(--radio)',
                  padding: '10px 14px',
                  fontWeight: 700,
                  fontSize: 13,
                }}
              >
                Ver perfil
              </a>
            )}

            <button
              type="button"
              onClick={() => {
                setArtistaSeleccionado(null)
                setCancionesArtista([])
              }}
              style={{
                width: 'fit-content',
                background: 'transparent',
                color: 'var(--texto-2)',
                border: '1px solid var(--borde-fuerte)',
                borderRadius: 'var(--radio)',
                padding: '9px 13px',
              }}
            >
              Cerrar detalle
            </button>
          </div>

          <div style={{ display: 'grid', gridTemplateColumns: 'repeat(auto-fit, minmax(210px, 1fr))', gap: 16 }}>
            <div style={detalleBloqueStyle}>
              <h4 style={detalleTituloStyle}>Biografía</h4>
              <p style={{ color: 'var(--texto-2)', lineHeight: 1.6 }}>
                {artistaSeleccionado.bio || 'No hay biografía disponible para este artista.'}
              </p>
            </div>

            <div style={detalleBloqueStyle}>
              <h4 style={detalleTituloStyle}>Álbumes</h4>
              {cargandoDetalle ? (
                <p style={textoSecundarioStyle}>Cargando álbumes...</p>
              ) : albumes.length > 0 ? (
                <ul style={listaStyle}>
                  {albumes.map(album => (
                    <li key={album} style={itemStyle}>{album}</li>
                  ))}
                </ul>
              ) : (
                <p style={textoSecundarioStyle}>No hay álbumes registrados.</p>
              )}
            </div>

            <div style={{ ...detalleBloqueStyle, gridColumn: '1 / -1' }}>
              <h4 style={detalleTituloStyle}>Canciones</h4>
              {cargandoDetalle ? (
                <p style={textoSecundarioStyle}>Cargando canciones...</p>
              ) : cancionesArtista.length > 0 ? (
                <div style={{ display: 'grid', gridTemplateColumns: 'repeat(auto-fill, minmax(220px, 1fr))', gap: 10 }}>
                  {cancionesArtista.map(cancion => (
                    <div key={cancion.id} style={itemStyle}>
                      <div style={{ fontWeight: 700 }}>{cancion.titulo}</div>
                      <div style={{ color: 'var(--texto-3)', fontSize: 12 }}>
                        {[cancion.album, cancion.yearLanzamiento].filter(Boolean).join(' - ')}
                      </div>
                    </div>
                  ))}
                </div>
              ) : (
                <p style={textoSecundarioStyle}>No hay canciones registradas.</p>
              )}
            </div>
          </div>
        </section>
      )}

      {cargando ? <Spinner /> : artistas.length === 0
        ? <EstadoVacio icono="A" texto="No hay artistas disponibles" />
        : (
          <div style={{
            display: 'grid',
            gridTemplateColumns: 'repeat(auto-fill, minmax(190px, 1fr))',
            gap: 14,
          }}>
            {artistas.map(a => {
              const activo = artistaSeleccionado?.id === a.id

              return (
                <button
                  key={a.id}
                  type="button"
                  onClick={() => seleccionarArtista(a)}
                  style={{
                    background: activo ? 'var(--bg-card-hover)' : 'var(--bg-card)',
                    border: `1px solid ${activo ? 'var(--acento)' : 'var(--borde)'}`,
                    borderRadius: 'var(--radio)',
                    padding: '24px 20px',
                    textAlign: 'center',
                    transition: 'var(--transition)',
                    color: 'var(--texto-1)',
                    boxShadow: activo ? '0 0 0 3px var(--acento-dim)' : 'none',
                  }}
                  onMouseEnter={e => {
                    e.currentTarget.style.background = 'var(--bg-card-hover)'
                    e.currentTarget.style.transform = 'translateY(-2px)'
                    e.currentTarget.style.boxShadow = activo ? '0 0 0 3px var(--acento-dim)' : 'var(--sombra)'
                  }}
                  onMouseLeave={e => {
                    e.currentTarget.style.background = activo ? 'var(--bg-card-hover)' : 'var(--bg-card)'
                    e.currentTarget.style.transform = 'translateY(0)'
                    e.currentTarget.style.boxShadow = activo ? '0 0 0 3px var(--acento-dim)' : 'none'
                  }}
                >
                  <div style={{
                    width: 64,
                    height: 64,
                    background: 'var(--acento-dim)',
                    border: '2px solid var(--acento)',
                    borderRadius: '50%',
                    display: 'flex',
                    alignItems: 'center',
                    justifyContent: 'center',
                    fontSize: 20,
                    fontWeight: 800,
                    color: 'var(--acento)',
                    margin: '0 auto 12px',
                  }}>
                    {obtenerIniciales(a.nombre)}
                  </div>
                  <div style={{ fontWeight: 600, fontSize: 15, marginBottom: 4 }}>{a.nombre}</div>
                  {a.genero && <div style={{ fontSize: 12, color: 'var(--texto-2)' }}>{a.genero}</div>}
                  {a.pais && <div style={{ fontSize: 11, color: 'var(--texto-3)', marginTop: 4 }}>{a.pais}</div>}
                </button>
              )
            })}
          </div>
        )
      }
    </div>
  )
}

const chipStyle = {
  display: 'inline-flex',
  border: '1px solid var(--borde-fuerte)',
  borderRadius: 999,
  color: 'var(--texto-2)',
  fontSize: 12,
  padding: '5px 10px',
}

const detalleBloqueStyle = {
  background: 'rgba(255,255,255,0.035)',
  border: '1px solid var(--borde)',
  borderRadius: 'var(--radio)',
  padding: 16,
}

const detalleTituloStyle = {
  fontSize: 13,
  color: 'var(--texto-1)',
  marginBottom: 10,
  textTransform: 'uppercase',
  letterSpacing: '0.8px',
}

const textoSecundarioStyle = {
  color: 'var(--texto-3)',
  fontSize: 13,
}

const listaStyle = {
  listStyle: 'none',
  display: 'grid',
  gap: 8,
}

const itemStyle = {
  background: 'var(--bg-base)',
  border: '1px solid var(--borde)',
  borderRadius: 'var(--radio)',
  color: 'var(--texto-2)',
  padding: '10px 12px',
}
