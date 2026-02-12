package com.matrixminute.controller;

import com.matrixminute.model.DailyProblem;
import com.matrixminute.service.DailyProblemService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import com.matrixminute.model.GuessRequest;
import com.matrixminute.model.GuessResponse;
import com.matrixminute.service.AttemptService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;



@RestController
public class DailyController {

    private final DailyProblemService dailyProblemService;
    private final AttemptService attemptService;


    public DailyController(DailyProblemService dailyProblemService,
                           AttemptService attemptService) {
        this.dailyProblemService = dailyProblemService;
        this.attemptService = attemptService;
    }


    @GetMapping("/api/daily")
    public DailyProblem getDaily() {
        DailyProblem p = dailyProblemService.getTodayProblem();
        p.answer = 0;   // temporary hide
        return p;
    }

    @PostMapping("/api/guess")
    public GuessResponse guess(@RequestBody GuessRequest request) {

        GuessResponse response = new GuessResponse();

        if (attemptService.isLocked(request.userId)) {
            response.locked = true;
            response.attemptsLeft = 0;
            return response;
        }

        var problem = dailyProblemService.getTodayProblem();
        int realAnswer = problem.answer;

        attemptService.incrementAttempts(request.userId);

        if (request.guess == realAnswer) {
            response.correct = true;
            response.feedback = "correct";
            attemptService.markSolved(request.userId);
        } else if (request.guess < realAnswer) {
            response.correct = false;
            response.feedback = "higher";
        } else {
            response.correct = false;
            response.feedback = "lower";
        }

        response.attemptsLeft = attemptService.attemptsLeft(request.userId);
        response.locked = attemptService.isLocked(request.userId);

        return response;
    }
}
