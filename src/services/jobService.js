const USE_MOCK = true

const MOCK_JOBS = [
  {
    id: 1,
    title: 'Data Engineer',
    company: 'WSFS Bank',
    location: 'Wilmington, DE',
    description: 'We are looking for a Data Engineer...',
    requiredSkills: 'Java, SQL, Python, AWS',
    salaryRange: '$80k-$100k',
    fetchedAt: '2026-04-15T10:00:00'
  },
  {
    id: 2,
    title: 'Backend Engineer',
    company: 'JPMorgan',
    location: 'Philadelphia, PA',
    description: 'Join our backend engineering team...',
    requiredSkills: 'Java, Spring Boot, SQL, REST APIs',
    salaryRange: '$90k-$120k',
    fetchedAt: '2026-04-15T09:00:00'
  }
]

export const searchJobs = async (query,location) => {
  if (USE_MOCK) return MOCK_JOBS
  // real fetch goes here later
}

export const getJobById = async (id) => {
  if (USE_MOCK) return MOCK_JOBS.find(job => job.id === id)
  // real fetch goes here later
}
