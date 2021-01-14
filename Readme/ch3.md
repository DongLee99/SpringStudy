# CH3 스프링 부트에서 JPA로 데이터 베이스 다루기

> 예전에 노드 js 를 공부 했을때도 느끼는건데 백엔드를 할때 데이터베이스가 중요 한것 같다 스프링부트를 할때도 바로 데이터 베이스 다루는것이 나오는걸 보고 처음 노드 js 를 했을때보다는 무엇을 왜 구현하는지 생각하면서 배우니까 재밌다.

---

## JPA 란?

* 간단하게 말하면 서로 다른 지향하는 바를 가진 2개의 영역을 중간에서  조율해주는 기술이라고 볼수있다. 
-> 자바 개발자는 객체지향적이다
-> SQL 은 관계형 데이터 베이스다
-> 이를 JPA가 조율을해 SQL 을 대신 생성하고 실행을 한다.
-> SQL 에 종속적인 프로그래밍을 하지 않아도 된다.


** 앞으로 구현할 부분 **

* 게시판 기능 
	
    * 게시글 조회
    * 게시글 등록
    * 게시글 수정
    * 게시글 삭제
    
* 회원 기능
	
    * 구글 / 네이버 로그인
    * 로그인한 사용자 글 작성 권한
    * 본인 작성 글에 대한 권한 관리

---
## 프로젝트에 Spring Date Jpa 	적용하기

** Domain 패키지 **
* 게시글, 댓글, 회원, 정산, 결제 등의 소프트웨어에 대한 요구사항 or 문제에 대한 영역이다.


** Posts 패키지와 Posts 클래스 **

### posts 패키지
> post 클래스는 실제 DB의 테이블과 매칭 될 클래스

