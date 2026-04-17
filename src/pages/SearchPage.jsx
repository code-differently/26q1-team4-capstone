import { useState } from 'react'
import { useAuth } from '../context/AuthContext'
import { searchJobs } from '../services/jobService'
import { getRecommendation } from '../services/recommendationService'

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
    <div style={{ color: 'white', padding: '2rem' }}>
      <div>
        <input
          placeholder="Job title or skill"
          value={query}
          onChange={(e) => setQuery(e.target.value)}
        />
        <input
          placeholder="Location"
          value={location}
          onChange={(e) => setLocation(e.target.value)}
        />
        <button onClick={handleSearch}>Search</button>
      </div>

      {isLoading && <p>Loading...</p>}

      <div>
        {results.map(job => (
          <div key={job.id} onClick={() => handleToggleSelect(job.id)}
            style={{ border: selectedIds.includes(job.id) ? '1px solid #00d2ff' : '1px solid #333',
              padding: '1rem', margin: '0.5rem 0', cursor: 'pointer' }}>
            <h3>{job.title}</h3>
            <p>{job.company} — {job.location}</p>
            <p>{job.requiredSkills}</p>
            <p style={{ fontSize: '11px', opacity: 0.5 }}>Fetched: {job.fetchedAt}</p>
          </div>
        ))}
      </div>

      {selectedIds.length > 0 && (
        <button onClick={handleGetRecommendation}>
          Get AI Recommendation ({selectedIds.length} jobs selected)
        </button>
      )}

      {recommendation && (
        <div style={{ border: '1px solid #c472f0', padding: '1rem', marginTop: '1rem' }}>
          <h3>Nodus AI Analysis</h3>
          <p style={{ whiteSpace: 'pre-wrap' }}>{recommendation.aiResponse}</p>
        </div>
      )}
    </div>
  )
}