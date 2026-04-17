import { useAuth } from '../context/AuthContext'
import { Link, useNavigate } from 'react-router-dom'

export default function Navbar() {
    const { user, isAuthenticated, logout } = useAuth()
    const navigate = useNavigate()

    const handleLogout = () => {
        logout()
        navigate('/login')
    }
    return (
        <nav>
            <Link to="/">Nodus</Link>

            {isAuthenticated ? (
                <div>
                    {/* logged in links */}
                    <Link to="/search">Search</Link>
                    <Link to="/directory">Directory</Link>
                    <Link to="/profile">Profile</Link>
                    <span>{user.name}</span>
                    <button onClick={handleLogout}>Logout</button>
                </div>
            ) : (
                <div>
                    {/* logged out links */}
                    <Link to="/login">Login</Link>
                    <Link to="/register">Register</Link>
                </div>
            )}
        </nav>
    )
}

