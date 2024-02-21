# 회원가입, 로그인
- Session 사용
  - redis sesison 사용
  - sessino에 email, id, name 저장
  - custom resolver를 통해 session 정보 파싱

# 오픈뱅킹
- DB에 정보 저장
  - 계좌 정보
  - 유저 정보
- Client 클래스를 만들어 모듈화
  - resttemplate으로 외부 api와 통신
  - 