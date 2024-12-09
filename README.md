# 이너써클 BE 온보딩 프로젝트

### 기능명세서
- [x] 설문조사 생성 API
  - [x] Post /api/v1/surveys
  - [x] item은 1 ~ 10개까지 포함가능
  - [x] 단일 선택 리스트, 다중 선택 리스트의 경우 선택 할 수 있는 후보를 요청 값에 포함하여야 한다.
- [x]  설문조사 조회 API
  - [x] GET /api/v1/surveys/{surveyId}
- [x]  설문항목 수정 API
  - [x] PATCH /api/v1/surveys/{surveyId}/survey-item/{surveyItemId}
  - [x] 단일 선택 리스트, 다중 선택 리스트의 경우 선택 할 수 있는 후보를 요청 값에 포함하여야 한다.
  - [x] 설문받을 항목이 추가, 변경, 삭제되더라도 기존 응답은 유지되어야 한다
- [x] 설문조사 응답 생성 API
  - [x] POST /{surveyId}/survey-item/{surveyItemId}/{surveyItemVersion}/survey-submission
  - [x] 설문받을 항목에 대응되는 응답 값이 포함.
  - [x] 응답 값은 설문조사의 설문받을 항목과 일치해야만 응답 가능
- [x] 설문 전체 응답 조회 API
  - [x] GET /api/v1/surveys/{surveyId}/response
  - [ ] (advanced) 설문 응답 항목의 이름과 응답 값을 기반으로 검색할 수 있다.

### 응답 데이터 예시
<details>
  <summary>설문 생성 후 조회 응답</summary>

```
{
    "code": 1000,
    "message": "요청 정상 처리되었습니다.",
    "data": {
        "id": 1,
        "name": "Customer Satisfaction Survey",
        "description": "Survey to evaluate customer satisfaction with our services",
        "questionResponses": [
            {
                "id": 7098992976706910464,
                "version": 1,
                "name": "What is your name?",
                "description": "Please provide your full name.",
                "type": "TEXT",
                "required": true,
                "surveyId": 1,
                "options": []
            },
            {
                "id": 8121888864774276615,
                "version": 1,
                "name": "Please describe your experience with our service.",
                "description": "Feel free to provide as much detail as you like.",
                "type": "PARAGRAPH",
                "required": true,
                "surveyId": 1,
                "options": []
            },
            {
                "id": 2397051438838496245,
                "version": 1,
                "name": "How satisfied are you with our service?",
                "description": "Choose one of the options below.",
                "type": "SINGLE_CHOICE_ANSWER",
                "required": true,
                "surveyId": 1,
                "options": [
                    "Very Satisfied",
                    "Satisfied",
                    "Neutral",
                    "Dissatisfied",
                    "Very Dissatisfied"
                ]
            },
            {
                "id": 3588147029527278395,
                "version": 1,
                "name": "Which of the following features did you use?",
                "description": "Select all that apply.",
                "type": "MULTI_CHOICE_ANSWER",
                "required": false,
                "surveyId": 1,
                "options": [
                    "Online Booking",
                    "Customer Support",
                    "Mobile App",
                    "Website"
                ]
            }
        ]
    }
}
```
</details>

<details>
  <summary>설문 수정 후 조회 응답</summary>

```
{
    "code": 1000,
    "message": "요청 정상 처리되었습니다.",
    "data": {
        "id": 7098992976706910464,    -- 단답형이 단항선택형으로 수정
        "version": 2,
        "name": "How satisfied are you with our service?",
        "description": "Choose one of the options below.",
        "type": "SINGLE_CHOICE_ANSWER",
        "required": true,
        "surveyId": 1,
        "options": [
            "Very Satisfied",
            "Satisfied",
            "Neutral",
            "Dissatisfied",
            "Very Dissatisfied"
        ]
    }
}
```
</details>

<details>
  <summary>설문 제출 후 조회 응답</summary>

```
{
    "code": 1000,
    "message": "요청 정상 처리되었습니다.",
    "data": null
}
```
</details>

<details>
  <summary>설문 응답 전체 조회 응답</summary>

