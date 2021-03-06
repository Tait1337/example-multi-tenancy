############################################################################
# Eclipse Temurin Open JRE 17 Build
#
# build from project root dir with: docker build -t example-multi-tenancy:1.0.0-SNAPSHOT .
# run with: docker run -p 8081:8081 -d example-multi-tenancy:1.0.0-SNAPSHOT
############################################################################
FROM eclipse-temurin:17-jre-alpine
LABEL maintainer="tait1337"

# App
WORKDIR /app
COPY ./target/example-multi-tenancy-1.0.0-SNAPSHOT.jar ./app.jar
EXPOSE $PORT
ENTRYPOINT ["java", "-jar", "app.jar"]
