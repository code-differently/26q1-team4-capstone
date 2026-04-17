const USE_MOCK = true

const MOCK_USER = {
  id: 1,
  name: 'Bobby Money',
  email: 'bobby@test.com',
  role: 'JOB_SEEKER',
  createdAt: '2026-04-15T10:00:00'
}

export const login = async (email, password) => {
  if (USE_MOCK) return MOCK_USER
  // real fetch goes here later
}

export const register = async (name,email, password,role) => {
  if (USE_MOCK) return MOCK_USER
  // real fetch goes here later
}

export const logout = async () => {
  localStorage.removeItem('nodus_user')
  // real fetch goes here later
}