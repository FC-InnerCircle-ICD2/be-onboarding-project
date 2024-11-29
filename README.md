# 이너써클 BE 온보딩 프로젝트

## ✍ 설문조사 서비스 API
> “설문조사 서비스”는 설문조사 양식을 만들고, 만들어진 양식을 기반으로 응답을 받을 수 있는 서비스입니다. (e.g. Google Forms, Tally, Typeform)

### 기술 스택
- Java 17
- Spring Boot 3.4.0

### Dependences
- Spring Boot JPA
  - 관계형 데이터베이스와 객체지향 자바 애플리케이션 사이의 매핑을 할 수 있는 인터페이스를 제공
- Spring Boot Validation
  - 클라이언트에서 요청한 데이터의 유효성을 판별
- H2 Database
  - 따로 설치가 필요 없는 인메모리 데이터베이스 관리 시스템 
- Lombok
  - 어노테이션을 사용해 반복되는 코드를 간소화 하고 가독성을 높일 수 있음
- QueryDSL
  - 문자열로 작성하는 JPQL 방식과 달리 Java 코드 스타일과 메소드 체이닝 지원으로 가독성과 유지보수 이점이 있음 

## 🍃 API 명세

### 공통 형식

| field name    | type   | description                           |
|---------------|--------|---------------------------------------|
| `code`        | String | ⏺ ResponseCode의 에러 코드                 |
| `message`     | String | ⏺ ResponseCode의 에러 메세지                |
| `data`        | String | api 결과를 담은 객체                         |
| `fieldErrors` | String | request 데이터의 유효성 검증 실패시 🚩FieldError 반환 |

- data는 request에 따른 response 결과를 담고 있습니다. 서비스 에러가 발생한 경우 `null` 입니다.
- request 데이터 검증에 성공한 경우 fieldErrors는 `null` 입니다.

#### ⏺ ResponseCode
|name|code| message |
|---|---|--|
|`SUCCESS`|00000|성공했습니다.|
|`NOT_FOUND_REQUIRED_ANSWER`|30000|필수로 응답해야 하는 항목에 응답하지 않았습니다.|
|`INVALID_SHORT_SENTENCE_ANSWER`|30001|단답형은 255자 미만으로 답할 수 있습니다.|
|`INVALID_OPTION`|30002|선택 리스트에 없는 값을 선택했습니다.|
|`NOT_VALID_DATA`|40000|데이터 검증에 실패했습니다.|
|`NOT_VALID_TYPE`|40001|파라미터의 타입을 확인해 주세요.|
|`NOT_FOUND_SURVEY`|40010|유효하지 않은 설문조사입니다. [설문조사 이름], [설문조사 설명]을 확인해 주세요|
|`NOT_FOUND_ANSWER`|40011|응답해야 하는 문항에 대한 항목이 없습니다.|
|`NOT_FOUND_SURVEY_IDX`|40012|idx[설문조사 식별자]가 필요합니다.|

#### 🚩 FieldError
| field name    | type   | description  |
|---------------|--------|--------------|
| `field`       | String | 검증에 실패한 필드 이름 |
| `rejectedValue` | T      | 검증에 실패한 객체   |
| `reason` | String | 검증에 실패사유     |

<br/>

### 1. 설문조사 생성 API
> 새로운 설문조사를 생성합니다. 

#### `POST` /api/v0/survey
#### Response Body > SurveyRequest
| field name    | type            | description |
|---------------|-----------------|------------|
| `name`        | String          | 설문조사 이름    |
| `description` | String          | 설문조사 설명    |
| `questions`   | QuestionRequest | 설문 받을 항목   |

#### Response Body > SurveyRequest > QuestionRequest
| field name     | type          | description                               |
|----------------|---------------|-------------------------------------------|
| `name`         | String        | 항목 이름                                     |
| `description`  | String        | 항목 설명                                     |
| `questionType` | String        | 항목 입력 형태 (단답형, 장문형, 단일 선택 리스트, 다중 선택 리스트) |
| `options`      | OptionRequest | 단일 선택 리스트, 다중 선택 리스트의 경우 후보 값             |


#### Response Body > SurveyRequest > QuestionRequest > OptionRequest
| field name     | type          | description                              |
|----------------|---------------|------------------------------------------|
| `name`         | String        | 옵션 이름                                    |

#### request example
```JSON
{
    "name": "겨울 방학 설문 조사",
    "description": "겨울 방학 계획에 대해 조사하고 있습니다.",
    "questions": [
        {
            "name": "성함이 무엇인가요?",
            "description": "이름 작성",
            "questionType": "단답형",
            "isRequired": true
        },
        {
            "name": "이번 겨울 방학이 처음인가요?",
            "description": "하나 선택",
            "questionType": "단일 선택 리스트",
            "isRequired": false,
            "options": [
                {"name": "예"},
                {"name": "아니오"}
            ]
        },
                {
            "name": "이번 겨울 방학에는 무엇을 할 건가요?",
            "description": "중복 선택 가능",
            "questionType": "다중 선택 리스트",
            "isRequired": false,
            "options": [
                {"name": "휴식"},
                {"name": "공부"},
                {"name": "취미활동"}
            ]
        },
        {
            "name": "이전 겨울 방학이 만족스럽지 못한 이유를 알려주세요.",
            "description": "의견을 작성해 주시면 됩니다.",
            "questionType": "장문형",
            "isRequired": true
        }
    ] 
}
```

