# spring-boot-web
***
### ORM 과 JPA
- ORM (Object Relational Mapping) 객체 지향과 관련이 있음.
  - 이는 곧 객체지향 패러다임을 관계형 데이터베이스에 보존하는 기술 임.
  - 패러다임 입장에서는 '객체지향 패러다임을 관계형 패러다임으로 매핑(mapping)' 해 주는 개념.
- 클래스와 테이블이 유사하듯 인스턴스와 Row(레코드 혹은 튜플) 도 상당히 유사.
- 객체지향에서는 클래스에서 인스턴스를 생성해서 인스턴스 공간에 데이터를 보관 함.
- 테이블에서는 하나의 'Row' 에 데이터를 저장 함.
  - 이 둘의 차이는 객체라는 단어가 '데이터 + 행위(메서드)' 라는 의미라면
    - 'Row' 는 '데이터' 만을 의미 함.

``` 
    핵심 = ORM 은 새로운 패러다임의 주장이 아님.
    객체지향과 관계형 사이의 변환 기법을 의미하는 것임.
    JPA 는 ORM 을 Java 언어에 맞게 사용하는 스펙 임.
    이를 구현체인 Hibernate 를 이용해 JPA 를 구현 함. 
```
***

### JPA Update 쿼리, Delete 쿼리 의 특징
- JpaRepository 를 상속 받은 Repository 인터페이스는 update 쿼리를 날릴 때 특이점이 존재 함.
  - Jpa 는 엔티티 객체들을 메모리상에 보관하려고 하기 떄문에 특정한 엔티티 객체가 존재 하는지 확인하는
  - select 가 먼저 실행되고 해당 @Id 를 가진 엔티티 객체가 있다면 update, 그렇지 않다면 insert 를 실행함.
  - 이는 Delete 쿼리 역시 같게 적용 됨.
***
  
### Paging / Sort
- JPA 는 내부적으로 Paging 과 Sort 를 'Direct' 를 이용 함.
- 덕분에 SQL 이 아닌 API 의 객체와 메서드를 사용하는 형태로 페이징 처리를 할 수 있게 됨.
  - Spring Data JPA 에서 페이징 처리는 뭘로 하는가?
    - findAll() 메서드를 사용한다.
    - findAll() 은 JpaRepository 인터페이스의 상위인 PagingAndSortRepository 의
    - 메서드 파라미터로 전달되는 Pageable 이라는 타입의 객체에 의해서 쿼리가 실행된다.
    - 주의점 : 리턴타입을 Page<T> 타입으로 지정하는 경우 반드시 Pageable 이용 해야함.
***

### 쿼리 메서드(@Query Methods) 기능과 @Query
1. 쿼리 메서드 : 메서드의 이름 자체가 쿼리의 구문으로 처리되는 기능
2. @Query : SQL 과 유사하게 엔티티 클래스의 정보를 이요해서 쿼리를 작성하게 하는 기능
   1. 필요한 데이터만 선별적으로 추철하는 기능이 가능
   2. 데이터베이스에 맞는 순수한 SQL(Native SQL) 을 사용하는 기능
   3. insert,update,delete 와 같은 select 가 아닌 DML 등을 처리하는 기능(@Modifying 과 함께 사용)
3. Querydsl 등의 동적 쿼리 처리 가능

***