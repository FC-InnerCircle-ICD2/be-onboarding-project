# 이너써클 BE 온보딩 프로젝트

## 결과물

- survey-0.0.2 : [link](https://drive.google.com/file/d/1NhW3JJawDR2HDqhkIQM0nTDb1lmeH0HD/view?usp=sharing)

## 프로젝트 소개

- 서베이 기능 요구사항이 구현된 Project 입니다.
- 이 프로젝트는 서베이 생성, 수정, 응답 제출 및 조회 기능을 제공합니다.

## 프로젝트 구조

```
survey
├── SurveyApplication.java
├── application
│   └── service
│       ├── SurveyAnswerService.java
│       ├── SurveyService.java
│       └── in
│           └── model
│               └── request
│                   ├── SearchSurveyAnswerRequest.java
│                   ├── SubmitSurveyAnswer.java
│                   ├── SurveyCreateRequest.java
│                   ├── SurveyQuestionRequest.java
│                   └── SurveyUpdateRequest.java
│               └── response
│                   ├── SearchSurveyAnswerResponse.java
│                   ├── SurveyCreateResponse.java
│                   └── SurveyUpdateResponse.java
├── domain
│   ├── common
│   │   └── ServiceResponse.java
│   ├── enums
│   │   └── UpdateType.java
│   ├── exception
│   │   ├── ServiceError.java
│   │   └── SurveyNotFoundException.java
│   └── surveybase
│       ├── Survey.java
│       ├── SurveyQuestion.java
│       └── dto
│           └── SurveyQuestionDto.java
├── infra
│   └── repository
│       └── jpa
│           └── SurveyJpaRepository.java
└── web
    └── controller
        └── SurveyController.java
```

## API 명세

- /v1/swagger-ui/index.html 를 통해 명세를 확인하실 수 있습니다.
  ```html
  http://localhost:8080/v1/swagger-ui/index.html
  ```

### Survey API 요약

- **POST /v1/survey**
  - 서베이 생성
  - 요청 본문: `SurveyCreateRequest`
  - 응답 본문: `SurveyCreateResponse`

- **PUT /v1/survey**
  - 서베이 업데이트
  - 요청 본문: `SurveyUpdateRequest`
  - 응답 본문: `SurveyUpdateResponse`

- **POST /v1/survey/{surveyId}/answer**
  - 서베이 응답 제출
  - 요청 본문: `List<SubmitSurveyAnswer>`
  - 응답 본문: `ServiceResponse<Void>`

- **GET /v1/survey/{surveyId}/answer/all**
  - 서베이 응답 전체 조회
  - 요청 파라미터
      - questionName (optional) : 질문 이름으로 응답 필터링
      - answerValue (optional) : 답변으로 응답 필터링
  - 응답 본문: `SearchSurveyAnswerResponse`

## 사용한 라이브러리

- commons-lang3 : String 검증 및 처리에 활용하기 위해 사용되었습니다.
- rest-assured : 통합 테스트 실행을 위해 추가되었습니다.