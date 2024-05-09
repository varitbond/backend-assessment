# Backend-Assessment

Backend - Rest API project to access resource on database 
develop with Java17 + Spring boot 3 with OpenID Connect (OIDC) authentication

## Description
There 2 API routes 

* '/users' to access users data 

* '/posts’ to access posts data


### Every route got mechanisms as below

* Getting a resource GET /get/{id}

* Listing all resources GET /get

* Creating a resource POST /create 

* Updating a resource PUT /update 

* Patching a resource PATCH /patch

* Deleting a resource DELETE /delete

* Filtering a resources GET /get/by-{another_resource_id}

All resources API in this server is protected by OpenID Connect (OIDC) with Bearer authentication method


## Getting Started
### Dependencies
* Java JDK 17 (Eclipse Temurin™ 17)
* MySQL 8.0 
* Apache Maven Command

### Installing

* Set Java home to JDK 17
* Prepare MySQL 8.0 empty database schema

* Change Database configuration in `pom.xml` section `<properties>` to your database
```
<jdbc.url>jdbc:mysql://localhost:3306/backend</jdbc.url>
<jdbc.user>root</jdbc.user>
<jdbc.password>password</jdbc.password>
```

* Run Maven flyway migrate for run flyway migration 
```
mvn flyway:migrate
```

* Run Maven generate sources for generate Jooq model
```
mvn generate-sources
```

* Change database configuration in `application.yml`
```
datasource:
  url: jdbc:mysql://localhost:3306/backend
  username: root
  password: password
```

### Executing program

* Run via mvnw
```
./mvnw spring-boot:run
```

* Run via mvn
```
mvn spring-boot:run
```

* Run manually
```
mvn clean package
java -jar target/backend.assessment-1.0.0-SNAPSHOT.jar
```

## Authentication 
To access server's API you need to login then use Bearer token as a authentication token

* Login
```
http://localhost:3000/login
```
After login will get an access token sent an access token in request header for example below
```
'Authorization: Bearer xxxxxxxx'
```

* Logout
```
http://localhost:3000/logout
```

## Swagger-ui

You can access service's swagger by url below after service start
```
http://localhost:3000/swagger-ui/index.html
```
or you can use file `openapi.json`

## Authors

Contributors names and contact info
* Varit C. [@varitbond](https://github.com/varitbond)

## Version History
* 1.0.0-SNAPSHOT (09 May 2024)

## License
* No license

## Acknowledgments

Readme template
* [README-Template.md](https://gist.github.com/DomPizzie/7a5ff55ffa9081f2de27c315f5018afc/)
