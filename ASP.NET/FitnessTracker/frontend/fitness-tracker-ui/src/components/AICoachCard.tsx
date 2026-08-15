import type { TrainingPlanRecommendation } from '../api'

type AICoachCardProps = {
  goal: string
  weeklyDays: number
  experienceLevel: string
  loading: boolean
  plan: TrainingPlanRecommendation | null
  error: string | null
  onGoalChange: (value: string) => void
  onWeeklyDaysChange: (value: number) => void
  onExperienceLevelChange: (value: string) => void
  onGenerate: () => Promise<void> | void
}

export function AICoachCard({
  goal,
  weeklyDays,
  experienceLevel,
  loading,
  plan,
  error,
  onGoalChange,
  onWeeklyDaysChange,
  onExperienceLevelChange,
  onGenerate,
}: AICoachCardProps) {
  return (
    <div className="ai-coach-card">
      <div className="page-panel__header page-panel__header--compact">
        <div>
          <p className="page-panel__eyebrow">AI coach</p>
          <h2>Training plan recommendation</h2>
        </div>
      </div>

      <div className="ai-coach-form">
        <label>
          Goal
          <select value={goal} onChange={(event) => onGoalChange(event.target.value)}>
            <option value="fat loss">Fat loss</option>
            <option value="muscle gain">Muscle gain</option>
            <option value="strength">Strength</option>
            <option value="endurance">Endurance</option>
            <option value="general fitness">General fitness</option>
          </select>
        </label>

        <label>
          Weekly days
          <select value={weeklyDays} onChange={(event) => onWeeklyDaysChange(Number(event.target.value))}>
            <option value={2}>2 days</option>
            <option value={3}>3 days</option>
            <option value={4}>4 days</option>
            <option value={5}>5 days</option>
            <option value={6}>6 days</option>
          </select>
        </label>

        <label>
          Experience
          <select value={experienceLevel} onChange={(event) => onExperienceLevelChange(event.target.value)}>
            <option value="beginner">Beginner</option>
            <option value="intermediate">Intermediate</option>
            <option value="advanced">Advanced</option>
          </select>
        </label>

        <button
          type="button"
          className="ai-coach-generate-button"
          onClick={() => void onGenerate()}
          disabled={loading}
        >
          {loading ? 'Generating...' : 'Generate plan'}
        </button>
      </div>

      {error && <div className="ai-coach-error">{error}</div>}

      {loading ? (
        <div className="ai-plan-result ai-plan-result--loading" aria-live="polite">
          <div className="ai-plan-summary">
            <strong>Generating plan...</strong>
            <span>Loading personalized recommendation</span>
          </div>
          <div className="ai-plan-loading" style={{ display: 'grid', gap: '0.75rem', marginTop: '1rem' }}>
            <div style={{ height: '12px', width: '65%', background: '#e5e7eb', borderRadius: '999px' }} />
            <div style={{ height: '12px', width: '100%', background: '#f3f4f6', borderRadius: '999px' }} />
            <div style={{ height: '12px', width: '80%', background: '#f3f4f6', borderRadius: '999px' }} />
          </div>
        </div>
      ) : (
        plan && (
          <div className="ai-plan-result">
            <div className="ai-plan-summary">
              <strong>{plan.goal}</strong>
              <span>{plan.weeklyDays}-day plan · {plan.experienceLevel}</span>
              <span className={`ai-plan-source ai-plan-source--${plan.source === 'gemini' ? 'gemini' : 'fallback'}`}>
                {plan.source === 'gemini' ? 'Gemini' : 'Local template'}
              </span>
            </div>

            <p>{plan.summary}</p>

            <div className="ai-plan-days">
              {plan.days.map((day) => (
                <div key={day.dayName} className="ai-plan-day">
                  <div className="ai-plan-day__head">
                    <strong>{day.dayName}</strong>
                    <span>{day.focus}</span>
                  </div>
                  <p>{day.notes}</p>
                  <ul>
                    {day.exercises.map((exercise) => (
                      <li key={`${day.dayName}-${exercise}`}>{exercise}</li>
                    ))}
                  </ul>
                </div>
              ))}
            </div>
          </div>
        )
      )}
    </div>
  )
}
