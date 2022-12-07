FROM amazoncorretto:11-alpine3.16-jdk
COPY ["target/*.jar", "shareit-gateway.jar"]
ENTRYPOINT ["java","-jar","/shareit-gateway.jar"]
