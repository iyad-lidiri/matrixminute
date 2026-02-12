package com.matrixminute.math;

public class Determinants {

    public static int det2(int a, int b, int c, int d) {
        return a * d - b * c;
    }

    public static int det3(int a, int b, int c,
                           int d, int e, int f,
                           int g, int h, int i) {
        return a * (e * i - f * h)
                - b * (d * i - f * g)
                + c * (d * h - e * g);
    }
}
