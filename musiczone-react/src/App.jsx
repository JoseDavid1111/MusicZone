import { BrowserRouter, Routes, Route, Navigate } from 'react-router-dom'
import { AuthProvider, useAuth } from './context/AuthContext'
import { ToastProvider } from './context/ToastContext'

import Login          from './pages/Login'
import Inicio         from './pages/Inicio'
import Canciones      from './pages/Canciones'
import Artistas       from './pages/Artistas'
import Playlists      from './pages/Playlists'
import PlaylistDetalle from './pages/PlaylistDetalle'
import AppLayout      from './components/layout/AppLayout'

// Ruta protegida — redirige al login si no hay sesión
function RutaProtegida({ children }) {
  const { usuario, cargando } = useAuth()
  if (cargando) return null
  return usuario ? children : <Navigate to="/login" replace />
}

// Redirige al app si ya hay sesión
function RutaPublica({ children }) {
  const { usuario, cargando } = useAuth()
  if (cargando) return null
  return usuario ? <Navigate to="/app" replace /> : children
}

export default function App() {
  return (
    <AuthProvider>
      <ToastProvider>
        <BrowserRouter>
          <Routes>
            {/* Ruta raíz */}
            <Route path="/" element={<Navigate to="/app" replace />} />

            {/* Login */}
            <Route path="/login" element={
              <RutaPublica><Login /></RutaPublica>
            } />

            {/* App protegida */}
            <Route element={
              <RutaProtegida><AppLayout /></RutaProtegida>
            }>
              <Route path="/app"            element={<Inicio />} />
              <Route path="/canciones"      element={<Canciones />} />
              <Route path="/artistas"       element={<Artistas />} />
              <Route path="/playlists"      element={<Playlists />} />
              <Route path="/playlists/:id"  element={<PlaylistDetalle />} />
            </Route>

            {/* Fallback */}
            <Route path="*" element={<Navigate to="/app" replace />} />
          </Routes>
        </BrowserRouter>
      </ToastProvider>
    </AuthProvider>
  )
}
