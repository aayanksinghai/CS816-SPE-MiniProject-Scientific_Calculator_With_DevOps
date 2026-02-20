package com.calculator;

import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Unit tests for CalculatorLogic class.
 */
public class CalculatorLogicTest {

    private CalculatorLogic calculator = new CalculatorLogic();

    @Test
    public void testAddition() {
        assertEquals(5.0, calculator.add(2.0, 3.0), 0.001);
        assertEquals(0.0, calculator.add(-2.0, 2.0), 0.001);
        assertEquals(-5.0, calculator.add(-2.0, -3.0), 0.001);
        assertEquals(10.5, calculator.add(7.2, 3.3), 0.001);
    }

    @Test
    public void testSubtraction() {
        assertEquals(2.0, calculator.subtract(5.0, 3.0), 0.001);
        assertEquals(-4.0, calculator.subtract(2.0, 6.0), 0.001);
        assertEquals(0.0, calculator.subtract(5.0, 5.0), 0.001);
        assertEquals(3.9, calculator.subtract(7.2, 3.3), 0.001);
    }

    @Test
    public void testMultiplication() {
        assertEquals(6.0, calculator.multiply(2.0, 3.0), 0.001);
        assertEquals(-6.0, calculator.multiply(-2.0, 3.0), 0.001);
        assertEquals(6.0, calculator.multiply(-2.0, -3.0), 0.001);
        assertEquals(0.0, calculator.multiply(5.0, 0.0), 0.001);
        assertEquals(23.76, calculator.multiply(7.2, 3.3), 0.001);
    }

    @Test
    public void testDivision() {
        assertEquals(2.0, calculator.divide(6.0, 3.0), 0.001);
        assertEquals(-2.0, calculator.divide(-6.0, 3.0), 0.001);
        assertEquals(2.0, calculator.divide(-6.0, -3.0), 0.001);
        assertEquals(0.5, calculator.divide(1.0, 2.0), 0.001);
        assertEquals(2.1818, calculator.divide(7.2, 3.3), 0.001);
    }

    @Test(expected = ArithmeticException.class)
    public void testDivisionByZero() {
        calculator.divide(5.0, 0.0);
    }

    @Test
    public void testZeroOperations() {
        assertEquals(5.0, calculator.add(5.0, 0.0), 0.001);
        assertEquals(5.0, calculator.subtract(5.0, 0.0), 0.001);
        assertEquals(0.0, calculator.multiply(5.0, 0.0), 0.001);
        assertEquals(0.0, calculator.divide(0.0, 5.0), 0.001);
    }

    @Test
    public void testSquareRoot() {
        assertEquals(2.0, calculator.squareRoot(4.0), 0.001);
        assertEquals(3.0, calculator.squareRoot(9.0), 0.001);
        assertEquals(5.0, calculator.squareRoot(25.0), 0.001);
        assertEquals(0.0, calculator.squareRoot(0.0), 0.001);
        assertEquals(1.4142, calculator.squareRoot(2.0), 0.001);
    }

    @Test(expected = ArithmeticException.class)
    public void testSquareRootNegative() {
        calculator.squareRoot(-4.0);
    }

    @Test
    public void testFactorial() {
        assertEquals(1.0, calculator.factorial(0.0), 0.001);
        assertEquals(1.0, calculator.factorial(1.0), 0.001);
        assertEquals(2.0, calculator.factorial(2.0), 0.001);
        assertEquals(6.0, calculator.factorial(3.0), 0.001);
        assertEquals(24.0, calculator.factorial(4.0), 0.001);
        assertEquals(120.0, calculator.factorial(5.0), 0.001);
        assertEquals(3628800.0, calculator.factorial(10.0), 0.001);
    }

    @Test(expected = ArithmeticException.class)
    public void testFactorialNegative() {
        calculator.factorial(-5.0);
    }

    @Test(expected = ArithmeticException.class)
    public void testFactorialNonInteger() {
        calculator.factorial(3.5);
    }

    @Test
    public void testNaturalLog() {
        assertEquals(0.0, calculator.naturalLog(1.0), 0.001);
        assertEquals(1.0, calculator.naturalLog(Math.E), 0.001);
        assertEquals(2.302585, calculator.naturalLog(10.0), 0.001);
        assertEquals(0.693147, calculator.naturalLog(2.0), 0.001);
    }

    @Test(expected = ArithmeticException.class)
    public void testNaturalLogZero() {
        calculator.naturalLog(0.0);
    }

    @Test(expected = ArithmeticException.class)
    public void testNaturalLogNegative() {
        calculator.naturalLog(-5.0);
    }

    @Test
    public void testPower() {
        assertEquals(8.0, calculator.power(2.0, 3.0), 0.001);
        assertEquals(1.0, calculator.power(5.0, 0.0), 0.001);
        assertEquals(25.0, calculator.power(5.0, 2.0), 0.001);
        assertEquals(0.25, calculator.power(2.0, -2.0), 0.001);
        assertEquals(2.0, calculator.power(4.0, 0.5), 0.001);
        assertEquals(-8.0, calculator.power(-2.0, 3.0), 0.001);
        assertEquals(4.0, calculator.power(-2.0, 2.0), 0.001);
    }
}

