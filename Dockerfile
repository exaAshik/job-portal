# FROM maven:3.8.5-openjdk-17

# WORKDIR /bezkoder-app
# COPY . .
# RUN mvn clean install

# CMD mvn spring-boot:run

FROM maven:3.8.5-openjdk-17

# Set the working directory inside the container
WORKDIR /bezkoder-app

# Copy all files from the local directory to the container's working directory
COPY . .

# Build the project using Maven
RUN mvn clean install

# Set the command to run the Spring Boot application
CMD ["mvn", "spring-boot:run"]

