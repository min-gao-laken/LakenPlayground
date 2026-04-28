import { useState } from 'react'
import { useNavigate } from 'react-router-dom'
import api from '../api'

export default function LoginPage() {
  const navigate = useNavigate()
  const [number, setNumber] = useState('')
  const [password, setPassword] = useState('')
  const [loading, setLoading] = useState(false)
  const [error, setError] = useState('')

  const onSubmit = async (event) => {
    event.preventDefault()
    setLoading(true)
    setError('')

    try {
      await api.post('/api/login/', { number, password })
      navigate('/books')
    } catch {
      setError('Login failed. Please check your account and password.')
    } finally {
      setLoading(false)
    }
  }

  return (
    <main className="page">
      <section className="card" style={{ maxWidth: 460, margin: '0 auto' }}>
        <h1>Manager Login</h1>
        {error ? <div className="error">{error}</div> : null}

        <form onSubmit={onSubmit}>
          <div className="form-row">
            <label htmlFor="number">Account</label>
            <input
              id="number"
              name="number"
              value={number}
              onChange={(event) => setNumber(event.target.value)}
              required
            />
          </div>

          <div className="form-row">
            <label htmlFor="password">Password</label>
            <input
              id="password"
              name="password"
              type="password"
              value={password}
              onChange={(event) => setPassword(event.target.value)}
              required
            />
          </div>

          <button type="submit" disabled={loading}>
            {loading ? 'Signing in...' : 'Sign In'}
          </button>
        </form>
      </section>
    </main>
  )
}
