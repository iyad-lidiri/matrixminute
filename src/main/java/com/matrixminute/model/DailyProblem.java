package com.matrixminute.model;

public class DailyProblem {
    public String date;         // "2026-02-11"
    public ProblemType type;
    public String prompt;

    public int[][] matrix2;     // 2x2 (use 0 for x placeholder on SOLVE_X)
    public int[][] matrix3;     // 3x3 or null
    public Integer target;      // only for SOLVE_X, else null

    public int answer;          // server uses this, but we won't send it to frontend
}
