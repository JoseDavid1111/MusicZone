import { useState } from 'react'
import { useNavigate } from 'react-router-dom'
import { useAuth } from '../context/AuthContext'
import { authService } from '../services/api'
import { Campo, BtnPrimario, MensajeError, MensajeExito } from '../components/ui'

export default function Login() {
  const [tab, setTab] = useState('login')
  const { login } = useAuth()
  const navigate = useNavigate()

  // Login state
  const [lUsuario, setLUsuario] = useState('')
  const [lPassword, setLPassword] = useState('')
  const [lError, setLError] = useState('')
  const [lCargando, setLCargando] = useState(false)

  // Registro state
  const [rUsuario, setRUsuario] = useState('')
  const [rCorreo, setRCorreo] = useState('')
  const [rPassword, setRPassword] = useState('')
  const [rError, setRError] = useState('')
  const [rExito, setRExito] = useState('')
  const [rCargando, setRCargando] = useState(false)

  const handleLogin = async () => {
    setLError('')
    if (!lUsuario || !lPassword) { setLError('Completa todos los campos'); return }
    setLCargando(true)
    try {
      const data = await authService.login(lUsuario, lPassword)
      if (data.exito) {
        login(data.datos)
        navigate('/app')
      } else {
        setLError(data.mensaje || 'Credenciales inválidas')
      }
    } catch {
      setLError('No se pudo conectar con el servidor')
    } finally {
      setLCargando(false)
    }
  }

  const handleRegistro = async () => {
    setRError(''); setRExito('')
    if (!rUsuario || !rCorreo || !rPassword) { setRError('Completa todos los campos'); return }
    setRCargando(true)
    try {
      const data = await authService.registrar(rUsuario, rCorreo, rPassword)
      if (data.exito !== false) {
        setRExito('¡Cuenta creada! Ahora puedes iniciar sesión.')
        setRUsuario(''); setRCorreo(''); setRPassword('')
        setTimeout(() => setTab('login'), 2000)
      } else {
        setRError(data.mensaje || 'No se pudo crear la cuenta')
      }
    } catch (e) {
      setRError(e.message || 'Error al registrarse')
    } finally {
      setRCargando(false)
    }
  }

  return (
    <div style={{
      minHeight: '100vh', display: 'flex',
      alignItems: 'center', justifyContent: 'center',
      background: 'radial-gradient(ellipse 80% 60% at 50% 0%, #12122a, var(--bg-base))',
      padding: 20,
    }}>
      {/* Glow decorativo */}
      <div style={{
        position: 'fixed', top: -150, left: '50%',
        transform: 'translateX(-50%)',
        width: 600, height: 600, borderRadius: '50%',
        background: 'radial-gradient(circle, var(--acento-glow) 0%, transparent 70%)',
        pointerEvents: 'none',
      }} />

      <div style={{
        background: 'var(--bg-card)',
        border: '1px solid var(--borde-fuerte)',
        borderRadius: 'var(--radio-lg)',
        padding: '48px 40px',
        width: '100%', maxWidth: 400,
        display: 'flex', flexDirection: 'column', gap: 24,
        boxShadow: 'var(--sombra)',
        animation: 'fadeUp 0.5s ease',
        position: 'relative', zIndex: 1,
      }}>
        {/* Logo */}
        <div style={{ textAlign: 'center' }}>
          <div style={{
            fontSize: 44, marginBottom: 8,
            filter: 'drop-shadow(0 0 16px var(--acento))',
          }}>♪</div>
          <h1 style={{
            fontFamily: 'var(--font-display)',
            fontSize: 38, letterSpacing: 4,
            color: 'var(--texto-1)',
          }}>
            MusicZone
          </h1>
        </div>

        {/* Tabs */}
        <div style={{
          display: 'flex', gap: 4,
          background: 'var(--bg-base)',
          borderRadius: 'var(--radio)',
          padding: 4,
        }}>
          {['login', 'registro'].map(t => (
            <button
              key={t}
              onClick={() => setTab(t)}
              style={{
                flex: 1, padding: '10px',
                border: 'none', borderRadius: 8,
                background: tab === t ? 'var(--acento)' : 'transparent',
                color: tab === t ? '#000' : 'var(--texto-2)',
                fontWeight: tab === t ? 700 : 500,
                fontSize: 13, cursor: 'pointer',
                transition: 'var(--transition)',
              }}
            >
              {t === 'login' ? 'Iniciar sesión' : 'Crear cuenta'}
            </button>
          ))}
        </div>

        {/* Login */}
        {tab === 'login' && (
          <div style={{ display: 'flex', flexDirection: 'column', gap: 16 }}>
            <Campo label="Usuario" value={lUsuario} onChange={setLUsuario} placeholder="Tu nombre de usuario" />
            <Campo label="Contraseña" type="password" value={lPassword} onChange={setLPassword} placeholder="••••••••" />
            <MensajeError texto={lError} />
            <BtnPrimario onClick={handleLogin} disabled={lCargando}>
              {lCargando ? 'Entrando...' : 'Entrar'}
            </BtnPrimario>
          </div>
        )}

        {/* Registro */}
        {tab === 'registro' && (
          <div style={{ display: 'flex', flexDirection: 'column', gap: 16 }}>
            <Campo label="Usuario" value={rUsuario} onChange={setRUsuario} placeholder="Elige un nombre de usuario" />
            <Campo label="Correo" type="email" value={rCorreo} onChange={setRCorreo} placeholder="tu@correo.com" />
            <Campo label="Contraseña" type="password" value={rPassword} onChange={setRPassword} placeholder="Mínimo 6 caracteres" />
            <MensajeError texto={rError} />
            <MensajeExito texto={rExito} />
            <BtnPrimario onClick={handleRegistro} disabled={rCargando}>
              {rCargando ? 'Creando cuenta...' : 'Crear cuenta'}
            </BtnPrimario>
          </div>
        )}
      </div>
    </div>
  )
}
