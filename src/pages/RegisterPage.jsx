import { useState } from 'react'
import { useNavigate, Link } from 'react-router-dom'
import { useAuth } from '../context/AuthContext'
import { register } from '../services/authService'
import styles from './RegisterPage.module.css'
export default function RegisterPage() {
  const [name, setName] = useState('')
  const [email, setEmail] = useState('')
  const [password, setPassword] = useState('')
  const [role, setRole] = useState('JOB_SEEKER')
  const [error, setError] = useState('')
  const { login: authLogin } = useAuth()
  const navigate = useNavigate()

  const handleSubmit = async (e) => {
    e.preventDefault()
    try {
      const user = await register(name, email, password, role)
      authLogin(user)
      navigate('/dashboard')
    } catch (err) {
      setError('Registration failed. Please try again.')
    }
  }

  return (
  <div className={styles.page}>
    <div className={styles.card}>
      <div className={styles.header}>
        <h1 className={styles.title}>Join <span className={styles.accent}>Nodus.</span></h1>
        <p className={styles.subtitle}>Your workforce intelligence platform</p>
      </div>
      {error && <p className={styles.error}>{error}</p>}
      <form className={styles.form} onSubmit={handleSubmit}>
        <input
          className={styles.input}
          type="text"
          placeholder="Full name"
          value={name}
          onChange={(e) => setName(e.target.value)}
        />
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
        <div>
          <span className={styles.roleLabel}>I am a</span>
          <div className={styles.roleGrid}>
            {[
              { value: 'JOB_SEEKER', label: 'Job Seeker' },
              { value: 'TRAINING_PROVIDER', label: 'Training Provider' },
              { value: 'EMPLOYER', label: 'Employer' },
            ].map(r => (
              <div
                key={r.value}
                className={`${styles.roleCard} ${role === r.value ? styles.roleCardActive : ''}`}
                onClick={() => setRole(r.value)}
              >
                {r.label}
              </div>
            ))}
          </div>
        </div>
        <button className={styles.submitBtn} type="submit">
          Create account →
        </button>
      </form>
      <p className={styles.footer}>
        Already have an account? <Link to="/login" className={styles.footerLink}>Sign in</Link>
      </p>
    </div>
  </div>
)
}