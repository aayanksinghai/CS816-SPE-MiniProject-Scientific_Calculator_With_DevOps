pipeline {
    agent any
    environment {
        DOCKER_IMAGE_NAME = 'scientific-calculator'
        GITHUB_REPO_URL = 'https://github.com/aayanksinghai/CS816-SPE-MiniProject-Scientific_Calculator_With_DevOps'
        DOCKER_HUB_USERNAME = 'aayanksinghai'
    }
    tools{
        maven 'Maven-3'
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

        stage('Build') {
            steps {
                sh 'mvn clean package -DskipTests'
            }
        }

        stage('Test') {
            steps {
                sh 'mvn test'
            }
        }

        stage('Verify JAR Existence') {
            steps {
                sh 'ls -lh target/'   // Check if the JAR file is actually created
            }
        }

        stage('Build and Push Docker Image') {
            steps {
                script {
                    sh """
                    docker build create --use || true
                    docker build inspect --bootstrap

                    docker build build \
                      --platform linux/amd64,linux/arm64 \
                      -t ${DOCKER_HUB_USERNAME}/${DOCKER_IMAGE_NAME}:latest \
                      --push .
                    """
                }
            }
        }

        stage('Deploy with Ansible') {
            steps {
                sh 'ansible-playbook deploy.yml'
            }
        }
    }
}