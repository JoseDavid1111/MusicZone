import { createContext, useContext, useState, useEffect } from 'react'

const AuthContext = createContext(null)

export function AuthProvider({ children }) {
  const [usuario, setUsuario] = useState(null)
  const [cargando, setCargando] = useState(true)

  useEffect(() => {
    const guardado = localStorage.getItem('mz_usuario')
    if (guardado) {
      try { setUsuario(JSON.parse(guardado)) }
      catch { localStorage.removeItem('mz_usuario') }
    }
    setCargando(false)
  }, [])

  const login = (datos) => {
    setUsuario(datos)
    localStorage.setItem('mz_usuario', JSON.stringify(datos))
  }

  const logout = () => {
    setUsuario(null)
    localStorage.removeItem('mz_usuario')
  }

  return (
    <AuthContext.Provider value={{ usuario, login, logout, cargando }}>
      {children}
    </AuthContext.Provider>
  )
}

export const useAuth = () => useContext(AuthContext)
