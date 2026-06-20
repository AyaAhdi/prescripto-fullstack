pipeline {
    agent any

    stages {
        stage('Checkout Code') {
            steps {
                echo 'Configuration de Git et récupération du projet...'
                script {
                    // Cette commande magique dit à Jenkins d'accepter GitHub sans bloquer
                    env.GIT_SSH_COMMAND = "ssh -o UserKnownHostsFile=/dev/null -o StrictHostKeyChecking=no"
                }
                checkout scmGit(
                    branches: [[name: '*/main']], 
                    userRemoteConfigs: [[
                        url: 'git@github.com:AyaAhdi/prescripto-fullstack.git', 
                        credentialsId: 'github-ssh-key'
                    ]]
                )
            }
        }
    }
}