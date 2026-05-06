import { createContext, useContext, useState, useCallback } from 'react'

const ToastContext = createContext(null)

export function ToastProvider({ children }) {
  const [toasts, setToasts] = useState([])

  const mostrar = useCallback((mensaje, tipo = 'info') => {
    const id = Date.now()
    setToasts(prev => [...prev, { id, mensaje, tipo }])
    setTimeout(() => setToasts(prev => prev.filter(t => t.id !== id)), 3200)
  }, [])

  const exito = useCallback((m) => mostrar(m, 'exito'), [mostrar])
  const error  = useCallback((m) => mostrar(m, 'error'),  [mostrar])

  return (
    <ToastContext.Provider value={{ exito, error }}>
      {children}
      <div style={{
        position: 'fixed', bottom: 28, left: '50%',
        transform: 'translateX(-50%)',
        display: 'flex', flexDirection: 'column', gap: 8,
        zIndex: 999, pointerEvents: 'none',
      }}>
        {toasts.map(t => (
          <div key={t.id} style={{
            background: 'var(--bg-card)',
            border: `1px solid ${t.tipo === 'exito' ? 'var(--acento)' : t.tipo === 'error' ? 'var(--rojo)' : 'var(--borde-fuerte)'}`,
            color: t.tipo === 'exito' ? 'var(--acento)' : t.tipo === 'error' ? 'var(--rojo)' : 'var(--texto-1)',
            padding: '12px 24px',
            borderRadius: 100,
            fontSize: 13,
            fontWeight: 500,
            whiteSpace: 'nowrap',
            animation: 'fadeUp 0.3s ease',
            boxShadow: 'var(--sombra)',
          }}>
            {t.mensaje}
          </div>
        ))}
      </div>
    </ToastContext.Provider>
  )
}

export const useToast = () => useContext(ToastContext)
