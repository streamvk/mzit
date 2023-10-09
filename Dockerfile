FROM openjdk:17
MAINTAINER vivekrathi74@gmail.com
ADD target/mzit.jar mzit.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "mzit.jar"]
