FROM   eclipse-temurin:17-jdk-jammy as builder
RUN  apt  update
RUN  apt install nodejs npm -y
RUN npm install -g newman
RUN apt-get -y install maven
WORKDIR /opt/student-management-system
COPY ./ ./
RUN mvn dependency:resolve
RUN mvn package 
CMD ["mvn", "spring-boot:run"]


FROM  eclipse-temurin:17-jdk-jammy
WORKDIR /opt/student-management-system
EXPOSE 8080
COPY --from=builder /opt/student-management-system/target/*.jar /opt/app/*.jar
ENTRYPOINT ["java", "-jar", "/opt/app/*.jar" ]