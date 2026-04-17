import { useState } from 'react'
import { useAuth } from '../context/AuthContext'
import { saveSkillProfile } from '../services/profileService'
import styles from './ProfilePage.module.css'

export default function ProfilePage() {
  const { user, skillProfile, updateSkillProfile } = useAuth()
  const [skills, setSkills] = useState(skillProfile?.skills || '')
  const [targetRole, setTargetRole] = useState(skillProfile?.targetRole || '')
  const [experienceLevel, setExperienceLevel] = useState(skillProfile?.experienceLevel || 'ENTRY')
  const [isDiscoverable, setIsDiscoverable] = useState(skillProfile?.isDiscoverable || false)
  const [saved, setSaved] = useState(false)

  const handleSave = async () => {
    const updated = await saveSkillProfile(user.id, { skills, targetRole, experienceLevel, isDiscoverable })
    updateSkillProfile(updated)
    setSaved(true)
    setTimeout(() => setSaved(false), 2000)
  }

  return (
    <div className={styles.page}>
      <div className={styles.header}>
        <h1 className={styles.title}>Your <span style={{ color: '#00d2ff' }}>Profile</span></h1>
        <p className={styles.subtitle}>Keep your skills current for better matches</p>
      </div>

      <div className={styles.card}>
       <div className={styles.field}>
  <label className={styles.label}>Skills confirmed</label>
  <input
    className={styles.input}
    placeholder="Type a skill and press Enter or Space"
    onKeyDown={(e) => {
      if (e.key === 'Enter') {
        e.preventDefault()
        const val = e.target.value.trim()
        if (val && !skills.split(',').map(s => s.trim()).includes(val)) {
          setSkills(prev => prev ? `${prev}, ${val}` : val)
        }
        e.target.value = ''
      }
    }}
  />
  {skills && (
    <div style={{ display: 'flex', flexWrap: 'wrap', gap: '6px', marginTop: '8px' }}>
      {skills.split(',').map((skill, i) => skill.trim() && (
        <span
          key={i}
          onClick={() => setSkills(skills.split(',').filter((_, idx) => idx !== i).join(','))}
          style={{
            padding: '4px 10px',
            background: 'rgba(0,210,255,0.08)',
            border: '1px solid rgba(0,210,255,0.25)',
            borderRadius: '20px',
            color: '#00d2ff',
            fontSize: '11px',
            fontFamily: 'Space Grotesk, sans-serif',
            fontWeight: 500,
            letterSpacing: '0.04em',
            cursor: 'pointer',
          }}
        >{skill.trim()} ✕</span>
      ))}
    </div>
  )}
</div>
        <div className={styles.field}>
          <label className={styles.label}>Target role</label>
          <input
            className={styles.input}
            placeholder="e.g. Data Engineer, Backend Developer"
            value={targetRole}
            onChange={(e) => setTargetRole(e.target.value)}
          />
        </div>

        <div className={styles.field}>
          <label className={styles.label}>Experience level</label>
          <select
            className={styles.select}
            value={experienceLevel}
            onChange={(e) => setExperienceLevel(e.target.value)}
          >
            <option value="ENTRY">Entry level</option>
            <option value="MID">Mid level</option>
            <option value="SENIOR">Senior level</option>
          </select>
        </div>

        <div className={styles.toggleRow}>
          <div>
            <p className={styles.toggleLabel}>Discoverable to employers</p>
            <p className={styles.toggleDesc}>Allow employers to find your profile in talent search</p>
          </div>
          <input
            type="checkbox"
            checked={isDiscoverable}
            onChange={(e) => setIsDiscoverable(e.target.checked)}
            style={{ width: '18px', height: '18px', accentColor: '#00d2ff', cursor: 'pointer' }}
          />
        </div>

        <button className={styles.saveBtn} onClick={handleSave}>
          Save Profile →
        </button>

        {saved && <p className={styles.savedMsg}>✓ Profile saved</p>}
      </div>
    </div>
  )
}