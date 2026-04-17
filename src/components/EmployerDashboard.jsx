import { useAuth } from '../context/AuthContext'
import { Link } from 'react-router-dom'

export default function EmployerDashboard() {
  const { user } = useAuth()

  return (
    <div style={{ padding: '2rem', maxWidth: '900px', margin: '0 auto' }}>
      <div style={{ marginBottom: '2rem' }}>
        <h1 style={{ fontFamily: 'Syne, sans-serif', fontSize: '28px', fontWeight: 800, color: '#fff', margin: '0 0 6px' }}>
          Welcome, <span style={{ color: '#00d2ff' }}>{user.name}.</span>
        </h1>
        <p style={{ fontFamily: 'Space Grotesk, sans-serif', fontSize: '14px', color: 'rgba(255,255,255,0.4)', margin: 0, letterSpacing: '0.06em' }}>
          EMPLOYER INTELLIGENCE DASHBOARD
        </p>
      </div>

      <div style={{ display: 'grid', gridTemplateColumns: 'repeat(3, 1fr)', gap: '1rem', marginBottom: '2rem' }}>
        {[
          { label: 'Active Postings', value: '4', color: '#00d2ff' },
          { label: 'Talent Matches', value: '23', color: '#00ffcc' },
          { label: 'Applications', value: '11', color: '#c472f0' },
        ].map(stat => (
          <div key={stat.label} style={{
            background: 'rgba(0,210,255,0.03)',
            border: '1px solid rgba(0,210,255,0.1)',
            borderRadius: '12px',
            padding: '1.25rem',
          }}>
            <p style={{ fontFamily: 'Space Grotesk, sans-serif', fontSize: '11px', color: 'rgba(255,255,255,0.4)', letterSpacing: '0.1em', textTransform: 'uppercase', margin: '0 0 8px' }}>{stat.label}</p>
            <p style={{ fontFamily: 'Syne, sans-serif', fontSize: '32px', fontWeight: 800, color: stat.color, margin: 0 }}>{stat.value}</p>
          </div>
        ))}
      </div>

      <div style={{ display: 'grid', gridTemplateColumns: '1fr 1fr', gap: '1rem' }}>
        <Link to="/directory" style={{ textDecoration: 'none' }}>
          <div style={{ background: 'rgba(0,210,255,0.04)', border: '1px solid rgba(0,210,255,0.15)', borderRadius: '12px', padding: '1.5rem' }}>
            <p style={{ fontFamily: 'Syne, sans-serif', fontSize: '16px', fontWeight: 700, color: '#00d2ff', margin: '0 0 8px' }}>Find Talent →</p>
            <p style={{ fontFamily: 'Space Grotesk, sans-serif', fontSize: '13px', color: 'rgba(255,255,255,0.4)', margin: 0 }}>Search discoverable job seeker profiles by skill</p>
          </div>
        </Link>
        <Link to="/directory" style={{ textDecoration: 'none' }}>
          <div style={{ background: 'rgba(0,255,204,0.03)', border: '1px solid rgba(0,255,204,0.12)', borderRadius: '12px', padding: '1.5rem' }}>
            <p style={{ fontFamily: 'Syne, sans-serif', fontSize: '16px', fontWeight: 700, color: '#00ffcc', margin: '0 0 8px' }}>Post a Role →</p>
            <p style={{ fontFamily: 'Space Grotesk, sans-serif', fontSize: '13px', color: 'rgba(255,255,255,0.4)', margin: 0 }}>Add your open positions to the Nodus network</p>
          </div>
        </Link>
      </div>
    </div>
  )
}