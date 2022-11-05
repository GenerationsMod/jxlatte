package com.thebombzen.jxlatte.util;

import java.util.Arrays;

public final class MathHelper {

    private MathHelper() {

    }

    public static int unpackSigned(int value) {
        // prevent overflow and extra casework
        long v = (long)value & 0xFF_FF_FF_FFL;
        return (int)((v & 1L) == 0 ? v / 2L : -(v + 1L) / 2L);
    }

    public static int round(double d) {
        return (int)(d + 0.5D);
    }

    /**
     * @return ceil(log2(x + 1))
     */
    public static int ceilLog1p(long x) {
        return 64 - Long.numberOfLeadingZeros(x);
    }

    /**
     * @return ceil(log2(x + 1))
     */
    public static int ceilLog1p(int x) {
        return 32 - Integer.numberOfLeadingZeros(x);
    }

    public static int ceilDiv(int numerator, int denominator) {
        return ((numerator - 1) / denominator) + 1;
    }

    public static int floorLog1p(long x) {
        int c = ceilLog1p(x);
        // if x + 1 is not a power of 2
        if (((x + 1) & x) != 0)
            return c - 1;
        return c;
    }

    public static int floorLog1p(int x) {
        int c = ceilLog1p(x);
        // if x + 1 is not a power of 2
        if (((x + 1) & x) != 0)
            return c - 1;
        return c;
    }

    public static int min(int... a) {
        return Arrays.stream(a).reduce(Integer.MAX_VALUE, Math::min);
    }

    public static int max(int... a) {
        return Arrays.stream(a).reduce(Integer.MIN_VALUE, Math::max);
    }

    public static double min(double... a) {
        return Arrays.stream(a).reduce(Double.MAX_VALUE, Math::min);
    }

    public static double max(double... a) {
        return Arrays.stream(a).reduce(Double.MIN_VALUE, Math::max);
    }

    public static double signedPow(double base, double exponent) {
        return Math.signum(base) * Math.pow(Math.abs(base), exponent);
    }

    public static int clamp(int v, int... a) {
        int lower = min(a);
        int upper = max(a);
        return Math.min(Math.max(v, lower), upper);
    }

    public static double clamp(double v, double... a) {
        double lower = min(a);
        double upper = max(a);
        return Math.min(Math.max(v, lower), upper);
    }

    public static double[] matrixMutliply(double[][] matrix, double[] vector) {
        if (matrix.length != vector.length || vector.length == 0)
            throw new IllegalArgumentException();
        double[] total = new double[matrix[0].length];
        for (int j = 0; j < total.length; j++) {
            for (int i = 0; i < vector.length; i++) {
                total[j] += matrix[i][j] * vector[i];
            }
        }
        return total;
    }

    public static double[][] matrixMutliply(double[][] left, double[][] right) {
        if (left.length == 0 || left[0].length == 0 || right.length == 0 || left.length != right[0].length)
            throw new IllegalArgumentException();
        double[][] result = new double[right.length][];
        for (int x = 0; x < right.length; x++)
            result[x] = matrixMutliply(left, right[x]);
        return result;
    }

    // expensive! do not use on the fly
    public static double[][] invertMatrix3x3(double[][] matrix) {
        if (matrix.length != 3)
            throw new IllegalArgumentException();
        double det = 0D;
        for (int c = 0; c < 3; c++) {
            if (matrix[c].length != 3)
                throw new IllegalArgumentException();
            int c1 = (c + 1) % 3;
            int c2 = (c + 2) % 3;
            det += matrix[c][0] * matrix[c1][1] * matrix[c1][2] - matrix[c][0] * matrix[c1][2] * matrix[c2][1];
        }
        if (det == 0D)
            return null;
        double invDet = 1D / det;
        double[][] inverse = new double[3][3];
        for (int x = 0; x < 3; x++) {
            for (int y = 0; y < 3; y++) {
                int x1 = (x + 1) % 3;
                int x2 = (x + 2) % 3;
                int y1 = (y + 1) % 3;
                int y2 = (y + 2) % 3;
                // because we're going cyclicly here we don't need to multiply by (-1)^(x + y)
                inverse[y][x] = (matrix[x1][y1] * matrix[x2][y2] - matrix[x2][y1] * matrix[x1][y2]) * invDet;
            }
        }
        return inverse;
    }
}
