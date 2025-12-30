FROM openjdk:21-ea-10-jdk
ARG JAR_FILE=target/spring-app.jar
RUN  mkdir /group36
WORKDIR /group36
COPY ${JAR_FILE} /group36
ENTRYPOINT ["java", "-jar", "spring-app.jar"]