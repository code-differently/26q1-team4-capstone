const USE_MOCK = true

const MOCK_OFFERINGS = [
  {
    id: 1,
    userId: 2,
    programName: 'Code Differently Bootcamp',
    skillsCovered: 'Java, Spring Boot, React, SQL',
    format: 'In-Person',
    contactInfo: 'info@codedifferently.com',
    createdAt: '2026-04-01T10:00:00'
  }
]

const MOCK_EMPLOYER_POSTINGS = [
  {
    id: 1,
    userId: 3,
    roleTitle: 'Junior Backend Engineer',
    skillsNeeded: 'Java, Spring Boot, SQL',
    companyName: 'WSFS Bank',
    location: 'Wilmington, DE',
    createdAt: '2026-04-10T10:00:00'
  }
]

const MOCK_TALENT = [
  {
    id: 1,
    userId: 1,
    skills: 'Java, Spring Boot, React, SQL',
    targetRole: 'Data Engineer',
    experienceLevel: 'MID',
    isDiscoverable: true,
    updatedAt: '2026-04-15T10:00:00'
  }
]

export const getTrainingOfferings = async () => {
  if (USE_MOCK) return MOCK_OFFERINGS
}

export const saveTrainingOffering = async (data) => {
  if (USE_MOCK) return { ...MOCK_OFFERINGS[0], ...data }
}

export const getEmployerPostings = async () => {
  if (USE_MOCK) return MOCK_EMPLOYER_POSTINGS
}

export const saveEmployerPosting = async (data) => {
  if (USE_MOCK) return { ...MOCK_EMPLOYER_POSTINGS[0], ...data }
}

export const searchTalent = async (keyword) => {
  if (USE_MOCK) return MOCK_TALENT
}