# Task manager API
Api description: Swagger UI - http://localhost:8080/swagger-ui/index.html

## About app
1) The RESTful-API system that provide functionality for the creation, editing, deletion and viewing of tasks. Each task must contain a title, description, status (for example, "pending", "in progress", "completed") and priority (for example, "high", "medium", "low"), as well as the author of the task and the performer.

2) The service support authentication and 
authorization of users by email and password.

3) API access authenticated using a JWT token.

4) Users can manage their tasks: create new ones, edit existing ones, view and delete, change the status and assign task performers.

5) Users can view other users tasks, and task performers can change the status of their tasks.

6) You can leave comments on the tasks.

7) The API allow you to receive tasks from a specific author or performer, as well as all comments on them. Supports filtering and pagination of the output

## Technologies
Java 17+, MySQL, Spring framework (Security, Web), Junit, Docker, Swagger.

## How to deploy
There are two ways you can deploy this application on your machine. First of all, you need to clone repository.

1)  Docker-way. Run 'mvn clean install' or 'mvn install -DskipTests' command in terminal, then run docker-compose file to create container with MySQL database and API. Then manage app in docker enviroment.

2) Native-way. To run application on your personal machine it should satisfy some criterias. Installed: JVM 17, MySQL, Maven. Run 'mvn clean install' if you have docker installed and 'mvn install -DskipTests' otherwise. Then go to ~/target and run java -jar 'task-api-0.0.1-SNAPSHOT.jar'.
