pipeline {
    agent any
    environment {
        DOCKER_IMAGE_BE = "springboot-app:latest"
        CONTAINER_NAME_BE = "iop-ci-catalog"
        SPRING_PROFILE = "dev"
    }

    stages {
        stage('Git Checkout') {
            steps {
                git credentialsId: 'gitlab_idpw', 
                     url: 'http://10.100.0.4:8080/inner2025/iop-ci-catalog.git', 
                     branch: 'main'
            }
        }

        stage('Build SpringBoot App') {
            steps {
                script {
                    docker.image('gradle:7.4.2-jdk17').inside {
                       sh '''
                            gradle wrapper --gradle-version 7.4.2 
                            chmod +x ./gradlew  
                            ./gradlew clean build -x test
                          '''
                    }
                }
            }
        }

        stage('Build Docker Image') {
            steps {
                sh "docker build -t ${DOCKER_IMAGE_BE} ."
            }
        }

        stage('Run Docker Container') {
            steps {
                sh "docker stop ${CONTAINER_NAME_BE} || true"
                sh "docker rm ${CONTAINER_NAME_BE} || true"
                sh """
                    docker run -d \\
                    -p 9991:9991 \\
                    --name ${CONTAINER_NAME_BE} \\
                    -e SPRING_PROFILES_ACTIVE=${SPRING_PROFILE} \\
                    -e CONFIG_SERVER_HOST="http://10.100.0.4:8888" \\
                    -e EUREKA_CLIENT_SERVICEURL_DEFAULTZONE="http://admin:admin@10.100.0.4:8761/eureka" \\
                    ${DOCKER_IMAGE_BE}
                """
            }
        }
    }
}