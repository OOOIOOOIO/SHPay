# SH Pay

## 개요
> 금융결제원 OpenAPI를 이용해 오픈뱅킹 서비스를 제공하여 사용자 정보 등을 제공하고 간편한 금융 서비스를 제공합니다. 사용자의 계좌 정보를 확인할 수 있으며 출금 및 입금을 간편하게 이용할 수 있습니다.<br>
> 더불어 ChatGPT 기반 챗봇을 통해 금융지식을 얻을 수 있으며 편리하게 사용자의 계좌 내역을 조회할 수 있습니다.

### 역할 및 기간
- **개인 프로젝트**
- **2024.01.15 ~** 

## 시작 가이드
- clone해서 실행하려고 할 때 요구사항과 버전들, 설치 방법
-

## SKills
- **Java 17**
- **Gradle**
- **Spring Boot 3.x**
- **Spring Data JPA**
- **Querydsl 5.0**
- **MySQL 8.x**
- **Redis**
- **Session(Spring Redis Session)**
- **AWS EC2, RDS, S3, Codedeploy**
- **Travis CI**
- **Docker**
- **금융결제원 OpenAPI**
- **OpenAI ChatGPT API** 
- **Domain Driven Design**
- **Layered Architecture**
- **RESTful API**





## Architecture

## ERD


## API 명세서



## 주요 Function
- 사용한 이유
- 성능, lighthouse

## Screenshot & Code

- 문제상황
- 해결방법 및 실행
- 성과

## 회고

### 성과 및 배운점

### 아쉬웠던 점

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

