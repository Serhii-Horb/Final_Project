# Use a base image with Java installed
#FROM openjdk:22

# Set the working directory in the container
#WORKDIR /app

# Copy the JAR file into the container at /app
#COPY target/*.jar app.jar



# Specify the command to run your application
#CMD ["java", "-jar", "app.jar"]
#ENTRYPOINT ["java", "-Dspring.profiles.active=prod", "-jar", "app.jar"]

# Use a base image with Java installed
FROM eclipse-temurin:22-jre-jammy

# Set the working directory in the container
WORKDIR /app

# Copy the JAR file into the container at /app
COPY target/*.jar /app/app.jar
RUN echo 'spring.datasource.url=jdbc:mysql://mysqldb:23306/shop_garden_and_home?createDatabaseIfNotExist=true' > /app/application.properties

# Specify the command to run your application
ENTRYPOINT ["java", "-jar", "app.jar"]
