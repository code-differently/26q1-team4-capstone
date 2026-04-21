const USE_MOCK = false

const BASE = import.meta.env.VITE_API_BASE_URL || 'http://localhost:8080'

const MOCK_PROFILE = {
  id: 1,
  userId: 1,
  skills: 'Java, Spring Boot, React, SQL',
  targetRole: 'Data Engineer',
  experienceLevel: 'MID',
  isDiscoverable: true,
  updatedAt: '2026-04-15T10:00:00',
}

export const getSkillProfile = async (userId) => {
  if (USE_MOCK) return MOCK_PROFILE
  try {
    const res = await fetch(`${BASE}/api/profiles/${userId}`)
    if (res.status === 404) return null
    if (!res.ok) throw new Error(`Profile fetch failed: ${res.status}`)
    return res.json()
  } catch (err) {
    console.error('getSkillProfile failed:', err)
    return null
  }
}

export const saveSkillProfile = async (userId, data) => {
  if (USE_MOCK) return { ...MOCK_PROFILE, ...data }
  try {
    const res = await fetch(`${BASE}/api/profiles/${userId}`, {
      method: 'PUT',
      headers: { 'Content-Type': 'application/json' },
      body: JSON.stringify(data),
    })
    if (!res.ok) throw new Error(`Profile save failed: ${res.status}`)
    return res.json()
  } catch (err) {
    console.error('saveSkillProfile failed:', err)
    throw err
  }
}
