FROM openjdk:17-alpine
EXPOSE 8081
ADD target/reading-tom-book-service-docker.jar reading-tom-book-service-docker.jar
ENTRYPOINT [ "java", "-jar", "/reading-tom-book-service-docker.jar"]
