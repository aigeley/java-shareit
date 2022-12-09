FROM amazoncorretto:11-alpine3.16-jdk
COPY ["target/*.jar", "shareit-server.jar"]
ENTRYPOINT ["java","-jar","/shareit-server.jar"]
