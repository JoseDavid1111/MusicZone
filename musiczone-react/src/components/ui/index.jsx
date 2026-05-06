// ═══════════════════════════════════════════
//  Componentes UI reutilizables
// ═══════════════════════════════════════════

import { useState } from 'react'

// ── Botón principal ───────────────────────
export function BtnPrimario({ children, onClick, disabled, tipo = 'button', style = {} }) {
  return (
    <button
      type={tipo}
      onClick={onClick}
      disabled={disabled}
      style={{
        background: disabled ? 'var(--texto-3)' : 'var(--acento)',
        color: '#000',
        border: 'none',
        borderRadius: 'var(--radio)',
        padding: '13px 20px',
        fontWeight: 700,
        fontSize: 14,
        cursor: disabled ? 'not-allowed' : 'pointer',
        transition: 'var(--transition)',
        width: '100%',
        letterSpacing: '0.3px',
        ...style,
      }}
      onMouseEnter={e => !disabled && (e.target.style.background = 'var(--acento-hover)')}
      onMouseLeave={e => !disabled && (e.target.style.background = 'var(--acento)')}
    >
      {children}
    </button>
  )
}

// ── Botón secundario ──────────────────────
export function BtnSecundario({ children, onClick, style = {} }) {
  return (
    <button
      onClick={onClick}
      style={{
        background: 'transparent',
        color: 'var(--texto-2)',
        border: '1px solid var(--borde-fuerte)',
        borderRadius: 'var(--radio)',
        padding: '11px 18px',
        fontSize: 13,
        cursor: 'pointer',
        transition: 'var(--transition)',
        ...style,
      }}
      onMouseEnter={e => { e.currentTarget.style.color = 'var(--texto-1)'; e.currentTarget.style.background = 'var(--bg-card-hover)' }}
      onMouseLeave={e => { e.currentTarget.style.color = 'var(--texto-2)'; e.currentTarget.style.background = 'transparent' }}
    >
      {children}
    </button>
  )
}

// ── Campo de texto ────────────────────────
export function Campo({ label, type = 'text', value, onChange, placeholder }) {
  const [focused, setFocused] = useState(false)
  return (
    <div style={{ display: 'flex', flexDirection: 'column', gap: 6 }}>
      {label && (
        <label style={{
          fontSize: 11, fontWeight: 600,
          textTransform: 'uppercase', letterSpacing: '0.8px',
          color: 'var(--texto-2)',
        }}>
          {label}
        </label>
      )}
      <input
        type={type}
        value={value}
        onChange={e => onChange(e.target.value)}
        placeholder={placeholder}
        onFocus={() => setFocused(true)}
        onBlur={() => setFocused(false)}
        style={{
          background: 'var(--bg-base)',
          border: `1px solid ${focused ? 'var(--acento)' : 'var(--borde-fuerte)'}`,
          borderRadius: 'var(--radio)',
          padding: '12px 16px',
          color: 'var(--texto-1)',
          fontSize: 14,
          outline: 'none',
          transition: 'var(--transition)',
          boxShadow: focused ? '0 0 0 3px var(--acento-dim)' : 'none',
        }}
      />
    </div>
  )
}

// ── Mensaje de error ──────────────────────
export function MensajeError({ texto }) {
  if (!texto) return null
  return (
    <div style={{
      background: 'var(--rojo-dim)',
      border: '1px solid var(--rojo)',
      color: 'var(--rojo)',
      padding: '10px 14px',
      borderRadius: 'var(--radio)',
      fontSize: 13,
    }}>
      {texto}
    </div>
  )
}

// ── Mensaje de éxito ──────────────────────
export function MensajeExito({ texto }) {
  if (!texto) return null
  return (
    <div style={{
      background: 'var(--acento-dim)',
      border: '1px solid var(--acento)',
      color: 'var(--acento)',
      padding: '10px 14px',
      borderRadius: 'var(--radio)',
      fontSize: 13,
    }}>
      {texto}
    </div>
  )
}

// ── Estado vacío ──────────────────────────
export function EstadoVacio({ icono, texto }) {
  return (
    <div style={{
      textAlign: 'center', padding: '60px 20px',
      color: 'var(--texto-3)',
      display: 'flex', flexDirection: 'column',
      alignItems: 'center', gap: 12,
    }}>
      <span style={{ fontSize: 48 }}>{icono}</span>
      <p style={{ fontSize: 15 }}>{texto}</p>
    </div>
  )
}

// ── Spinner de carga ──────────────────────
export function Spinner() {
  return (
    <div style={{
      display: 'flex', alignItems: 'center',
      justifyContent: 'center', padding: 40,
    }}>
      <div style={{
        width: 32, height: 32,
        border: '3px solid var(--borde-fuerte)',
        borderTop: '3px solid var(--acento)',
        borderRadius: '50%',
        animation: 'spin 0.8s linear infinite',
      }} />
      <style>{`@keyframes spin { to { transform: rotate(360deg) } }`}</style>
    </div>
  )
}

// ── Modal base ────────────────────────────
export function Modal({ visible, onClose, titulo, children }) {
  if (!visible) return null
  return (
    <div
      onClick={(e) => e.target === e.currentTarget && onClose()}
      style={{
        position: 'fixed', inset: 0,
        background: 'rgba(0,0,0,0.75)',
        backdropFilter: 'blur(8px)',
        display: 'flex', alignItems: 'center', justifyContent: 'center',
        zIndex: 200, animation: 'fadeIn 0.2s ease',
      }}
    >
      <div style={{
        background: 'var(--bg-card)',
        border: '1px solid var(--borde-fuerte)',
        borderRadius: 'var(--radio-lg)',
        padding: 36,
        width: '100%', maxWidth: 460,
        display: 'flex', flexDirection: 'column', gap: 18,
        boxShadow: 'var(--sombra)',
        animation: 'fadeUp 0.25s ease',
      }}>
        <h3 style={{
          fontFamily: 'var(--font-display)',
          fontSize: 26, letterSpacing: 1,
        }}>
          {titulo}
        </h3>
        {children}
      </div>
    </div>
  )
}

// ── Chip de género ────────────────────────
export function ChipGenero({ texto }) {
  return (
    <span style={{
      display: 'inline-block',
      fontSize: 11, fontWeight: 600,
      padding: '3px 10px',
      background: 'var(--acento-dim)',
      color: 'var(--acento)',
      borderRadius: 100,
    }}>
      {texto}
    </span>
  )
}
