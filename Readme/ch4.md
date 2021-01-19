# CH4 머스테치로 화면 구성하기

## 4.1 서버 템플릿 엔진과 머스테치 소개
---
> 템플릿 엔진이란? 지정된 템플릿 양식과 데이터가 합쳐져 HTML 문서를 출력하는 소프트웨어를 의미한다. (_간단하게 웹사이트의 화면을 어떤 형태로 만들껀지 도와주는 양식_)

* **템플릿 엔진의 종류** : JSP, Freemaker / 리엑트```react```, 뷰```vue```
	
    * JSP, Freemaker : 서버 템플릿 엔진
    
    * 리엑트```React```, 뷰 ```Vue``` : 클라이언트 템플릿 엔진
	~~개인적으로 학교 수업시간에 다뤄본 JSP 말고는 아직 다뤄본게 없다...~~
    
* **머스테치란?** 머스테치는 템플릿의 한종류라고 생각하면 되고 수많은 언어를 지원하는 가장 심플한 템플릿 엔진이다.
	
    * 머스테치의 장점 
    	1. 문법이 다른 템플릿 엔진보다 심플함
        2. 로직 코드를 사용할 수 없어 View의 역할과 서버의 역할이 명확하게 구분
        3. Mustache.js, Mustache.java 2가지가 다 있어 하나의 문법으로 클라이언트/서버 템플릿을 모두 사용 가능하다.
---
 
 ## 4.2	기본 페이지 만들기
  ** _책의 한 챕터를 모두 돌은 뒤에 작성한 글이라 책과 코드가 다를수 있음_ **
  
