pipeline {
    agent any // Jenkins peut utiliser n'importe quel agent disponible
    
    // On dit à Jenkins d'utiliser l'outil Maven qu'on vient d'appeler M3
    tools {
        maven 'M3'
    }

    stages {
        // Notre première étape !
        stage('Build & Test') {
            steps {
                // On se place dans le bon dossier
                dir('backend') {
                    // On compile et on lance les tests JUnit
                    sh 'mvn clean verify'
                }
            }
        }
    }
}
