pipeline {
    agent any
    environment {
        DOCKER_IMAGE_NAME = 'scientific-calculator'
        GITHUB_REPO_URL = 'https://github.com/aayanksinghai/CS816-SPE-MiniProject-Scientific_Calculator_With_DevOps'
        DOCKER_HUB_USERNAME = 'aayanksinghai'
        EMAIL_RECIPIENT = 'aayanksinghai02@gmail.com'
    }

    stages {
        stage('Clone Git') {
            steps {
                script {
                    git branch: 'main',
                        credentialsId: 'github_credentials',
                        url: "${GITHUB_REPO_URL}"
                }
            }
        }

        stage('Build the Maven Project') {
            steps {
                sh 'mvn clean package -DskipTests'
            }
        }

        stage('Test the Maven project') {
            steps {
                sh 'mvn test'
            }
        }

        stage('Build Docker Image') {
            steps {
                script {
                    docker.build("${DOCKER_IMAGE_NAME}", '.')
                }
            }
        }

        stage('Push Docker Image to Docker Hub') {
            steps {
                script {
                    docker.withRegistry('', 'DockerHubCred') {
                        sh "docker tag ${DOCKER_IMAGE_NAME} ${DOCKER_HUB_USERNAME}/${DOCKER_IMAGE_NAME}:latest"
                        sh "docker push ${DOCKER_HUB_USERNAME}/${DOCKER_IMAGE_NAME}:latest"
                    }
                }
            }
        }

        stage('Deploy with Ansible') {
            steps {
                script {
                    ansiblePlaybook(
                        playbook: 'deploy.yml',
                        inventory: 'inventory'
                    )
                }
            }
        }
    }

    post {
        failure {
            script {
                echo "Attempting to send failure email to: ${EMAIL_RECIPIENT}"
                mail to: "${EMAIL_RECIPIENT}",
                     subject: "BUILD FAILED: ${env.JOB_NAME} - Build #${env.BUILD_NUMBER}",
                     body: """
Build Failed!

Project: ${env.JOB_NAME}
Build Number: ${env.BUILD_NUMBER}
Build URL: ${env.BUILD_URL}
Console Output: ${env.BUILD_URL}console

Please check the console output to investigate the failure.
                     """
                echo "Email sent successfully"
            }
        }
        fixed {
            script {
                echo "Attempting to send fixed email to: ${EMAIL_RECIPIENT}"
                mail to: "${EMAIL_RECIPIENT}",
                     subject: "BUILD FIXED: ${env.JOB_NAME} - Build #${env.BUILD_NUMBER}",
                     body: """
Build Fixed!

The build is back to normal.

Project: ${env.JOB_NAME}
Build Number: ${env.BUILD_NUMBER}
Build URL: ${env.BUILD_URL}

The previous build failure has been resolved.
                     """
                echo "Email sent successfully"
            }
        }
    }
}