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

***
# Spring Boot + thymeleaf + JPA + Maven 생성일, 수정일 컬럼 처리.
### 생성일(regDate)과 수정일(modDate) Entity Column
- 해당 컬럼들은 JPA 의 Entity 에서 공통적인 컬럼으로 사용된다.
  - 따라서 이와같은 처리는 코드 중복을 방지 해주는 효과가 있다.
    - 처리방법은 아래와 같다.
```java
/**
 * 스프링부트 프로젝트에서 추가 되어야할 라이브러리는 아래와 같다.
 * Spring Boot DevTools
 * Lombok
 * Spring Web
 * Template Engines
 * Thymeleaf
 * Spring Data JPA
 * 
 * 
 */
//pom.xml 의존성 추가.
<dependency>
    <groupId>org.thymeleaf.extras</groupId>
    <artifactId>thymeleaf-extras-java8time</artifactId>
 </dependency>
```
```java
import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;

@MappedSuperclass // 해당 어노테이션이 적용된 클래스는 테이블로 생성되지 않는다. 
// 실제 테이블은 해당 추상 클래스를 상속한 엔티티 클래스로 데이터 베이스 테이블이 생성된다.
@EntityListeners(value = {AuditingEntityListener.class})
@Getter
abstract class BaseEntity { //추상클래스로 작성된다.
    
    @CreatedDate
    @Column(name = "regdate" , updatable = false) // updatable = false 해당 엔티티 객체를 데이터베이스에 반영할 때 regdate 컬럼값은 변경되지 않는다.
    private LocalDateTime regDate; // 생성일

    @LastModifiedDate
    @Column(name = "moddate")
    private LocalDateTime modDate; // 수정일
}

```
```java
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;


@SpringBootApplication
@EnableJpaAuditing //추가 어노테이션
public class BoardApplication {

    public static void main(String[] args) {
        SpringApplication.run(BoardApplication.class, args);
    }
}

```
