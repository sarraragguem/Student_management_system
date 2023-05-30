FROM   eclipse-temurin:17-jdk-jammy as builder
RUN  apt  update
RUN  apt install nodejs npm -y
RUN npm install -g newman
RUN apt-get -y install maven
WORKDIR /opt/student-management-system
COPY ./ ./
RUN mvn dependency:resolve
RUN mvn clean
RUN mvn compile test
RUN mvn verify sonar:sonar
RUN mvn package 
#RUN newman run /opt/student-management-system/src/API_tests/PPP.postman_collection.json           




FROM  eclipse-temurin:17-jdk-jammy
WORKDIR /opt/student-management-system
EXPOSE 8080
COPY --from=builder /opt/student-management-system/target/*.jar /opt/app/*.jar
COPY ./entrypoint.sh ./
RUN chmod 777 ./entrypoint.sh
ENTRYPOINT ["./entrypoint.sh"]
