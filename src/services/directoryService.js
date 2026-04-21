const USE_MOCK = false

const BASE = import.meta.env.VITE_API_BASE_URL || 'http://localhost:8080'

// ---- helpers ----

async function get(path) {
  const res = await fetch(`${BASE}${path}`)
  if (!res.ok) throw new Error(`GET ${path} failed: ${res.status}`)
  return res.json()
}

async function post(path, body) {
  const res = await fetch(`${BASE}${path}`, {
    method: 'POST',
    headers: { 'Content-Type': 'application/json' },
    body: JSON.stringify(body),
  })
  if (!res.ok) throw new Error(`POST ${path} failed: ${res.status}`)
  return res.json()
}

// ---- mock data ----

const MOCK_OFFERINGS = [
  {
    id: 1,
    userId: 2,
    programName: 'Code Differently Bootcamp',
    skillsCovered: 'Java, Spring Boot, React, SQL',
    format: 'In-Person',
    contactInfo: 'info@codedifferently.com',
    createdAt: '2026-04-01T10:00:00',
  },
]

const MOCK_EMPLOYER_POSTINGS = [
  {
    id: 1,
    userId: 3,
    roleTitle: 'Junior Backend Engineer',
    skillsNeeded: 'Java, Spring Boot, SQL',
    companyName: 'WSFS Bank',
    location: 'Wilmington, DE',
    createdAt: '2026-04-10T10:00:00',
  },
]

const MOCK_TALENT = [
  {
    id: 1,
    userId: 1,
    skills: 'Java, Spring Boot, React, SQL',
    targetRole: 'Data Engineer',
    experienceLevel: 'MID',
    isDiscoverable: true,
    updatedAt: '2026-04-15T10:00:00',
  },
]

// ---- service ----

export const getTrainingOfferings = async () => {
  if (USE_MOCK) return MOCK_OFFERINGS
  try {
    return await get('/api/directory/training')
  } catch (err) {
    console.error('getTrainingOfferings failed:', err)
    return []
  }
}

export const saveTrainingOffering = async (data) => {
  if (USE_MOCK) return { ...MOCK_OFFERINGS[0], ...data }
  try {
    return await post('/api/directory/training', data)
  } catch (err) {
    console.error('saveTrainingOffering failed:', err)
    throw err
  }
}

export const getEmployerPostings = async () => {
  if (USE_MOCK) return MOCK_EMPLOYER_POSTINGS
  try {
    return await get('/api/directory/employers')
  } catch (err) {
    console.error('getEmployerPostings failed:', err)
    return []
  }
}

export const saveEmployerPosting = async (data) => {
  if (USE_MOCK) return { ...MOCK_EMPLOYER_POSTINGS[0], ...data }
  try {
    return await post('/api/directory/employers', data)
  } catch (err) {
    console.error('saveEmployerPosting failed:', err)
    throw err
  }
}

export const searchTalent = async (keyword) => {
  if (USE_MOCK) return MOCK_TALENT
  try {
    const params = keyword ? `?skill=${encodeURIComponent(keyword)}` : ''
    return await get(`/api/directory/talent${params}`)
  } catch (err) {
    console.error('searchTalent failed:', err)
    return []
  }
}
