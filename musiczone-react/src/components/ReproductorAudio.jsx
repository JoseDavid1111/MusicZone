export default function ReproductorAudio({ cancion, onClose, onEnded, indice = 0, total = 0 }) {
  if (!cancion) return null

  return (
    <div style={{
      position: 'fixed',
      left: 'var(--sidebar-w)',
      right: 0,
      bottom: 0,
      zIndex: 180,
      display: 'flex',
      alignItems: 'center',
      gap: 18,
      padding: '16px 26px',
      background: 'linear-gradient(135deg, rgba(16,16,26,0.98), rgba(24,24,42,0.97))',
      borderTop: '1px solid var(--borde-fuerte)',
      boxShadow: '0 -16px 44px rgba(0,0,0,0.55)',
      backdropFilter: 'blur(16px)',
    }}>
      <div style={{
        width: 46,
        height: 46,
        borderRadius: 10,
        background: 'var(--acento-dim)',
        border: '1px solid var(--acento)',
        color: 'var(--acento)',
        display: 'flex',
        alignItems: 'center',
        justifyContent: 'center',
        fontSize: 22,
        flex: '0 0 auto',
      }}>
        ♪
      </div>

      <div style={{ minWidth: 180, flex: '0 1 300px' }}>
        <div style={{
          color: 'var(--acento)',
          fontSize: 10,
          fontWeight: 800,
          textTransform: 'uppercase',
          letterSpacing: 1.2,
          marginBottom: 4,
        }}>
          Reproduciendo
        </div>
        <div style={{ fontWeight: 700, fontSize: 14, whiteSpace: 'nowrap', overflow: 'hidden', textOverflow: 'ellipsis' }}>
          {cancion.titulo}
        </div>
        <div style={{ color: 'var(--texto-2)', fontSize: 12, marginTop: 3, whiteSpace: 'nowrap', overflow: 'hidden', textOverflow: 'ellipsis' }}>
          {cancion.artista || 'Artista desconocido'}
          {total > 1 ? ` · ${indice + 1} de ${total}` : ''}
        </div>
      </div>

      {cancion.urlAudio ? (
        <audio
          key={cancion.id || cancion.idCancion || cancion.urlAudio}
          controls
          autoPlay
          src={cancion.urlAudio}
          onEnded={onEnded}
          style={{
            flex: '1 1 420px',
            minWidth: 240,
            height: 40,
            accentColor: 'var(--acento)',
          }}
        >
          Tu navegador no soporta reproducción de audio.
        </audio>
      ) : (
        <div style={{ flex: 1, color: 'var(--texto-3)', fontSize: 13 }}>
          Audio no disponible
        </div>
      )}

      <button
        onClick={onClose}
        title="Cerrar reproductor"
        style={{
          width: 34,
          height: 34,
          borderRadius: 8,
          border: '1px solid var(--borde-fuerte)',
          background: 'rgba(255,255,255,0.03)',
          color: 'var(--texto-2)',
          fontSize: 18,
        }}
      >
        x
      </button>
    </div>
  )
}
