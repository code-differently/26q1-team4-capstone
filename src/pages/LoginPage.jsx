import { useState } from 'react'
import { useNavigate, Link } from 'react-router-dom'
import { useAuth } from '../context/AuthContext'
import { login } from '../services/authService'
import styles from './LoginPage.module.css'

export default function LoginPage() {
  const [email, setEmail] = useState('')
  const [password, setPassword] = useState('')
  const [error, setError] = useState('')
  const { login: authLogin } = useAuth()
  const navigate = useNavigate()

  const handleSubmit = async (e) => {
    e.preventDefault()
    try {
      const user = await login(email, password)
      authLogin(user)
      navigate('/dashboard')
    } catch (err) {
      setError('Invalid email or password')
    }
  }


  
return (
  <div className={styles.page}>
    <div className={styles.card}>
      <div className={styles.header}>
        <h1 className={styles.title}>Welcome <span className={styles.accent}>back.</span></h1>
        <p className={styles.subtitle}>Sign in to your Nodus account</p>
      </div>
      {error && <p className={styles.error}>{error}</p>}
      <form className={styles.form} onSubmit={handleSubmit}>
        <input
          className={styles.input}
          type="email"
          placeholder="Email address"
          value={email}
          onChange={(e) => setEmail(e.target.value)}
        />
        <input
          className={styles.input}
          type="password"
          placeholder="Password"
          value={password}
          onChange={(e) => setPassword(e.target.value)}
        />
        <button className={styles.submitBtn} type="submit">
          Sign in →
        </button>
      </form>
      <p className={styles.footer}>
        Don't have an account? <Link to="/register" className={styles.footerLink}>Get started</Link>
      </p>
    </div>
  </div>
)
  
}