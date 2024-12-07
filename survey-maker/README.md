# 설문 조사 생성, 수정 프로젝트

설문의 등록 및 수정을 담당하는 모듈<br/>

## 실행 방법

1. Docker 실행
2. redis 이미지 다운
    - `docker pull redis`
    - `docker run -d -p 6379:6379 redis`
3. h2 데이터베이스 실행
    - homebrew 등을 통해 설치 이후 실행
4. 스프링 부트 실행

## 주요 기능

- 설문 생성, 수정이 가능하다.
    - 추후 날짜 설정, 로그인 기능 등 추가 기능 개발 예정
- 설문을 수정할 경우 redisson을 이용하여 lock을 걸어 다른 사용자가 동시에 수정하지 못하도록 한다.
    - 응답할 때도 같은 식별자를 갖고 있는 설문은 lock을 걸어 다른 사용자가 응답하지 못하도록 한다.

## API 명세

- 설문 생성
    - POST /survey
    - Request
        - 설문지 정보
            - 설문지 이름
            - 설문지 설명
            - 설문지 항목
                - 항목 이름
                - 항목 설명
                - 항목 타입
                - 필수 여부
                - 선택지 (항목 타입이 선택지인 경우)
    - Response
        - 설문지 ID
        - 설문지 정보
            - 설문지 이름
            - 설문지 설명
            - 설문지 항목
                - 항목 ID
                - 항목 이름
                - 항목 설명
                - 항목 타입
                - 필수 여부
                - 선택지 (항목 타입이 선택지인 경우)

### 요청

``` json
{
    "name": "설문지1",
    "description": "설문을 하는 거야.",
    "items": [
        {
            "item": "우리는 어떻게 살아갈 것인가 ?",
            "description": "우리가 어떻게 살아갈 것인지 고민하는 것.",
            "type": "SHORT_TEXT",
            "required": true
        },
        {
            "item": "자바가 좋나요 아니면 파이썬이 좋나요?",
            "description": "어떤 프로그래밍 언어를 선호하는지",
            "type": "SINGLE_SELECT",
            "required": true,
            "options": [
                "자바",
                "파이썬"
            ]
        }
    ]
}
```

### 응답 - 성공

``` json
{
    "response": {
        "id": 3952642095044545270,
        "name": "설문지1",
        "description": "설문을 하는 거야.",
        "items": [
            {
                "id": 3952642095226943832,
                "item": "우리는 어떻게 살아갈 것인가 ?",
                "description": "우리가 어떻게 살아갈 것인지 고민하는 것.",
                "inputType": "SHORT_TEXT",
                "required": true,
                "options": null
            },
            {
                "id": 3952642095246803523,
                "item": "자바가 좋나요 아니면 파이썬이 좋나요?",
                "description": "어떤 프로그래밍 언어를 선호하는지",
                "inputType": "SINGLE_SELECT",
                "required": true,
                "options": [
                    "자바",
                    "파이썬"
                ]
            }
        ]
    },
    "message": "처리되었습니다.",
    "status": 200
}
```

### 응답 - 실패

``` json
{
    "response": null,
    "message": "단답형 타입은 옵션이 필요하지 않습니다.",
    "status": 400
}

{
    "response": null,
    "message": "장문형 타입은 옵션이 필요하지 않습니다.",
    "status": 400
}

{
    "response": null,
    "message": "단일 선택 목록형 타입은 2개 이상의 옵션이 필요합니다.",
    "status": 400
}

{
    "response": null,
    "message": "다중 선택 목록형 타입은 2개 이상의 옵션이 필요합니다.",
    "status": 400
}
```

## 설문 수정

- PATCH /api/v1/survey
    - Request
        - 설문지 정보
            - 설문지 이름
            - 설문지 설명
            - 설문지 항목
                - 항목 이름
                - 항목 설명
                - 항목 타입
                - 필수 여부
                - 선택지 (항목 타입이 선택지인 경우)
    - Response
        - 설문지 ID
        - 설문지 정보
            - 설문지 이름
            - 설문지 설명
            - 설문지 항목
                - 항목 ID
                - 항목 이름
                - 항목 설명
                - 항목 타입
                - 필수 여부
                - 선택지 (항목 타입이 선택지인 경우)

### 요청

```json
{
    "id": 3951922495511924462,
    "name": "설문지1",
    "description": "설문을 하는 거야.",
    "items": [
        {
            "id": 3950234195127775913,
            "item": "우리는 어떻게 살아갈 것인가 ?111",
            "description": "우리가 어떻게 살아갈 것인지 고민하는 것.111",
            "type": "SHORT_TEXT",
            "required": true
        },
        {
            "id": 3950234195147787403,
            "item": "자바가 좋나요 아니면 파이썬이 좋나요?222",
            "description": "어떤 프로그래밍 언어를 선호하는지222",
            "type": "SINGLE_SELECT",
            "required": true,
            "options": [
                "자바",
                "파이썬"
            ]
        }
    ]
}
```

## 응답 - 성공

```json
{
    "response": {
        "id": 3951922495511924462,
        "name": "설문지1",
        "description": "설문을 하는 거야.",
        "items": [
            {
                "id": 3950234195127775913,
                "item": "우리는 어떻게 살아갈 것인가 ?111",
                "description": "우리가 어떻게 살아갈 것인지 고민하는 것.111",
                "inputType": "SHORT_TEXT",
                "required": true,
                "options": []
            },
            {
                "id": 3950234195147787403,
                "item": "자바가 좋나요 아니면 파이썬이 좋나요?222",
                "description": "어떤 프로그래밍 언어를 선호하는지222",
                "inputType": "SINGLE_SELECT",
                "required": true,
                "options": [
                    "자바",
                    "파이썬"
                ]
            }
        ]
    },
    "message": "처리되었습니다.",
    "status": 200
}
```

### 응답 - 실패

```json
{
    "response": null,
    "message": "단답형 타입은 옵션이 필요하지 않습니다.",
    "status": 400
}
```

