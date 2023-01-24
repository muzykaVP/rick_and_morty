﻿# 👴Rick and Morty API👦

The web application that downloads data from a third-party service to the internal database.
Implemented API requests fetch data from a local database.
## ✨Features✨
* Randomly generates a wiki about one character in the universe the animated series Rick & Morty.
* Search and returns list of all characters whose name contains the search string.
* Auto synchronization with Rick & Morty api with cron job.

## 🧬Project structure🧬
### N-tier architecture
Project built by using N-tier architecture divides application
into logical layers:
* **Frontend Framework**: Frontend framework sends a request to the server it reaches our controller.
* **Controller**: Separates the UI from the backend logic. The controller has a service component autowired which can help him return the request.
* **Service**: Interacts with data through an autowired repository
and contains the business logic that move and process data between the data and presentation layers.
* **Repository**: Interacts with the chosen way to persist data. Another name for this layer is the data access layer.
* **Data tier**: data store/retrieve layer.

### DB structure

## 👩‍💻Technologies👩‍💻
* JDK 17
* Apache Maven
* PostgreSQL
* JPA, Hibernate
* Spring Boot
* Swagger
* Docker

## 🛠Setup guide🛠

First of all fork this repository. Clone it and create new project from version control in IntelliJ IDEA.
####
To run this project locally on your computer, you need to make sure you have the following components installed:
* JDK 17 ([installation guide](https://docs.oracle.com/en/java/javase/17/install/overview-jdk-installation.html#GUID-8677A77F-231A-40F7-98B9-1FD0B48C346A))
* IntelliJ IDEA Ultimate ([download here](https://www.jetbrains.com/idea/download/#section=windows))
* Apache Maven ([download here](https://maven.apache.org/download.cgi), [installation guide](https://maven.apache.org/install.html))
* PostgreSQL ([installation guide](https://www.guru99.com/download-install-postgresql.html))
* Docker ([installation guide](https://docs.docker.com/desktop/install/windows-install/))

### Application configuration
#### Build project
To build this project run following command in terminal: 
```
mvn clean package
```
#### Run application in Docker
To run this application run following command in terminal:
```
docker-compose up
```
#### You will be connected to default access port: `http://localhost:6868`


## 🚀You are ready to go!🚀
To test application functionality it`s better to use [Postman](https://www.postman.com/downloads/), 
where you can send API requests to controllers end-points and get responses.
####
**List of available end-points:**
* `GET: /movie-characters/random` - returns info about random character from db
* `GET: /movie-characters/by-name` - returns list of characters that contains given name part
