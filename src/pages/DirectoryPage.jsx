import { useState, useEffect } from 'react'
import { useAuth } from '../context/AuthContext'
import { getTrainingOfferings, getEmployerPostings, searchTalent } from '../services/directoryService'

export default function DirectoryPage() {
  const { user } = useAuth()
  const [activeTab, setActiveTab] = useState('training')
  const [trainingOfferings, setTrainingOfferings] = useState([])
  const [employerPostings, setEmployerPostings] = useState([])
  const [talentResults, setTalentResults] = useState([])
  const [keyword, setKeyword] = useState('')

  useEffect(() => {
    const load = async () => {
      const offerings = await getTrainingOfferings()
      const postings = await getEmployerPostings()
      setTrainingOfferings(offerings)
      setEmployerPostings(postings)
    }
    load()
  }, [])

  const handleTalentSearch = async () => {
    const results = await searchTalent(keyword)
    setTalentResults(results)
  }

  return (
    <div style={{ color: 'white', padding: '2rem' }}>
      <h2>Directory</h2>
      <div style={{ display: 'flex', gap: '1rem', marginBottom: '1rem' }}>
        <button onClick={() => setActiveTab('training')}
          style={{ color: activeTab === 'training' ? '#00d2ff' : 'white' }}>
          Training Programs
        </button>
        <button onClick={() => setActiveTab('employers')}
          style={{ color: activeTab === 'employers' ? '#00d2ff' : 'white' }}>
          Employers
        </button>
        {user.role === 'EMPLOYER' && (
          <button onClick={() => setActiveTab('talent')}
            style={{ color: activeTab === 'talent' ? '#00d2ff' : 'white' }}>
            Find Talent
          </button>
        )}
      </div>

      {activeTab === 'training' && (
        <div>
          {trainingOfferings.map(o => (
            <div key={o.id} style={{ border: '1px solid #0a3a5a', padding: '1rem', margin: '0.5rem 0' }}>
              <h3>{o.programName}</h3>
              <p>{o.skillsCovered}</p>
              <p>{o.format} — {o.contactInfo}</p>
            </div>
          ))}
        </div>
      )}

      {activeTab === 'employers' && (
        <div>
          {employerPostings.map(p => (
            <div key={p.id} style={{ border: '1px solid #0a3a5a', padding: '1rem', margin: '0.5rem 0' }}>
              <h3>{p.roleTitle}</h3>
              <p>{p.companyName} — {p.location}</p>
              <p>{p.skillsNeeded}</p>
            </div>
          ))}
        </div>
      )}

      {activeTab === 'talent' && (
        <div>
          <input
            placeholder="Search by skill..."
            value={keyword}
            onChange={(e) => setKeyword(e.target.value)}
          />
          <button onClick={handleTalentSearch}>Search</button>
          {talentResults.map(t => (
            <div key={t.id} style={{ border: '1px solid #0a3a5a', padding: '1rem', margin: '0.5rem 0' }}>
              <p>{t.skills}</p>
              <p>{t.targetRole} — {t.experienceLevel}</p>
            </div>
          ))}
        </div>
      )}
    </div>
  )
}