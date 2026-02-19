# Java Calculator Application

A simple Java-based GUI calculator application with basic arithmetic operations and JUnit test cases.

## Features

- **Basic Operations**: Addition, Subtraction, Multiplication, Division
- **GUI Interface**: User-friendly Swing-based interface
- **Error Handling**: Division by zero protection
- **Unit Testing**: Comprehensive JUnit test cases

## Project Structure

```
CS816-SPE-MiniProject-Scientific_Calculator_With_DevOps/
├── pom.xml                                    # Maven build configuration
├── src/
│   ├── main/
│   │   └── java/
│   │       └── com/
│   │           └── calculator/
│   │               ├── Calculator.java        # Main GUI application
│   │               └── CalculatorLogic.java   # Core calculation logic
│   └── test/
│       └── java/
│           └── com/
│               └── calculator/
│                   └── CalculatorLogicTest.java  # JUnit test cases
├── README.md
└── LICENSE
```

## How to Run

### Prerequisites
- Java JDK 11 or higher
- Apache Maven 3.6+

### Using Maven (Recommended)

1. **Compile the code:**
   ```bash
   mvn compile
   ```

2. **Run tests:**
   ```bash
   mvn test
   ```

3. **Package the application:**
   ```bash
   mvn package
   ```

4. **Run the application:**
   ```bash
   mvn exec:java -Dexec.mainClass="com.calculator.Calculator"
   ```
   
   Or after packaging:
   ```bash
   java -jar target/scientific-calculator-1.0-SNAPSHOT.jar
   ```

5. **Clean and rebuild:**
   ```bash
   mvn clean install
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
