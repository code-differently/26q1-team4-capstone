import { useAuth } from '../context/AuthContext'
import { Link, useNavigate } from 'react-router-dom'
import styles from './Navbar.module.css'

export default function Navbar() {
  const { user, isAuthenticated, logout } = useAuth()
  const navigate = useNavigate()

  const handleLogout = () => {
    logout()
    navigate('/login')
  }

  return (
    <nav className={styles.nav}>
      <Link to="/" className={styles.logo}>NODUS</Link>

      {isAuthenticated ? (
        <div className={styles.links}>
          <Link to="/search" className={styles.link}>Search</Link>
          <Link to="/directory" className={styles.link}>Directory</Link>
          <Link to="/history" className={styles.link}>History</Link>
          {user.role === 'JOB_SEEKER' && (
  <Link to="/profile" className={styles.link}>Profile</Link>
)}
          <span className={styles.userBadge}>{user.name}</span>
          <button className={styles.logoutBtn} onClick={handleLogout}>Logout</button>
        </div>
      ) : (
        <div className={styles.authLinks}>
          <Link to="/login" className={styles.link}>Log in</Link>
          <Link to="/register" className={styles.registerBtn}>Get Started</Link>
        </div>
      )}
    </nav>
  )
}