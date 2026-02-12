package com.matrixminute.model;

public class GuessResponse {
    public boolean correct;
    public String feedback;   // "higher", "lower", "correct"
    public int attemptsLeft;
    public boolean locked;
}
