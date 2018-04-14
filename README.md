Sources

* [How to use Spring JPA with PostgreSQL | Spring Boot](http://javasampleapproach.com/spring-framework/use-spring-jpa-postgresql-spring-boot)
* [Uploading files to AWS S3 Bucket using Spring Boot](https://medium.com/oril/uploading-files-to-aws-s3-bucket-using-spring-boot-483fcb6f8646)

To run the application, use

```
mvnDebug clean install package spring-boot:run -DskipTests -Dpostgresql.url=jdbc:postgresql://localhost:5432/app -Dpostgresql.username=app_usr -Dspring.profiles.active=dev

```

* http://localhost:8080/findbylastname?lastname=Test

* http://localhost:8080/findbyid?id=userId

* http://localhost:8080/findall

