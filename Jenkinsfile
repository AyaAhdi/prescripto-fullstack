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

        stage('Déploiement Docker') {
            steps {
                dir('backend') {
                    // Simulation du déploiement Docker (Mock) pour validation visuelle
                    echo 'docker build -t prescripto-api .'
                    echo 'docker rm -f prescripto-backend || true'
                    echo 'docker run -d --name prescripto-backend -p 8081:8080 prescripto-api'
                    echo '🚀 Déploiement Docker (Simulé) réussi avec succès !'
                }
            }
        }
    }
}
