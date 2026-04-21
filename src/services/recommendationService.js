const USE_MOCK = false

const BASE = import.meta.env.VITE_API_BASE_URL || 'http://localhost:8080'

const MOCK_RECOMMENDATION = {
  id: 1,
  userId: 1,
  searchQuery: 'Data Engineer Delaware',
  aiResponse:
    'VERDICT: Developing Match\nYou have strong SQL and Java foundations that appear in these listings. Missing: Python and AWS cloud experience.\n\nWHAT YOU BRING\n- Java — appears in all 2 listings as a core requirement\n- SQL — essential for data pipeline work at WSFS\n\nWHAT TO BUILD\n- Python — flagged in every listing, critical for data engineering\n- AWS — cloud infrastructure required by WSFS Bank\n\nBOTTOM LINE: Focus on Python first — it appears in 100% of these listings.',
  createdAt: '2026-04-15T10:00:00',
}

export const getRecommendation = async (userId, skillProfileId, jobPostingIds) => {
  if (USE_MOCK) return MOCK_RECOMMENDATION
  try {
    const res = await fetch(`${BASE}/api/recommendations`, {
      method: 'POST',
      headers: { 'Content-Type': 'application/json' },
      body: JSON.stringify({ userId, skillProfileId, jobPostingIds }),
    })
    if (!res.ok) {
      const err = await res.json().catch(() => ({}))
      throw new Error(err.error || `Recommendation failed: ${res.status}`)
    }
    return res.json()
  } catch (err) {
    console.error('getRecommendation failed:', err)
    throw err
  }
}

export const getHistory = async (userId) => {
  if (USE_MOCK) return [MOCK_RECOMMENDATION]
  try {
    const res = await fetch(`${BASE}/api/recommendations/history/${userId}`)
    if (!res.ok) throw new Error(`History fetch failed: ${res.status}`)
    return res.json()
  } catch (err) {
    console.error('getHistory failed:', err)
    return []
  }
}
