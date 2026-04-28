import { useEffect, useState } from 'react'
import api from '../api'
import { canWriteRole } from '../auth'

export default function BorrowDemo() {
  const [form, setForm] = useState({ reader_id: '', book_id: '', due_date: '' })
  const [msg, setMsg] = useState('')
  const [role, setRole] = useState('')

  useEffect(() => {
    api.get('/api/me/').then((res) => setRole(res.data.role ?? '')).catch(() => setRole(''))
  }, [])

  const onChange = (e) => {
    const { name, value } = e.target
    setForm((p) => ({ ...p, [name]: value }))
  }

  const onSubmit = async (e) => {
    e.preventDefault()
    if (!canWriteRole(role)) {
      setMsg(`Role ${role || 'unknown'} is read-only`)
      return
    }
    setMsg('')
    try {
      const res = await api.post('/circulation/loans/borrow/', {
        reader_id: Number(form.reader_id),
        book_id: Number(form.book_id),
        due_date: form.due_date,
      })
      setMsg(`Success: loan id = ${res.data.id}`)
    } catch (err) {
      setMsg(err?.response?.data?.detail || 'Request failed')
    }
  }

  return (
    <form onSubmit={onSubmit}>
      <input name="reader_id" placeholder="reader_id" value={form.reader_id} onChange={onChange} />
      <input name="book_id" placeholder="book_id" value={form.book_id} onChange={onChange} />
      <input name="due_date" type="date" value={form.due_date} onChange={onChange} />
      <button type="submit" disabled={!canWriteRole(role)}>Borrow</button>
      <div>{msg}</div>
    </form>
  )
}
