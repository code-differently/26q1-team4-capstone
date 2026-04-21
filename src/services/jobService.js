const USE_MOCK = false

const BASE = import.meta.env.VITE_API_BASE_URL || 'http://localhost:8080'

const MOCK_JOBS = [
  {
    id: 1,
    title: 'Data Engineer',
    company: 'WSFS Bank',
    location: 'Wilmington, DE',
    description: 'We are looking for a Data Engineer...',
    requiredSkills: 'Java, SQL, Python, AWS',
    salaryRange: '$80,000–$100,000',
    fetchedAt: '2026-04-15T10:00:00',
  },
  {
    id: 2,
    title: 'Backend Engineer',
    company: 'JPMorgan',
    location: 'Philadelphia, PA',
    description: 'Join our backend engineering team...',
    requiredSkills: 'Java, Spring Boot, SQL, REST API',
    salaryRange: '$90,000–$120,000',
    fetchedAt: '2026-04-15T09:00:00',
  },
]

export const searchJobs = async (query, location) => {
  if (USE_MOCK) return MOCK_JOBS
  try {
    const params = new URLSearchParams({ query: query || '', location: location || '' })
    const res = await fetch(`${BASE}/api/jobs/search?${params}`)
    if (!res.ok) throw new Error(`Search failed: ${res.status}`)
    return res.json()
  } catch (err) {
    console.error('searchJobs failed:', err)
    return []
  }
}

export const getJobById = async (id) => {
  if (USE_MOCK) return MOCK_JOBS.find((j) => j.id === id) || null
  try {
    const res = await fetch(`${BASE}/api/jobs/${id}`)
    if (!res.ok) return null
    return res.json()
  } catch (err) {
    console.error('getJobById failed:', err)
    return null
  }
}
