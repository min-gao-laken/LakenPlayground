param(
  [string]$BaseUrl = "http://localhost:5095",
  [string]$Username = "demo_user",
  [string]$Password = "Demo123!",
  [ValidateRange(1, 5)]
  [int]$Count = 5
)

Set-StrictMode -Version Latest
$ErrorActionPreference = 'Stop'

function Invoke-Api {
  param(
    [ValidateSet('GET', 'POST', 'PUT', 'DELETE')]
    [string]$Method,
    [string]$Url,
    [hashtable]$Headers = @{},
    [object]$Body = $null
  )

  if ($null -ne $Body) {
    $jsonBody = $Body | ConvertTo-Json -Depth 10
    return Invoke-RestMethod -Method $Method -Uri $Url -Headers $Headers -ContentType 'application/json' -Body $jsonBody
  }

  return Invoke-RestMethod -Method $Method -Uri $Url -Headers $Headers
}

function Try-Register {
  param(
    [string]$Url,
    [string]$User,
    [string]$Pass
  )

  try {
    [void](Invoke-Api -Method POST -Url "$Url/api/auth/register" -Body @{ username = $User; password = $Pass })
    Write-Host "Created demo account: $User"
  }
  catch {
    # If already exists, login path below still works.
    Write-Host "Demo account exists or cannot be registered now. Proceeding with login..."
  }
}

function New-WorkoutTemplate {
  param(
    [datetime]$Date,
    [string]$Notes,
    [array]$Exercises
  )

  return [PSCustomObject]@{
    date = $Date
    notes = $Notes
    exercises = $Exercises
  }
}

function New-ExerciseTemplate {
  param(
    [string]$Name,
    [array]$Sets
  )

  return [PSCustomObject]@{
    name = $Name
    sets = $Sets
  }
}

function New-SetTemplate {
  param(
    [double]$Weight,
    [int]$Reps
  )

  return [PSCustomObject]@{
    weight = $Weight
    reps = $Reps
  }
}

Try-Register -Url $BaseUrl -User $Username -Pass $Password

$login = Invoke-Api -Method POST -Url "$BaseUrl/api/auth/login" -Body @{ username = $Username; password = $Password }
$token = $login.token
if ([string]::IsNullOrWhiteSpace($token)) {
  throw "Login succeeded but token is empty."
}

$authHeaders = @{ Authorization = "Bearer $token" }

