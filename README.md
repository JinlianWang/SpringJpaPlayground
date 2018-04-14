[Initial Source/Article](http://javasampleapproach.com/spring-framework/use-spring-jpa-postgresql-spring-boot)

To run the application, use

```
mvnDebug clean install package spring-boot:run -DskipTests -Dpostgresql.url=jdbc:postgresql://localhost:5432/app -Dpostgresql.username=app_usr -Dspring.profiles.active=dev

```

* http://localhost:8080/findbylastname?lastname=Test

* http://localhost:8080/findbyid?id=userId

* http://localhost:8080/findall

