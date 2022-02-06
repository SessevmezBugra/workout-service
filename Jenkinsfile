pipeline {

    agent any

    stages {

        stage('training-service') {
            steps {
                build job: 'training-service-pipeline', propagate: true, wait: true
            }
        }

        stage('deploy') {
            steps {
                sh 'docker-compose up -d'
            }
        }

    }
}
