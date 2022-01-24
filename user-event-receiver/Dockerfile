FROM openjdk:11 as rabbitmq
EXPOSE 8080
COPY target/user-event-receiver-0.0.1-SNAPSHOT.jar app.jar
ENTRYPOINT ["java","-jar","app.jar"]