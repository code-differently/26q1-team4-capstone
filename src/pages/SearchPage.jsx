import { useState } from 'react'
import { useAuth } from '../context/AuthContext'
import { searchJobs } from '../services/jobService'
import { getRecommendation } from '../services/recommendationService'
import styles from './SearchPage.module.css'
export default function SearchPage() {
  const { user, skillProfile } = useAuth()
  const [query, setQuery] = useState('')
  const [location, setLocation] = useState('')
  const [results, setResults] = useState([])
  const [selectedIds, setSelectedIds] = useState([])
  const [recommendation, setRecommendation] = useState(null)
  const [isLoading, setIsLoading] = useState(false)

  const handleSearch = async () => {
    setIsLoading(true)
    const jobs = await searchJobs(query, location)
    setResults(jobs)
    setIsLoading(false)
  }

  const handleToggleSelect = (id) => {
    setSelectedIds(prev =>
      prev.includes(id) ? prev.filter(i => i !== id) : [...prev, id]
    )
  }

  const handleGetRecommendation = async () => {
    setIsLoading(true)
    const rec = await getRecommendation(user.id, skillProfile?.id, selectedIds)
    setRecommendation(rec)
    setIsLoading(false)
  }

  return (
  <div className={styles.page}>
    <div className={styles.header}>
      <h1 className={styles.title}>Intelligence <span style={{ color: '#00d2ff' }}>Search</span></h1>
      <p className={styles.subtitle}>Find roles — get AI-powered gap analysis</p>
    </div>

    <div className={styles.searchBar}>
      <input
        className={styles.input}
        placeholder="Job title, skill, or keyword"
        value={query}
        onChange={(e) => setQuery(e.target.value)}
      />
      <input
        className={styles.input}
        placeholder="Location"
        value={location}
        onChange={(e) => setLocation(e.target.value)}
        style={{ maxWidth: '200px' }}
      />
      <button className={styles.searchBtn} onClick={handleSearch}>
        Search →
      </button>
    </div>

    {isLoading && <p className={styles.loading}>● Scanning network...</p>}

    <div className={styles.results}>
      {results.map(job => (
        <div
          key={job.id}
          className={`${styles.jobCard} ${selectedIds.includes(job.id) ? styles.jobCardSelected : ''}`}
          onClick={() => handleToggleSelect(job.id)}
        >
          <h3 className={styles.jobTitle}>
            {selectedIds.includes(job.id) && <span className={styles.selectedIndicator} />}
            {job.title}
          </h3>
          <p className={styles.jobMeta}>{job.company} — {job.location}</p>
          <p className={styles.jobSkills}>{job.requiredSkills}</p>
          <p className={styles.jobFreshness}>Fetched: {job.fetchedAt}</p>
        </div>
      ))}
    </div>

    {selectedIds.length > 0 && (
      <button className={styles.recommendBtn} onClick={handleGetRecommendation}>
        ✦ Get AI Recommendation — {selectedIds.length} {selectedIds.length === 1 ? 'role' : 'roles'} selected
      </button>
    )}

    {recommendation && (
      <div className={styles.aiPanel}>
        <p className={styles.aiHeader}>✦ Nodus AI Analysis</p>
        <p className={styles.aiResponse}>{recommendation.aiResponse}</p>
      </div>
    )}
  </div>
)
}