const USE_MOCK = true

const MOCK_RECOMMENDATION = {
  id: 1,
  userId: 1,
  searchQuery: 'Data Engineer Delaware',
  aiResponse: 'VERDICT: Developing Match\nYou have strong SQL and Java foundations that appear in these listings. Missing: Python and AWS cloud experience. WHAT TO BUILD: Complete a Python for Data Engineering course (2-3 months) and pursue AWS Cloud Practitioner certification. BOTTOM LINE: Focus on Python first — it appears in 100% of these listings.',
  jobPostings: [],
  createdAt: '2026-04-15T10:00:00'
}

export const getRecommendation = async (userId, profileId, jobIds) => {
  if (USE_MOCK) return MOCK_RECOMMENDATION
}

export const getHistory = async (userId) => {
  if (USE_MOCK) return [MOCK_RECOMMENDATION]
}