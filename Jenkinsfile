pipeline {
    agent any
    
    tools {
        maven 'M3'
    }

    stages {
        stage('Build & Test') {
            steps {
                dir('backend') {
                    sh 'mvn clean verify -DskipTests'
                }
            }
        }
        
        stage('Analyse SonarQube') {
            environment {
                SONAR_TOKEN = credentials('sonar-token')
            }
            steps {
                dir('backend') {
                    sh '''
                        mvn org.sonarsource.scanner.maven:sonar-maven-plugin:sonar \
                        -Dsonar.projectKey=Prescripto-Backend \
                        -Dsonar.projectName='Prescripto-Backend' \
                        -Dsonar.host.url=http://host.docker.internal:9000 \
                        -Dsonar.token=$SONAR_TOKEN
                    '''
                }
            }
        }
    }
}
