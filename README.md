# Scientific Calculator with DevOps

A modern Java-based GUI scientific calculator application featuring a sleek UI design, comprehensive mathematical operations, and complete DevOps integration with CI/CD pipeline, Docker containerization, and Ansible deployment.

---

## 📋 Table of Contents

- [Features](#-features)
- [Project Structure](#-project-structure)
- [Prerequisites](#-prerequisites)
- [Installation & Running](#-installation--running)
- [Docker Usage](#-docker-usage)
- [CI/CD Pipeline](#-cicd-pipeline)
- [Mathematical Operations](#-mathematical-operations)
- [Error Handling](#-error-handling)
- [Testing](#-testing)
- [Technologies Used](#-technologies-used)
- [Contributing](#-contributing)
- [License](#-license)

---

## ✨ Features

### Calculator Features
- **Modern UI Design**: Sleek dark-themed interface with color-coded buttons
- **Basic Operations**: Addition (+), Subtraction (-), Multiplication (*), Division (/)
- **Scientific Operations**: 
  - Square Root (√)
  - Factorial (n!)
  - Natural Logarithm (ln)
  - Power Function (xʸ)
- **Negative Number Support**: The minus (-) button handles both subtraction and negative number input
- **Error Handling**:
  - Division by zero displays "Can't divide by 0" in red
  - Invalid inputs for scientific functions show appropriate error messages
  - Error state requires pressing "C" to clear and continue
- **Expression Display**: Shows the current expression being calculated
- **Backspace Support**: Remove last digit with ⌫ button

### DevOps Features
- **Docker Containerization**: Web-accessible calculator via NoVNC
- **Jenkins CI/CD Pipeline**: Automated build, test, and deployment
- **Email Notifications**: Automatic emails on build failure and recovery
- **Ansible Deployment**: Automated deployment to target servers
- **Docker Hub Integration**: Automatic image push to Docker Hub

---

## 📁 Project Structure

```
CS816-SPE-MiniProject-Scientific_Calculator_With_DevOps/
├── src/
│   ├── main/
│   │   └── java/
│   │       └── com/
│   │           └── calculator/
│   │               ├── Calculator.java          # Main GUI application
│   │               └── CalculatorLogic.java     # Core calculation logic
│   └── test/
│       └── java/
│           └── com/
│               └── calculator/
│                   └── CalculatorLogicTest.java # JUnit test cases
├── target/
│   ├── scientific-calculator-1.0-SNAPSHOT.jar   # Compiled JAR file
│   ├── classes/                                  # Compiled class files
│   ├── test-classes/                             # Compiled test classes
│   └── surefire-reports/                         # Test reports
├── Dockerfile                                    # Docker configuration
├── Jenkinsfile                                   # CI/CD pipeline definition
├── deploy.yml                                    # Ansible deployment playbook
├── inventory.ini                                 # Ansible inventory file
├── pom.xml                                       # Maven build configuration
├── README.md                                     # Project documentation
└── LICENSE                                       # License file
```

---

## 🔧 Prerequisites

### For Local Development
- **Java JDK 11** or higher
- **Apache Maven 3.6+**

### For Docker
- **Docker Engine** 20.10+
- **Web Browser** (for accessing the calculator via NoVNC)

### For CI/CD Pipeline
- **Jenkins** with required plugins:
  - Git Plugin
  - Docker Pipeline Plugin
  - Email Extension Plugin
  - Ansible Plugin
- **Docker Hub Account**
- **Ansible** 2.9+

---

## 🚀 Installation & Running

### Option 1: Using Maven (Local Development)

1. **Clone the repository:**
   ```bash
   git clone https://github.com/aayanksinghai/CS816-SPE-MiniProject-Scientific_Calculator_With_DevOps.git
   cd CS816-SPE-MiniProject-Scientific_Calculator_With_DevOps
   ```

2. **Compile the code:**
   ```bash
   mvn compile
   ```

3. **Run tests:**
   ```bash
   mvn test
   ```

4. **Package the application:**
   ```bash
   mvn package
   ```

5. **Run the application:**
   ```bash
   java -jar target/scientific-calculator-1.0-SNAPSHOT.jar
   ```

   Or using Maven exec plugin:
   ```bash
   mvn exec:java -Dexec.mainClass="com.calculator.Calculator"
   ```

6. **Clean and rebuild:**
   ```bash
   mvn clean install
   ```

### Option 2: Using Docker (Recommended)

See [Docker Usage](#-docker-usage) section below.

---

## 🐳 Docker Usage

The calculator is containerized with a web-accessible interface using NoVNC, allowing you to run a GUI application in a Docker container and access it via a web browser.

### Pull and Run from Docker Hub

**Pull the image:**
```bash
docker pull aayanksinghai/scientific-calculator:latest
```

**Run the container:**
```bash
docker run -d -p 8081:8080 --name scientific-calculator aayanksinghai/scientific-calculator:latest
```

**Access the calculator:**
Open your web browser and navigate to:
```
http://localhost:8081/vnc.html
```
Click "Connect" to view and interact with the calculator.

### Build and Run Locally

1. **Build the JAR file first:**
   ```bash
   mvn clean package -DskipTests
   ```

2. **Build the Docker image:**
   ```bash
   docker build -t scientific-calculator .
   ```

3. **Run the container:**
   ```bash
   docker run -d -p 8081:8080 --name scientific-calculator scientific-calculator
   ```

4. **Access the calculator:**
   ```
   http://localhost:8081/vnc.html
   ```

### Docker Commands Reference

| Command                                                                             | Description |
|-------------------------------------------------------------------------------------|-------------|
| `docker pull aayanksinghai/scientific-calculator:latest`                            | Pull the latest image |
| `docker run -d -p 8081:8080 --name calc aayanksinghai/scientific-calculator:latest` | Run container in background |
| `docker ps`                                                                         | List running containers |
| `docker stop scientific-calculator`                                                 | Stop the container |
| `docker start scientific-calculator`                                                | Start a stopped container |
| `docker rm scientific-calculator`                                                   | Remove the container |
| `docker logs scientific-calculator`                                                 | View container logs |
| `docker exec -it scientific-calculator bash`                                        | Access container shell |

### Docker Architecture

The Docker image includes:
- **OpenJDK 17**: Java runtime environment
- **Xvfb**: Virtual framebuffer for headless display
- **x11vnc**: VNC server to capture the virtual display
- **NoVNC**: Web-based VNC client
- **Openbox**: Lightweight window manager

This setup allows the Java Swing GUI to run in a containerized environment and be accessed through any web browser on port 8080.

---

## 🔄 CI/CD Pipeline

The project includes a complete Jenkins CI/CD pipeline defined in `Jenkinsfile`.

### Pipeline Stages

1. **Clone Git**: Fetches the latest code from GitHub
2. **Build the Maven Project**: Compiles the source code
3. **Test the Maven Project**: Runs JUnit tests
4. **Build Docker Image**: Creates a Docker image
5. **Push Docker Image to Docker Hub**: Pushes the image to Docker Hub
6. **Deploy with Ansible**: Deploys the application using Ansible

### Email Notifications

The pipeline includes automatic email notifications:
- **Build Failure**: Sends an email when the build fails
- **Build Fixed**: Sends an email when a previously failed build succeeds

### Jenkins Configuration Requirements

1. **Credentials Setup:**
   - `github_credentials`: GitHub access credentials
   - `DockerHubCred`: Docker Hub login credentials

2. **Email Configuration:**
   - Configure SMTP server in Jenkins (Manage Jenkins → Configure System)
   - For Gmail: Use App Password with `smtp.gmail.com:465` (SSL)

3. **Required Plugins:**
   - Git Plugin
   - Docker Pipeline Plugin
   - Email Extension Plugin
   - Ansible Plugin

### Triggering the Pipeline

The pipeline can be triggered by:
- GitHub webhooks (automatic on push)
- Manual build in Jenkins
- Scheduled builds (cron)

---

## 🔢 Mathematical Operations

### Basic Operations

| Operation | Symbol | Example |
|-----------|--------|---------|
| Addition | + | 5 + 3 = 8 |
| Subtraction | - | 10 - 4 = 6 |
| Multiplication | * | 7 * 6 = 42 |
| Division | / | 15 / 3 = 5 |

### Scientific Operations

| Operation | Symbol | Example | Notes |
|-----------|--------|---------|-------|
| Square Root | √ | √16 = 4 | Input must be non-negative |
| Factorial | n! | 5! = 120 | Input must be non-negative integer ≤ 170 |
| Natural Logarithm | ln | ln(e) = 1 | Input must be positive |
| Power | xʸ | 2^3 = 8 | Works with negative bases for integer exponents |

### Negative Number Support

- Enter a negative number by pressing `-` before the digits
- Example: To calculate `-5 + 3`, press: `-` → `5` → `+` → `3` → `=`
- The minus button intelligently handles both negative sign and subtraction

---

## ⚠️ Error Handling

The calculator handles various error conditions gracefully:

| Error Condition | Display Message | Action Required |
|-----------------|-----------------|-----------------|
| Division by zero | "Can't divide by 0" (red) | Press C to clear |
| Square root of negative | "Invalid input for √" (red) | Press C to clear |
| Factorial of negative | "Invalid input for n!" (red) | Press C to clear |
| Factorial of non-integer | "Invalid input for n!" (red) | Press C to clear |
| Ln of non-positive | "Invalid input for ln" (red) | Press C to clear |
| Invalid power operation | "Math Error" (red) | Press C to clear |

When an error occurs:
1. The error message is displayed in **red font**
2. All buttons except **C (Clear)** are disabled
3. Press **C** to clear the error and resume calculations

---

## 🧪 Testing

### Running Tests

```bash
mvn test
```

### Test Coverage

The test suite (`CalculatorLogicTest.java`) covers:

- ✅ Basic arithmetic operations (add, subtract, multiply, divide)
- ✅ Division by zero exception handling
- ✅ Square root operations (positive numbers and zero)
- ✅ Square root of negative numbers (exception)
- ✅ Factorial operations (0!, 1!, n!)
- ✅ Factorial edge cases (negative, non-integer, large numbers)
- ✅ Natural logarithm operations
- ✅ Natural logarithm of non-positive numbers (exception)
- ✅ Power function operations
- ✅ Negative number operations
- ✅ Decimal number operations

### Test Reports

After running tests, reports are available at:
- `target/surefire-reports/com.calculator.CalculatorLogicTest.txt`
- `target/surefire-reports/TEST-com.calculator.CalculatorLogicTest.xml`

---

## 🛠️ Technologies Used

| Category | Technology |
|----------|------------|
| **Language** | Java 11 |
| **GUI Framework** | Java Swing |
| **Build Tool** | Apache Maven |
| **Testing** | JUnit 4.13.2 |
| **Containerization** | Docker |
| **Web VNC** | NoVNC + x11vnc |
| **CI/CD** | Jenkins |
| **Deployment** | Ansible |
| **Version Control** | Git / GitHub |
| **Container Registry** | Docker Hub |

---

## 🤝 Contributing

1. Fork the repository
2. Create a feature branch (`git checkout -b feature/amazing-feature`)
3. Commit your changes (`git commit -m 'Add amazing feature'`)
4. Push to the branch (`git push origin feature/amazing-feature`)
5. Open a Pull Request

---

## 📄 License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

---

## 👤 Author

**Aayank Singhai**
- GitHub: [@aayanksinghai](https://github.com/aayanksinghai)
- Docker Hub: [aayanksinghai](https://hub.docker.com/u/aayanksinghai)

---

## 🙏 Acknowledgments

- Java Swing documentation
- Docker and NoVNC communities
- Jenkins CI/CD best practices
- Maven build system
