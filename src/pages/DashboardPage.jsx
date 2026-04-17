import { useAuth } from '../context/AuthContext'
import JobSeekerDashboard from '../components/JobSeekerDashboard'
import TrainingProviderDashboard from '../components/TrainingProviderDashboard'
import EmployerDashboard from '../components/EmployerDashboard'

export default function DashboardPage() {
  const { user } = useAuth()

  if (user.role === 'JOB_SEEKER') return <JobSeekerDashboard />
  if (user.role === 'TRAINING_PROVIDER') return <TrainingProviderDashboard />
  if (user.role === 'EMPLOYER') return <EmployerDashboard />
}