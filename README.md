
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
  - `400 Bad Request`: 잘못된 파라미터

---

#### 일정 수정 API
- URL: `PUT /api/schedules/{id}`
- 설명: 해당 ID의 일정 수정(작성자 본인만 수정 가능)
- Request Body
```json
  "title": "수정 제목",
  "todo": "내용 내용"
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
    - `201 OK`: 유저 생성
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
      "createdAt": "2025-04-03T12:00:00",
      "modifiedAt": "2025-04-03T12:00:00"
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
  "createdAt": "2025-04-03T13:00:00",
  "modifiedAt": "2025-04-03T13:00:00"
}
```
- 예외 명세서:
    - `200 OK`: 정상 작성
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
  "createdAt": "2025-04-03T13:00:00",
  "modifiedAt": "2025-04-03T13:30:00"
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
