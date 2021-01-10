# SpringStudy


# CH2 스프링 부트에서 테스트 코드 작성해보기 
_스프링 부트와 AWS로 혼자 구현하는 웹서비스 책을 보고 혼자 끄적이는 필기_

> 예전부터 테스트 코드 이야기가 많았다고 한다. 하지만 과거에는 테스트 코드를 진행하는 비율이 많지 않았지만 지금은 대부분의 회사가 테스트 코드를 요구한다 한다.


---
## TDD 와 단위테스트
* TDD = 테스트 주도 개발 (테스트 코드를 먼저 작성함)
	
    * 레드 그린 사이클![](https://images.velog.io/images/donglee99/post/f589c56c-b2e5-444b-b019-bbf5f358fea7/%EC%8A%A4%ED%81%AC%EB%A6%B0%EC%83%B7%202021-01-10%20%EC%98%A4%ED%9B%84%209.25.23.png)

  
    1. red = 항상 실패하는 테스트 먼저 작성
    2. green = 테스트가 통과하는 프로덕션 코드 작성
    3. Refactor = 테스트가 통과하면 프로덕션 코드 리펙토링
    
    
    
    <br>
    
 
 * 단위 테스트
 	
    * TDD의 기능 단위 테스트 코드 작성을 의미 하고 TDD 와 달리 테스트 코드를 먼저 작성하지 않아도 된다
    

* 테스트 코드의 이점
	
    1. 개발 단계 초기에 문제 발견을 도와준다.
    2. 기능에 대한 불확실성 감소.
    


###### _실은 이번이 책을 처음보는 것은 아니다 일주일전에 깃에 안올려두고 코드를 지워버려서 파일이 다날아간김에 처음부터 다시하고 있다......_
---
## Hello Controller 테스트 코드 작성

![](https://images.velog.io/images/donglee99/post/b3a1b57f-1e14-47a2-aedc-9eb43d1902b1/%EC%8A%A4%ED%81%AC%EB%A6%B0%EC%83%B7%202021-01-10%20%EC%98%A4%ED%9B%84%209.36.48.png)


* 코드를 보면 간단하다 일단 SpringBootApplication로 스프링 부트의 자동 설정, 스프링 Bean 읽기 생성을 할수 있게 해준다 (이 클래스는 항상 프로젝트의 최상단에 있어야 함)

* Application 클래스는 이 프로젝트의 메인 클래스가 된다. 프로젝트를 빌드 시키고 싶으면 이 부분을 빌드 시키면 된다.


** Web 패키지 **

* Web 패키지는 앞으로 컨트롤러와 관련된 클래스들이 담길 패키지이다.


![](https://images.velog.io/images/donglee99/post/fcad1a71-ebfa-4c9a-837d-8a46ed4a9fd8/%EC%8A%A4%ED%81%AC%EB%A6%B0%EC%83%B7%202021-01-10%20%EC%98%A4%ED%9B%84%209.41.56.png)

* Web 패키지안에 HelloController를 만들어주고 간단한 요청을 처리하는 API를 만들어 준다.
	
    *	RestController = 컨트롤러를 JSON (값과 쌍으로 이루어진 인간이 읽을수있는 텍스트) 으로 반환하는 컨트롤러로 만들어 준다.
    * GetMapping = 	HTTP 메서드의 GET 요청을 받을수 있게 도와준다.
    

** Hello Controller 에 대한 Test 코드 만들어 보기 **

![](https://images.velog.io/images/donglee99/post/7b8a7739-5efe-43c9-b48d-43ae48c8cc43/%EC%8A%A4%ED%81%AC%EB%A6%B0%EC%83%B7%202021-01-10%20%EC%98%A4%ED%9B%84%209.46.07.png)

_책이 시간이 지남에 따라 버전이 바뀌어 몇가지 코드가 바꼈다 크게 Test를 Import 할때랑 RunWith이 바뀌었는데 아마 이전에 했었을때 책 그래로 하면 오류가 났던 걸로 기억한다 이는 책을 쓰신 분의 블로그에 자세히 나와있다 [ 클릭 ](https://jojoldu.tistory.com/539) _

* RunWith(SpringRunner.class) => ExtendWith(SpringExtension.class)
테스트를 진행할때 JUnit에 내장된 실행자외 다른 실행자를 실행 시키는 코드이다.

* WebMvcTest = 스트링 테스트 어노테이션중 Web에 집중 할 수 있는 어노테이션
@Controller, ControllerAdvice 사용할 수 있게 해줌

* private MockMvc mvc = 스프링 MVC 테스트의 시작점이며 GET POST API 생성 가능

* andExpect(status().isOk()) =  mvc.perform 의 결과 검증 200 = 정상 인지 아닌지 ( 없으면 다음 코드가 안됨 )
* andExpect(content().string(hello)) = hello가 온건지 확인 하는데 위의 코드가 없으면 오류가 남


---

## LomBok(롬복) 소개 및 설치

> 롬복은 객체지향언어를 개발할때 자주 나오는 Getter Setter, 기본생성자 등을 자동 생성해주는 어노테이션이다.


## Hello Controller 코드를 롬복으로 전환하기

** DTO ** 
* Web 패키지 안에 DTO  패키지를 생성해주고 이 부분엔 모든 응답 DTO 를 담아준다.


![](https://images.velog.io/images/donglee99/post/c83d8788-15cd-4521-aca9-87bc155cbdec/%EC%8A%A4%ED%81%AC%EB%A6%B0%EC%83%B7%202021-01-10%20%EC%98%A4%ED%9B%84%209.59.33.png)

* LomBok.Getter를 선언했음으로 선언된 모든 필드에 따로 Getter 메소드를 생성해주지 않아도 된다. 

* RequiredArgsConstructor = final로 선언된 변수들의 생성자를 만들어줌 (final 이 아닌 경우 X)
![](https://images.velog.io/images/donglee99/post/921e6c63-4b9b-4a64-87e1-cce02d36b3c3/%EC%8A%A4%ED%81%AC%EB%A6%B0%EC%83%B7%202021-01-10%20%EC%98%A4%ED%9B%84%2010.02.57.png)

*  테스트 코드를 구현 할때는 given, when, then 순으로 구현을 하게 된다.

* given 부분에서는 name = test, amount = 1000으로 변수를 선언하고

* when 부분에서는 이 값들을 토대로 HelloResponseDto 의 객체 DTO 를 만들어준다.

* then 부분에서는 이 값들이 정상적으로 생성이 생성자에 의해 생성이 되었는지 확인 해 보는 부분으로 assertThat 을 통해 isEqualTo와 함께 검증을 하게 된다.


** 다음으로는 HelloController 에서 새로만든 ResponseDTO를 사용하는것을 테스트 해보자 **

![](https://images.velog.io/images/donglee99/post/a0b70655-b07c-45d5-9bae-99e3fe938ab0/%EC%8A%A4%ED%81%AC%EB%A6%B0%EC%83%B7%202021-01-10%20%EC%98%A4%ED%9B%84%2010.08.24.png)

* RequestParam 외부에서 API 로 넘긴 파라미터를 가져오는 어노테이션이다
( 외부에서 넘긴 name 을 name 에 저장 )

* 이후 ResponseDTO객체에 값들을 담아 리턴 한다.

** 이를 테스트 해보면 **

![](https://images.velog.io/images/donglee99/post/53d1107a-5e5b-4831-9d30-abf77ebfe098/%EC%8A%A4%ED%81%AC%EB%A6%B0%EC%83%B7%202021-01-10%20%EC%98%A4%ED%9B%84%2010.02.21.png)

---

이번 장에서 배운 것 
* TDD 와 단위 테스트 차이

* 스프링 부트에서 테스트 코드 작성

* 롬복 사용법
