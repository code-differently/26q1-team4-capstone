import { useState, useEffect } from 'react'
import { useAuth } from '../context/AuthContext'
import { getHistory } from '../services/recommendationService'
import styles from './HistoryPage.module.css'
export default function HistoryPage() {
  const { user } = useAuth()
  const [recommendations, setRecommendations] = useState([])
  const [isLoading, setIsLoading] = useState(true)

  useEffect(() => {
    const load = async () => {
      const history = await getHistory(user.id)
      setRecommendations(history)
      setIsLoading(false)
    }
    load()
  }, [])

 return (
  <div className={styles.page}>
    <div className={styles.header}>
      <h1 className={styles.title}>Recommendation <span style={{ color: '#c472f0' }}>History</span></h1>
      <p className={styles.subtitle}>Your past AI analyses</p>
    </div>

    {isLoading && <p className={styles.loading}>● Loading history...</p>}

    {!isLoading && recommendations.length === 0 && (
      <p className={styles.empty}>No recommendations yet — run a search to get started.</p>
    )}

    <div className={styles.list}>
      {recommendations.map(rec => (
        <div key={rec.id} className={styles.card}>
          <p className={styles.cardDate}>{rec.createdAt}</p>
          <p className={styles.cardQuery}>Search: {rec.searchQuery}</p>
          <p className={styles.aiLabel}>✦ Nodus AI Analysis</p>
          <p className={styles.cardResponse}>{rec.aiResponse}</p>
        </div>
      ))}
    </div>
  </div>
)
}