import { useState, useEffect } from 'react'
import { useAuth } from '../context/AuthContext'
import { getHistory } from '../services/recommendationService'

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
    <div style={{ color: 'white', padding: '2rem' }}>
      <h2>My Recommendation History</h2>
      {isLoading && <p>Loading...</p>}
      {recommendations.map(rec => (
        <div key={rec.id} style={{ border: '1px solid #c472f0', padding: '1rem', margin: '0.5rem 0' }}>
          <p style={{ opacity: 0.5, fontSize: '12px' }}>{rec.createdAt}</p>
          <p style={{ color: '#00d2ff' }}>Search: {rec.searchQuery}</p>
          <p style={{ whiteSpace: 'pre-wrap' }}>{rec.aiResponse}</p>
        </div>
      ))}
    </div>
  )
}