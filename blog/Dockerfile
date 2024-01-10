FROM ubuntu:22.04 AS builder
RUN apt-get update && apt-get install -y openjdk-17-jdk

WORKDIR /app
RUN apt-get update && apt-get install -y git && apt-get install -y maven && git clone https://github.com/HusseinOkasha/blog-server.git

WORKDIR /app/blog-server/blog

RUN mvn clean package
CMD ["java", "-jar","target/demo-0.0.1-SNAPSHOT.jar"]

VOLUME /tmp
EXPOSE 8080

