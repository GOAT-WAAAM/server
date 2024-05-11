FROM amazoncorretto:17.0.7-alpine
COPY build/libs/server-0.0.1-SNAPSHOT.jar goat.jar

ENV TZ Asia/Seoul

ENTRYPOINT ["java", "-jar","-Dspring.profiles.active=prod", "goat.jar"]