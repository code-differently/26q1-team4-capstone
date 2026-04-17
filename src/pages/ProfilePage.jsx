import { useState } from 'react'
import { useAuth } from '../context/AuthContext'
import { saveSkillProfile } from '../services/profileService'

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
    <div style={{ color: 'white', padding: '2rem' }}>
      <h2>My Profile</h2>
      <div>
        <label>Skills (comma separated)</label>
        <input value={skills} onChange={(e) => setSkills(e.target.value)} style={{ display: 'block', width: '100%' }} />
      </div>
      <div>
        <label>Target Role</label>
        <input value={targetRole} onChange={(e) => setTargetRole(e.target.value)} style={{ display: 'block', width: '100%' }} />
      </div>
      <div>
        <label>Experience Level</label>
        <select value={experienceLevel} onChange={(e) => setExperienceLevel(e.target.value)}>
          <option value="ENTRY">Entry</option>
          <option value="MID">Mid</option>
          <option value="SENIOR">Senior</option>
        </select>
      </div>
      <div>
        <label>
          <input type="checkbox" checked={isDiscoverable} onChange={(e) => setIsDiscoverable(e.target.checked)} />
          Make my profile discoverable to employers
        </label>
      </div>
      <button onClick={handleSave}>Save Profile</button>
      {saved && <p style={{ color: '#00d2ff' }}>Profile saved!</p>}
    </div>
  )
}