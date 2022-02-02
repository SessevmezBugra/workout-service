pipeline {

    agent any

    stages {

        stage('workout-service') {
            steps {
                build job: 'workout-service-pipeline', propagate: true, wait: true
            }
        }

        stage('deploy') {
            steps {
                sh 'docker-compose up -d'
            }
        }

    }
}
