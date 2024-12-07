# 이너써클 BE 온보딩 프로젝트

## jar 파일 경로
- [command](https://github.com/Raemerrr/be-onboarding-project/blob/main/command/build/libs/command-0.0.1-SNAPSHOT.jar)
- [query](https://github.com/Raemerrr/be-onboarding-project/blob/main/query/build/libs/query-0.0.1-SNAPSHOT.jar)

## 방향성
페이징이나 자세한 제약 조건이 없기에 나름대로 제약 조건을 설정하고 진행하였습니다~!
최대한 연관 관계를 맺지 않으려 하였습니다. 🥲

* common: 공통으로 필요한 내용을 담은 모듈입니다. (그러나.. 큰 의미 없음)
* command: Survey 명령(생성, 수정, 삭제(?)) 관련 로직을 모아둔 모듈입니다.
* query: Survey 조회 관련 로직을 모아둔 모듈입니다.

## 응답 예시
<details>
<summary>설문 생성 후 조회 응답 예시</summary>

```json
{
  "id": "284bfd69-bf89-4c33-873b-60adbef3c215",
  "title": "설문조사 이름",
  "description": "설문조사 설명",
  "questions": [
    {
      "id": "f74c9b03-8335-4a0f-a68a-9f8a39694171",
      "name": "단일 선택 리스트 설문조사 이름",
      "description": "단일 선택 리스트 설문조사 설명",
      "required": false,
      "type": "SINGLE_CHOICE",
      "options": ["option1"]
    },
    {
      "id": "fb1d0b48-bb85-4e72-98bf-9f6e084236d8",
      "name": "다중 선택 리스트 설문조사 이름",
      "description": "다중 선택 리스트 설문조사 설명",
      "required": true,
      "type": "MULTIPLE_CHOICE",
      "options": ["option1", "option2", "option3"]
    },
    {
      "id": "87fb4d51-4afa-472d-9a84-c7a193017380",
      "name": "단답형 설문조사 이름",
      "description": "단답형 설문조사 설명",
      "required": true,
      "type": "SHORT_TEXT",
      "options": []
    },
    {
      "id": "e8fe4b17-39ef-421b-954e-9d79ebcdf7e4",
      "name": "장문형 설문조사 이름",
      "description": "장문형 설문조사 설명",
      "required": true,
      "type": "LONG_TEXT",
      "options": []
    }
  ],
  "responses": []
}

```
</details>

<details>
<summary>설문 수정 후 조회 응답 예시</summary>

```json
{
  "id": "284bfd69-bf89-4c33-873b-60adbef3c215",
  "title": "설문조사 이름",
  "description": "설문조사 설명",
  "questions": [
    {
      "id": "f74c9b03-8335-4a0f-a68a-9f8a39694171",
      "name": "단일 선택 리스트 설문조사 이름",
      "description": "단일 선택 리스트 설문조사 설명",
      "required": false,
      "type": "SINGLE_CHOICE",
      "options": ["option1"]
    },
    {
      "id": "fb1d0b48-bb85-4e72-98bf-9f6e084236d8",
      "name": "다중 선택 리스트 설문조사 이름",
      "description": "다중 선택 리스트 설문조사 설명",
      "required": true,
      "type": "MULTIPLE_CHOICE",
      "options": ["option1", "option2", "변경한 옵션"]
    },
    {
      "id": "87fb4d51-4afa-472d-9a84-c7a193017380",
      "name": "단답형 설문조사 이름",
      "description": "단답형 설문조사 설명",
      "required": false,
      "type": "SHORT_TEXT",
      "options": []
    },
    {
      "id": "e8fe4b17-39ef-421b-954e-9d79ebcdf7e4",
      "name": "장문형 설문조사 이름",
      "description": "장문형 설문조사 설명 수정",
      "required": true,
      "type": "LONG_TEXT",
      "options": []
    }
  ],
  "responses": []
}

```
</details>

<details>
<summary>설문 응답 후 조회 응답 예시</summary>

> 설문 항목이 수정 가능한 요구사항이 있어 태양님의 피드백을 받아, QuestionSnapshot을 저장하는 형태로 변경하였음.
```json
{
  "id": "495312e0-96e1-40c8-9105-7910d452c3d1",
  "title": "설문조사 이름",
  "description": "설문조사 설명",
  "responses": [
    {
      "id": "efa3e146-0375-46f1-a78b-2a21cb063594",
      "answers": [
        {
          "id": "968fe84b-b3aa-4811-b6b4-98d5a818972e",
          "question": {
            "id": "978d2112-5e91-40f0-a675-ca054b591d58",
            "name": "단일 선택 리스트 설문조사 이름",
            "description": "단일 선택 리스트 설문조사 설명",
            "required": true,
            "type": "SINGLE_CHOICE",
            "options": ["option1"]
          },
          "content": {
            "type": "SINGLE_CHOICE",
            "selectedOption": "option1"
          }
        },
        {
          "id": "807dec5f-9c04-493c-a8e3-781d00aa9ff9",
          "question": {
            "id": "b33851ad-14b0-440c-a781-42d90edd413d",
            "name": "다중 선택 리스트 설문조사 이름",
            "description": "다중 선택 리스트 설문조사 설명",
            "required": true,
            "type": "MULTIPLE_CHOICE",
            "options": ["option1", "option2", "option3"]
          },
          "content": {
            "type": "MULTIPLE_CHOICE",
            "selectedOptions": ["option1", "option2"]
          }
        },
        {
          "id": "8e37896a-860f-4b0b-9ed3-f4923d8a27be",
          "question": {
            "id": "ade1b2bd-691c-44b6-b3f9-4fef1a207a69",
            "name": "단답형 설문조사 이름",
            "description": "단답형 설문조사 설명",
            "required": true,
            "type": "SHORT_TEXT",
            "options": []
          },
          "content": {
            "type": "SHORT_TEXT",
            "text": "단답형 설문조사 응답"
          }
        },
        {
          "id": "dbd43615-4df3-4878-a10a-53c10049ca26",
          "question": {
            "id": "592633c8-7239-4df9-aa2c-eb1ee3cbc16a",
            "name": "장문형 설문조사 이름",
            "description": "장문형 설문조사 설명",
            "required": true,
            "type": "LONG_TEXT",
            "options": []
          },
          "content": {
            "type": "LONG_TEXT",
            "text": "장문형 설문조사 응답"
          }
        }
      ]
    }
  ]
}
```
</details>

<details>
<summary>설문 응답 검색 응답 예시</summary>

> `option1` 검색 시 응답 값 혹은 설문 항목과 비교하여 검색함.
```json
[
  {
    "id": "cddde811-b1ed-488c-9513-8728f987ca90",
    "question": {
      "id": "94e5db31-f8f1-420a-8f52-d7ca53a0645c",
      "name": "단일 선택 리스트 설문조사 이름",
      "description": "단일 선택 리스트 설문조사 설명",
      "required": true,
      "type": "SINGLE_CHOICE",
      "options": [
        "option1"
      ]
    },
    "content": {
      "type": "SINGLE_CHOICE",
      "selectedOption": "option1"
    }
  },
  {
    "id": "ef129bd4-8e9d-4629-afe2-d4b27c3143c6",
    "question": {
      "id": "cc1a3c2d-a36c-465a-b1be-8836e08f5229",
      "name": "다중 선택 리스트 설문조사 이름",
      "description": "다중 선택 리스트 설문조사 설명",
      "required": true,
      "type": "MULTIPLE_CHOICE",
      "options": [
        "option1",
        "option2",
        "option3"
      ]
    },
    "content": {
      "type": "MULTIPLE_CHOICE",
      "selectedOptions": [
        "option1",
        "option2"
      ]
    }
  }
]
```
</details>

### 기능 명세서
- [x] 설문조사 생성 API
  - [x] POST /c/surveys
  - [x] item은 1 ~ 10개까지 포함 가능
  - [x] 단일 선택 리스트, 다중 선택 리스트의 경우 선택 할 수 있는 후보를 요청 값에 포함하여야 한다.
- [x] 설문조사 조회 API
  - [x] GET /q/surveys/{surveyId}
- [x] 설문조사 수정 API
  - [x] PATCH /c/surveys/{surveyId}
  - [x] 단일 선택 리스트, 다중 선택 리스트의 경우 선택 할 수 있는 후보를 요청 값에 포함하여야 한다.
  - [x] 설문받을 항목이 추가, 변경, 삭제되더라도 기존 응답은 유지되어야 한다.
- [x] 설문조사 응답 생성 API
  - [x] POST /surveys/{surveyId}/responses
  - [x] 설문받을 항목에 대응되는 응답 값이 포함.
  - [x] 응답 값은 설문조사의 설문받을 항목과 일치해야만 응답 가능.
- [x] 설문조사 응답 조회 API
  - [x] GET /q/surveys/{id}/responses
  - [x] 요청 값에는 설문조사 식별자가 포함됨.
  - [x] 해당 설문조사의 전체 응답을 조회됨.
  - [x] (Advanced) 설문 응답 항목의 이름과 응답 값을 기반으로 검색할 수 있다.
    - GET /q/answers/search?query=option1

## 온보딩 프로젝트의 목적

- 공통된 내용과 기술스택을 이용한 기술 경험 수준 평가
- 최대한 과거에 경험 해보시지 못한 주제를 선정하여 기술적으로 챌린지 하실 수 있게끔 구성
- 점수를 매기거나 합격과 불합격을 구분하는 목적은 아님.
- 서로가 서로에게 도움 줄 수 있는 각자의 강점을 파악하기 위하여 진행
  - 꼼꼼한 요구사항 분석과 문서화
  - 새로운 기술적 접근 방식
  - 안정적인 아키텍처 구성

## Introduction

- “설문조사 서비스"를 구현하려고 합니다.
- “온보딩 프로젝트 기능 요구사항"을 구현해 주시기 바랍니다.
- 온보딩 프로젝트 기능 요구 사항 및 기술 요구사항이 충족되지 않은 결과물은 코드레벨 평가를 진행하지 않습니다.
- 아래의 “코드레벨 평가항목"으로 코드를 평가합니다.
- “설문조사 서비스"의 API 명세를 함께 제출해주세요.
- 우대사항은 직접 구현하지 않더라도 README에 적용 방법 등을 구체적으로 명시해주시는 것으로 대체 할 수 있습니다.

## 온보딩 프로젝트 기능 요구사항

### 개요

- “설문조사 서비스”는 설문조사 양식을 만들고, 만들어진 양식을 기반으로 응답을 받을 수 있는 서비스입니다. (e.g. Google Forms, Tally, Typeform)
- 설문조사 양식은 [설문조사 이름], [설문조사 설명], [설문 받을 항목]의 구성으로 이루어져있습니다.
- [설문 받을 항목]은 [항목 이름], [항목 설명], [항목 입력 형태], [항목 필수 여부]의 구성으로 이루어져있습니다.
- [항목 입력 형태]는 [단답형], [장문형], [단일 선택 리스트], [다중 선택 리스트]의 구성으로 이루어져있습니다.


### 1. 설문조사 생성 API

- 요청 값에는 [설문조사 이름], [설문조사 설명], [설문 받을 항목]이 포함됩니다.
- [설문 받을 항목]은 [항목 이름], [항목 설명], [항목 입력 형태], [항목 필수 여부]의 구성으로 이루어져있습니다.
- [항목 입력 형태]는 [단답형], [장문형], [단일 선택 리스트], [다중 선택 리스트]의 구성으로 이루어져있습니다.
    - [단일 선택 리스트], [다중 선택 리스트]의 경우 선택 할 수 있는 후보를 요청 값에 포함하여야 합니다.
- [설문 받을 항목]은 1개 ~ 10개까지 포함 할 수 있습니다.


### 2. 설문조사 수정 API

- 요청 값에는 [설문조사 이름], [설문조사 설명], [설문 받을 항목]이 포함됩니다.
- [설문 받을 항목]은 [항목 이름], [항목 설명], [항목 입력 형태], [항목 필수 여부]의 구성으로 이루어져있습니다.
- [항목 입력 형태]는 [단답형], [장문형], [단일 선택 리스트], [다중 선택 리스트]의 구성으로 이루어져있습니다.
    - [단일 선택 리스트], [다중 선택 리스트]의 경우 선택 할 수 있는 후보를 요청 값에 포함하여야 합니다.
- [설문 받을 항목]이 추가/변경/삭제 되더라도 기존 응답은 유지되어야 합니다.


### 3. 설문조사 응답 제출 API

- 요청 값에는 [설문 받을 항목]에 대응되는 응답 값이 포함됩니다.
- 응답 값은 설문조사의 [설문 받을 항목]과 일치해야만 응답 할 수 있습니다.


### 4. 설문조사 응답 조회 API

- 요청 값에는 [설문조사 식별자]가 포함됩니다.
- 해당 설문조사의 전체 응답을 조회합니다.
- **(Advanced)** 설문 응답 항목의 이름과 응답 값을 기반으로 검색 할 수 있습니다.

<br/>

> 💡 주어진 요구사항 이외의 추가 기능 구현에 대한 제약은 없으며, 새롭게 구현한 기능이 있을 경우 README 파일에 기재 해주세요.

<br/>

## 기술 요구 사항

- JAVA 11 이상 또는 Kotlin 사용
- Spring Boot 사용
- Gradle 기반의 프로젝트
- 온보딩 프로젝트 기능 요구사항은 서버(백엔드)에서 구현/처리
- 구현을 보여줄 수 있는 화면(프론트엔드)은 구현 금지
- DB는 인메모리 RDBMS(예: h2)를 사용하며 DB 컨트롤은 JPA로 구현. (NoSQL 사용 X)
- API의 HTTP Method는 자유롭게 선택해주세요.
- 에러 응답, 에러 코드는 자유롭게 정의해주세요.
- 외부 라이브러리 및 오픈소스 사용 가능 (단, README 파일에 사용한 오픈 소스와 사용 목적을 명확히 명시해 주세요.)

## 코드레벨 평가 항목

온보딩 프로젝트는 다음 내용을 고려하여 평가 하게 됩니다.

- 프로젝트 구성 방법 및 관련된 시스템 아키텍처 설계 방법이 적절한가?
- 작성한 애플리케이션 코드의 가독성이 좋고 의도가 명확한가?
    - e.g. 불필요한(사용되지) 않는 코드의 존재 여부, 일정한 코드 컨벤션 등
- 작성한 테스트 코드는 적절한 범위의 테스트를 수행하고 있는가?
    - e.g. 유닛/통합 테스트 등
- Spring Boot의 기능을 적절히 사용하고 있는가?
- 예외 처리(Exception Handling)은 적절히 수행하고 있는가?

## 우대사항

- 프로젝트 구성 추가 요건: 멀티 모듈 구성 및 모듈간 의존성 제약
- Back-end 추가 요건
    - 트래픽이 많고, 저장되어 있는 데이터가 많음을 염두에 둔 구현
    - 다수의 서버, 인스턴스에서 동작할 수 있음을 염두에 둔 구현
    - 동시성 이슈가 발생할 수 있는 부분을 염두에 둔 구현
 
## 온보딩 프로젝트 제출 방식

### 소스코드

- 본 Repository에 main 브랜치를 포크하여 작업을 시작합니다.
- SpringBoot 프로젝트를 신규로 설정하고, 개인별로 main 브랜치에 PR을 공개적으로 먼저 작성한 후에 작업을 시작합니다.
  - 이때 PR에는 WIP 레이블을 붙여서 작업 중임을 알게 해주세요.
  - 코드를 마무리해서 리뷰받을 준비가 되면 WIP 레이블을 제거하고, Needs Review 레이블을 추가해주세요.
  - 피드백을 받은 후 추가 작업을 진행할 때는 WIP 레이블을 다시 추가하고 Needs Review 레이블을 제거해주세요.
- 최소 기능 단위로 완성할 때 마다 커밋합니다.

### 기능 점검을 위한 빌드 결과물

빌드 결과물을 Executable jar 형태로 만들어 위 Branch에 함께 업로드 하시고, README에 다운로드 링크 정보를 넣어주시기 바랍니다. GitHub의 용량 문제로 업로드가 안되는 경우 다른 곳(개인 구글 드라이브 등)에 업로드 한 후 해당 다운로드 링크 정보를 README에 넣어주셔도 됩니다.

해당 파일을 다운로드 및 실행(e.g. java -jar project.jar)하여 요구 사항 기능 검증을 진행하게 됩니다. 해당 파일을 다운로드할 수 없거나 실행 시 에러가 발생하는 경우에는 기능 점검을 진행하지 않습니다. 온보딩 프로젝트 제출 전 해당 실행 파일 다운로드 및 정상 동작 여부를 체크해 주시기 바랍니다.
