# N:1(다대일) 연관관계
## Project 설정 파일 분리 properties -> yml
- 목표 : 기존 스프링 application.properties 를 yml 로 분리한다.
```
# 기존 properties 파일정보
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver
spring.datasource.url=jdbc:mysql://127.0.0.1/{schema}
spring.datasource.username={username}
spring.datasource.password={password}

spring.jpa.hibernate.ddl-auto=update
spring.jpa.properties.hibernate.format_sql=true
spring.jpa.show-sql=true

spring.thymeleaf.cache=false

```
- db 정보가 제외된 application.yml
```
# application.yml
spring:
  datasource:
    driver-class-name: com.mysql.cj.jdbc.Driver
  jpa:
    hibernate:
      ddl-auto: update
      format_sql: true
    show-sql: true
  thymeleaf:
    cache: false
  profiles:
    include: db
```
- 아래의 yml 파일에 db 정보를 담는다.
```
# application-db.yml
spring:
  datasource:
    url:  jdbc:mysql://127.0.0.1/{schema}
    username: {username}
    password: {password}
```