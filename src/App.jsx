import { Routes, Route } from 'react-router-dom'
import Navbar from './components/Navbar'
import ProtectedRoute from './router/ProtectedRoute'
import LoginPage from './pages/LoginPage'
import RegisterPage from './pages/RegisterPage'
import DashboardPage from './pages/DashboardPage'

const Placeholder = ({ name }) => (
  <div style={{ color: 'white', padding: '2rem' }}>{name} page</div>
)

function App() {
  return (
    <div>
      <Navbar />
      <Routes>
        <Route path="/" element={<Placeholder name="Landing" />} />
        <Route path="/login" element={<LoginPage />} />
<Route path="/register" element={<RegisterPage />} />
        
        <Route element={<ProtectedRoute />}>
          <Route path="/dashboard" element={<DashboardPage />} />
          <Route path="/search" element={<Placeholder name="Search" />} />
          <Route path="/profile" element={<Placeholder name="Profile" />} />
          <Route path="/directory" element={<Placeholder name="Directory" />} />
          <Route path="/history" element={<Placeholder name="History" />} />
        </Route>
      </Routes>
    </div>
  )
}

export default App