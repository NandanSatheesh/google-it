FROM openjdk:8-jdk-alpine
VOLUME /tmp
EXPOSE 8080
ADD target/google-it-0.0.1-SNAPSHOT.jar google-it-0.0.1-SNAPSHOT.jar
ENTRYPOINT ["java","-jar","/google-it-0.0.1-SNAPSHOT.jar"]