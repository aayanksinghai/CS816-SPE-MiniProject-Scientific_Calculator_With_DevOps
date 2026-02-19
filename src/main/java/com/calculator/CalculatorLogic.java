package com.calculator;

/**
 * Calculator logic class containing all mathematical operations.
 */
public class CalculatorLogic {

    public double add(double a, double b) {
        return a + b;
    }

    public double subtract(double a, double b) {
        return a - b;
    }

    public double multiply(double a, double b) {
        return a * b;
    }

    public double divide(double a, double b) {
        if (b == 0) {
            throw new ArithmeticException("Division by zero is not allowed");
        }
        return a / b;
    }

    public double squareRoot(double a) {
        if (a < 0) {
            throw new ArithmeticException("Cannot calculate square root of negative number");
        }
        return Math.sqrt(a);
    }

    public double factorial(double a) {
        if (a < 0) {
            throw new ArithmeticException("Cannot calculate factorial of negative number");
        }
        if (a != Math.floor(a)) {
            throw new ArithmeticException("Cannot calculate factorial of non-integer");
        }
        if (a > 170) {
            throw new ArithmeticException("Number too large for factorial");
        }

        long n = (long) a;
        if (n == 0 || n == 1) {
            return 1;
        }

        double result = 1;
        for (long i = 2; i <= n; i++) {
            result *= i;
        }
        return result;
    }
}

