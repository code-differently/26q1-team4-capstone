const USE_MOCK = true

const MOCK_PROFILE = {
  id: 1,
  userId: 1,
  skills: 'Java, Spring Boot, React, SQL',
  targetRole: 'Data Engineer',
  experienceLevel: 'MID',
  isDiscoverable: true,
  updatedAt: '2026-04-15T10:00:00'
}

export const getSkillProfile = async (userId) => {
  if (USE_MOCK) return MOCK_PROFILE
}

export const saveSkillProfile = async (userId, data) => {
  if (USE_MOCK) return { ...MOCK_PROFILE, ...data }
}