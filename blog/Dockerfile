FROM openjdk:17
VOLUME /tmp
EXPOSE 8080
ADD target/demo-0.0.1-SNAPSHOT.jar blog.jar
ENTRYPOINT ["java", "-jar", "/blog.jar"]
