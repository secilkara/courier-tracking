# COURIER TRACKING

### Tech Stack

-----
* Java 17
* Spring Boot 3.2.0
* Maven
* H2DB
* Swagger
* Spring Data JPA
* Junit



### Build & Run

-----
* Clone the repository

        git clone https://github.com/secilkara/courier-tracking.git


* Navigate to the root directory of the project
    
        cd courier-tracking


* Build the project using Maven

        mvn clean install


* Run the project using Maven

        mvn spring-boot:run



### Test

----
##### Swagger UI
You can access the api document with the swagger ui interface link below

http://localhost:8080/swagger-ui/index.html


##### H2DB Console

http://localhost:8080/h2-console    

    JDBC url: jdbc:h2:./test
    username: sa
    password: 123


### Endpoints

------

* `POST /stores/default` creates the stores in the _stores.json_ file
* `POST /stores ` creates a new store
* `POST /courier` creates a new courier
* `PATCH /courier/{id}/location` updates the courier's location
* `GET /courier/{id}/total-distance` Returns the courier's total travel distance