![](https://images.velog.io/images/donglee99/post/126b0102-3ac7-4fa6-ac3f-e6201fcf9869/%EC%8A%A4%ED%81%AC%EB%A6%B0%EC%83%B7%202021-01-13%20%EC%98%A4%ED%9B%84%209.07.55.png)

* Getter 와 NoArgsConstructor 는 getter 와 파라미터가 없는 기본 생성자를 생성하게 도와준다.
* Entity 는 JPA의 어노테이션이다 Entity 가 붙은 클래스를 JPA 가 관리 하게 된다.
* ID = 해당 테이블의 PK 필드를 나타낸다.
* Column = 테이블의 칼럼을 나타내며 굳이 선언하지 않아도 해당 클래스의 필드는 모두 칼럼이 된다.
* Builder 해당 클래스의 빌더 패턴 클래스를 생성
* 총 Id 와 title, content, author 를 가지는 테이블이 만들어 진다.

_이 테이블에는 Getter 는 있지만 Setter 는 없다 그이유는 클래스의 인스턴스 값들이 언제 어디서 변하는지 명확하게 구분 할수 없어 그렇다. Entity 클래스에서는 절대 Setter 를 만들지 않는다._

** => 그렇다면 어떻게 DB에 삽입을 하냐? **

* 생성자를 통해 최종 값을 채우고 삽입/ 변경시에는 이벤트에 맞는 메서드를 이용해 변경
* 위 코드에서는 Builder 를 사용해 생성 시점에 값을 채워준다.

### postsRepostitory

![](https://images.velog.io/images/donglee99/post/e93a1069-4c1c-4064-b74c-4d4f06e91b1f/%EC%8A%A4%ED%81%AC%EB%A6%B0%EC%83%B7%202021-01-13%20%EC%98%A4%ED%9B%84%209.20.08.png)

* 이 코드에서 봐야 할 부분은 extends JpaRepostory<Posts, Long> 이다. Posts 와 Long 은 각각 Entity 클래스 , PK 타입이며 이 코드를 상속함으로 기본적인 CRUD 메소드가 자동 생성 된다.

_Entity 클래스와 Entity Repository 의 위치는 항상 같아야 한다._

---
## Spring Data JPA 테스트 코드 작성하기

** save, findAll test **

![](https://images.velog.io/images/donglee99/post/f5000594-148e-4234-9be2-26e8f81b0c0f/%EC%8A%A4%ED%81%AC%EB%A6%B0%EC%83%B7%202021-01-13%20%EC%98%A4%ED%9B%84%209.23.50.png)

* 이 부분은 책과 버젼이 달라 수정해야 할 부분이 있다.
* @AfterEach = 단위테스트가 끝날때마다 실행하는 메서드 지정 
보통 테스트를 만들때 하나의 테스트만 하는게 아니므로 변수의 값이 충돌이 일어날수 있다 이를 방지 하기 위해 cleanup 이라는 메서드를 지정해 두었다.
* postsRepository.save => posts에 insert/update를 실행
(id 값이 있다면 update 없으면 insert)
* postsRepository.findAll() 모든 데이터를 조회
* 위 코드는 간단하게 title, content, author 를 저장하고 get으로 불러와 비교를 하여 테스트하는 코드이다.

## 등록/수정/조회 API 만들기

* Request 테이터를 받을 Dto
* Api 요청을 받을 Controller
* 트랜잭션, 도메인 기능 간의 순서를 보장하는 Service
(즉 기능 자체는 도메인에 저장 되어있고 이제 그 기능을 어떻게 사용할 것인지)

** 스프링 웹 계층 **
![](https://images.velog.io/images/donglee99/post/5fff36a3-5d74-4a7a-a233-0b942e427c4a/%EC%8A%A4%ED%81%AC%EB%A6%B0%EC%83%B7%202021-01-13%20%EC%98%A4%ED%9B%84%209.58.27.png)

* Web Layer 
	
    * 컨트롤러와 뷰템플릿 영역 (외부 요청과 응답의 전반적 영역)
    
* Service Layer

    * @Service 에 사용되는 서비스 영역 (Controller 와 Dao 의 중간 영역)
    
* Repository Layer

    * Database와 같이 데이터 저장소에 접근 하는 영역
    
* Dtos

    * Dto 는 계층 간에 데이터 교환을 위한 객체 Dtos 는 이들의 영역

* Domain Model

    * 도메인이라 불리는 개발 대상을 모든 사람이 동일한 관점에서 이해할 수 있고 공유할 수 있도록 단순화 시킨 것
   
** PostsApiController **

![](https://images.velog.io/images/donglee99/post/2314493a-337b-46a4-b45f-20b1d325b323/%EC%8A%A4%ED%81%AC%EB%A6%B0%EC%83%B7%202021-01-13%20%EC%98%A4%ED%9B%84%2010.04.43.png)
    
* /api/v1/posts 로 요청이들어올 경우 아래 메소드를 실행한다는 코드이다
* 이때 @RequestBody 는 post로 요청이 들어올때 Http 요청의 Body내용을 Java Object로 변환시켜주는 역할을 한다 이후 return 을 통해 service 의 save를 호출하게 된다.


** PostsService **
![](https://images.velog.io/images/donglee99/post/eb0ff835-a278-4834-811e-9ba65c9b8fc4/%EC%8A%A4%ED%81%AC%EB%A6%B0%EC%83%B7%202021-01-13%20%EC%98%A4%ED%9B%84%2010.24.28.png)

* ApiController 에서 리턴 받은 PostsService.save 이번에는 PostsRepository의 save를 호출한다. ( 위에서 설명한 서비스 패키지가 직접 기능을 행하는 부분은 아니라는것을 증명한다. )

** PostsSaveRequestDto **

![](https://images.velog.io/images/donglee99/post/d1f61eb9-5b76-44a3-b55f-092ad08b6b67/%EC%8A%A4%ED%81%AC%EB%A6%B0%EC%83%B7%202021-01-13%20%EC%98%A4%ED%9B%84%2010.27.54.png)

* Controller 와 Service 에서 파라미터로 사용하는 Dto



** Posts_등록 테스트 **![](https://images.velog.io/images/donglee99/post/4378ebda-4e46-4a04-8838-553fe778f54d/%EC%8A%A4%ED%81%AC%EB%A6%B0%EC%83%B7%202021-01-14%20%EC%98%A4%ED%9B%84%207.22.58.png)

* 위 코드는 PostsSaveRequestDto 에 선언한 값들을 빌드 한후
값들이 정상적으로 저장이 되었는지 확인하는 테스트이다.
* 이전의 HelloController와 달리 @WebMvcTest를 사용하지 않고 @SpringBootTest와 TestRestTemplate 를 사용하였다. 이는 @WebMvcTest가 JPA 기능이 작동하지 않기 때문이다.

### 수정 및 조회 기능 만들기 
** PostsApiController **
![](https://images.velog.io/images/donglee99/post/5d7a55c8-bf19-4cac-843b-775519caaa25/%EC%8A%A4%ED%81%AC%EB%A6%B0%EC%83%B7%202021-01-14%20%EC%98%A4%ED%9B%84%207.30.16.png)

* 위의 메서드는 Update 즉 수정을 위한 메서드 이고 아래의 메서드는 조회를 위한 메서드이다 두 함수는 요청하는 주소가 같지만 방식이 다르다.

** PostsService **
![](https://images.velog.io/images/donglee99/post/11416f3e-84f6-4f46-93af-73b52f113f28/%EC%8A%A4%ED%81%AC%EB%A6%B0%EC%83%B7%202021-01-14%20%EC%98%A4%ED%9B%84%207.38.58.png)

* /api/v1/posts/{id}가 Put 방식으로 요청된경우 PostsApiController로 들어가서 return이 postsService.update(id, requestDtO)이다 이때 service의 update 가 호출되어 처리를 하는데 코드를 보면 id 의 게시물을 찾고 있다면 Posts.update를 진행하게 된다.

** PostsApiControllerTest **

* 이제 위의 수정기능을 만들었으면 테스트를 해보자

![](https://images.velog.io/images/donglee99/post/3740bfbd-6328-47e2-873f-03777e0c64a4/%EC%8A%A4%ED%81%AC%EB%A6%B0%EC%83%B7%202021-01-14%20%EC%98%A4%ED%9B%84%207.43.12.png)

* given = 먼저 수정을 확인 하려면 수정을 하기위해 저장된 값을 만들어 주어야 된다. 따라서 PostsRespository의 save를 이용해 값을 지정해준다.
* 이후 savePosts 즉 저장한 값의 id 값을 따로 변수에 저장하고 변경할 값들을 저장해둔뒤 requestDto에 담는다.
* when = 위에서 변경할 변수를 저장한 값들을 /api/v1/posts/id로 요청을 보낸다.
* then = 이후 변경된 값들을 비교 하면서 성공적으로 update가 됐는지 확인 해본다.

## 3.5 JPA Auditing으로 생성시간/수정시간 자동화 
* 이 부분은 기능면에서는 딱히 중요하지 않다고 생각하여 따로 필기는 하지 않겠다.