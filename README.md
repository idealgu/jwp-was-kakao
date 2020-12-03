
# 웹 애플리케이션 서버
## 진행 방법
* 웹 애플리케이션 서버 요구사항을 파악한다.
* 요구사항에 대한 구현을 완료한 후 자신의 github 아이디에 해당하는 브랜치에 Pull Request(이하 PR)를 통해 코드 리뷰 요청을 한다.
* 코드 리뷰 피드백에 대한 개선 작업을 하고 다시 PUSH한다.
* 모든 피드백을 완료하면 다음 단계를 도전하고 앞의 과정을 반복한다.

## 온라인 코드 리뷰 과정
* [텍스트와 이미지로 살펴보는 온라인 코드 리뷰 과정](https://github.com/next-step/nextstep-docs/tree/master/codereview)


## Todo
* WAS 모듈과 Application 모듈 분리
* HttpResponse 처리책임을 Controller 와 RequestHandler 에서 분리
* HttpResponse.forward() 매소드 추가. static resource 를 읽어들여 client 에 내려준다
* HttpResponse 테스트 작성


## Done
* ~~모든 request header 출력~~
* ~~HttpRequest 에서 Request-Line 추출~~
* ~~Request URI 를 로딩하여 응답으로 내려주기~~
* ~~static resource 요청처리~~
* ~~회원가입요청 처리~~
* ~~POST 요청처리~~
* ~~response message 출력~~
* ~~회원가입 완료 후 index.html 페이지로 자동으로 이동 ( redirect )~~
* ~~로그인성공시 index.html 로 실패시 user/login_failed.html 페이지로 이동~~
* ~~로그인성공시 쿠키설정~~
* ~~인증이 필요한 페이지는 인증정보가 있어야 접근 할 수 있다~~
