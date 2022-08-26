FROM maven:3-jdk-11
COPY . /app
WORKDIR /app
RUN ls -l
RUN mvn clean flyway:migrate -Dflyway.configFiles=conf/flywayConfig.conf
RUN mvn package
EXPOSE 8080
ENTRYPOINT ["mvn","spring-boot:run"]