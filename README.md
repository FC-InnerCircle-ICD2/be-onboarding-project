# 프로젝트 구조

### 멀티 모듈 구성
- **Core**: 공통 예외 처리, 글로벌 설정, 유틸리티
- **Survey**: 설문조사 생성 및 수정 로직
- **Response**: 설문 조사 응답 제출 및 조회 로직
- **API**: REST API 엔드포인트 정의

--- 

### 기술 스택
- Java 17
- Spring Boot 3.x
- Gradle
- H2 Database
- Redis: 동시성 제어를 위한 캐싱
- Swagger: API 문서화 및 테스트
- JUnit: 단위 테스트 및 통합 테스트 작성

비고: Redis 를 써서 localhost:6379 로 레디스가 동작 중 이어야합니다. </br>
core 모듈에 docker 패키지 내부에 docker-compose.yml 존재합니다.

---

### API 문서 확인
```text
http://localhost:8080/swagger-ui/index.html
```

### Jar 파일
1. [Executable JAR 파일 다운로드](https://drive.google.com/file/d/1cp6uzi5TbAHRu_ywFrX_YFjeEGFOQNKr/view?usp=drive_link)
2. Jar 파일 실행:
  ```BASH
    java -jar api-1.0.0.jar
  ```