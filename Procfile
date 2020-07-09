web: java -Dserver.port=$PORT -Dspring.profiles.active=prod -jar target/*.jar
release: ./mvnw spring-boot:run -Drun.jvmArguments="-Dspring.profiles.active=local" flyway:migrate
