package com.matrixminute.controller;

import com.matrixminute.model.DailyProblem;
import com.matrixminute.service.DailyProblemService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import com.matrixminute.model.GuessRequest;
import com.matrixminute.model.GuessResponse;
import com.matrixminute.model.ProblemType;
import com.matrixminute.model.DailyProblemResponse;
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
    public DailyProblemResponse getDaily() {
        DailyProblem p = dailyProblemService.getTodayProblem();

        DailyProblemResponse res = new DailyProblemResponse();
        res.date = p.date;
        res.type = p.type;
        res.prompt = p.prompt;
        res.matrix2 = p.matrix2;
        res.matrix3 = p.matrix3;
        res.target = p.target;

        return res;
    }

    @PostMapping("/api/guess")
    public GuessResponse guess(@RequestBody GuessRequest request) {

        GuessResponse response = new GuessResponse();

        //  If locked → stop immediately
        if (attemptService.isLocked(request.userId)) {
            response.locked = true;
            response.attemptsLeft = 0;
            response.correct = false;
            response.feedback = "locked";
            return response;
        }

        //  If already solved → stop immediately
        if (attemptService.isSolved(request.userId)) {
            response.locked = true;
            response.correct = true;
            response.feedback = "already_solved";
            response.attemptsLeft = attemptService.attemptsLeft(request.userId);
            return response;
        }

        //  Now process guess normally
        var problem = dailyProblemService.getTodayProblem();
        int realAnswer = problem.answer;

        attemptService.incrementAttempts(request.userId);

        if (request.guess == realAnswer) {
            response.correct = true;
            response.feedback = "correct";
            attemptService.markSolved(request.userId);
        } else {
            response.correct = false;

            if (problem.type == ProblemType.DET_3X3
                    || problem.type == ProblemType.AREA_SCALE) {
                response.feedback = "incorrect";
            } else {
                response.feedback = request.guess < realAnswer ? "higher" : "lower";
            }
        }

        response.attemptsLeft = attemptService.attemptsLeft(request.userId);
        response.locked = attemptService.isLocked(request.userId);

        return response;
    }
}
