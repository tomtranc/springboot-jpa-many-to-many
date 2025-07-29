FROM alpine/java:21-jre
WORKDIR /app
COPY target/*.war /app/app.war
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "/app/app.war", "--spring.profiles.active=prod"]