FROM openjdk:17-oracle
WORKDIR /students-auth
COPY /target/ResourceServer-0.0.1-SNAPSHOT.jar .
CMD ["java", "-jar" ,"ResourceServer-0.0.1-SNAPSHOT.jar"]


