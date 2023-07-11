FROM khipu/openjdk17-alpine
MAINTAINER <maltesh>
COPY ./target/*.jar ED_Api.jar
EXPOSE 9004
ENTRYPOINT [ "java","-jar","ED_Api.jar" ]