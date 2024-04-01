# SH Pay

## 개요
> 금융결제원 OpenAPI를 이용해 오픈뱅킹 서비스를 제공하여 사용자 정보 등을 제공하고 간편한 금융 서비스를 제공합니다. 사용자의 계좌 정보를 확인할 수 있으며 출금 및 입금을 간편하게 이용할 수 있습니다.<br><br>
> 더불어 ChatGPT 기반 챗봇을 통해 금융지식을 얻을 수 있으며 편리하게 사용자의 계좌 내역을 조회할 수 있습니다.

### 역할 및 기간
- **개인 프로젝트**
- **2024.01.15 ~** 

<br>
<hr>

## 시작 가이드
```html
1. git clone this repository
2. write application-real.yml
3. you need 금융결제원 OpenAPI Account
    3-1. Sign up 금융결제원 사이트
    3-2. add below list at application-real.yml
          bank-tran-id: -
          client-id: -
          client-secret: -
          redirect-uri: -
4. run this project
```
- [금융결제원 OpenAPI 개발자 사이트](https://developers.kftc.or.kr/dev)
- [Check out application-real.yml file](https://github.com/OOOIOOOIO/SHPay/wiki/application%E2%80%90real.md)

<br>
<hr>

## SKills
- **Java 17**
- **Gradle**
- **Spring Boot 3.2.1**
- **Spring Data JPA**
- **Querydsl 5.0**
- **MySQL 8.1.0**
- **Redis**
- **Session(Spring Redis Session)**
- **AWS EC2, RDS**
- **Docker**
- **금융결제원 OpenAPI**
- **OpenAI ChatGPT API** 
- **Domain Driven Design**
- **Layered Architecture**
- **RESTful API**

<br>
<hr>

## Architecture

![img.png](img.png)

## ERD

![img_1.png](img_1.png)

<br>
<hr>

## API 목록
[API 확인하러 가기](https://github.com/OOOIOOOIO/SHPay/tree/main/src/main/java/http)

<br>
<hr>

## 역할 및 기능
- **도메인 개발**
  - **User**
  - **OpenBanking**
  - **Account**

- **금융결제원 OpenAPI를 활용한 API 개발**
  - **사용자 AuthCode 발급**
  - **사용자 토큰 발급(3-legged)**
    - **사용자 정보 조회용**
  - **사용자 토큰 발급(2-legged)**
    - **입-출금이체용**
  - **사용자 토큰 갱신**
  - **사용자 정보 조회**
  - **사용자 계좌 리스트 조회**
  - **사용자 잔액 조회**
  - **거래내역 조회**
  - **출금이체**
  - **입금이체**

<br>
<hr>

## Screenshot
[Screenshot 보러가기](https://github.com/OOOIOOOIO/SHPay/wiki/Screenshot.md)

<br>
<hr>

## Code

#### Redis를 이용한 bank_tran_id 저장

![img_2.png](img_2.png)
- 문제상황
  - bank_tran_id는 은행 거래 고유번호로서 같은 번호일 경우 00시까지 중복을 허용하지 않습니다. 때문에 API 호출 시 고유번호가 유일한지 검사해야 했기에, 매번 DB IO를 타게 될 경우 성능 저하 이슈가 발생할 수 있었습니다. 
- 해결방법 및 실행
  - 이를 해결하고자 Redis의 캐싱 사용해 빠르게 읽어와 고유번호가 유일한지 확인하였습니다. 또한 00시까지 expire time을 설정하여 초기화를 진행하였습니다.
- 성과
  - 이를 통해 불필요한 DB IO를 줄일 수 있었으며 캐싱 기능을 통해 성능을 향상시킬 수 있었습니다.

####

<br>
<hr>

## 회고


### 성과 및 배운점


### 아쉬웠던 점


[//]: # (# 회원가입, 로그인)

[//]: # (- Session 사용)

[//]: # (  - redis sesison 사용)

[//]: # (  - custom resolver를 통해 session 정보 파싱)

[//]: # ()
[//]: # (# 오픈뱅킹)

[//]: # (- DB에 정보 저장)

[//]: # (  - 계좌 정보)

[//]: # (  - 유저 정보)

[//]: # (- ApiClient 클래스를 만들어 모듈화)

[//]: # (  - resttemplate으로 외부 api와 통신)

