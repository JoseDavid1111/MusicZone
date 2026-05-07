import { Outlet } from 'react-router-dom'
import Header from './Header'
import Sidebar from './Sidebar'

export default function AppLayout() {
  return (
    <div style={{ minHeight: '100vh' }}>
      <Header />
      <div style={{ display: 'flex', marginTop: 'var(--header-h)' }}>
        <Sidebar />
        <main style={{
          marginLeft: 'var(--sidebar-w)',
          flex: 1, padding: 32,
          minHeight: 'calc(100vh - var(--header-h))',
        }}>
          <Outlet />
        </main>
      </div>
    </div>
  )
}
