FROM adoptopenjdk:13.0.1_9-jre-hotspot
ENV SPRING_PROFILES_ACTIVE=development
WORKDIR /app
COPY target/app.jar /app/app.jar
CMD ["java", "-XX:+UseNUMA", "-jar", "app.jar"]
