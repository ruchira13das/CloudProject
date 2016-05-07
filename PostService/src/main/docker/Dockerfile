FROM java:8
ADD PostService-0.0.1-SNAPSHOT.jar PostService.jar
EXPOSE 8082
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","PostService.jar"]