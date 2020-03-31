pipeline {
  agent any
  stages {
    stage('clean') {
      steps {
        dir(path: 'android') {
          sh './gradlew clean'
        }

      }
    }

    stage('build debug') {
      steps {
        dir(path: 'android') {
          sh './gradlew assembledebug'
        }

      }
    }

  }
}