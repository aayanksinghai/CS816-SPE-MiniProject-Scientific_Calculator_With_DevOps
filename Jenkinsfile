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
                emailext(
                    subject: "BUILD FAILED: ${env.JOB_NAME} - Build #${env.BUILD_NUMBER}",
                    body: '''
                        <h2 style="color: red;">Build Failed!</h2>
                        <p><strong>Project:</strong> ${ENV, var="JOB_NAME"}</p>
                        <p><strong>Build Number:</strong> ${ENV, var="BUILD_NUMBER"}</p>
                        <p><strong>Build URL:</strong> <a href="${ENV, var="BUILD_URL"}">${ENV, var="BUILD_URL"}</a></p>
                        <p><strong>Console Output:</strong> <a href="${ENV, var="BUILD_URL"}console">${ENV, var="BUILD_URL"}console</a></p>
                        <br/>
                        <p>Please check the console output to investigate the failure.</p>
                    ''',
                    to: "${EMAIL_RECIPIENT}",
                    mimeType: 'text/html',
                    recipientProviders: [[$class: 'DevelopersRecipientProvider'], [$class: 'RequesterRecipientProvider']]
                )
            }
        }
        fixed {
            script {
                emailext(
                    subject: "BUILD FIXED: ${env.JOB_NAME} - Build #${env.BUILD_NUMBER}",
                    body: '''
                        <h2 style="color: green;">Build Fixed!</h2>
                        <p>The build is back to normal.</p>
                        <p><strong>Project:</strong> ${ENV, var="JOB_NAME"}</p>
                        <p><strong>Build Number:</strong> ${ENV, var="BUILD_NUMBER"}</p>
                        <p><strong>Build URL:</strong> <a href="${ENV, var="BUILD_URL"}">${ENV, var="BUILD_URL"}</a></p>
                        <br/>
                        <p>The previous build failure has been resolved.</p>
                    ''',
                    to: "${EMAIL_RECIPIENT}",
                    mimeType: 'text/html',
                    recipientProviders: [[$class: 'DevelopersRecipientProvider'], [$class: 'RequesterRecipientProvider']]
                )
            }
        }
    }
}