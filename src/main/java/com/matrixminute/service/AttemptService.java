package com.matrixminute.service;

import com.matrixminute.entity.AttemptEntity;
import com.matrixminute.repository.AttemptRepository;
import org.springframework.stereotype.Service;

@Service
public class AttemptService {

    private final AttemptRepository repo;
    private static final int MAX_ATTEMPTS = 5;

    public AttemptService(AttemptRepository repo) {
        this.repo = repo;
    }

    private AttemptEntity getOrCreate(String userId, String date) {
        return repo.findByUserIdAndDate(userId, date)
                .orElseGet(() -> {
                    AttemptEntity a = new AttemptEntity();
                    a.userId = userId;
                    a.date = date;
                    a.attemptsUsed = 0;
                    a.solved = false;
                    return repo.save(a);
                });
    }

    public void incrementAttempts(String userId, String date) {
        AttemptEntity a = getOrCreate(userId, date);
        a.attemptsUsed++;
        repo.save(a);
    }

    public int attemptsLeft(String userId, String date) {
        AttemptEntity a = getOrCreate(userId, date);
        return MAX_ATTEMPTS - a.attemptsUsed;
    }

    public boolean isLocked(String userId, String date) {
        AttemptEntity a = getOrCreate(userId, date);
        return a.attemptsUsed >= MAX_ATTEMPTS || a.solved;
    }

    public boolean isSolved(String userId, String date) {
        AttemptEntity a = getOrCreate(userId, date);
        return a.solved;
    }

    public void markSolved(String userId, String date) {
        AttemptEntity a = getOrCreate(userId, date);
        a.solved = true;
        repo.save(a);
    }
}