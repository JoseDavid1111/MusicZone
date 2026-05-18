import { useState } from 'react'
import { playlistService } from '../services/api'
import { useToast } from '../context/ToastContext'
import { Modal, BtnSecundario, ChipGenero } from './ui'

export default function TarjetaCancion({ cancion, playlists = [], onReproducir }) {
  const [modalVisible, setModalVisible] = useState(false)
  const { exito, error } = useToast()

  const agregar = async (idPlaylist) => {
    try {
      await playlistService.agregarCancion(idPlaylist, cancion.id)
      exito('Canción agregada ✓')
      setModalVisible(false)
    } catch (e) {
      error(e.message || 'No se pudo agregar')
    }
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
        cursor: 'default',
      }}
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
        {/* Línea acento superior */}
        <div style={{
          position: 'absolute', top: 0, left: 0, right: 0, height: 2,
          background: 'var(--acento)', transform: 'scaleX(0)',
          transition: 'transform 0.25s ease', transformOrigin: 'left',
        }} className="linea-acento" />

        <div style={{ fontWeight: 600, fontSize: 15 }}>{cancion.titulo}</div>
        <div style={{ fontSize: 13, color: 'var(--texto-2)' }}>
          🎤 {cancion.artista || 'Artista desconocido'}
        </div>
        {cancion.genero && <ChipGenero texto={cancion.genero} />}

        <button
          onClick={() => onReproducir?.(cancion)}
          disabled={!cancion.urlAudio}
          style={{
            marginTop: 8,
            padding: '9px',
            background: cancion.urlAudio ? 'var(--acento)' : 'var(--bg-glass)',
            color: cancion.urlAudio ? '#000' : 'var(--texto-3)',
            border: cancion.urlAudio ? '1px solid var(--acento)' : '1px solid var(--borde)',
            borderRadius: 8,
            cursor: cancion.urlAudio ? 'pointer' : 'not-allowed',
            fontSize: 12,
            fontWeight: 700,
            transition: 'var(--transition)',
          }}
        >
          Reproducir
        </button>

        <button
          onClick={() => setModalVisible(true)}
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

      {/* Modal para elegir playlist */}
      <Modal visible={modalVisible} onClose={() => setModalVisible(false)} titulo="Agregar a playlist">
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
        <BtnSecundario onClick={() => setModalVisible(false)} style={{ alignSelf: 'flex-end' }}>
          Cancelar
        </BtnSecundario>
      </Modal>
    </>
  )
}
