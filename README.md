# 이너써클 BE 온보딩 프로젝트

## 프로젝트 소개

- 설문조사 기능 요구사항이 구현된 Project 입니다.
- 이 프로젝트는 설문조사 생성, 수정, 응답 제출 및 조회 기능을 제공합니다.

## 프로젝트 구조

```
-- survey
|-- SurveyApplication.java
|-- configuration
|-- controller
|   `-- SurveyController.java
|-- dto
|   |-- common
|   |   |-- Error.java
|   |   `-- ServiceResponse.java
|   |-- request
|   |   |-- SearchSurveyAnswerRequest.java
|   |   |-- SubmitSurveyAnswer.java
|   |   |-- SurveyCreateRequest.java
|   |   |-- SurveyQuestionOptionRequest.java
|   |   |-- SurveyQuestionRequest.java
|   |   |-- SurveyRequest.java
|   |   `-- SurveyUpdateRequest.java
|   `-- response
|       |-- SearchSurveyAnswerResponse.java
|       |-- SurveyAnswerValue.java
|       |-- SurveyAnswerValue.java~
|       |-- SurveyCreateResponse.java
|       |-- SurveyCreateResponse.java~
|       |-- SurveyGenerateResponse.java~
|       `-- SurveyUpdateResponse.java
|-- exception
|   |-- ServiceError.java
|   |-- ServiceExceptionHandler.java
|   |-- SurveyCreationException.java
|   |-- SurveyNotFoundException.java
|   |-- SurveyServiceException.java
|   `-- SurveySubmitValidationException.java
|-- model
|   |-- entity
|   |   |-- Survey.java
|   |   |-- SurveyAnswer.java
|   |   |-- SurveyAnswerSubmission.java
|   |   |-- SurveyQuestion.java
|   |   |-- SurveyQuestionOption.java
|   |   `-- common
|   `-- enums
|       |-- ItemRequired.java
|       `-- SurveyItemType.java
|-- repository
|   |-- SurveyAnswerRepository.java
|   |-- SurveyQuestionOptionRepository.java
|   |-- SurveyQuestionRepository.java
|   `-- SurveyRepository.java
`-- service
    |-- SurveyAnswerService.java
    `-- SurveyService.java
```

### 주요 패키지 설명

- **controller**: RESTful API 엔드포인트를 정의하는 컨트롤러 클래스가 위치합니다.
- **dto**: Data Transfer Object 클래스가 위치합니다.
  - **request**: 클라이언트 요청을 처리하는 DTO 클래스.
  - **response**: 서버 응답을 처리하는 DTO 클래스.
- **exception**: 애플리케이션에서 발생하는 예외를 정의하는 클래스가 위치합니다.
- **model**: entity 및 enum 클래스가 위치합니다.
  - **entity**: JPA entity 클래스.
  - **enums**: 프로젝트에 주요 사용되는 상수들이 있는 enum 클래스.
- **repository**: 데이터베이스와 상호작용하는 JPA repository 인터페이스가 위치합니다.
- **service**: 비즈니스 로직을 처리하는 서비스 클래스가 위치합니다.

## API 명세

### Survey API

- **POST /v1/survey**
  - 설문조사 생성
  - 요청 본문: `SurveyCreateRequest`
  - 응답 본문: `SurveyCreateResponse`

- **PUT /v1/survey**
  - 설문조사 업데이트
  - 요청 본문: `SurveyUpdateRequest`
  - 응답 본문: `SurveyUpdateResponse`

- **POST /v1/survey/{surveyId}/answer**
  - 설문조사 응답 제출
  - 요청 본문: `List<SubmitSurveyAnswer>`
  - 응답 본문: `ServiceResponse<Void>`

- **GET /v1/survey/{surveyId}/answer/all**
  - 설문조사 응답 전체 조회
  - 요청 본문:
    - Parameter
      - questionName (optional) : 질문 이름으로 응답 필터링
      - answerValue (optional) : 답변으로 응답 필터링
  - 응답 본문: `SearchSurveyAnswerResponse`