```
{
    "code": 1000,
    "message": "요청 정상 처리되었습니다.",
    "data": [
        {
            "surveyItemId": 7098992976706910464,
            "surveyItemVersion": 1,
            "surveyItemName": "What is your name?",
            "surveyItemDescription": "Please provide your full name.",
            "type": "TEXT",
            "required": true,
            "submissionInquiryResponses": []
        },
        {
            "surveyItemId": 8121888864774276615,
            "surveyItemVersion": 1,
            "surveyItemName": "Please describe your experience with our service.",
            "surveyItemDescription": "Feel free to provide as much detail as you like.",
            "type": "PARAGRAPH",
            "required": true,
            "submissionInquiryResponses": []
        },
        {
            "surveyItemId": 2397051438838496245,
            "surveyItemVersion": 1,
            "surveyItemName": "How satisfied are you with our service?",
            "surveyItemDescription": "Choose one of the options below.",
            "type": "SINGLE_CHOICE_ANSWER",
            "required": true,
            "submissionInquiryResponses": []
        },
        {
            "surveyItemId": 3588147029527278395,
            "surveyItemVersion": 1,
            "surveyItemName": "Which of the following features did you use?",
            "surveyItemDescription": "Select all that apply.",
            "type": "MULTI_CHOICE_ANSWER",
            "required": false,
            "submissionInquiryResponses": []
        },
        {
            "surveyItemId": 7098992976706910464,
            "surveyItemVersion": 2,
            "surveyItemName": "How satisfied are you with our service?",
            "surveyItemDescription": "Choose one of the options below.",
            "type": "SINGLE_CHOICE_ANSWER",
            "required": true,
            "submissionInquiryResponses": [
                {
                    "surveySubmissionId": 1,
                    "surveyItemId": 7098992976706910464,
                    "surveyItemVersion": 2,
                    "response": "Very Satisfied"
                }
            ]
        }
    ]
}
```
</details>

### 고민포인트
1. 다양한 타입의 설문항목을 어떻게 요청받을 것인가?
  - 이 부분은 래민님의 코드를 보고 바로 이해해버렸습니다.. 참고하여 리팩토링 진행하겠습니다.

2. 패키지간 의존성은 어떻게 관리할 것인가?
  - 이 부분은 정말 모르겠습니다. 프로젝트의 설계부터 다시 진행되어야한다고 판단하고 있습니다. 관련 문서나 자료를 찾아봐도 패키지간 의존성에 대해 정확히 명시해 놓은 자료가 보이지 않아 혼란스럽습니다. 사실 이 부분은 은탄환이 없이 개발자 역량에 따라 갈리는 부분인가 싶기도 합니다.

3. 도메인과 Jpa 엔티티를 분리하여 차후 JPA를 걷어내야하는 상황이 되어도 도메인과 서비스 컴포넌트는 지킬수 있게 하였는데 과연 의미가 있는 행위였는가?
  - 도메인과 Jpa 엔티티를 분리하여 개발한 것은 처음인데, 도메인 로직은 도메인에, Jpa entity는 persistence 정도만 담당하게 하여 도메인을 특정 기술에 종속되지 않게하는 것은 좋은 방법이였다고 생각합니다. 허나 실제 프로덕트 레벨로 올라가면 JPA를 걷어낸다는 건 프로젝트를 새로 만드는 것과 큰 차이가 없다고 생각하여 이게 과연 의미가 있는가 라는 생각도 듭니다.

4. 설문항목의 응답을 설문항목의 Version 을 두어 관리한 부분
  - 설문항목은 특성 상 1개의 항목에 많은 답변이 존재하게 됩니다. 이러한 답변에 질문을 스냅샷하는게 DB 용량부분에서 좋지않다고 생각하여 설문항목에 Version을 두고 Join으로 풀어나갔습니다. 이 방식에 대해서 피드백 요청드립니다.
  - 또한 설문항목 수정이 빈번하게 일어날 것인가 라는 부분에도 그렇지 않다라고 판단이 되어 많은 Version으로 인해 문제가 발생하긴 어렵다고 판단하였습니다.
  
