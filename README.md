# Order System Platform
Order System Platform developed with microservice approach using the Spring Framework and Hexagonal Architecture and DDD(domain driven design) . </br> </br>
## Architecture:
![order-system](https://github.com/user-attachments/assets/8f7c1959-f3b0-4cac-87ee-91b83c94613c)
* Sub domain core------->order-service
* Supporting sub domains -----------> driver-service,payment-service
* SAGA Coordinator ---------->order-service
* Outbox tables in order and driver and payment
## Used Technologies:
* Back-end: Java 17, Spring (Boot, Cloud, Data, Security), JPA / Hibernate, PostgreSQL,KafKa,ELK Stack
* Docker
* Minikube,Kubernetes(k8s),Helm
## Microservice Structure:
![hexagonal-architecture-ddd-domain-driven-design-600x484](https://github.com/user-attachments/assets/f8832b06-1986-46b7-94aa-7a418426463d)
* in every microservice i used the hexagonal architecture with DDD
## Features
* Clean Architecture
* Event Sourcing
* Integrity,Consistency
* Centralized monitoring
* Reliable event driven communication
* Separating read and write operations