### ** IndexController **
(페이지 관련 모든 컨트롤러는 IndexController에 담긴다)
   ![](https://images.velog.io/images/donglee99/post/d1f6a1c3-14a0-47b6-92b4-b631243d6817/%EC%8A%A4%ED%81%AC%EB%A6%B0%EC%83%B7%202021-01-19%20%EC%98%A4%ED%9B%84%203.38.37.png)

* 여기서 주목할 부분은 ```return index``` 이다. 왜 return d을 index 만 써도 되는 걸까? = ** 이는 앞에서 추가한 의존성 mustache starter 에 의해 앞에 경로와 뒤의 경로가 자동으로 지정된다.** ( index -> ```src/main/resources/templates/index.mustach``` )

### ** Test**
![](https://images.velog.io/images/donglee99/post/dab33bb7-01a3-4d1d-8d86-7f45842b96e7/%EC%8A%A4%ED%81%AC%EB%A6%B0%EC%83%B7%202021-01-19%20%EC%98%A4%ED%9B%84%203.57.43.png)

* ```TestRestTemplate``` 를 이용해 ```/``` 를 호출 했을때 ```index.mustache```에 ```"스프링 부트로 시작하는 웹서비스"```가 포함되어있나 테스트하는 코드
---
## 4.3 게시글 등록 화면 만들기

> 레이아웃 방식이란 ? 공통영역을 별도의 파일로 분리하여 필요한 곳에서 가져다 쓰는 방식

### **(layout) Header.mustache **

![](https://images.velog.io/images/donglee99/post/c0e2b582-38a5-4760-b33e-b8ae6c4eef70/%EC%8A%A4%ED%81%AC%EB%A6%B0%EC%83%B7%202021-01-19%20%EC%98%A4%ED%9B%84%208.13.00.png)

### **(layout) footer.mustache **
![](https://images.velog.io/images/donglee99/post/0e435087-9644-4063-85a2-e5fc6081a7cf/%EC%8A%A4%ED%81%AC%EB%A6%B0%EC%83%B7%202021-01-19%20%EC%98%A4%ED%9B%84%208.14.45.png)

* _책에 설명을 보면 위 코드에 css 와 js 의 위치가 다르다고 나와있다. 근데 나는 원래 어떻게 코드가 짜여져있는지 모르겠어서 이 말이 공감이 안된다._
* 위 코드에 Header 에는 css, Footer 에는 js 가 있다 이는 펭이지 로딩 속도를 위한 의도라 한다. (css는 화면을 구성하는 코드여서 불러오지 않으면 화면이 깨진다 따라서 먼저 css 를 불러오기 위해 Header 에 css를 위치 시킨 것이다.)

### ** Index.mustache **
![](https://images.velog.io/images/donglee99/post/75d8b7e0-2779-40a0-b6d1-6fbc2b5a62e6/%EC%8A%A4%ED%81%AC%EB%A6%B0%EC%83%B7%202021-01-19%20%EC%98%A4%ED%9B%84%208.19.50.png)
* 위 코드는 layout이 적용된 코드이다 코드를 살펴보면 위와 아래에 
```{{>layout/header}}, {{>layout/footer}}``` 가 있다. 이 코드는 ```header``` 가 불러와지고 그 다음에 ```Index.mustache```만의 페이지를 짠 코드가 나오고 마지막에 ```footer```로 마무리가 된다. 

### **등록 **
** IndexController**
![](https://images.velog.io/images/donglee99/post/8c1e590c-0173-42a2-81d5-da5a31367ef4/%EC%8A%A4%ED%81%AC%EB%A6%B0%EC%83%B7%202021-01-19%20%EC%98%A4%ED%9B%84%208.31.34.png)

* ```IndexController``` 의 코드는 매우 간단하다. ```/posts/save```로 매핑이 되어 요청이 들어오 IndexController의```"posts-save"```를 리턴 시킨다. 이 ```posts-save```-> ```posts-save.mustach``` 위에서 ```Index```를 리턴했을때와 마찬가지로 경로가 생략 되어있다.

** posts-save.mustache **
![](https://images.velog.io/images/donglee99/post/fa7fa1d1-421d-43e6-8d08-eb54432ecea9/%EC%8A%A4%ED%81%AC%EB%A6%B0%EC%83%B7%202021-01-19%20%EC%98%A4%ED%9B%84%208.34.28.png)

* 위에서 ```return``` 으로 인한 ```posts-save.mustache``` 가 불러와져 이때 등록 버튼을 누르게 되면 ```btn-save```라는 api를 호출하게 된다.

** Index.js**
![](https://images.velog.io/images/donglee99/post/dabecfa9-9626-46ec-b063-73c6a5b99a18/%EC%8A%A4%ED%81%AC%EB%A6%B0%EC%83%B7%202021-01-19%20%EC%98%A4%ED%9B%84%208.36.36.png)
* 코드를 설명하기 앞서 ```var main = {...}``` 을 선언한 이유는 만약 또다른 js 파일이 같은 이름의 메서드를 가지고 있다면 마지막으로 실행된 메서드를 덮어 쓰게 되어 이를 방지하기 위해 ```index.js```만의 유효 범위를 만들어 주는것이다.
* 코드 설명 : 먼저 ```btn-save```라는 이벤트가 발생시 ```save function```이 불러와지는데 이때 ```var data``` 에 title, author, content 를 담아 JSON형태로 ```/api/v1/posts``` = > PostsController의 save로 매핑 시킨다.
이후 성공하면 done 실패하면 error 를 띄운다.
---
## 4.4 ** 전체 조회 **
_이 부분은 간단한 머스테치 문법 몇개만 집고 넘어가겠다._
![](https://images.velog.io/images/donglee99/post/14bf760d-6a50-4816-bba6-c3f95a7c898e/%EC%8A%A4%ED%81%AC%EB%A6%B0%EC%83%B7%202021-01-19%20%EC%98%A4%ED%9B%84%208.46.01.png)

* ```{{#posts}}``` = posts 라는 List를 순회한다 (for 문과 비슷하게 posts의 list 인덱스가 끝날때까지 반복)
* ```{{id}}``` = List 에서 뽑아낸 객체의 필드를 사용한다.

![](https://images.velog.io/images/donglee99/post/0a5fdce0-e8fe-4dc1-b470-4d4615812610/%EC%8A%A4%ED%81%AC%EB%A6%B0%EC%83%B7%202021-01-19%20%EC%98%A4%ED%9B%84%208.56.54.png)
* 다음으로 조회의 기능 중 중요하다 여기는 이 부분은 Model을 이용해 Index.mustache 의 ```{{#posts}}``` 이부분으로 ```postsService.findAllDesc()```를 넘겨주는 부분이다. 
---

## 4.5 수정, 삭제
### 수정

![](https://images.velog.io/images/donglee99/post/0ffbb12b-92b9-4ea0-a440-e6a1755e4889/%EC%8A%A4%ED%81%AC%EB%A6%B0%EC%83%B7%202021-01-19%20%EC%98%A4%ED%9B%84%209.02.25.png)
* 위 코드를 보면 ```title``` 을 클리하면 ```/posts/update/{{id}}``` 로 연결되는걸 볼수 있다. 따라 가보면

![](https://images.velog.io/images/donglee99/post/f40f2c27-4658-4061-baf8-1eee45f69f58/%EC%8A%A4%ED%81%AC%EB%A6%B0%EC%83%B7%202021-01-19%20%EC%98%A4%ED%9B%84%209.08.41.png)

* ```IndexController``` 의 ```postsUpdate``` 메서드가 호출 된다.
위 코드를 보면 id 에 해당하는 결과를 찾아 ```PostsResponseDto ```에 담아 ```model``` 을 이용해 ```post```로 넘긴다. 이후 ```posts-update```를 리턴한다.

** posts-update.mustache **
![](https://images.velog.io/images/donglee99/post/96c49ce7-6b1c-47a3-866d-487bef383d60/%EC%8A%A4%ED%81%AC%EB%A6%B0%EC%83%B7%202021-01-19%20%EC%98%A4%ED%9B%84%209.11.14.png)
* 위에서 ```return posts-update```로 인해 ```posts-update.mustache```가 불러와졌다.
* 이 때 ```post```에 담겨온 값들이 매칭되어 화면에 표시되는데 이후 수정완료 버튼을 누르면 ```Index.js``` 의 ```btn-update```로 매핑된다.

_ 이때 진행을 살펴보면 비슷한 형태로 진행되는것을 알수있다. mustache -> IndexController -> mustache -> Index.js -> mustache 형식으로 진행된다는 사실_ 

~~삭제 부분은 이 수정, 조회와 비슷하여 생략하겠다.~~

---