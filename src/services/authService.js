const USE_MOCK = false

const BASE = import.meta.env.VITE_API_BASE_URL || 'http://localhost:8080'

// ---- helpers ----

async function post(path, body) {
  const res = await fetch(`${BASE}${path}`, {
    method: 'POST',
    headers: { 'Content-Type': 'application/json' },
    body: JSON.stringify(body),
  })
  if (!res.ok) {
    const err = await res.json().catch(() => ({}))
    throw new Error(err.error || `Request failed: ${res.status}`)
  }
  return res.json()
}

// ---- service ----

export const login = async (email, password) => {
  if (USE_MOCK) {
    return { id: 1, name: 'Bobby Money', email, role: 'JOB_SEEKER' }
  }
  try {
    return await post('/api/auth/login', { email, password })
  } catch (err) {
    console.error('login failed:', err)
    throw err
  }
}

export const register = async (name, email, password, role) => {
  if (USE_MOCK) {
    return { id: 1, name, email, role }
  }
  try {
    return await post('/api/auth/register', { name, email, password, role })
  } catch (err) {
    console.error('register failed:', err)
    throw err
  }
}

export const logout = () => {
  localStorage.removeItem('nodus_user')
}
