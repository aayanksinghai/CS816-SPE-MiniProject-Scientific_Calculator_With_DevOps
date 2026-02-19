# Java Calculator Application

A simple Java-based GUI calculator application with basic arithmetic operations and JUnit test cases.

## Features

- **Basic Operations**: Addition, Subtraction, Multiplication, Division
- **GUI Interface**: User-friendly Swing-based interface
- **Error Handling**: Division by zero protection
- **Unit Testing**: Comprehensive JUnit test cases

## Files

- `Calculator.java` - Main GUI application
- `CalculatorLogic.java` - Core calculation logic
- `CalculatorLogicTest.java` - JUnit test cases
- `build.xml` - Ant build file for compilation and testing

## How to Run

### Prerequisites
- Java JDK 8 or higher
- Apache Ant (optional, for using build.xml)

### Method 1: Using Ant (Recommended)

1. **Compile the code:**
   ```bash
   ant compile
   ```

2. **Run tests:**
   ```bash
   ant test
   ```

3. **Run the application:**
   ```bash
   ant run
   ```

4. **Create JAR file:**
   ```bash
   ant jar
   ```

### Method 2: Manual Compilation

1. **Compile the files:**
   ```bash
   javac *.java
   ```

2. **Run the calculator:**
   ```bash
   java Calculator
   ```

3. **Run tests (requires JUnit):**
   ```bash
   java -cp .:junit-4.13.2.jar:hamcrest-core-1.3.jar org.junit.runner.JUnitCore CalculatorLogicTest
   ```

## Usage

1. Launch the calculator application
2. Click on number buttons to input numbers
3. Click on operation buttons (+, -, *, /) to select operation
4. Click "=" to get the result
5. Click "C" to clear the display

## Test Cases

The application includes comprehensive test cases covering:
- Basic arithmetic operations
- Edge cases (zero operations)
- Division by zero error handling
- Negative number operations
- Decimal number operations
