pipeline {
  agent any
  stages {
    stage('clean') {
      steps {
        sh './gradlew clean'
      }
    }

    stage('build debug') {
      steps {
        sh './gradlew assembledebug'
      }
    }

  }
}