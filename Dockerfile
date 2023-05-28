FROM eclipse-temurin:17-jdk-jammy as builder
RUN  apt  update
RUN  apt install nodejs npm -y
RUN npm install -g newman
WORKDIR /opt/student-management-system
COPY .mvn/ .mvn
COPY mvnw pom.xml ./
RUN ./mvnw dependency:go-offline
COPY ./src ./src
RUN ./mvnw clean install
 
FROM eclipse-temurin:17-jre-jammy
WORKDIR /opt/app
EXPOSE 8080
COPY --from=builder /opt/student-management-system/target/*.jar /opt/app/*.jar
ENTRYPOINT ["java", "-jar", "/opt/app/*.jar" ]