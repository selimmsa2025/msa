FROM openjdk:17-jdk-alpine
VOLUME /tmp
ARG JAR_FILE=build/libs/*.jar
COPY ${JAR_FILE} app.jar
ENV HEALTH_MESSAGE="serviceOk"
ENV AUTH_TOKEN_SECRET="my-secret-key"
ENV CONFIG_SERVER_HOST="http://10.100.0.4:8888"
ENV EUREKA_CLIENT_SERVICEURL_DEFAULTZONE="http://admin:admin@10.100.0.4:8761/eureka"
ENTRYPOINT ["java","-jar","/app.jar"]
EXPOSE 9991
