import { Routes, Route, useLocation } from 'react-router-dom'
import Navbar from './components/Navbar'
import ProtectedRoute from './router/ProtectedRoute'
import LoginPage from './pages/LoginPage'
import RegisterPage from './pages/RegisterPage'
import DashboardPage from './pages/DashboardPage'
import SearchPage from './pages/SearchPage'
import ProfilePage from './pages/ProfilePage'
import DirectoryPage from './pages/DirectoryPage'
import HistoryPage from './pages/HistoryPage'
import LandingPage from './pages/LandingPage'


const Placeholder = ({ name }) => (
  <div style={{ color: 'white', padding: '2rem' }}>{name} page</div>
)

function App() {
 const location = useLocation()
  return (
    <div>
      {location.pathname !== '/' && <Navbar />}
      <Routes>
        <Route path="/" element={<LandingPage />} />
        <Route path="/login" element={<LoginPage />} />
<Route path="/register" element={<RegisterPage />} />
        
        <Route element={<ProtectedRoute />}>
          <Route path="/dashboard" element={<DashboardPage />} />
          <Route path="/search" element={<SearchPage/>} />
          <Route path="/profile" element={<ProfilePage />} />
          <Route path="/directory" element={<DirectoryPage />} />
          <Route path="/history" element={<HistoryPage/>} />
        </Route>
      </Routes>
    </div>
  )
}

export default App