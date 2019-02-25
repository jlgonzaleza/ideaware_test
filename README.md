# ideaware_test

## Run the application
you need to install in the machine java 8 docker and docker-compose.
after installed the tools, clone this project, to run the data base:
```
docker-compose up db
```
to run the application:
```
./gradlew bootRun
```
to verify if the app is running go to http://localhost:8080/api/
you should see an Whitelabel Error Page

## API documentation 
This API have swagger for documentation.
Local api can be found on http://localhost:8080/api/swagger-ui.html


## ReentrantLockSolution

on the class ReentrantLockSolution is the example for Reentrant Lock