package com.matrixminute.service;

import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

@Service
public class AttemptService {

    private static final int MAX_ATTEMPTS = 6;

    private final Map<String, Integer> attemptsMap = new HashMap<>();
    private final Map<String, Boolean> solvedMap = new HashMap<>();

    private String key(String userId) {
        return userId + "_" + LocalDate.now();
    }

    public int getAttempts(String userId) {
        return attemptsMap.getOrDefault(key(userId), 0);
    }

    public void incrementAttempts(String userId) {
        String k = key(userId);
        attemptsMap.put(k, getAttempts(userId) + 1);
    }

    public int attemptsLeft(String userId) {
        return MAX_ATTEMPTS - getAttempts(userId);
    }

    public boolean isLocked(String userId) {
        return solvedMap.getOrDefault(key(userId), false)
                || getAttempts(userId) >= MAX_ATTEMPTS;
    }

    public void markSolved(String userId) {
        solvedMap.put(key(userId), true);
    }
}
