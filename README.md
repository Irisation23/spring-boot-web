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
## fetch join
### 특정한 엔티티를 조회할 때 연관관계를 가진 모든 엔티티를 같이 로딩하는 것을 'Eager loading' = '즉시 로딩' 이라 함.
- 해당 로딩의 장점은??
  - 로딩 한번에 연관관계가 있는 모든 엔티티를 가져옴.
- 해당 로딩의 단점은??
  - 연관관계가 복잡해 지면 join 으로 인한 성능 저하 발생함.
- 어떻게 해결해 볼 수있음??
  - 반대개념인 'Lazy loading' = '지연 로딩' 을 추천!!
- 어떻게 사용함??
  - 연관관계 어노테이션 속성으로 'fetch' 모드를 지정함.
  - @ManyToOne (fetch = FetchType.LAZY) 명시적
- 보편적인 코딩가이드에서는 어떻게 하는가??
  - 지연로딩을 기본으로 사용하고, 상황에 맞게 필요한 방법을 찾는다.
    - 여기서 말하는 필요한 방법이란??
    - JPQL 과 left (outer) join 의 사용이다.
    - 향후 기술 예정.
***

---
# @Transactional
- 메서드에 선언된 `@Transcational` 은 해당 메서드를 하나의 '트랜잭션' 으로 처리하라는 의미임.
- 트랜잭션으로 처리하면 속성에 따라 다르게 동작하지만,
- 기본적으로는 필요할 때 다시 데이터베이스와 연결이 생성됨.
---

---
# 연관관계가 없는 엔티티 조인 처리에는 on 을 사용
- Board 와 Member 사이에는 내부적으로 참조를 통해서 연관관계가 있음.
- Board 와 Reply 는 좀 상황이 다름.
- Board 입장에서는 Reply 객체를 참조하고 있지 않음.
- 이런 경우에는 직접 조인에 필요한 조건은 'on' 을 이용해서 작성해 주어야 함.
  - '특정 게시물과 해당 게시물에 속한 댓글을 조회' 하는 쿼리문
```sql
SELECT 
    board.bno, board.title, board.writer_email,
    rno, text
FROM 
    board left outer join reply
ON 
    reply.board_bno = board.bno
WHERE
    board.bno = 100;

```
---
# JPQL
- JPQL 에서의 update 와 delete 는 어떻게 처리하는가???
  - @Modifying 을 사용해 준다.
  
