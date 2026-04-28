import { useState } from 'react'
import { Link } from 'react-router-dom'
import api from '../api'

const defaultForm = {
  reader_id: '',
  top_k_users: '10',
  limit: '10',
}

export default function RecommendationsPage() {
  const [form, setForm] = useState(defaultForm)
  const [loading, setLoading] = useState(false)
  const [error, setError] = useState('')
  const [data, setData] = useState(null)

  const onChange = (event) => {
    const { name, value } = event.target
    setForm((prev) => ({ ...prev, [name]: value }))
  }

  const onSubmit = async (event) => {
    event.preventDefault()
    setLoading(true)
    setError('')
    setData(null)

    try {
      const readerId = Number(form.reader_id)
      const topKUsers = Number(form.top_k_users || 10)
      const limit = Number(form.limit || 10)
      const res = await api.get(`/circulation/readers/${readerId}/recommendations/`, {
        params: {
          top_k_users: topKUsers,
          limit,
        },
      })
      setData(res.data)
    } catch (err) {
      setError(err?.response?.data?.detail || 'Failed to load recommendations.')
    } finally {
      setLoading(false)
    }
  }

  const buildExportUrl = () => {
    const readerId = Number(form.reader_id)
    const topKUsers = Number(form.top_k_users || 10)
    const limit = Number(form.limit || 10)
    const baseUrl = api.defaults.baseURL || ''
    return `${baseUrl}/circulation/readers/${readerId}/recommendations/export-csv/?top_k_users=${topKUsers}&limit=${limit}`
  }

  const onExport = () => {
    if (!form.reader_id) {
      setError('Please input Reader ID first.')
      return
    }
    setError('')
    window.open(buildExportUrl(), '_blank')
  }

  return (
    <main className="page">
      <div className="header-row">
        <h1>Book Recommendations</h1>
        <div className="nav-links">
          <Link to="/books">Books</Link>
          <Link to="/analytics">Analytics</Link>
          <Link to="/borrow">Borrow</Link>
          <Link to="/login">Login</Link>
        </div>
      </div>

      {error ? <div className="error">{error}</div> : null}

      <section className="card" style={{ marginBottom: 16 }}>
        <h2>User-based Collaborative Filtering</h2>
        <form className="form-grid" onSubmit={onSubmit}>
          <div className="form-row">
            <label htmlFor="reader_id">Reader ID</label>
            <input
              id="reader_id"
              name="reader_id"
              type="number"
              min="1"
              value={form.reader_id}
              onChange={onChange}
              required
            />
          </div>

          <div className="form-row">
            <label htmlFor="top_k_users">Top K Similar Readers</label>
            <input
              id="top_k_users"
              name="top_k_users"
              type="number"
              min="1"
              max="100"
              value={form.top_k_users}
              onChange={onChange}
              required
            />
          </div>

          <div className="form-row">
            <label htmlFor="limit">Recommendation Count</label>
            <input
              id="limit"
              name="limit"
              type="number"
              min="1"
              max="50"
              value={form.limit}
              onChange={onChange}
              required
            />
          </div>

          <div className="actions">
            <button type="submit" disabled={loading}>
              {loading ? 'Loading...' : 'Get Recommendations'}
            </button>
            <button type="button" className="btn-secondary" onClick={onExport}>
              Export CSV
            </button>
          </div>
        </form>
      </section>

      {data ? (
        <section className="card">
          <h2>
            Results for Reader #{data.reader_id}
          </h2>
          <div style={{ marginBottom: 12 }}>
            Algorithm: {data.algorithm} {data.neighbors_used !== undefined ? `(neighbors: ${data.neighbors_used})` : ''}
          </div>

          <table className="table">
            <thead>
              <tr>
                <th>Book ID</th>
                <th>Book Name</th>
                <th>Score</th>
                <th>Reason</th>
              </tr>
            </thead>
            <tbody>
              {(data.results || []).length ? (
                data.results.map((item) => (
                  <tr key={item.book_id}>
                    <td>{item.book_id}</td>
                    <td>{item.book_name}</td>
                    <td>{item.score}</td>
                    <td>{item.reason}</td>
                  </tr>
                ))
              ) : (
                <tr>
                  <td colSpan={4}>No recommendations.</td>
                </tr>
              )}
            </tbody>
          </table>
        </section>
      ) : null}
    </main>
  )
}
