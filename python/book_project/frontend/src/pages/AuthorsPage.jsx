import { useEffect, useMemo, useState } from 'react'
import { Link } from 'react-router-dom'
import api from '../api'
import { canWriteRole } from '../auth'

const emptyForm = {
  name: '',
  book_ids: [],
}

export default function AuthorsPage() {
  const [authors, setAuthors] = useState([])
  const [books, setBooks] = useState([])
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
      const [authorsRes, booksRes, meRes] = await Promise.all([
        api.get('/api/authors/'),
        api.get('/api/books/'),
        api.get('/api/me/'),
      ])
      setAuthors(authorsRes.data.results ?? [])
      setBooks(booksRes.data.results ?? [])
      setRole(meRes.data.role ?? '')
    } catch {
      setError('Failed to load authors.')
    } finally {
      setLoading(false)
    }
  }

  useEffect(() => {
    loadData()
  }, [])

  const onChangeName = (event) => {
    const { value } = event.target
    setForm((prev) => ({ ...prev, name: value }))
  }

  const onToggleBook = (bookId) => {
    if (!canWrite) return
    setForm((prev) => {
      const exists = prev.book_ids.includes(bookId)
      return {
        ...prev,
        book_ids: exists ? prev.book_ids.filter((id) => id !== bookId) : [...prev.book_ids, bookId],
      }
    })
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
      name: form.name,
      book_ids: form.book_ids,
    }

    try {
      if (isEditing) {
        await api.put(`/api/authors/${editingId}/`, payload)
      } else {
        await api.post('/api/authors/', payload)
      }
      await loadData()
      resetForm()
    } catch {
      setError('Save failed.')
    } finally {
      setSaving(false)
    }
  }

  const onEdit = (author) => {
    if (!canWrite) return
    setEditingId(author.id)
    setForm({
      name: author.name ?? '',
      book_ids: author.book_ids ?? [],
    })
  }

  const onDelete = async (authorId) => {
    if (!canWrite) return
    if (!window.confirm('Delete this author?')) return
    setError('')
    try {
      await api.delete(`/api/authors/${authorId}/`)
      await loadData()
      if (editingId === authorId) resetForm()
    } catch {
      setError('Delete failed.')
    }
  }

  return (
    <main className="page">
      <div className="header-row">
        <h1>Author Management</h1>
        <div className="nav-links">
          <Link to="/books">Books</Link>
          <Link to="/publishers">Publishers</Link>
          <Link to="/analytics">Analytics</Link>
          <Link to="/login">Login</Link>
        </div>
      </div>

      {error ? <div className="error">{error}</div> : null}
      {!canWrite ? <div className="card" style={{ marginBottom: 16 }}>Role: {role || 'unknown'} (read-only)</div> : null}

      {canWrite ? (
        <section className="card" style={{ marginBottom: 16 }}>
          <h2>{isEditing ? 'Edit Author' : 'Add Author'}</h2>
          <form onSubmit={onSubmit}>
            <div className="form-row">
              <label htmlFor="author_name">Name</label>
              <input id="author_name" name="name" value={form.name} onChange={onChangeName} required />
            </div>

            <div className="form-row">
              <label>Books</label>
              <div className="checkbox-grid">
                {books.length ? (
                  books.map((book) => (
                    <label key={book.id} className="checkbox-item">
                      <input type="checkbox" checked={form.book_ids.includes(book.id)} onChange={() => onToggleBook(book.id)} />
                      <span>{book.name}</span>
                    </label>
                  ))
                ) : (
                  <div>No books available.</div>
                )}
              </div>
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
              <th>Name</th>
              <th>Books</th>
              <th>Actions</th>
            </tr>
          </thead>
          <tbody>
            {authors.length ? (
              authors.map((author) => (
                <tr key={author.id}>
                  <td>{author.id}</td>
                  <td>{author.name}</td>
                  <td>{author.book_names?.length ? author.book_names.join(', ') : 'None'}</td>
                  <td>
                    {canWrite ? (
                      <>
                        <button type="button" className="btn-small" onClick={() => onEdit(author)}>
                          Edit
                        </button>
                        <button type="button" className="btn-small btn-danger" onClick={() => onDelete(author.id)}>
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
                <td colSpan={4}>No data.</td>
              </tr>
            )}
          </tbody>
        </table>
      )}
    </main>
  )
}
