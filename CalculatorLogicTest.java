import org.junit.Test;
import static org.junit.Assert.*;

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
}
