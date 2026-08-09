import { useEffect, useRef, useState } from 'react'
import './App.scss'
import { fetchMe, fetchTrainingStats, fetchWorkoutHistory, initializeAuthFromStorage, setStoredAuthToken, setUnauthorizedHandler } from './api'
import AuthPage from './AuthPage'
import { WorkoutsPage } from './components/WorkoutsPage'
import { useWorkoutPage } from './hooks/useWorkoutPage'

function App() {
  const [token, setToken] = useState<string | null>(initializeAuthFromStorage())
  const [username, setUsername] = useState<string | null>(null)
  const [loading, setLoading] = useState(true)
  const [menuOpen, setMenuOpen] = useState(false)
  const [currentPage, setCurrentPage] = useState<'home' | 'workouts'>('home')
  const [stats, setStats] = useState<{ totalWorkouts: number; totalExercises: number; totalSets: number; weeklyWorkouts: number; monthlyWorkouts: number; lastWorkoutLabel: string | null } | null>(null)
  const [history, setHistory] = useState<Array<{ id: number; date: string; notes: string | null; exerciseCount: number; setCount: number; summary: string }>>([])
  const menuRef = useRef<HTMLDivElement | null>(null)
  const {
    loading: workoutsLoading,
    error: workoutsError,
    success: workoutSuccess,
    workouts,
    newWorkoutDate,
    newWorkoutNotes,
    selectedWorkout,
    selectedWorkoutId,
    newExerciseName,
    workoutEditDate,
    workoutEditNotes,
    exerciseNameDrafts,
    setDrafts,
    editingSet,
    setNewWorkoutDate,
    setNewWorkoutNotes,
    setSelectedWorkoutId,
    setNewExerciseName,
    setWorkoutEditDate,
    setWorkoutEditNotes,
    setExerciseNameDrafts,
    setSetDrafts,
    setEditingSet,
    handleCreateWorkout,
    handleAddExercise,
    handleUpdateWorkout,
    handleDeleteWorkout,
    handleUpdateExercise,
    handleDeleteExercise,
    handleCreateSet,
    handleStartEditSet,
    handleSaveSet,
    handleDeleteSet,
    loadData: loadWorkouts,
  } = useWorkoutPage()

  useEffect(() => {
    setUnauthorizedHandler(() => {
      setStoredAuthToken(null)
      setToken(null)
      setUsername(null)
      setStats(null)
      setHistory([])
      setMenuOpen(false)
      setCurrentPage('home')
      setLoading(false)
    })

    return () => setUnauthorizedHandler(null)
  }, [])

  useEffect(() => {
    if (!token) {
      setUsername(null)
      setStats(null)
      setHistory([])
      setLoading(false)
      return
    }

    void (async () => {
      try {
        const profile = await fetchMe(token)
        setUsername(profile.username)
        const nextStats = await fetchTrainingStats()
        const nextHistory = await fetchWorkoutHistory()
        setStats(nextStats)
        setHistory(nextHistory)
      } catch {
        setStoredAuthToken(null)
        setToken(null)
        setUsername(null)
        setStats(null)
        setHistory([])
      } finally {
        setLoading(false)
      }
    })()
  }, [token])

  useEffect(() => {
    if (!menuOpen) return

    const handleClickOutside = (event: MouseEvent) => {
      if (menuRef.current && !menuRef.current.contains(event.target as Node)) {
        setMenuOpen(false)
      }
    }

    document.addEventListener('mousedown', handleClickOutside)
    return () => document.removeEventListener('mousedown', handleClickOutside)
  }, [menuOpen])

  const handleLogout = () => {
    setStoredAuthToken(null)
    setToken(null)
    setUsername(null)
    setMenuOpen(false)
  }

  const scrollToSection = (sectionId: string) => {
    document.getElementById(sectionId)?.scrollIntoView({ behavior: 'smooth', block: 'start' })
  }

  if (loading) {
    return <div className="App app-loading">Loading...</div>
  }

  if (!token) {
    return <AuthPage onAuthenticated={(newToken, newUsername) => {
      setStoredAuthToken(newToken)
      setToken(newToken)
      setUsername(newUsername)
    }} />
  }

  return (
    <div className="App app-shell">
      <header className="page-header app-topbar">
        <div className="app-topbar__brand">
          <div className="brand-mark">FT</div>
          <div>
            <h1>Fitness Tracker</h1>
            <p>Track your workouts and progress</p>
          </div>
        </div>

        <div className="app-topbar__actions">
          <div className="user-menu-wrap" ref={menuRef}>
            <button className="user-menu-trigger" onClick={() => setMenuOpen((open) => !open)}>
              <span className="user-avatar">{(username ?? 'U').charAt(0).toUpperCase()}</span>
              <span className="user-menu-label">{username ?? 'User'}</span>
              <span className="user-menu-caret">▾</span>
            </button>

            {menuOpen && (
              <div className="user-menu-dropdown">
                <div className="user-menu-info">
                  <p className="user-menu-name">{username ?? 'User'}</p>
                  <p className="user-menu-role">Signed in</p>
                </div>
                <button className="user-menu-logout" onClick={handleLogout}>
                  Logout
                </button>
              </div>
            )}
          </div>
        </div>
      </header>

      <main className="app-main">
        <aside className="app-sidebar">
          <div className="sidebar-card">
            <p className="sidebar-label">Overview</p>
            <h3>Manage your training</h3>
            <p>Create workouts, add exercises, and review your progress in one place.</p>
          </div>
          <div className="sidebar-card sidebar-card--muted">
            <p className="sidebar-label">Navigation</p>
            <div className="sidebar-nav">
              <button
                className={`sidebar-nav__button ${currentPage === 'home' ? 'sidebar-nav__button--active' : ''}`}
                type="button"
                onClick={() => setCurrentPage('home')}
              >
                Home
              </button>
              <button
                className={`sidebar-nav__button ${currentPage === 'workouts' ? 'sidebar-nav__button--active' : ''}`}
                type="button"
                onClick={() => setCurrentPage('workouts')}
              >
                Workouts
              </button>
            </div>
          </div>
        </aside>

        <section className="app-content">
          {currentPage === 'home' ? (
            <>
              <div className="repo-hero-card">
                <div className="repo-hero-card__head">
                  <div>
                    <p className="repo-hero-card__eyebrow">Repository</p>
                    <h2>Fitness Tracker Workspace</h2>
                    <p>Overview / Activity</p>
                  </div>
                  <div className="repo-hero-card__tabs">
                    <span className="repo-pill repo-pill--active">Overview</span>
                    <span className="repo-pill">Activity</span>
                    <span className="repo-pill">Settings</span>
                  </div>
                </div>

                <div className="repo-metrics-grid">
                  <div className="repo-metric-card">
                    <span className="repo-metric-card__label">Workouts</span>
                    <strong>{stats?.totalWorkouts ?? 0} total</strong>
                    <p>{stats?.weeklyWorkouts ?? 0} this week · {stats?.monthlyWorkouts ?? 0} this month</p>
                  </div>
                  <div className="repo-metric-card">
                    <span className="repo-metric-card__label">Exercises</span>
                    <strong>{stats?.totalExercises ?? 0} logged</strong>
                    <p>{stats?.totalSets ?? 0} sets tracked across your sessions.</p>
                  </div>
                  <div className="repo-metric-card">
                    <span className="repo-metric-card__label">Latest</span>
                    <strong>{stats?.lastWorkoutLabel ?? 'No workouts yet'}</strong>
                    <p>Keep your training history growing with every session.</p>
                  </div>
                </div>
              </div>

              <div className="management-grid history-grid">
                <div className="management-card">
                  <div className="page-panel__header page-panel__header--compact">
                    <div>
                      <p className="page-panel__eyebrow">Training stats</p>
                      <h2>Progress snapshot</h2>
                    </div>
                  </div>
                  <div className="history-list">
                    <div className="history-item">
                      <span>Total workouts</span>
                      <strong>{stats?.totalWorkouts ?? 0}</strong>
                    </div>
                    <div className="history-item">
                      <span>Exercises logged</span>
                      <strong>{stats?.totalExercises ?? 0}</strong>
                    </div>
                    <div className="history-item">
                      <span>Sets tracked</span>
                      <strong>{stats?.totalSets ?? 0}</strong>
                    </div>
                  </div>
                </div>

                <div className="management-card">
                  <div className="page-panel__header page-panel__header--compact">
                    <div>
                      <p className="page-panel__eyebrow">History</p>
                      <h2>Recent workouts</h2>
                    </div>
                  </div>
                  {history.length === 0 ? (
                    <p className="empty">No history yet. Create a workout and it will appear here.</p>
                  ) : (
                    <div className="history-list history-list--stacked">
                      {history.slice(0, 5).map((item) => (
                        <div key={item.id} className="history-card">
                          <div className="history-card__head">
                            <strong>{new Date(item.date).toLocaleDateString()}</strong>
                            <span>{item.exerciseCount} exercises · {item.setCount} sets</span>
                          </div>
                          <p>{item.summary}</p>
                          {item.notes ? <small>{item.notes}</small> : null}
                        </div>
                      ))}
                    </div>
                  )}
                </div>
              </div>
            </>
          ) : (
            <WorkoutsPage
              workouts={workouts}
              workoutsLoading={workoutsLoading}
              selectedWorkoutId={selectedWorkoutId}
              newWorkoutDate={newWorkoutDate}
              newWorkoutNotes={newWorkoutNotes}
              selectedWorkout={selectedWorkout}
              newExerciseName={newExerciseName}
              workoutEditDate={workoutEditDate}
              workoutEditNotes={workoutEditNotes}
              exerciseNameDrafts={exerciseNameDrafts}
              setDrafts={setDrafts}
              editingSet={editingSet}
              success={workoutSuccess}
              onLoadWorkouts={loadWorkouts}
              onCreateWorkout={handleCreateWorkout}
              onDateChange={setNewWorkoutDate}
              onNotesChange={setNewWorkoutNotes}
              onSelectWorkout={setSelectedWorkoutId}
              onAddExercise={handleAddExercise}
              onExerciseNameChange={setNewExerciseName}
              onWorkoutDateChange={setWorkoutEditDate}
              onWorkoutNotesChange={setWorkoutEditNotes}
              onUpdateWorkout={handleUpdateWorkout}
              onDeleteWorkout={handleDeleteWorkout}
              onExerciseNameEdit={(exerciseId, value) =>
                setExerciseNameDrafts((prev) => ({ ...prev, [exerciseId]: value }))
              }
              onUpdateExercise={handleUpdateExercise}
              onDeleteExercise={handleDeleteExercise}
              onCreateSet={handleCreateSet}
              onSetDraftChange={(exerciseId, field, value) =>
                setSetDrafts((prev) => ({
                  ...prev,
                  [exerciseId]: {
                    weight: field === 'weight' ? value : prev[exerciseId]?.weight ?? '',
                    reps: field === 'reps' ? value : prev[exerciseId]?.reps ?? '',
                  },
                }))
              }
              onStartEditSet={handleStartEditSet}
              onSaveSet={handleSaveSet}
              onCancelEditSet={() => setEditingSet({ id: null, weight: '', reps: '' })}
              onDeleteSet={handleDeleteSet}
              onEditSetValueChange={(field, value) =>
                setEditingSet((prev) => ({ ...prev, [field]: value }))
              }
            />
          )}
        </section>
      </main>
    </div>
  )
}

export default App
