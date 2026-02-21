package com.matrixminute.service;

import com.matrixminute.math.Determinants;
import com.matrixminute.model.DailyProblem;
import com.matrixminute.model.ProblemType;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Random;

@Service
public class DailyProblemService {

    private static final LocalDate START_DATE = LocalDate.of(2026, 1, 1);
    private DailyProblem cachedProblem;
    private LocalDate cachedDate;

    public DailyProblem getTodayProblem() {
        LocalDate today = LocalDate.now();

        if (cachedProblem != null && today.equals(cachedDate)) {
            return cachedProblem;
        }

        long dayIndex = ChronoUnit.DAYS.between(START_DATE, today);
        int typeIndex = (int) (Math.floorMod(dayIndex, 3));

        Random rng = new Random(today.toEpochDay());

        DailyProblem generated;
        if (typeIndex == 0) generated = makeAreaScaleProblem(today, rng);
        else if (typeIndex == 1) generated = makeSolveXProblem(today, rng);
        else generated = makeDet3Problem(today, rng);

        cachedProblem = generated;
        cachedDate = today;
        return cachedProblem;
    }

    private DailyProblem makeAreaScaleProblem(LocalDate date, Random rng) {
        int a = randNonZeroSmall(rng);
        int b = randSmall(rng);
        int c = randSmall(rng);
        int d = randNonZeroSmall(rng);

        int det = Determinants.det2(a, b, c, d);
        int ans = Math.abs(det);

        // avoid boring 0 answer
        if (ans == 0) return makeAreaScaleProblem(date, rng);

        DailyProblem p = new DailyProblem();
        p.date = date.toString();
        p.type = ProblemType.AREA_SCALE;
        p.prompt = "Matrix A transforms the unit square. What is the area of the transformed shape? (Answer = |det(A)|)";
        p.matrix2 = new int[][]{{a, b}, {c, d}};
        p.matrix3 = null;
        p.target = null;
        p.answer = ans;
        return p;
    }

    private DailyProblem makeSolveXProblem(LocalDate date, Random rng) {
        // Matrix: [[x, b], [c, d]] and target = |x*d - b*c|
        int x = 7 + rng.nextInt(9); // 7..15
        int b = randSmall(rng);
        int c = randSmall(rng);
        int d = randNonZeroSmall(rng); // avoid 0 so equation is real

        int target = Math.abs(x * d - b * c);
        if (target == 0) return makeSolveXProblem(date, rng);

        DailyProblem p = new DailyProblem();
        p.date = date.toString();
        p.type = ProblemType.SOLVE_X;
        p.prompt = "A = [[x, " + b + "], [" + c + ", " + d + "]]. The area scale factor is " + target +
                ". If x is a positive integer greater than 6, find x.";
        p.matrix2 = new int[][]{{0, b}, {c, d}}; // 0 is placeholder for x
        p.matrix3 = null;
        p.target = target;
        p.answer = x;
        return p;
    }

    private DailyProblem makeDet3Problem(LocalDate date, Random rng) {
        while (true) {
            int[][] m = new int[3][3];
            for (int r = 0; r < 3; r++) {
                for (int c = 0; c < 3; c++) {
                    m[r][c] = randSmall(rng);
                }
            }

            int det = Determinants.det3(m);

            // keep it reasonable
            if (det == 0) continue;
            if (Math.abs(det) > 60) continue;

            DailyProblem p = new DailyProblem();
            p.date = date.toString();
            p.type = ProblemType.DET_3X3;
            p.prompt = "Compute the determinant of the 3×3 matrix.";
            p.matrix2 = null;
            p.matrix3 = m;
            p.target = null;
            p.answer = det;
            System.out.println("MATRIX = " + java.util.Arrays.deepToString(m));
            System.out.println("DET = " + det);
            return p;
        }
    }

    private int randSmall(Random rng) {
        return rng.nextInt(7) - 3; // -3..3
    }

    private int randNonZeroSmall(Random rng) {
        int v;
        do { v = randSmall(rng); } while (v == 0);
        return v;
    }
}
