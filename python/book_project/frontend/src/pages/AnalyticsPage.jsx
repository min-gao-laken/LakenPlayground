import { useEffect, useMemo, useState } from 'react'
import { Link } from 'react-router-dom'
import {
  Bar,
  BarChart,
  CartesianGrid,
  Legend,
  Line,
  LineChart,
  ResponsiveContainer,
  Tooltip,
  XAxis,
  YAxis,
} from 'recharts'
import api from '../api'
import { canWriteRole } from '../auth'

const defaultBorrowForm = {
  book_id: '',
  quantity: '1',
  borrowed_on: '',
}

export default function AnalyticsPage() {
  const [loading, setLoading] = useState(true)
  const [error, setError] = useState('')
  const [overview, setOverview] = useState({ top_books: [], borrow_trend: [], summary: null })
  const [books, setBooks] = useState([])
  const [borrowForm, setBorrowForm] = useState(defaultBorrowForm)
  const [submitting, setSubmitting] = useState(false)
  const [role, setRole] = useState('')

  const canWrite = canWriteRole(role)
  const topBooksChartData = useMemo(
    () => (overview.top_books || []).map((item) => ({ ...item, label: item.name })),
    [overview.top_books]
  )

  const borrowTrendData = useMemo(() => overview.borrow_trend || [], [overview.borrow_trend])

  const loadData = async () => {
    setLoading(true)
    setError('')
    try {
      const [overviewRes, booksRes, meRes] = await Promise.all([
        api.get('/api/analytics/overview/?days=30'),
        api.get('/api/books/'),
        api.get('/api/me/'),
      ])
      setOverview(overviewRes.data)
      setBooks(booksRes.data.results || [])
      setRole(meRes.data.role ?? '')
    } catch {
      setError('Failed to load analytics data.')
    } finally {
      setLoading(false)
    }
  }

  useEffect(() => {
    loadData()
  }, [])

  const onBorrowFormChange = (event) => {
    const { name, value } = event.target
    setBorrowForm((prev) => ({ ...prev, [name]: value }))
  }

  const onCreateBorrowRecord = async (event) => {
    event.preventDefault()
    if (!canWrite) return
    setSubmitting(true)
    setError('')

    const payload = {
      book_id: Number(borrowForm.book_id),
      quantity: Math.max(Number(borrowForm.quantity || 1), 1),
      borrowed_on: borrowForm.borrowed_on || undefined,
    }

    try {
      await api.post('/api/borrow-records/', payload)
      setBorrowForm(defaultBorrowForm)
      await loadData()
    } catch {
      setError('Failed to create borrow record.')
    } finally {
      setSubmitting(false)
    }
  }

  return (
    <main className="page">
      <div className="header-row">
        <h1>Data Visualization</h1>
        <div className="nav-links">
          <Link to="/books">Books</Link>
          <Link to="/publishers">Publishers</Link>
          <Link to="/authors">Authors</Link>
          <Link to="/recommendations">Recommendations</Link>
          <Link to="/login">Login</Link>
        </div>
      </div>

      {error ? <div className="error">{error}</div> : null}

      {canWrite ? (
        <section className="card" style={{ marginBottom: 16 }}>
          <h2>Quick Borrow Log</h2>
          <form onSubmit={onCreateBorrowRecord} className="form-grid">
            <div className="form-row">
              <label htmlFor="book_id">Book</label>
              <select id="book_id" name="book_id" value={borrowForm.book_id} onChange={onBorrowFormChange} required>
                <option value="">Select a book</option>
                {books.map((book) => (
                  <option key={book.id} value={book.id}>
                    {book.name}
                  </option>
                ))}
              </select>
            </div>

            <div className="form-row">
              <label htmlFor="quantity">Borrow Quantity</label>
              <input id="quantity" name="quantity" type="number" min="1" value={borrowForm.quantity} onChange={onBorrowFormChange} required />
            </div>

            <div className="form-row">
              <label htmlFor="borrowed_on">Borrow Date (optional)</label>
              <input id="borrowed_on" name="borrowed_on" type="date" value={borrowForm.borrowed_on} onChange={onBorrowFormChange} />
            </div>

            <div className="actions">
              <button type="submit" disabled={submitting || loading}>
                {submitting ? 'Saving...' : 'Add Borrow Record'}
              </button>
            </div>
          </form>
        </section>
      ) : (
        <section className="card" style={{ marginBottom: 16 }}>
          Role: {role || 'unknown'} (read-only)
        </section>
      )}

      {loading ? (
        <div>Loading...</div>
      ) : (
        <>
          <section className="analytics-kpis">
            <div className="kpi-card">
              <div className="kpi-label">Total Borrowed (30d)</div>
              <div className="kpi-value">{overview.summary?.total_borrowed ?? 0}</div>
            </div>
            <div className="kpi-card">
              <div className="kpi-label">Borrow Records</div>
              <div className="kpi-value">{overview.summary?.records ?? 0}</div>
            </div>
            <div className="kpi-card">
              <div className="kpi-label">Window</div>
              <div className="kpi-value">{overview.summary?.days ?? 30} days</div>
            </div>
          </section>

          <section className="card" style={{ marginBottom: 16 }}>
            <h2>Most Popular Books</h2>
            <div style={{ width: '100%', height: 320 }}>
              <ResponsiveContainer>
                <BarChart data={topBooksChartData} margin={{ top: 16, right: 16, left: 0, bottom: 0 }}>
                  <CartesianGrid strokeDasharray="3 3" />
                  <XAxis dataKey="label" />
                  <YAxis allowDecimals={false} />
                  <Tooltip />
                  <Legend />
                  <Bar dataKey="borrow_count" name="Borrow Count" fill="#1d4ed8" radius={[4, 4, 0, 0]} />
                  <Bar dataKey="sale_num" name="Sales" fill="#0f766e" radius={[4, 4, 0, 0]} />
                </BarChart>
              </ResponsiveContainer>
            </div>
          </section>

          <section className="card">
            <h2>Borrow Trend (Last 30 Days)</h2>
            <div style={{ width: '100%', height: 320 }}>
              <ResponsiveContainer>
                <LineChart data={borrowTrendData} margin={{ top: 16, right: 16, left: 0, bottom: 0 }}>
                  <CartesianGrid strokeDasharray="3 3" />
                  <XAxis dataKey="date" tick={{ fontSize: 12 }} />
                  <YAxis allowDecimals={false} />
                  <Tooltip />
                  <Legend />
                  <Line type="monotone" dataKey="borrow_count" name="Borrow Count" stroke="#b91c1c" strokeWidth={2} dot={false} />
                </LineChart>
              </ResponsiveContainer>
            </div>
          </section>
        </>
      )}
    </main>
  )
}
