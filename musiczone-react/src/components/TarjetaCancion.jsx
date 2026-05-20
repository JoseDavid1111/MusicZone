import { useState } from 'react'
import { playlistService } from '../services/api'
import { useToast } from '../context/ToastContext'
import { Modal, BtnSecundario, ChipGenero } from './ui'

function formatearDuracion(segundos) {
  if (!segundos) return 'No registrada'
  const minutos = Math.floor(segundos / 60)
  const resto = String(segundos % 60).padStart(2, '0')
  return `${minutos}:${resto}`
}

export default function TarjetaCancion({ cancion, playlists = [], onReproducir }) {
  const [modalPlaylist, setModalPlaylist] = useState(false)
  const [modalDetalle, setModalDetalle] = useState(false)
  const { exito, error } = useToast()

  const agregar = async (idPlaylist) => {
    try {
      await playlistService.agregarCancion(idPlaylist, cancion.id)
      exito('Canción agregada ✓')
      setModalPlaylist(false)
      setModalDetalle(false)
    } catch (e) {
      error(e.message || 'No se pudo agregar')
    }
  }

  const abrirPlaylist = (e) => {
    e?.stopPropagation()
    setModalDetalle(false)
    setModalPlaylist(true)
  }

  const reproducir = (e) => {
    e?.stopPropagation()
    onReproducir?.(cancion)
  }

  return (
    <>
      <div style={{
        background: 'var(--bg-card)',
        border: '1px solid var(--borde)',
        borderRadius: 'var(--radio)',
        padding: 18,
        display: 'flex', flexDirection: 'column', gap: 8,
        transition: 'var(--transition)',
        position: 'relative', overflow: 'hidden',
        cursor: 'pointer',
      }}
        onClick={() => setModalDetalle(true)}
        onMouseEnter={e => {
          e.currentTarget.style.background = 'var(--bg-card-hover)'
          e.currentTarget.style.borderColor = 'var(--borde-fuerte)'
          e.currentTarget.style.transform = 'translateY(-2px)'
          e.currentTarget.style.boxShadow = '0 8px 24px rgba(0,0,0,0.5)'
        }}
        onMouseLeave={e => {
          e.currentTarget.style.background = 'var(--bg-card)'
          e.currentTarget.style.borderColor = 'var(--borde)'
          e.currentTarget.style.transform = 'translateY(0)'
          e.currentTarget.style.boxShadow = 'none'
        }}
      >
        <div style={{
          position: 'absolute', top: 0, left: 0, right: 0, height: 2,
          background: 'var(--acento)',
        }} />

        <div style={{ fontWeight: 600, fontSize: 15 }}>{cancion.titulo}</div>
        <div style={{ fontSize: 13, color: 'var(--texto-2)' }}>
          {cancion.artista || 'Artista desconocido'}
        </div>
        {cancion.genero && <ChipGenero texto={cancion.genero} />}

        <button
          onClick={reproducir}
          disabled={!cancion.urlAudio}
          style={botonReproducirStyle(cancion.urlAudio)}
        >
          Reproducir
        </button>

        <button
          onClick={abrirPlaylist}
          style={{
            marginTop: 8, padding: '9px',
            background: 'var(--acento-dim)',
            color: 'var(--acento)',
            border: '1px solid var(--acento)',
            borderRadius: 8, cursor: 'pointer',
            fontSize: 12, fontWeight: 700,
            transition: 'var(--transition)',
          }}
          onMouseEnter={e => { e.currentTarget.style.background = 'var(--acento)'; e.currentTarget.style.color = '#000' }}
          onMouseLeave={e => { e.currentTarget.style.background = 'var(--acento-dim)'; e.currentTarget.style.color = 'var(--acento)' }}
        >
          + Agregar a playlist
        </button>
      </div>

      <Modal visible={modalDetalle} onClose={() => setModalDetalle(false)} titulo={cancion.titulo}>
        <div style={{ display: 'grid', gap: 10 }}>
          <Dato label="Artista" valor={cancion.artista || 'Artista desconocido'} />
          <Dato label="Álbum" valor={cancion.album || 'Sin álbum registrado'} />
          <Dato label="Género" valor={cancion.genero || 'Sin género registrado'} />
          <Dato label="Año" valor={cancion.yearLanzamiento || 'No registrado'} />
          <Dato label="Duración" valor={formatearDuracion(cancion.duracionSegundos)} />
          {cancion.numeroTrack && <Dato label="Track" valor={cancion.numeroTrack} />}
        </div>

        <div style={{ display: 'flex', gap: 10, justifyContent: 'flex-end', flexWrap: 'wrap' }}>
          <BtnSecundario onClick={() => setModalDetalle(false)}>Cerrar</BtnSecundario>
          <button
            onClick={abrirPlaylist}
            style={{
              background: 'var(--acento-dim)',
              color: 'var(--acento)',
              border: '1px solid var(--acento)',
              borderRadius: 'var(--radio)',
              padding: '11px 18px',
              fontSize: 13,
              fontWeight: 700,
            }}
          >
            Agregar a playlist
          </button>
          <button
            onClick={reproducir}
            disabled={!cancion.urlAudio}
            style={{
              ...botonReproducirStyle(cancion.urlAudio),
              marginTop: 0,
              padding: '11px 18px',
              width: 'auto',
            }}
          >
            Reproducir
          </button>
        </div>
      </Modal>

      <Modal visible={modalPlaylist} onClose={() => setModalPlaylist(false)} titulo="Agregar a playlist">
        {playlists.length === 0 ? (
          <p style={{ color: 'var(--texto-2)', fontSize: 14 }}>
            No tienes playlists. Crea una primero en la sección "Mis Playlists".
          </p>
        ) : (
          <div style={{ display: 'flex', flexDirection: 'column', gap: 8, maxHeight: 300, overflow: 'auto' }}>
            {playlists.map(p => (
              <div key={p.id} style={{
                display: 'flex', alignItems: 'center',
                justifyContent: 'space-between',
                gap: 12,
                padding: '10px 14px',
                background: 'var(--bg-base)',
                borderRadius: 8, border: '1px solid var(--borde)',
              }}>
                <span style={{ fontSize: 14 }}>📋 {p.nombre}</span>
                <button
                  onClick={() => agregar(p.id)}
                  style={{
                    background: 'var(--acento-dim)', color: 'var(--acento)',
                    border: '1px solid var(--acento)',
                    borderRadius: 6, padding: '6px 14px',
                    fontSize: 12, fontWeight: 700, cursor: 'pointer',
                    transition: 'var(--transition)',
                  }}
                  onMouseEnter={e => { e.currentTarget.style.background = 'var(--acento)'; e.currentTarget.style.color = '#000' }}
                  onMouseLeave={e => { e.currentTarget.style.background = 'var(--acento-dim)'; e.currentTarget.style.color = 'var(--acento)' }}
                >
                  Agregar
                </button>
              </div>
            ))}
          </div>
        )}
        <BtnSecundario onClick={() => setModalPlaylist(false)} style={{ alignSelf: 'flex-end' }}>
          Cancelar
        </BtnSecundario>
      </Modal>
    </>
  )
}

function Dato({ label, valor }) {
  return (
    <div style={{
      display: 'flex',
      justifyContent: 'space-between',
      gap: 16,
      padding: '10px 12px',
      border: '1px solid var(--borde)',
      borderRadius: 8,
      background: 'var(--bg-base)',
    }}>
      <span style={{ color: 'var(--texto-3)', fontSize: 12, textTransform: 'uppercase', letterSpacing: 0.6 }}>
        {label}
      </span>
      <span style={{ color: 'var(--texto-1)', fontWeight: 600, textAlign: 'right' }}>
        {valor}
      </span>
    </div>
  )
}

function botonReproducirStyle(habilitado) {
  return {
    marginTop: 8,
    padding: '9px',
    background: habilitado ? 'var(--acento)' : 'var(--bg-glass)',
    color: habilitado ? '#000' : 'var(--texto-3)',
    border: habilitado ? '1px solid var(--acento)' : '1px solid var(--borde)',
    borderRadius: 8,
    cursor: habilitado ? 'pointer' : 'not-allowed',
    fontSize: 12,
    fontWeight: 700,
    transition: 'var(--transition)',
  }
}
