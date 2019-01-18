FROM openjdk:8-jdk-alpine
VOLUME /tmp
ARG JAR_FILE=target/demo-0.0.1-SNAPSHOT.jar
COPY ${JAR_FILE} app.jar
EXPOSE $PORT
CMD ["/usr/bin/java","-Djava.security.egd=file:/dev/./urandom", "-jar", "app.jar"]