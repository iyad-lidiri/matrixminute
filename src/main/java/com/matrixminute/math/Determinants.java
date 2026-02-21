package com.matrixminute.math;

public class Determinants {

    public static int det2(int[][] m) {
        return m[0][0] * m[1][1] - m[0][1] * m[1][0];
    }

    public static int det2(int a, int b, int c, int d) {
        return a * d - b * c;
    }

    public static int det3(int[][] m) {
        int a = m[0][0], b = m[0][1], c = m[0][2];
        int d = m[1][0], e = m[1][1], f = m[1][2];
        int g = m[2][0], h = m[2][1], i = m[2][2];

        return a * (e * i - f * h)
                - b * (d * i - f * g)
                + c * (d * h - e * g);
    }

    public static int det3(
            int a, int b, int c,
            int d, int e, int f,
            int g, int h, int i
    ) {
        return det3(new int[][]{
                {a, b, c},
                {d, e, f},
                {g, h, i}
        });
    }
}