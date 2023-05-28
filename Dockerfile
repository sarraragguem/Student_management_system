FROM   eclipse-temurin:17-jdk-jammy as builder
RUN  apt  update
RUN  apt install nodejs npm -y
RUN npm install -g newman
RUN apt-get -y install maven
WORKDIR /opt/student-management-system
COPY ./junit-jupiter-api_5.9.1.jar ./
RUN jar xf ./junit-jupiter-api_5.9.1.jar
COPY mvnw pom.xml ./
RUN mvn dependency:go-offline
COPY ./src ./src
RUN mvn  clean install
 
FROM  eclipse-temurin:17-jdk-jammy
WORKDIR /opt/student-management-system
EXPOSE 8080
COPY --from=builder /opt/student-management-system/target/*.jar /opt/app/*.jar
ENTRYPOINT ["java", "-jar", "/opt/app/*.jar" ]