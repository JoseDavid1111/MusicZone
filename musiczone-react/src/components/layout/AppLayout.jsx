import { useState } from 'react'
import { Outlet } from 'react-router-dom'
import Header from './Header'
import Sidebar from './Sidebar'
import ReproductorAudio from '../ReproductorAudio'

export default function AppLayout() {
  const [cancionActiva, setCancionActiva] = useState(null)
  const [cola, setCola] = useState([])
  const [indiceCola, setIndiceCola] = useState(0)

  const reproducirCancion = (cancion) => {
    if (!cancion?.urlAudio) return
    setCola([cancion])
    setIndiceCola(0)
    setCancionActiva(cancion)
  }

  const reproducirLista = (canciones, indiceInicial = 0) => {
    const reproducibles = (canciones || []).filter(c => c?.urlAudio)
    if (reproducibles.length === 0) return

    const indiceSeguro = Math.min(Math.max(indiceInicial, 0), reproducibles.length - 1)
    setCola(reproducibles)
    setIndiceCola(indiceSeguro)
    setCancionActiva(reproducibles[indiceSeguro])
  }

  const cerrarReproductor = () => {
    setCancionActiva(null)
    setCola([])
    setIndiceCola(0)
  }

  const reproducirSiguiente = () => {
    if (cola.length === 0 || indiceCola >= cola.length - 1) return
    const siguienteIndice = indiceCola + 1
    setIndiceCola(siguienteIndice)
    setCancionActiva(cola[siguienteIndice])
  }

  return (
    <div style={{ minHeight: '100vh' }}>
      <Header />
      <div style={{ display: 'flex', marginTop: 'var(--header-h)' }}>
        <Sidebar />
        <main style={{
          marginLeft: 'var(--sidebar-w)',
          flex: 1,
          padding: '32px 32px 112px',
          minHeight: 'calc(100vh - var(--header-h))',
        }}>
          <Outlet context={{ cancionActiva, reproducirCancion, reproducirLista }} />
        </main>
      </div>
      <ReproductorAudio
        cancion={cancionActiva}
        onClose={cerrarReproductor}
        onEnded={reproducirSiguiente}
        indice={indiceCola}
        total={cola.length}
      />
    </div>
  )
}
