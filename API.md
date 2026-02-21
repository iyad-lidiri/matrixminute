# Matrix Minute API

## GET /api/daily
Returns today's problem (no answer).

Response:
- date: string
- type: AREA_SCALE | SOLVE_X | DET_3X3
- prompt: string
- matrix2: number[][] | null
- matrix3: number[][] | null
- target: number | null

## POST /api/guess
Body:
{ "userId": "string", "guess": number }

Response:
{ "correct": boolean, "feedback": string, "attemptsLeft": number, "locked": boolean }

feedback:
- "correct"
- "incorrect" (DET_3X3, AREA_SCALE)
- "higher" | "lower" (SOLVE_X)
- "locked"
- "already_solved"
