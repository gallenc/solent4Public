# Example Project

## Instructions

build the project using maven
```
mvn clean install
```
### Getting Started

once the project has built, move to the web folder and use the command
```
cd web
mvn jetty:run
```
You should be able to browse to the app at http:localhost:8680

user: admin 
password: admin

This project contains two examples so there are two urls on the home page - one for the motor cycle ride planner and the other to access a simple enty retreival jpa based app using ReST.
