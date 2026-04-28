import { useEffect, useMemo, useState } from 'react'
import { Link } from 'react-router-dom'
import api from '../api'
import { canWriteRole } from '../auth'

const emptyForm = {
  name: '',
  price: '',
  inventory: '',
  sale_num: '0',
  publisher_id: '',
}

export default function BooksPage() {
  const [books, setBooks] = useState([])
  const [publishers, setPublishers] = useState([])
  const [loading, setLoading] = useState(true)
  const [saving, setSaving] = useState(false)
  const [error, setError] = useState('')
  const [form, setForm] = useState(emptyForm)
  const [editingId, setEditingId] = useState(null)
  const [role, setRole] = useState('')

  const isEditing = useMemo(() => editingId !== null, [editingId])
  const canWrite = canWriteRole(role)

  const loadData = async () => {
    setLoading(true)
    setError('')
    try {
      const [booksRes, publishersRes, meRes] = await Promise.all([
        api.get('/api/books/'),
        api.get('/api/publishers/'),
        api.get('/api/me/'),
      ])
      setBooks(booksRes.data.results ?? [])
      setPublishers(publishersRes.data.results ?? [])
      setRole(meRes.data.role ?? '')
    } catch {
      setError('Failed to load data from API.')
    } finally {
      setLoading(false)
    }
  }

  useEffect(() => {
    loadData()
  }, [])

  const onChange = (event) => {
    const { name, value } = event.target
    setForm((prev) => ({ ...prev, [name]: value }))
  }

  const resetForm = () => {
    setForm(emptyForm)
    setEditingId(null)
  }

  const onSubmit = async (event) => {
    event.preventDefault()
    if (!canWrite) return
    setSaving(true)
    setError('')

    const payload = {
      ...form,
      publisher_id: Number(form.publisher_id),
      inventory: Number(form.inventory),
      sale_num: Number(form.sale_num || 0),
    }

    try {
      if (isEditing) {
        await api.put(`/api/books/${editingId}/`, payload)
      } else {
        await api.post('/api/books/', payload)
      }
      await loadData()
      resetForm()
    } catch {
      setError('Save failed. Please check required fields.')
    } finally {
      setSaving(false)
    }
  }

  const onEdit = (book) => {
    if (!canWrite) return
    setEditingId(book.id)
    setForm({
      name: book.name ?? '',
      price: book.price ?? '',
      inventory: String(book.inventory ?? ''),
      sale_num: String(book.sale_num ?? 0),
      publisher_id: String(book.publisher_id ?? ''),
    })
    window.scrollTo({ top: 0, behavior: 'smooth' })
  }

  const onDelete = async (bookId) => {
    if (!canWrite) return
    if (!window.confirm('Delete this book?')) return

    setError('')
    try {
      await api.delete(`/api/books/${bookId}/`)
      await loadData()
      if (editingId === bookId) resetForm()
    } catch {
      setError('Delete failed.')
    }
  }

  return (
    <main className="page">
      <div className="header-row">
        <h1>Book Management</h1>
        <div className="nav-links">
          <Link to="/publishers">Publishers</Link>
          <Link to="/authors">Authors</Link>
          <Link to="/analytics">Analytics</Link>
          <Link to="/recommendations">Recommendations</Link>
          <Link to="/login">Login</Link>
        </div>
      </div>

      {error ? <div className="error">{error}</div> : null}
      {!canWrite ? <div className="card" style={{ marginBottom: 16 }}>Role: {role || 'unknown'} (read-only)</div> : null}

      {canWrite ? (
        <section className="card" style={{ marginBottom: 16 }}>
          <h2>{isEditing ? 'Edit Book' : 'Add Book'}</h2>
          <form onSubmit={onSubmit} className="form-grid">
            <div className="form-row">
              <label htmlFor="name">Title</label>
              <input id="name" name="name" value={form.name} onChange={onChange} required />
            </div>

            <div className="form-row">
              <label htmlFor="price">Price</label>
              <input id="price" name="price" type="number" step="0.01" min="0" value={form.price} onChange={onChange} required />
            </div>

            <div className="form-row">
              <label htmlFor="inventory">Inventory</label>
              <input id="inventory" name="inventory" type="number" min="0" value={form.inventory} onChange={onChange} required />
            </div>

            <div className="form-row">
              <label htmlFor="sale_num">Sales</label>
              <input id="sale_num" name="sale_num" type="number" min="0" value={form.sale_num} onChange={onChange} required />
            </div>

            <div className="form-row">
              <label htmlFor="publisher_id">Publisher</label>
              <select id="publisher_id" name="publisher_id" value={form.publisher_id} onChange={onChange} required>
                <option value="">Select a publisher</option>
                {publishers.map((publisher) => (
                  <option key={publisher.id} value={publisher.id}>
                    {publisher.publisher_name}
                  </option>
                ))}
              </select>
            </div>

            <div className="actions">
              <button type="submit" disabled={saving || loading}>
                {saving ? 'Saving...' : isEditing ? 'Update' : 'Create'}
              </button>
              {isEditing ? (
                <button type="button" className="btn-secondary" onClick={resetForm}>
                  Cancel
                </button>
              ) : null}
            </div>
          </form>
        </section>
      ) : null}

      {loading ? (
        <div>Loading...</div>
      ) : (
        <table className="table">
          <thead>
            <tr>
              <th>ID</th>
              <th>Title</th>
              <th>Price</th>
              <th>Inventory</th>
              <th>Sales</th>
              <th>Publisher</th>
              <th>Actions</th>
            </tr>
          </thead>
          <tbody>
            {books.length ? (
              books.map((book) => (
                <tr key={book.id}>
                  <td>{book.id}</td>
                  <td>{book.name}</td>
                  <td>{book.price}</td>
                  <td>{book.inventory}</td>
                  <td>{book.sale_num}</td>
                  <td>{book.publisher_name ?? '-'}</td>
                  <td>
                    {canWrite ? (
                      <>
                        <button type="button" className="btn-small" onClick={() => onEdit(book)}>
                          Edit
                        </button>
                        <button type="button" className="btn-small btn-danger" onClick={() => onDelete(book.id)}>
                          Delete
                        </button>
                      </>
                    ) : (
                      'Read-only'
                    )}
                  </td>
                </tr>
              ))
            ) : (
              <tr>
                <td colSpan={7}>No data.</td>
              </tr>
            )}
          </tbody>
        </table>
      )}
    </main>
  )
}
