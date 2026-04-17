import { useState, useEffect } from 'react'
import { useAuth } from '../context/AuthContext'
import { getTrainingOfferings, getEmployerPostings, searchTalent } from '../services/directoryService'
import styles from './DirectoryPage.module.css'
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
  <div className={styles.page}>
    <div className={styles.header}>
      <h1 className={styles.title}>Network <span style={{ color: '#00d2ff' }}>Directory</span></h1>
      <p className={styles.subtitle}>Training programs, employers, and talent</p>
    </div>

    <div className={styles.tabs}>
      <button
        className={`${styles.tab} ${activeTab === 'training' ? styles.tabActive : ''}`}
        onClick={() => setActiveTab('training')}
      >
        Training Programs
      </button>
      <button
        className={`${styles.tab} ${activeTab === 'employers' ? styles.tabActive : ''}`}
        onClick={() => setActiveTab('employers')}
      >
        Employers
      </button>
      {user.role === 'EMPLOYER' && (
        <button
          className={`${styles.tab} ${activeTab === 'talent' ? styles.tabActive : ''}`}
          onClick={() => setActiveTab('talent')}
        >
          Find Talent
        </button>
      )}
    </div>

    {activeTab === 'training' && (
      <div className={styles.grid}>
        {trainingOfferings.map(o => (
          <div key={o.id} className={styles.card}>
            <p className={styles.cardTitle}>{o.programName}</p>
            <p className={styles.cardSkills}>{o.skillsCovered}</p>
            <p className={styles.cardMeta}>{o.format} — {o.contactInfo}</p>
          </div>
        ))}
      </div>
    )}

    {activeTab === 'employers' && (
      <div className={styles.grid}>
        {employerPostings.map(p => (
          <div key={p.id} className={styles.card}>
            <p className={styles.cardTitle}>{p.roleTitle}</p>
            <p className={styles.cardMeta}>{p.companyName} — {p.location}</p>
            <p className={styles.cardSkills}>{p.skillsNeeded}</p>
          </div>
        ))}
      </div>
    )}

    {activeTab === 'talent' && (
      <div>
        <div className={styles.searchBar}>
          <input
            className={styles.input}
            placeholder="Search by skill..."
            value={keyword}
            onChange={(e) => setKeyword(e.target.value)}
          />
          <button className={styles.searchBtn} onClick={handleTalentSearch}>
            Search →
          </button>
        </div>
        <div className={styles.grid}>
          {talentResults.map(t => (
            <div key={t.id} className={styles.talentCard}>
              <p className={styles.talentSkills}>{t.skills}</p>
              <p className={styles.talentMeta}>{t.targetRole} — {t.experienceLevel}</p>
            </div>
          ))}
        </div>
      </div>
    )}
  </div>
)
}