#### response example
```JSON
{
    "code": "00000",
    "message": "성공했습니다.",
    "data": {
        "idx": 1,
        "name": "겨울 방학 설문 조사",
        "description": "겨울 방학 계획에 대해 조사하고 있습니다.",
        "questions": [
            {
                "idx": 1,
                "name": "성함이 무엇인가요?",
                "description": "이름 작성",
                "options": []
            },
            {
                "idx": 2,
                "name": "이번 겨울 방학이 처음인가요?",
                "description": "하나 선택",
                "options": [
                    {
                        "name": "예"
                    },
                    {
                        "name": "아니오"
                    }
                ]
            },
            {
                "idx": 3,
                "name": "이번 겨울 방학에는 무엇을 할 건가요?",
                "description": "중복 선택 가능",
                "options": [
                    {
                        "name": "휴식"
                    },
                    {
                        "name": "공부"
                    },
                    {
                        "name": "취미활동"
                    }
                ]
            },
            {
                "idx": 4,
                "name": "이전 겨울 방학이 만족스럽지 못한 이유를 알려주세요.",
                "description": "의견을 작성해 주시면 됩니다.",
                "options": []
            }
        ]
    }
}
```

<br/>

### 2. 설문조사 수정 API
> 기존에 등록된 설문조사를 수정합니다.

`PUT` /api/v0/survey

#### Response Body > SurveyRequest
| field name    | type           | description |
|---------------|----------------|------------|
| `idx`          | int            | 설문조사 고유값   |
| `name`        | String         | 설문조사 이름    |
| `description` | String         | 설문조사 설명    |
| `questions`   | QuestionRequest | 설문 받을 항목   |

#### Response Body > SurveyRequest > QuestionRequest
| field name     | type          | description |
|----------------|---------------|-------|
| `idx`          | int           | 항목 고유값 |
| `name`         | String        | 항목 이름 |
| `description`  | String        | 항목 설명 |
| `questionType` | String        | 항목 입력 형태 (단답형, 장문형, 단일 선택 리스트, 다중 선택 리스트) |
| `options`      | OptionRequest | 단일 선택 리스트, 다중 선택 리스트의 경우 후보 값 |

- 요청에 `idx`값이 없는 경우 새로 추가한 항목으로 처리됩니다. 
- 기존 설문에 포함된 항목 `idx`를 요청에 보내주시는 경우 업데이트 처리됩니다.
- 기존 설문에 있었으나 요청에 포함되지 않은 항문은 삭제 처리 됩니다.

#### Response Body > SurveyRequest > QuestionRequest > OptionRequest
| field name     | type          | description                              |
|----------------|---------------|------------------------------------------|
| `name`         | String        | 옵션 이름                                    |

#### request example
```JSON
{
    "idx": 1,
    "name": "겨울 방학 설문조사",
    "description": "겨울 방학에 대한 계획을 조사하고 있습니다. ",
    "questions": [
        {
            "idx": 1,
            "name": "항목을 변경합니다.",
            "description": "변경된 설문입니다.",
            "questionType": "단일 선택 리스트",
            "isRequired": true,
            "options": [
                {
                    "name": "예"
                },
                {
                    "name": "아니오"
                }
            ]
        }
    ] 
}
```

#### response example
```JSON
{
    "code": "00000",
    "message": "성공했습니다.",
    "data": {
        "idx": 1,
        "name": "겨울 방학 설문 조사",
        "description": "겨울 방학 계획에 대해 조사하고 있습니다.",
        "questions": [
            {
                "idx": 1,
                "name": "항목을 변경합니다.",
                "description": "변경된 설문입니다.",
                "options": [
                    {
                        "name": "예"
                    },
                    {
                        "name": "아니오"
                    }
                ]
            }
        ]
    }
}
```


### 3. 설문조사 응답 제출 API

- 요청 값에는 [설문 받을 항목]에 대응되는 응답 값이 포함됩니다.
- 응답 값은 설문조사의 [설문 받을 항목]과 일치해야만 응답 할 수 있습니다.


### 4. 설문조사 응답 조회 API

- 요청 값에는 [설문조사 식별자]가 포함됩니다.
- 해당 설문조사의 전체 응답을 조회합니다.
- **(Advanced)** 설문 응답 항목의 이름과 응답 값을 기반으로 검색 할 수 있습니다.
