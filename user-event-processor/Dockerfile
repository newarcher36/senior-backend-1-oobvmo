FROM openjdk:11 as rabbitmq
EXPOSE 8081
COPY target/user-event-processor-0.0.1-SNAPSHOT.jar app.jar
ENTRYPOINT ["java","-jar","app.jar"]