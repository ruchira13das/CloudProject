FROM java:8
ADD UserService-0.0.1-SNAPSHOT.jar UserService.jar
EXPOSE 8081
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","UserService.jar"]