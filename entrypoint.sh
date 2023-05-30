#!/bin/bash

# Wait for MySQL to become available on port 3306
while ! test -z "$(echo > /dev/tcp/mysql_db/3306)"; do
  echo "Waiting for MySQL to become available..."
  sleep 1
done

# Start your Spring Boot application
java -jar /opt/app/*.jar
