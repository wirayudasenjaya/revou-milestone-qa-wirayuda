pipeline {
    agent any
    
    tools {
        maven 'maven'
        jdk 'JDK17'
    }
    
    environment {
        API = 'rest-assured'
        WEB = 'selenium'
    }

    
    stages {
        stage('Checkout') {
            steps {
                checkout scm
            }
        }

        stage('Prepare Directories') {
            steps {
                dir(API) {
                    sh 'mkdir -p target/cucumber-reports'
                }
                dir(WEB) {
                    sh 'mkdir -p target/cucumber-reports'
                }
            }
        }
        
        stage('Build API Testing') {
            steps {
                dir(API) {
                    sh 'mvn clean install -DskipTests'
                }
            }
        }
        
        stage('API Testing') {
            steps {
                dir(API) {
                    sh 'mvn test | true'
                }
            }
        }

        stage('Generate API Report') {
            steps{
                cucumber buildStatus: 'UNSTABLE',
                    reportTitle: 'Rest Assured Test report',
                    fileIncludePattern: 'rest-assured/**/*.json',
                    trendsLimit: 10,
                    classifications: [
                        [
                            'key': 'Browser',
                            'value': 'Chrome'
                        ]
                    ]
            }
        }
        
        stage('Build Web Testing') {
            steps {
                dir(WEB) {
                    sh 'mvn clean install -DskipTests'
                }
            }
        }
        
        stage('Web Testing') {
            steps {
                dir(WEB) {
                    sh 'mvn test || true'
                }
            }
        }

        stage('Generate Web Testing Report') {
            steps {
                cucumber buildStatus: 'UNSTABLE',
                    reportTitle: 'Selenium Test Report',
                    fileIncludePattern: 'selenium/**/*.json',
                    trendsLimit: 10,
                    classifications: [
                        [
                            'key': 'Browser',
                            'value': 'Chrome'
                        ]
                    ]
            }
        }

        stage('Generate Reports') {
            steps {
                parallel(
                    "API Testing Report": {
                        dir(API) {
                            sh 'mvn site'
                        }
                    },
                    "Web Testing Report Report": {
                        dir(WEB) {
                            sh 'mvn site'
                        }
                    }
                )
            }
        }
    }
    
    post {
        success {
            echo 'Pipeline succeeded.'
        }
        failure {
            echo 'Pipeline failed.'
        }
    }
}