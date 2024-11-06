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
    }
    
    post {
        always {
            cucumber(
                fileIncludePattern: '**/cucumber.json',
                jsonReportDirectory: "${API}/target/reports",
                reportTitle: 'API Test Report',
                buildStatus: 'UNSTABLE',
                classifications: [
                    [   
                        'key': 'Environment',
                        'value': 'API Testing'
                    ]
                ]
            )
            
            cucumber(
                fileIncludePattern: '**/cucumber.json',
                jsonReportDirectory: "${WEB}/target/reports",
                reportTitle: 'Web Test Report',
                buildStatus: 'UNSTABLE',
                classifications: [
                    [   
                        'key': 'Environment',
                        'value': 'Web Testing'
                    ]
                ]
            )
        }
        success {
            echo 'Pipeline succeeded.'
        }
        failure {
            echo 'Pipeline failed.'
        }
    }
}