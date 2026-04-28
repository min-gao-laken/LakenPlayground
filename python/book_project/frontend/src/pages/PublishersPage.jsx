import { useEffect, useMemo, useState } from 'react'
import { Link } from 'react-router-dom'
import api from '../api'
import { canWriteRole } from '../auth'

const emptyForm = {
  name: '',
  publisher_address: '',
}

export default function PublishersPage() {
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
      const [response, meRes] = await Promise.all([api.get('/api/publishers/'), api.get('/api/me/')])
      setPublishers(response.data.results ?? [])
      setRole(meRes.data.role ?? '')
    } catch {
      setError('Failed to load publishers.')
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
      publisher_name: form.name,
      publisher_address: form.publisher_address,
    }

    try {
      if (isEditing) {
        await api.put(`/api/publishers/${editingId}/`, payload)
      } else {
        await api.post('/api/publishers/', payload)
      }
      await loadData()
      resetForm()
    } catch {
      setError('Save failed.')
    } finally {
      setSaving(false)
    }
  }

  const onEdit = (publisher) => {
    if (!canWrite) return
    setEditingId(publisher.id)
    setForm({
      name: publisher.publisher_name ?? '',
      publisher_address: publisher.publisher_address ?? '',
    })
  }

  const onDelete = async (publisherId) => {
    if (!canWrite) return
    if (!window.confirm('Delete this publisher?')) return
    setError('')
    try {
      await api.delete(`/api/publishers/${publisherId}/`)
      await loadData()
      if (editingId === publisherId) resetForm()
    } catch {
      setError('Delete failed. It may still be referenced by books.')
    }
  }

  return (
    <main className="page">
      <div className="header-row">
        <h1>Publisher Management</h1>
        <div className="nav-links">
          <Link to="/books">Books</Link>
          <Link to="/authors">Authors</Link>
          <Link to="/analytics">Analytics</Link>
          <Link to="/login">Login</Link>
        </div>
      </div>

      {error ? <div className="error">{error}</div> : null}
      {!canWrite ? <div className="card" style={{ marginBottom: 16 }}>Role: {role || 'unknown'} (read-only)</div> : null}

      {canWrite ? (
        <section className="card" style={{ marginBottom: 16 }}>
          <h2>{isEditing ? 'Edit Publisher' : 'Add Publisher'}</h2>
          <form onSubmit={onSubmit} className="form-grid">
            <div className="form-row">
              <label htmlFor="name">Name</label>
              <input id="name" name="name" value={form.name} onChange={onChange} required />
            </div>
            <div className="form-row">
              <label htmlFor="publisher_address">Address</label>
              <input id="publisher_address" name="publisher_address" value={form.publisher_address} onChange={onChange} required />
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
              <th>Address</th>
              <th>Actions</th>
            </tr>
          </thead>
          <tbody>
            {publishers.length ? (
              publishers.map((publisher) => (
                <tr key={publisher.id}>
                  <td>{publisher.id}</td>
                  <td>{publisher.publisher_name}</td>
                  <td>{publisher.publisher_address}</td>
                  <td>
                    {canWrite ? (
                      <>
                        <button type="button" className="btn-small" onClick={() => onEdit(publisher)}>
                          Edit
                        </button>
                        <button type="button" className="btn-small btn-danger" onClick={() => onDelete(publisher.id)}>
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
