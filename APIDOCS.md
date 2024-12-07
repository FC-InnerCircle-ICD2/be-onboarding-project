# 설문조사 API 문서

## 개요

이 API는 설문조사를 생성, 수정, 조회하는 기능을 제공합니다.

### 1. **설문조사 추가**

- **엔드포인트**: `/api/v1/survey`
- **메서드**: `POST`
- **설명**: 설문조사를 추가합니다.
- **요청 본문**:
    - `name`: 설문조사의 이름
    - `description`: 설문조사의 설명
    - `itemList`: 설문조사의 항목 목록
    - `itemList.name`: 설문조사의 항목 이름
    - `itemList.description`: 설문조사의 항목 설명
    - `itemList.required`: 설문조사의 항목 필수 여부
    - `itemList.type`: 설문조사의 항목 type ( SHORT_ANSWER,LONG_ANSWER,SINGLE_CHOICE,MULTI_CHOICE)
        - `itemList.option` : SINGLE_CHOICE 일 경우 String값으로 전달
        - `itemList.options` : MULTI_CHOICE 일 경우 List<String>값으로 전달
- **응답**:
    - **상태 코드**: `200 OK`

#### 예시 응답:

```json
{
  "name": "test",
  "description": "test",
  "itemList": [
    {
      "name": "test1",
      "description": "test",
      "required": true,
      "type": "SHORT_ANSWER"
    },
    {
      "name": "test2",
      "description": "test",
      "required": true,
      "type": "LONG_ANSWER"
    },
    {
      "name": "test3",
      "description": "test",
      "required": false,
      "type": "SINGLE_CHOICE",
      "option": "das"
    },
    {
      "name": "test4",
      "description": "test",
      "required": true,
      "type": "MULTIPLE_CHOICE",
      "options": [
        ""
      ]
    }
  ]
}
```

### 2. **설문조사 정보 조회 (ID로 조회)**

- **엔드포인트**: `/api/v1/survey/{surveyId}`
- **메서드**: `GET`
- **설명**: 설문조사의 ID를 통해 설문조사 정보를 조회합니다.
- **경로 파라미터**:
    - `surveyId` (필수): 조회할 설문조사의 고유 ID

- **응답**:
    - **상태 코드**: `200 OK`

#### 예시 응답:

```json
{
  "name": "test",
  "description": "test",
  "version": 1,
  "itemList": [
    {
      "name": "test1",
      "description": "test",
      "required": true,
      "type": "SHORT_ANSWER"
    },
    {
      "name": "test2",
      "description": "test",
      "required": true,
      "type": "LONG_ANSWER"
    },
    {
      "name": "test3",
      "description": "test",
      "required": false,
      "type": "SINGLE_CHOICE",
      "option": "test"
    },
    {
      "name": "test4",
      "description": "test",
      "required": true,
      "type": "MULTIPLE_CHOICE",
      "options": [
        "test",
        "test"
      ]
    }
  ]
}
```

### 3. **설문조사 정보 수정 (ID로 수정)**

- **엔드포인트**: `/api/v1/survey/{surveyId}`
- **메서드**: `PUT`
- **설명**: 설문조사의 ID를 통해 설문조사 정보를 수정합니다.(Survey Version 추가)
- **경로 파라미터**:
    - `surveyId` (필수): 조회할 설문조사의 고유 ID

- **응답**:
    - **상태 코드**: `200 OK`

- **요청 본문**:
    - `name`: 설문조사의 이름
    - `description`: 설문조사의 설명
    - `itemList`: 설문조사의 항목 목록
    - `itemList.name`: 설문조사의 항목 이름
    - `itemList.description`: 설문조사의 항목 설명
    - `itemList.required`: 설문조사의 항목 필수 여부
    - `itemList.type`: 설문조사의 항목 type ( SHORT_ANSWER,LONG_ANSWER,SINGLE_CHOICE,MULTI_CHOICE)
        - `itemList.option` : SINGLE_CHOICE 일 경우 String값으로 전달
        - `itemList.options` : MULTI_CHOICE 일 경우 List<String>값으로 전달

#### 예시 요청:

```json
{
  "name": "test",
  "description": "test",
  "itemList": [
    {
      "name": "test1",
      "description": "test",
      "required": true,
      "type": "SHORT_ANSWER"
    },
    {
      "name": "test2",
      "description": "test",
      "required": true,
      "type": "LONG_ANSWER"
    },
    {
      "name": "test3",
      "description": "test",
      "required": false,
      "type": "SINGLE_CHOICE",
      "option": "test"
    },
    {
      "name": "test4",
      "description": "test",
      "required": true,
      "type": "MULTIPLE_CHOICE",
      "options": [
        "test",
        "test123"
      ]
    }
  ]
}
```

### 4. **설문조사 답변 추가**

- **엔드포인트**: `/api/v1/survey/{surveyId}`
- **메서드**: `POST`
- **설명**: 설문조사의 답변을 추가합니다.
- **경로 파라미터**:
    - `surveyId` (필수): 설문조사 ID
- **요청 본문**:
    - `name`: 설문조사의 이름
    - `description`: 설문조사의 설명
    - `itemList`: 설문조사의 항목 목록
    - `itemList.name`: 설문조사의 항목 이름
    - `itemList.description`: 설문조사의 항목 설명
    - `itemList.required`: 설문조사의 항목 필수 여부
    - `itemList.type`: 설문조사의 항목 type ( SHORT_ANSWER,LONG_ANSWER,SINGLE_CHOICE,MULTI_CHOICE)
        - `itemList.option` : SINGLE_CHOICE 일 경우 String값으로 전달
        - `itemList.options` : MULTI_CHOICE 일 경우 List<String>값으로 전달
- **응답**:
    - **상태 코드**: `200 OK`

#### 예시요청

```json
[
  {
    "itemId": "1",
    "type": "SHORT_ANSWER",
    "answer": "123123"
  },
  {
    "itemId": "2",
    "type": "LONG_ANSWER",
    "answer": "1"
  },
  {
    "itemId": "3",
    "type": "SINGLE_CHOICE",
    "option": "test"
  },
  {
    "itemId": "4",
    "type": "MULTI_CHOICE",
    "options": [
      "test"
    ]
  }
]
```
### **설문조사 답변 조회 (설문 ID로 조회)**

- **엔드포인트**: `/api/v1/survey/{surveyId}/answers`
- **메서드**: `GET`
- **설명**: 특정 설문조사 ID에 대한 답변 목록을 조회합니다. `questionName` 또는 `answerValue`를 사용하여 필터링할 수 있습니다.

#### 경로 파라미터

- `surveyId` (필수): 조회할 설문조사의 고유 ID

#### 쿼리 파라미터

- `questionName` (선택): 특정 질문에 대한 답변을 필터링할 수 있는 질문 이름
- `answerValue` (선택): 특정 답변 값으로 필터링할 수 있는 답변 값

#### 응답

- **상태 코드**: `200 OK`
- **응답 본문**: `List<AnswerDTO.ResDTO>` (설문조사의 답변 목록)

```json
[
    {
        "answerId": 1,
        "surveyVersion": 1,
        "answerItemResDTOS": [
            {
                "itemId": 1,
                "type": "SHORT_ANSWER",
                "answer": "123123"
            },
            {
                "itemId": 2,
                "type": "LONG_ANSWER",
                "answer": "1"
            },
            {
                "itemId": 3,
                "type": "SINGLE_CHOICE",
                "option": "test"
            },
            {
                "itemId": 4,
                "type": "MULTIPLE_CHOICE",
                "options": [
                    "test"
                ]
            }
        ]
    }
]
```