$today = Get-Date
$workoutTemplates = @(
  (New-WorkoutTemplate -Date $today.Date.AddHours(19).AddMinutes(10) -Notes "Upper focus. Strong pressing session with controlled tempo." -Exercises @(
    (New-ExerciseTemplate -Name "Barbell Bench Press" -Sets @((New-SetTemplate 62.5 8), (New-SetTemplate 65 7), (New-SetTemplate 67.5 5))),
    (New-ExerciseTemplate -Name "Incline Dumbbell Press" -Sets @((New-SetTemplate 24 10), (New-SetTemplate 24 9), (New-SetTemplate 22 11))),
    (New-ExerciseTemplate -Name "Cable Triceps Pushdown" -Sets @((New-SetTemplate 30 10), (New-SetTemplate 32.5 8)))
  )),
  (New-WorkoutTemplate -Date $today.AddDays(-1).Date.AddHours(18).AddMinutes(30) -Notes "Push day. Controlled tempo, focused on chest lockout." -Exercises @(
    (New-ExerciseTemplate -Name "Barbell Bench Press" -Sets @((New-SetTemplate 60 10), (New-SetTemplate 65 8), (New-SetTemplate 67.5 6))),
    (New-ExerciseTemplate -Name "Incline Dumbbell Press" -Sets @((New-SetTemplate 24 10), (New-SetTemplate 24 9), (New-SetTemplate 22 11))),
    (New-ExerciseTemplate -Name "Cable Triceps Pushdown" -Sets @((New-SetTemplate 27.5 12), (New-SetTemplate 30 10)))
  )),
  (New-WorkoutTemplate -Date $today.AddDays(-2).Date.AddHours(7).AddMinutes(15) -Notes "Lower body strength. Kept rest 2-3 minutes on compound lifts." -Exercises @(
    (New-ExerciseTemplate -Name "Back Squat" -Sets @((New-SetTemplate 80 8), (New-SetTemplate 85 6), (New-SetTemplate 90 5))),
    (New-ExerciseTemplate -Name "Romanian Deadlift" -Sets @((New-SetTemplate 70 10), (New-SetTemplate 75 8))),
    (New-ExerciseTemplate -Name "Leg Press" -Sets @((New-SetTemplate 180 12), (New-SetTemplate 200 10), (New-SetTemplate 220 8)))
  )),
  (New-WorkoutTemplate -Date $today.AddDays(-3).Date.AddHours(19) -Notes "Pull day. Good contraction on rows and lats." -Exercises @(
    (New-ExerciseTemplate -Name "Lat Pulldown" -Sets @((New-SetTemplate 55 12), (New-SetTemplate 60 10), (New-SetTemplate 62.5 9))),
    (New-ExerciseTemplate -Name "Seated Cable Row" -Sets @((New-SetTemplate 50 12), (New-SetTemplate 55 10))),
    (New-ExerciseTemplate -Name "EZ Bar Curl" -Sets @((New-SetTemplate 25 12), (New-SetTemplate 27.5 10)))
  )),
  (New-WorkoutTemplate -Date $today.AddDays(-5).Date.AddHours(8).AddMinutes(10) -Notes "Conditioning + core. Moderate intensity, no missed reps." -Exercises @(
    (New-ExerciseTemplate -Name "Kettlebell Swing" -Sets @((New-SetTemplate 20 20), (New-SetTemplate 20 20), (New-SetTemplate 24 15))),
    (New-ExerciseTemplate -Name "Walking Lunge" -Sets @((New-SetTemplate 16 12), (New-SetTemplate 16 12))),
    (New-ExerciseTemplate -Name "Plank" -Sets @((New-SetTemplate 1 60), (New-SetTemplate 1 60)))
  )),
  (New-WorkoutTemplate -Date $today.AddDays(-7).Date.AddHours(18).AddMinutes(45) -Notes "Upper hypertrophy. Added one back-off set on pressing." -Exercises @(
    (New-ExerciseTemplate -Name "Dumbbell Shoulder Press" -Sets @((New-SetTemplate 20 10), (New-SetTemplate 22 8), (New-SetTemplate 18 12))),
    (New-ExerciseTemplate -Name "Machine Chest Press" -Sets @((New-SetTemplate 55 12), (New-SetTemplate 60 10))),
    (New-ExerciseTemplate -Name "Face Pull" -Sets @((New-SetTemplate 22.5 15), (New-SetTemplate 25 12)))
  ))
)

$workouts = $workoutTemplates | Select-Object -First $Count

foreach ($workout in $workouts) {
  $createBody = @{
    date = $workout.date.ToString('o')
    notes = $workout.notes
    exercises = @($workout.exercises | ForEach-Object { @{ name = $_.name } })
  }

  $created = Invoke-Api -Method POST -Url "$BaseUrl/api/workouts" -Headers $authHeaders -Body $createBody
  Write-Host "Created workout #$($created.id) on $($workout.date.ToString('yyyy-MM-dd'))."

  if ($null -eq $created.exercises) {
    Write-Warning "Workout #$($created.id) returned no exercises; skipping set creation."
    continue
  }

  for ($i = 0; $i -lt $workout.exercises.Count; $i++) {
    $exerciseTemplate = $workout.exercises[$i]
    $createdExercise = $created.exercises[$i]
    if ($null -eq $createdExercise) {
      continue
    }

    foreach ($set in $exerciseTemplate.sets) {
      [void](Invoke-Api -Method POST -Url "$BaseUrl/api/exercises/$($createdExercise.id)/sets" -Headers $authHeaders -Body @{
        weight = $set.weight
        reps = $set.reps
      })
    }

    Write-Host "  Added sets for $($exerciseTemplate.name)."
  }
}

$history = Invoke-Api -Method GET -Url "$BaseUrl/api/workouts/history" -Headers $authHeaders

Write-Host ""
Write-Host "Recent workouts preview (top 5):"
$history | Select-Object -First 5 | ForEach-Object {
  Write-Host ("- {0}: {1} exercises, {2} sets, {3}" -f ((Get-Date $_.date).ToString('yyyy-MM-dd')), $_.exerciseCount, $_.setCount, $_.summary)
}

Write-Host ""
Write-Host "Done. Refresh the frontend to see updated Training stats and Recent workouts."