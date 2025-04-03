# Scheduler Project - JPA 기반 일정 관리 서비스

## 프로젝트 소개
본 프로젝트는 사용자 중심의 일정 관리 시스템을 JPA 기반으로 구현하는 데 목적이 잇습니다.

회원가입/로그인 기능을 기반으로 일정 생성, 수정, 삭제, 조회는 물론 댓글 기능까지 제공하며, Swagger UI 문서화와 예외처리 일원화 구조도 적용되었습니다.

---

## 기술 스택
- **Language**: Java 17
- **Framework**: Spring Boot 3.2.5
- **ORM**: Spring Data JPA (Query 직접 구현)
- **Database**: MySQL
- **Documentation**: Swagger UI (springdoc-openapi)
- **Build Tool**: Gradle

---

## 실행 방법
```bash
./gradlew bootRun
```
- Swagger 문서 접속 -> 
[http://localhost:8080/swagger-ui/index.html](http://localhost:8080/swagger-ui/index.html)

---

## 주요 기능 요약

### 유저 기능
- 회원가입 / 로그인 (세션 기반)
- 유저 정보 조회 / 수정 / 탈퇴

### 일정 기능
- 일정 등록 / 수정 / 삭제
- 일정 페이징 조회 (정렬 지원)
- 일정 단건 조회

### 댓글 기능
- 댓글 등록 (익명 포함)
- 댓글 수정 / 삭제 (로그인 또는 비밀번호)
- 댓글 페이징 조회

### 인증/보안
- 세션 기반 로그인 상태 관리
- 일정/댓글 수정 및 삭제 시 작성자 권한 검증

### 문서화 및 예외처리
- Swagger UI 기반 API 문서 자동화
- CustomException + ErrorCode 기반 예외 처리 일원화

---

## 데이터베이스 테이블 생성 SQL
`/schedule.sql` 파일 참고

---

## 버전 정보
- `v1.0.0`: 핵심 기능 구현 및 Swagger 적용 완료

---

##  향후 과제 계선 방향
- 인터셉터 기반 인증 처리 적용
- 댓글 좋아요 기능 추가


## API 명세서

---

### 일정 API 명세서

#### 일정 생성 API
- URL: `POST /api/schedules`
- 설명: 새로운 일정 등록
- Request Body
```json
{
  "title": "제목",
  "todo": "내용"
}
```
- Response
```json
{
    "id": 1,
    "userName": "이름",
    "userId": 1,
    "title": "제목",
    "todo": "내용",
    "scheduleCreatedAt": "2025-04-01T00:00:00.000000",
    "scheduleModifiedAt": "2025-04-01T00:00:00.000000"
}
```
- 예외 명세서
  - `201 Created`: 일정 생성
  - `401 Unauthorized`: 로그인하지 않은 사용자

---

#### 일정 전체 조회 API
- URL: `GET /api/schedules?page=1`
- 설명: 전체 일정을 페이지 단위로 조정
- Request Body: X
- Query Parameters:
  - `page`: 페이지 번호
  - `size`: 페이지 크기(기본값: 10)
  - `sort`: 정렬 필드 (기본값: modifiedAt, desc)
- Response
```json
{
  "content": [
    {
      "id": 1,
      "userId": 1,
      "userName": "이름",
      "title": "제목",
      "todo": "내용",
      "scheduleCreatedAt": "2025-04-01T00:00:00.000000",
      "scheduleModifiedAt": "2025-04-01T00:00:00.000000"
    }
  ],
  "totalPages": 2,
  "totalElements": 13,
  "number": 0,
  "size": 10
}
```
- 예외 명세서
  - `200 OK`: 정상 조회
  - `400 Bad Request`: 잘못된 파라미터
---

#### 단건 일정 조회 API
- URL: `GET /api/schedules/{id}`
- 설명: ID 기반 일정 단건 조회
- Request Body: X
- Response
```json
{
  "id": 1,
  "userId": 1,
  "userName": "이름",
  "title": "제목",
  "todo": "내용",
  "scheduleCreatedAt": "2025-04-01T00:00:00.000000",
  "scheduleModifiedAt": "2025-04-01T00:00:00.000000"
}
```
- 예외 명세서
  - `200 OK`: 정상 조회
  - `404 Not Found`: 존재하지 않는 일정 ID

---

#### 일정 수정 API
- URL: `PUT /api/schedules/{id}`
- 설명: 해당 ID의 일정 수정(작성자 본인만 수정 가능)
- Request Body
```json
{
  "title": "수정 제목",
  "todo": "내용 내용"
}
```
- Response
```json
{
    "id": 1,
    "userName": "이름",
    "userId": 1,
    "title": "수정 제목",
    "todo": "수정 일정",
    "scheduleCreatedAt": "2025-04-01T00:00:00.000000",
    "scheduleModifiedAt": "2025-04-01T00:00:00.000000"
}
```
- 예외 명세서
  - `200 OK`: 정상 수정
  - `401 Unauthorized`: 로그인하지 않음
  - `403 Forbidden`: 작성자가 아님
  - `404 Not Found`: 일정 ID 없음
---

#### 일정 삭제 API
- URL: `DELETE /api/schedules/1`
- 설명: 해당 ID의 일정 삭제(작성자 본인만 삭제 가능)
- Request Body : X
- Response : X
- 예외 명세서
  - `204 No Content`: 정상 삭제
  - `401 Unauthorized`: 로그인하지 않음
  - `403 Forbidden`: 작성자가 아님
  - `404 Not Found`: 일정 ID 없음
---

### 유저 API 명세서

#### 회원가입 API
- URL: `POST /api/users/signup`
- 설명: 새로운 유저 등록
- Request Body:
```json
{
  "userName": "사용자 이름",
  "email": "email@example.com",
  "password": "1234"
}
```
- Response:
```json
{
  "id": 1,
  "userName": "사용자 이름",
  "email": "email@example.com",
  "createdAt": "2025-04-01T00:00:00.000000",
  "modifiedAt": "2025-04-01T00:00:00.000000"
}
```
- 예외 명세서:
    - `201 Created`: 유저 생성
    - `400 Bad Request`: 입력값 검증 실패
    - `409 Conflict`: 중복된 이메일 존재

---

#### 로그인 API
- URL: `POST /api/users/login`
- 설명: 로그인 처리 (Session 기반)
- Request Body:
```json
{
  "email": "email@example.com",
  "password": "1234"
}
```
- Response:  
  `200 OK` (응답 바디 없음, 세션 저장만 수행)
- 예외 명세서:
    - `200 OK`: 로그인 성공
    - `401 Unauthorized`: 이메일 또는 비밀번호 불일치

---

#### 내 정보 조회 API
- URL: `GET /api/users/me`
- 설명: 로그인된 유저 정보 조회
- Request Body: 없음
- Response:
```json
{
  "id": 1,
  "userName": "사용자 이름",
  "email": "email@example.com",
  "createdAt": "2025-04-01T00:00:00.000000",
  "modifiedAt": "2025-04-01T00:00:00.000000"
}
```
- 예외 명세서:
    - `200 OK`: 조회 성공
    - `401 Unauthorized`: 로그인하지 않은 사용자

---

#### 유저 정보 수정 API
- URL: `PUT /api/users/me`
- 설명: 유저 정보 수정 (현재 비밀번호 필요)
- Request Body:
```json
{
  "userName": "새 이름",
  "email": "new@email.com",
  "password": "현재 비밀번호",
  "newPassword": "변경할 비밀번호"
}
```
- Response:
```json
{
  "id": 1,
  "userName": "새 이름",
  "email": "new@email.com",
  "createdAt": "2025-04-01T00:00:00.000000",
  "modifiedAt": "2025-04-01T01:00:00.000000"
}
```
- 예외 명세서:
    - `200 OK`: 수정 성공
    - `401 Unauthorized`: 로그인하지 않음
    - `403 Forbidden`: 비밀번호 불일치

---

#### 유저 탈퇴 API
- URL: `DELETE /api/users/me`
- 설명: 로그인된 유저 탈퇴 (비밀번호 필요)
- Request Body:
```json
{
  "password": "현재 비밀번호"
}
```
- Response:  
  `204 No Content`
- 예외 명세서:
    - `204 No Content`: 정상 탈퇴
    - `401 Unauthorized`: 로그인하지 않음
    - `403 Forbidden`: 비밀번호 불일치

---

### 댓글 API 명세서

#### 댓글 목록 조회 API
- URL: `GET /api/comments`
- 설명: 특정 일정 ID에 해당하는 댓글 목록 조회 (페이징 지원)
- Request Body: 없음
- Query Parameters:
    - `scheduleId`: Long (필수)
    - `page`: 페이지 번호 (기본값 0)
    - `size`: 페이지 크기 (기본값 5)
    - `sort`: 정렬 필드 (기본값: createdAt, asc)
- Response:
```json
{
  "content": [
    {
      "id": 1,
      "authorName": "익명",
      "comment": "좋은 일정입니다!",
      "createdAt": "2025-04-01T00:00:00.000000",
      "modifiedAt": "2025-04-01T01:00:00.000000"
    }
  ],
  "totalPages": 2,
  "totalElements": 8,
  "number": 0,
  "size": 5
}
```
- 예외 명세서:
    - `200 OK`: 정상 조회
    - `400 Bad Request`: 파라미터 누락 또는 형식 오류

---

#### 댓글 작성 API
- URL: `POST /api/comments`
- 설명: 댓글 작성 (익명 포함 가능)
- Request Body:
```json
{
  "scheduleId": 1,
  "authorName": "익명",
  "comment": "댓글 내용",
  "password": "1234"
}
```
- Response:
```json
{
  "id": 10,
  "authorName": "익명",
  "comment": "댓글 내용",
  "createdAt": "2025-04-01T00:00:00.000000",
  "modifiedAt": "2025-04-01T01:00:00.000000"
}
```
- 예외 명세서:
    - `201 Created`: 댓글 생성
    - `400 Bad Request`: 유효성 검증 실패
    - `404 Not Found`: 존재하지 않는 일정 ID

---

#### 댓글 수정 API
- URL: `PUT /api/comments/{commentId}`
- 설명: 특정 댓글 수정 (로그인 또는 비밀번호 필요)
- Request Body:
```json
{
  "comment": "수정된 댓글",
  "password": "1234"
}
```
- Response:
```json
{
  "id": 10,
  "authorName": "익명",
  "comment": "수정된 댓글",
  "createdAt": "2025-04-01T00:00:00.000000",
  "modifiedAt": "2025-04-01T01:00:00.000000"
}
```
- 예외 명세서:
    - `200 OK`: 수정 성공
    - `403 Forbidden`: 비밀번호 불일치 또는 권한 없음
    - `404 Not Found`: 댓글 ID 없음

---

#### 댓글 삭제 API
- URL: `DELETE /api/comments/{commentId}`
- 설명: 특정 댓글 삭제 (로그인 또는 비밀번호 필요)
- Request Body:
```json
{
  "password": "1234"
}
```
- Response:  
  `204 No Content`
- 예외 명세서:
    - `204 No Content`: 정상 삭제
    - `403 Forbidden`: 비밀번호 불일치 또는 권한 없음
    - `404 Not Found`: 댓글 ID 없음

---

## ERD
- 이미지
![ERD.jpg](ERD.jpg)

### 1. `Schedules` 테이블

컬럼명 | 타입 | 제약조건
:-- | :--  | :--
id | BIGINT | PK, AUTO_INCREMENT
title | VARCHAR(100) | NOT NULL
todo | TEXT | NOT NULL
user_id | BIGINT | FK (users.id)
created_at | DATETIME | DEFAULT CURRENT_TIMESTAMP
modified_at | DATETIME | ON UPDATE CURRENT_TIMESTAMP

<br>

### 2. `User` 테이블

컬럼명 | 타입 | 제약조건
:-- | :--  | :--
id | BIGINT | PK, AUTO_INCREMENT
title | VARCHAR(100) | NOT NULL
todo | TEXT | NOT NULL
user_id | BIGINT | FK (users.id)
created_at | DATETIME | DEFAULT CURRENT_TIMESTAMP
modified_at | DATETIME | ON UPDATE CURRENT_TIMESTAMP

<br>

### 3. `Comment` 테이블

컬럼명 | 타입 | 제약조건
:-- | :--  | :--
id | BIGINT | PK, AUTO_INCREMENT
schedule_id | BIGINT | FK (schedules.id)
author_name | VARCHAR(30) | NOT NULL
comment | TEXT | NOT NULL
password | VARCHAR(255) | NULL (로그인 시 NULL)
created_at | DATETIME | DEFAULT CURRENT_TIMESTAMP
modified_at | DATETIME | ON UPDATE CURRENT_TIMESTAMP

<br>

### 4. 관계 요약
#### - `users (1)` ↔ `schedules (M)`
#### - `schedules (1)`  ↔ `comment (M)`
#### - `comment`은 로그인 여부 상관 없이 작성 가능 함으로 `user`와 직접 연결하지 않음

---

## SQL

```sql

-- users 테이블
CREATE TABLE users (
                       id BIGINT AUTO_INCREMENT PRIMARY KEY,
                       user_name VARCHAR(20) NOT NULL,
                       email VARCHAR(100) NOT NULL UNIQUE,
                       password VARCHAR(255) NOT NULL,
                       created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
                       modified_at DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

-- schedules 테이블
CREATE TABLE schedules (
                           id BIGINT AUTO_INCREMENT PRIMARY KEY,
                           title VARCHAR(100) NOT NULL,
                           todo TEXT NOT NULL,
                           user_id BIGINT NOT NULL,
                           created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
                           modified_at DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
                           CONSTRAINT fk_schedule_user FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE
);

-- comments 테이블
CREATE TABLE comments (
                          id BIGINT AUTO_INCREMENT PRIMARY KEY,
                          schedule_id BIGINT NOT NULL,
                          author_name VARCHAR(30) NOT NULL,
                          comment TEXT NOT NULL,
                          password VARCHAR(255),
                          created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
                          modified_at DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
                          CONSTRAINT fk_comment_schedule FOREIGN KEY (schedule_id) REFERENCES schedules(id) ON DELETE CASCADE
);
```
