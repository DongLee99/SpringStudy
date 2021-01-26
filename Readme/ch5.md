# CH5 스프링 시큐리티와 OAuth2.0으로 고르인 기능 구현하기

> 스프링 시큐리티는 막강한 인증, 인가 기능을 가진 프레임 워크이다
이번장에서는 스프링 시큐리티와 OAuth을 구현한 구글 로그인을 연동하여 로그인 기능을 만들어 보는 연습을 할것이다.

---

## 스프링 시큐리티와 스프링 시큐르티 OAuth2 클라이언트
_ 이전에 Node.js 를 이용해 캡스톤 디자인을 하면서 한 프로젝트에서Node.js 연습 겸 카카오 로그인 까지 구현해 본적이 있었다. 그러면서 느꼈던점은 카카오 로그인 기능을 사용하면 매우 간단하게 로그인 기능을 구현이 가능하고 클라이언트 입장에서도 회원가입을 따로 안해도 되니까 편하겠다 라는 생각을 하게 되었었다._
* 요즘 모든 웹사이트, 앱을 보면 전부 소셜 로그인을 같이 사용하느 경우가 많다. 이번 장에서는 그 소셜 로그인을 구현하기를 해볼것인데 소셜 로그인을 왜 사용할까?
	
    * 그 이유에는 위에서도 말했듯이 직접 구현하면 우선 코드가 복잡해지고 배보다 배꼽이 더 커진다. 
    * 소셜 로그인의 장점
    	* 로그인 보안 
       * 비밀번호 찾기
       * 비밀번호 변경
       * 회원 정보 변경
       등등을 직접 구현하지 않아도 된다는 장점이 있다.

_책에 구글 서비스 등록 방법은 자세히 나와있으므로 생략_

---

## 구글 로그인 연동하기

** User클래스 **
* 사용자의 정보를 담당할 도메인
![](https://images.velog.io/images/donglee99/post/755712ae-b061-4973-99af-81ca8155d873/%EC%8A%A4%ED%81%AC%EB%A6%B0%EC%83%B7%202021-01-26%20%EC%98%A4%ED%9B%84%202.48.30.png)
	
  * 기본적인 ```id```, ```email```(null = false), ```picture```(null = false)을 컬럼으로 만들어주고 builder를 통해 생성자를 만들어준다.
  * ```@Enumerated(EnumType.STRING)``` : JPA로 데이터 베이스 저장시 Enum 값이 어떤 형태로 저장될지 결정.
  
** Role 클래스 **
* 사용자 권한 관리할 Enum 클래스![](https://images.velog.io/images/donglee99/post/c71037f8-f510-4dfb-aa1a-726b1ee39481/%EC%8A%A4%ED%81%AC%EB%A6%B0%EC%83%B7%202021-01-26%20%EC%98%A4%ED%9B%84%203.06.18.png)
	
    * 스프링 시큐리티에서 권한 코드 앞에는 항상 ROLE_이 있어야한다. 이 코드에는 권한을 GUEST 와 USER 로 구별을 해두었다.
    
** config.auth 패키지 **
* 시큐리티 관련 클래스는 모두 이곳으로 

** config/auth/SecurityConfig**
* 로그인을 하면 허가를 주는 클래스![](https://images.velog.io/images/donglee99/post/0a2cd387-e8a3-4d66-a0be-c12a03ef2521/%EC%8A%A4%ED%81%AC%EB%A6%B0%EC%83%B7%202021-01-26%20%EC%98%A4%ED%9B%84%203.16.13.png)
	
    * 이 코드는 다시봐도 생소하다...
    ```@EnableWebSecurity```
    Spring Security 설정 활성화
  ```@csrf().disable().headers().frameOptions().disable()	```
  h2-console 화면을사용하기 위한 옵션 disable
    ```@authorizeRequests```
    권환 관리 대상 지정 (URL, HTTP 메소드별로 관리 가능)
    ```@antMatchers```
    "/"등 지정된 URL -> permitAll()전체 열람
    "/api/v1/\**" -> USER 권한 가진 사람만 가능
   ```@anyRequest```
    설정된 값 뺀 나머지 URL에 authenticated = 로그인된 사용자만 허용
    
** CustomOAuth2UserService **
* 구글에서 가져온 사용자의 정보를 기반으로 가입, 세션 저장등의 기능 수행
![](https://images.velog.io/images/donglee99/post/e9a55c51-1149-465d-9fca-1b27c73d7223/%EC%8A%A4%ED%81%AC%EB%A6%B0%EC%83%B7%202021-01-26%20%EC%98%A4%ED%9B%84%203.14.15.png)