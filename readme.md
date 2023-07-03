# Java Spring microcervices Credibanco
[![standard-readme compliant](https://img.shields.io/badge/readme%20style-standard-brightgreen.svg?style=flat-square)](https://github.com/RichardLitt/standard-readme)

this repo is based on https://github.com/Qleoz12/java-spring-cloud-gateway-demo 

Project to resolve Prueba - Backend - CredibanCo.

This repository contains:

- card-service
- transaction-service
- gateway (secure)
- domain (DDD)
- discovery (discover microservices)

This project requires the machine to have the environment tools configured:

- maven
- java jdk17
- docker

# website

find more development info at my website 
- https://qleoz12.github.io/
- https://www.linkedin.com/posts/leonardo-sanchez-89590b127_github-qleoz12java-spring-cloud-gateway-demo-activity-7024927750268289024-R1vZ?utm_source=share&utm_medium=member_desktop 

## Arquitecture

![Alt text](./docs/arquitectura.drawio.png?raw=true "Arquitecture microservices")

## Install

set the variable `DIRECTORY_HOME` inside the bats 
- mvn_clean_package_DOCKER.bat 
- mvn_clean_package_DEV.bat

where you clone the project after that runthe .bat

```sh
$ mvn_clean_package_DOCKER.bat
```

when all projects build correctly you could run up the docker-compose

```sh
$ ./devops/docker-compose up -d 
```

check the logs an validate the conection with the next section

## Usage

### Swagger docs

- http://localhost:8082/card-service/swagger-ui/#/card-controller
- http://localhost:8083/transaction-service/swagger-ui/#/transaction-controller

### CREATE TOKEN

- URL DIRECT: http://localhost:8080/auth/token
- URL CLOUD GATEWAY: http://localhost:8080/auth/token
- Body: x-www-form-urlencoded / username: admin / password: teste

### REFRESH TOKEN

- URL DIRECT: http://localhost:8080/auth/refreshToken
- URL CLOUD GATEWAY: http://localhost:8080/auth/refreshToken
- Body: x-www-form-urlencoded / refresh_token: (refresh_token gerado no CREATE TOKEN)

### Card Services 

- card number: http://localhost:8080/card-service/v1/card/{{productid}}/number
- card enroll: http://localhost:8080/card-service/v1/card/enroll
- get card add balance: http://localhost:8080/card-service/v1/card/balance
- get card balance: http://localhost:8080/card-service/v1/card/balance/{{cardid}}

### Transaction services

- transaction purchase: http://localhost:8080/transaction-service/v1/transaction/purchase
- transaction anulation: http://localhost:8080/transaction-service/v1/transaction/anulation

### DISCOVERY

URL SPRING EUREKA: http://localhost:8081/discovery

## Deploy AWS

the next link introduce comands for deploy in a aws cloud
-  https://aws.amazon.com/blogs/containers/deploy-applications-on-amazon-ecs-using-docker-compose/

### references

- https://github.com/ronniemikihiro/microservices-spring-cloud
- https://github.com/PacktPublishing/Mastering-Microservices-with-Java-9-Second-Edition (Chapter 06)
- https://www.amazon.com/Mastering-Microservices-Java-domain-driven-microservice-based/dp/1787281442?
- https://github.com/serlesen/microservices-bookstore
- https://www.youtube.com/watch?v=iwq0rc-PYNQ&list=PLab_if3UBk9-BjDP7Yh4uiy8z0pEd14Tp
