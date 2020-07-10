web: java -Dserver.port=$PORT -Dspring.profiles.active=prod -jar target/*.jar
flyway-clean: ./mvnw flyway:clean
release: ./mvnw flyway:migrate
