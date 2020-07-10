web: java -Dserver.port=$PORT -Dspring.profiles.active=prod -jar target/*.jar
release: ./mvnw flyway:clean ./mvnw flyway:migrate
