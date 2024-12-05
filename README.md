## API 명세서

| Method | Uri | Description | 개발 완료 여부 |
| --- | --- | --- | --- |
| POST | /research/add | 설문조사 등록 API | O |
| POST | /research/edit | 설문조사 수정 API | O |
| POST | /research/submit | 설문조사 응답 제출 API | O |
| GET | /research/get/research-submit | 설문조사 응답 조회 API | O |
| GET | /research/search/research-answer | 설문조사 항목 및 답변 값 검색 API | O |

---

## // TODO

- [ ]  ExceptionHandler 처리 → 실패 ㅠ (참고한 코드와 동일한 구성으로 Exception, CommonResponse 등 처리 해놓았는데 Exception 발생시 정상적으로 처리가 잘 이루어 지지 않는 것 같습니다.,
- [x]  @OneToMany @ManyToOne 관계 CRUD 처리에 대한 이해
- [ ]  Swagger로 API 명세서 작성 → 실패…
- [x]  @CreatedDate, @LastModifiedDate 처리

---

## `POST` 설문조사 등록 API

`/research/add`

### Request

| Field | Data Type | Example Value | Description | Description2 |
| --- | --- | --- | --- | --- |
| **title** | String | "title" | 설문 제목 |  |
| **description** | String | "description" | 설문 설명 |  |
| **itemVoList** | List |  | 설문 항목 리스트 | itemVoList의 길이는 최소 1에서 최대 10 |
| **itemVoList[n]** | **ItemVo** |  | First item in the item list. |  |
| - name | String | "itemName1" | 설문 항목명 |  |
| - description | String | "itemDescription1" | 설문 항목 설명 |  |
| - itemType | Integer | 1 | 설문 항목 유형 | 1: 단답형
2: 장문형
3: 단일 선택형
4: 다중 선택형 |
| - itemChoiceList | List |  | 설문 항목 선택 리스트 | itemType 값이 3, 4일 경우에만 itemChoiceList 데이터 저장됨. |
| -- itemChoiceList[m] | **ItemChoice** |  |  |  |
| --- content | String | "itemChoiceContent1_1" | 설문 항목 선택 값 |  |
| - isRequired | Boolean | true | 필수 입력 여부 |  |

### requestBody Example

```json
{
    "title": "title",
    "description": "description",
    "itemVoList": [
        {
            "name": "itemName1",
            "description": "itemDescription1",
            "itemType": 1,
            "itemChoiceList" : [
                {
                    "content": "itemChoiceContent1_1"
                },
                {
                    "content": "itemChoiceContent1_2"
                },
                {
                    "content": "itemChoiceContent1_3"
                },
                {
                    "content": "itemChoiceContent1_4"
                },
                {
                    "content": "itemChoiceContent1_5"
                }
            ],
            "isRequired": true
        },
        {
            "name": "itemName2",
            "description": "itemDescription2",
            "itemType": 2,
            "itemChoiceList" : [
                {
                    "content": "itemChoiceContent2_1"
                },
                {
                    "content": "itemChoiceContent2_2"
                },
                {
                    "content": "itemChoiceContent2_3"
                },
                {
                    "content": "itemChoiceContent2_4"
                },
                {
                    "content": "itemChoiceContent2_5"
                }
            ],
            "isRequired": true
        },
        {
            "name": "itemName3",
            "description": "itemDescription3",
            "itemType": 3,
            "itemChoiceList" : [
                {
                    "content": "itemChoiceContent3_1"
                },
                {
                    "content": "itemChoiceContent3_2"
                },
                {
                    "content": "itemChoiceContent3_3"
                },
                {
                    "content": "itemChoiceContent3_4"
                },
                {
                    "content": "itemChoiceContent3_5"
                }
            ],
            "isRequired": true
        },
        {
            "name": "itemName4",
            "description": "itemDescription4",
            "itemType": 4,
            "itemChoiceList" : [
                {
                    "content": "itemChoiceContent4_1"
                },
                {
                    "content": "itemChoiceContent4_2"
                },
                {
                    "content": "itemChoiceContent4_3"
                },
                {
                    "content": "itemChoiceContent4_4"
                },
                {
                    "content": "itemChoiceContent4_5"
                }
            ],
            "isRequired": true
        }
    ]

}
```

### Response

| Field | Data Type | Example Value | Description | Description2 |
| --- | --- | --- | --- | --- |
| researchId | Long (Number) | 1 | 설문 id |  |
| **title** | String | "title" | 설문 제목 |  |
| **description** | String | "description" | 설문 설명 |  |
| researchItemResponseList | List |  | 설문 항목 리스트 | itemVoList의 길이는 최소 1에서 최대 10 |
| researchItemResponseList**[n]** | **ItemVo** |  | First item in the item list. |  |
| - researchItemId | Long (Number) | 1 | 설문 항목 id |  |
| - name | String | "itemName1" | 설문 항목명 |  |
| - description | String | "itemDescription1" | 설문 항목 설명 |  |
| - itemType | Integer | 1 | 설문 항목 유형 | 1: 단답형
2: 장문형
3: 단일 선택형
4: 다중 선택형 |
| - itemTypeName | String | “SHORT_ANSWER” | 설문 항목 유형 | SHORT_ANSWER: 단답형
LONG_SENTENCE: 장문형
SINGLE_SELECTION: 단일 선택형
MULTIPLE_SELECTION: 다중 선택형 |
| - researchItemChoiceResponseList | List |  | 설문 항목 선택 리스트 | itemType 값이 3, 4일 경우에만 itemChoiceList 데이터 저장됨. |
| -- researchItemChoiceResponseList[m] | **ItemChoice** |  |  |  |
| --- researchItemChoiceId | Long (Number) | 1 | 설문 항목 선택 id |  |
| --- content | String | "itemChoiceContent1" | 설문 항목 선택 값 |  |
| - isRequired | Boolean | false | 필수 입력 여부 |  |

### responseBody Example

```json
{
    "researchId": 1,
    "title": "description",
    "description": null,
    "researchItemResponseList": [
        {
            "researchItemId": 1,
            "name": "itemName1",
            "description": "itemDescription1",
            "itemType": 1,
            "itemTypeName": "SHORT_ANSWER",
            "isRequired": true,
            "researchItemChoiceResponseList": []
        },
        {
            "researchItemId": 2,
            "name": "itemName2",
            "description": "itemDescription2",
            "itemType": 2,
            "itemTypeName": "LONG_SENTENCE",
            "isRequired": true,
            "researchItemChoiceResponseList": []
        },
        {
            "researchItemId": 3,
            "name": "itemName3",
            "description": "itemDescription3",
            "itemType": 3,
            "itemTypeName": "SINGLE_SELECTION",
            "isRequired": true,
            "researchItemChoiceResponseList": [
                {
                    "researchItemChoiceId": 1,
                    "content": "itemChoiceContent3_1"
                },
                {
                    "researchItemChoiceId": 2,
                    "content": "itemChoiceContent3_2"
                },
                {
                    "researchItemChoiceId": 3,
                    "content": "itemChoiceContent3_3"
                },
                {
                    "researchItemChoiceId": 4,
                    "content": "itemChoiceContent3_4"
                },
                {
                    "researchItemChoiceId": 5,
                    "content": "itemChoiceContent3_5"
                }
            ]
        },
        {
            "researchItemId": 4,
            "name": "itemName4",
            "description": "itemDescription4",
            "itemType": 4,
            "itemTypeName": "MULTIPLE_SELECTION",
            "isRequired": true,
            "researchItemChoiceResponseList": [
                {
                    "researchItemChoiceId": 6,
                    "content": "itemChoiceContent4_1"
                },
                {
                    "researchItemChoiceId": 7,
                    "content": "itemChoiceContent4_2"
                },
                {
                    "researchItemChoiceId": 8,
                    "content": "itemChoiceContent4_3"
                },
                {
                    "researchItemChoiceId": 9,
                    "content": "itemChoiceContent4_4"
                },
                {
                    "researchItemChoiceId": 10,
                    "content": "itemChoiceContent4_5"
                }
            ]
        }
    ]
}
```

---

## `POST` 설문조사 수정 API

`/research/edit`

### Request

| Field | Data Type | Example Value | Description | Description2 |
| --- | --- | --- | --- | --- |
| researchId | Long (Number) | 1 | 설문 id |  |
| **title** | String | "title_edited" | 설문 제목 |  |
| **description** | String | "description_edited" | 설문 설명 |  |
| **itemVoList** | List |  | 설문 항목 리스트 | itemVoList의 길이는 최소 1에서 최대 10 |
| **itemVoList[n]** | **ItemVo** |  | First item in the item list. |  |
| - name | String | "itemName1_edited" | 설문 항목명 |  |
| - description | String | "itemDescription1_edited" | 설문 항목 설명 |  |
| - itemType | Integer | 1 | 설문 항목 유형 | 1: 단답형
2: 장문형
3: 단일 선택형
4: 다중 선택형 |
| - itemChoiceList | List |  | 설문 항목 선택 리스트 | itemType 값이 3, 4일 경우에만 itemChoiceList 데이터 저장됨. |
| -- itemChoiceList[m] | **ItemChoice** |  |  |  |
| --- content | String | "itemChoiceContent1_1_edited" | 설문 항목 선택 값 |  |
| - isRequired | Boolean | false | 필수 입력 여부 |  |

### requestBody Example

```json
{
    "researchId": 1,
    "title": "title_edited",
    "description": "description_edited",
    "itemVoList": [
        {
            "name": "itemName1_edited",
            "description": "itemDescription1_edited",
            "itemType": 3,
            "itemChoiceList" : [
                {
                    "content": "itemChoiceContent1_1_edited"
                },
                {
                    "content": "itemChoiceContent1_2_edited"
                },
                {
                    "content": "itemChoiceContent1_3_edited"
                },
                {
                    "content": "itemChoiceContent1_4_edited"
                },
                {
                    "content": "itemChoiceContent1_5_edited"
                }
            ],
            "isRequired": false
        },
        {
            "name": "itemName2_edited",
            "description": "itemDescription2_edited",
            "itemType": 4,
            "itemChoiceList" : [
                {
                    "content": "itemChoiceContent2_1_edited"
                },
                {
                    "content": "itemChoiceContent2_2_edited"
                },
                {
                    "content": "itemChoiceContent2_3_edited"
                },
                {
                    "content": "itemChoiceContent2_4_edited"
                },
                {
                    "content": "itemChoiceContent2_5_edited"
                }
            ],
            "isRequired": true
        },
        {
            "name": "itemName3_edited",
            "description": "itemDescription3_edited",
            "itemType": 1,
            "itemChoiceList" : [
                {
                    "content": "itemChoiceContent3_1"
                },
                {
                    "content": "itemChoiceContent3_2_edited"
                },
                {
                    "content": "itemChoiceContent3_3_edited"
                },
                {
                    "content": "itemChoiceContent3_4_edited"
                },
                {
                    "content": "itemChoiceContent3_5_edited"
                }
            ],
            "isRequired": true
        },
        {
            "name": "itemName4_edited",
            "description": "itemDescription4_edited",
            "itemType": 2,
            "itemChoiceList" : [
                {
                    "content": "itemChoiceContent4_1_edited"
                },
                {
                    "content": "itemChoiceContent4_2_edited"
                },
                {
                    "content": "itemChoiceContent4_3_edited"
                },
                {
                    "content": "itemChoiceContent4_4_edited"
                },
                {
                    "content": "itemChoiceContent4_5_edited"
                }
            ],
            "isRequired": false
        }
    ]

}
```

### Response

| Field | Data Type | Example Value | Description | Description2 |
| --- | --- | --- | --- | --- |
| researchId | Long (Number) | 1 | 설문 id |  |
| **title** | String | "title_edited" | 설문 제목 |  |
| **description** | String | "description_edited" | 설문 설명 |  |
| researchItemResponseList | List |  | 설문 항목 리스트 | researchItemResponseList의 길이는 최소 1에서 최대 10 |
| researchItemResponseList**[n]** | **ItemVo** |  | First item in the item list. |  |
| - researchItemId | Long (Number) | 1 | 설문 항목 id |  |
| - name | String | "itemName1_edited" | 설문 항목명 |  |
| - description | String | "itemDescription1_edited" | 설문 항목 설명 |  |
| - itemType | Integer | 1 | 설문 항목 유형 | 1: 단답형
2: 장문형
3: 단일 선택형
4: 다중 선택형 |
| - itemTypeName | String | “SHORT_ANSWER” | 설문 항목 유형 | SHORT_ANSWER: 단답형
LONG_SENTENCE: 장문형
SINGLE_SELECTION: 단일 선택형
MULTIPLE_SELECTION: 다중 선택형 |
| - researchItemChoiceResponseList | List |  | 설문 항목 선택 리스트 | itemType 값이 3, 4일 경우에만 itemChoiceList 데이터 저장됨. |
| -- researchItemChoiceResponseList[m] | **ItemChoice** |  |  |  |
| --- researchItemChoiceId | Long (Number) | "5" | 설문 항목 선택 id |  |
| --- content | String | "itemChoiceContent1_1_edited" | 설문 항목 선택 값 |  |
| - isRequired | Boolean | false | 필수 입력 여부 |  |

### responseBody Example

```json
{
    "researchId": 1,
    "title": "title_edited",
    "description": "description_edited",
    "researchItemResponseList": [
        {
            "researchItemId": 5,
            "name": "itemName1_edited",
            "description": "itemDescription1_edited",
            "itemType": 3,
            "itemTypeName": "SINGLE_SELECTION",
            "isRequired": false,
            "researchItemChoiceResponseList": [
                {
                    "researchItemChoiceId": 11,
                    "content": "itemChoiceContent1_1_edited"
                },
                {
                    "researchItemChoiceId": 12,
                    "content": "itemChoiceContent1_2_edited"
                },
                {
                    "researchItemChoiceId": 13,
                    "content": "itemChoiceContent1_3_edited"
                },
                {
                    "researchItemChoiceId": 14,
                    "content": "itemChoiceContent1_4_edited"
                },
                {
                    "researchItemChoiceId": 15,
                    "content": "itemChoiceContent1_5_edited"
                }
            ]
        },
        {
            "researchItemId": 6,
            "name": "itemName2_edited",
            "description": "itemDescription2_edited",
            "itemType": 4,
            "itemTypeName": "MULTIPLE_SELECTION",
            "isRequired": true,
            "researchItemChoiceResponseList": [
                {
                    "researchItemChoiceId": 16,
                    "content": "itemChoiceContent2_1_edited"
                },
                {
                    "researchItemChoiceId": 17,
                    "content": "itemChoiceContent2_2_edited"
                },
                {
                    "researchItemChoiceId": 18,
                    "content": "itemChoiceContent2_3_edited"
                },
                {
                    "researchItemChoiceId": 19,
                    "content": "itemChoiceContent2_4_edited"
                },
                {
                    "researchItemChoiceId": 20,
                    "content": "itemChoiceContent2_5_edited"
                }
            ]
        },
        {
            "researchItemId": 7,
            "name": "itemName3_edited",
            "description": "itemDescription3_edited",
            "itemType": 1,
            "itemTypeName": "SHORT_ANSWER",
            "isRequired": true,
            "researchItemChoiceResponseList": []
        },
        {
            "researchItemId": 8,
            "name": "itemName4_edited",
            "description": "itemDescription4_edited",
            "itemType": 2,
            "itemTypeName": "LONG_SENTENCE",
            "isRequired": false,
            "researchItemChoiceResponseList": []
        }
    ]
}
```

---

## `POST` 설문조사 응답 제출 API

`/research/submit`

### Request

| Field | Data Type | Example Value | Description | Description2 |
| --- | --- | --- | --- | --- |
| **researchId** | Long (Number) | 1 | 응답할 설문 id |  |
| **answerVoList** | List |  | 답변 리스트 |  |
| **answerVoList[n]** | **AnswerVo** |  |  |  |
| - answer | Long (Number) | 13 | A numeric answer. | itemTypeName: SINGLE_SELECTION |
| - answer | List<Long> | [16, 18] | An array of Longs as an answer. | itemTypeName: MULTIPLE_SELECTION |
| - answer | String | "한글도 입력 됩니다." | A string answer in string. | itemTypeName: 
SHORT_ANSWER or
LONG_SENTENCE |
| - answer | String | "" | empty string answer. | 답변 생략 케이스 |

### requestBody Example

```json
{
    "researchId": 1,
    "answerVoList": [
        {
            "answer": 13
        },
        {
            "answer": [16, 18]
        },
        {
            "answer": "한글도 입력 됩니다."
        },
        {
            "answer": ""
        }
    ]

}
```

### Response

| Field | Data Type | Example Value | Description | Description |
| --- | --- | --- | --- | --- |
| **researchAnswerId** | Long
(Number) | 1 | 설문 응답 답변 id |  |
| **data** | Object |  | 설문 응답 데이터 |  |
| --- researchId | Long
(Number) | 1 | 설문 id |  |
| --- title | String | "title_edited" | 설문 제목 |  |
| --- description | String | "description_edited" | 설문 설명 |  |
| --- researchAnswerItemResponseList | List |  | 설문 응답 항목 리스트 |  |
| ---- researchAnswerItemResponseList[n] | **ResearchItemResponse** |  | First item in the item list. |  |
| ------ researchItemId | Long
(Number) | 5 | 설문 항목 id |  |
| ------ name | String | "itemName1_edited" | 설문 항목명 |  |
| ------ description | String | "itemDescription1_edited" | 설문 항목 설명 |  |
| ------ itemType | Integer | 3 | 설문 항목 유형 | 1: 단답형
2: 장문형
3: 단일 선택형
4: 다중 선택형 |
| ------ itemTypeName | String | "SINGLE_SELECTION" | 설문 항목 유형 | SHORT_ANSWER: 단답형
LONG_SENTENCE: 장문형
SINGLE_SELECTION: 단일 선택형
MULTIPLE_SELECTION: 다중 선택형 |
| ------ isRequired | Boolean | false | 필수 입력 여부 |  |
| ------ researchItemChoiceResponseList | List |  | 설문 항목 선택 리스트 | itemType 값이 3, 4일 경우에만 itemChoiceList 데이터 저장됨. |
| ------- researchItemChoiceResponseList[m] | **ItemChoiceResponse** |  |  |  |
| -------- researchItemChoiceId | Long
(Number) | 11 | 설문 항목 선택 id |  |
| -------- content | String | "itemChoiceContent1_1_edited" | 설문 항목 선택 값 |  |
| ---- answer | Integer or Array or String | 13 (or [16, 18]) or "한글도 입력 됩니다." | 응답 값 |  |

### responseBody Example

```json
{
    "researchAnswerId": 1,
    "data": {
        "researchId": 1,
        "title": "title_edited",
        "description": "description_edited",
        "researchAnswerItemResponseList": [
            {
                "researchItemId": 5,
                "name": "itemName1_edited",
                "description": "itemDescription1_edited",
                "itemType": 3,
                "itemTypeName": "SINGLE_SELECTION",
                "isRequired": false,
                "researchItemChoiceResponseList": [
                    {
                        "researchItemChoiceId": 11,
                        "content": "itemChoiceContent1_1_edited"
                    },
                    {
                        "researchItemChoiceId": 12,
                        "content": "itemChoiceContent1_2_edited"
                    },
                    {
                        "researchItemChoiceId": 13,
                        "content": "itemChoiceContent1_3_edited"
                    },
                    {
                        "researchItemChoiceId": 14,
                        "content": "itemChoiceContent1_4_edited"
                    },
                    {
                        "researchItemChoiceId": 15,
                        "content": "itemChoiceContent1_5_edited"
                    }
                ],
                "answer": 13
            },
            {
                "researchItemId": 6,
                "name": "itemName2_edited",
                "description": "itemDescription2_edited",
                "itemType": 4,
                "itemTypeName": "MULTIPLE_SELECTION",
                "isRequired": true,
                "researchItemChoiceResponseList": [
                    {
                        "researchItemChoiceId": 16,
                        "content": "itemChoiceContent2_1_edited"
                    },
                    {
                        "researchItemChoiceId": 17,
                        "content": "itemChoiceContent2_2_edited"
                    },
                    {
                        "researchItemChoiceId": 18,
                        "content": "itemChoiceContent2_3_edited"
                    },
                    {
                        "researchItemChoiceId": 19,
                        "content": "itemChoiceContent2_4_edited"
                    },
                    {
                        "researchItemChoiceId": 20,
                        "content": "itemChoiceContent2_5_edited"
                    }
                ],
                "answer": [
                    16,
                    18
                ]
            },
            {
                "researchItemId": 7,
                "name": "itemName3_edited",
                "description": "itemDescription3_edited",
                "itemType": 1,
                "itemTypeName": "SHORT_ANSWER",
                "isRequired": true,
                "researchItemChoiceResponseList": [],
                "answer": "한글도 입력 됩니다."
            },
            {
                "researchItemId": 8,
                "name": "itemName4_edited",
                "description": "itemDescription4_edited",
                "itemType": 2,
                "itemTypeName": "LONG_SENTENCE",
                "isRequired": false,
                "researchItemChoiceResponseList": [],
                "answer": ""
            }
        ]
    }
}
```

---

## `GET` 설문조사 응답 조회 API

`/research/get/research-submit`

### Request

| Field | Data Type | Example Value | Description | Description |
| --- | --- | --- | --- | --- |
| **researchAnswerId** | Long(Number) | 1 | 설문 응답 답변 id |  |

### request Example uri

`/research/get/research-answer?researchAnswerId=1`

### Response

| Field | Data Type | Example Value | Description | Description |
| --- | --- | --- | --- | --- |
| **researchAnswerId** | Long
(Number) | 1 | 설문 응답 답변 id |  |
| **data** | Object |  | 설문 응답 데이터 |  |
| --- researchId | Long
(Number) | 1 | 설문 id |  |
| --- title | String | "title_edited" | 설문 제목 |  |
| --- description | String | "description_edited" | 설문 설명 |  |
| --- researchAnswerItemResponseList | List |  | 설문 응답 항목 리스트 |  |
| ---- researchAnswerItemResponseList[n] | **ResearchItemResponse** |  | First item in the item list. |  |
| ------ researchItemId | Long
(Number) | 5 | 설문 항목 id |  |
| ------ name | String | "itemName1_edited" | 설문 항목명 |  |
| ------ description | String | "itemDescription1_edited" | 설문 항목 설명 |  |
| ------ itemType | Integer | 3 | 설문 항목 유형 | 1: 단답형
2: 장문형
3: 단일 선택형
4: 다중 선택형 |
| ------ itemTypeName | String | "SINGLE_SELECTION" | 설문 항목 유형 | SHORT_ANSWER: 단답형
LONG_SENTENCE: 장문형
SINGLE_SELECTION: 단일 선택형
MULTIPLE_SELECTION: 다중 선택형 |
| ------ isRequired | Boolean | false | 필수 입력 여부 |  |
| ------ researchItemChoiceResponseList | List |  | 설문 항목 선택 리스트 | itemType 값이 3, 4일 경우에만 itemChoiceList 데이터 저장됨. |
| ------- researchItemChoiceResponseList[m] | **ItemChoiceResponse** |  |  |  |
| -------- researchItemChoiceId | Long
(Number) | 11 | 설문 항목 선택 id |  |
| -------- content | String | "itemChoiceContent1_1_edited" | 설문 항목 선택 값 |  |
| ---- answer | Integer or Array or String | 13 (or [16, 18]) or "한글도 입력 됩니다." | 응답 값 |  |

### responseBody Example

```json
{
    "researchAnswerId": 1,
    "data": {
        "researchId": 1,
        "title": "title_edited",
        "description": "description_edited",
        "researchAnswerItemResponseList": [
            {
                "researchItemId": 5,
                "name": "itemName1_edited",
                "description": "itemDescription1_edited",
                "itemType": 3,
                "itemTypeName": "SINGLE_SELECTION",
                "isRequired": false,
                "researchItemChoiceResponseList": [
                    {
                        "researchItemChoiceId": 11,
                        "content": "itemChoiceContent1_1_edited"
                    },
                    {
                        "researchItemChoiceId": 12,
                        "content": "itemChoiceContent1_2_edited"
                    },
                    {
                        "researchItemChoiceId": 13,
                        "content": "itemChoiceContent1_3_edited"
                    },
                    {
                        "researchItemChoiceId": 14,
                        "content": "itemChoiceContent1_4_edited"
                    },
                    {
                        "researchItemChoiceId": 15,
                        "content": "itemChoiceContent1_5_edited"
                    }
                ],
                "answer": 13
            },
            {
                "researchItemId": 6,
                "name": "itemName2_edited",
                "description": "itemDescription2_edited",
                "itemType": 4,
                "itemTypeName": "MULTIPLE_SELECTION",
                "isRequired": true,
                "researchItemChoiceResponseList": [
                    {
                        "researchItemChoiceId": 16,
                        "content": "itemChoiceContent2_1_edited"
                    },
                    {
                        "researchItemChoiceId": 17,
                        "content": "itemChoiceContent2_2_edited"
                    },
                    {
                        "researchItemChoiceId": 18,
                        "content": "itemChoiceContent2_3_edited"
                    },
                    {
                        "researchItemChoiceId": 19,
                        "content": "itemChoiceContent2_4_edited"
                    },
                    {
                        "researchItemChoiceId": 20,
                        "content": "itemChoiceContent2_5_edited"
                    }
                ],
                "answer": [
                    16,
                    18
                ]
            },
            {
                "researchItemId": 7,
                "name": "itemName3_edited",
                "description": "itemDescription3_edited",
                "itemType": 1,
                "itemTypeName": "SHORT_ANSWER",
                "isRequired": true,
                "researchItemChoiceResponseList": [],
                "answer": "한글도 입력 됩니다."
            },
            {
                "researchItemId": 8,
                "name": "itemName4_edited",
                "description": "itemDescription4_edited",
                "itemType": 2,
                "itemTypeName": "LONG_SENTENCE",
                "isRequired": false,
                "researchItemChoiceResponseList": [],
                "answer": ""
            }
        ]
    }
}
```

---

## `GET` 설문조사 항목명, 응답 값 검색 API

`/research/search/research-answer`

### Request

| Field | Data Type | Example Value | Description | Description |
| --- | --- | --- | --- | --- |
| keyword | String | "한글" | 검색어 |  |

### request Example uri

`/research/search/research-answer?keyword=한글`

### Response

| Field | Data Type | Example Value | Description | Description |
| --- | --- | --- | --- | --- |
| researchAnswerList | List |  | 설문 응답 리스트 |  |
| researchAnswerList[N] |  |  |  |  |
| **researchAnswerId** | Long
(Number) | 1 | 설문 응답 답변 id |  |
| **data** | Object |  | 설문 응답 데이터 |  |
| —-- researchId | Long
(Number) | 1 | 설문 id |  |
| -—- title | String | "title_edited" | 설문 제목 |  |
| --— description | String | "description_edited" | 설문 설명 |  |
| -—- researchAnswerItemResponseList | List |  | 설문 응답 항목 리스트 |  |
| -—-- researchAnswerItemResponseList[n] | **ResearchItemResponse** |  | First item in the item list. |  |
| -—---- researchItemId | Long
(Number) | 5 | 설문 항목 id |  |
| -—---- name | String | "itemName1_edited" | 설문 항목명 |  |
| --—--- description | String | "itemDescription1_edited" | 설문 항목 설명 |  |
| -—---- itemType | Integer | 3 | 설문 항목 유형 | 1: 단답형
2: 장문형
3: 단일 선택형
4: 다중 선택형 |
| ---—-- itemTypeName | String | "SINGLE_SELECTION" | 설문 항목 유형 | SHORT_ANSWER: 단답형
LONG_SENTENCE: 장문형
SINGLE_SELECTION: 단일 선택형
MULTIPLE_SELECTION: 다중 선택형 |
| -----— isRequired | Boolean | false | 필수 입력 여부 |  |
| -----— researchItemChoiceResponseList | List |  | 설문 항목 선택 리스트 | itemType 값이 3, 4일 경우에만 itemChoiceList 데이터 저장됨. |
| ------— researchItemChoiceResponseList[m] | **ItemChoiceResponse** |  |  |  |
| -------— researchItemChoiceId | Long
(Number) | 11 | 설문 항목 선택 id |  |
| ------—- content | String | "itemChoiceContent1_1_edited" | 설문 항목 선택 값 |  |
| ---— answer | Integer or Array or String | 13 (or [16, 18]) or "한글도 입력 됩니다." | 응답 값 |  |

### responseBody Example

```json
{
    "researchAnswerList": [
        {
            "researchAnswerId": 1,
            "data": {
                "researchId": 1,
                "title": "title_edited",
                "description": "description_edited",
                "researchAnswerItemResponseList": [
                    {
                        "researchItemId": 5,
                        "name": "itemName1_edited",
                        "description": "itemDescription1_edited",
                        "itemType": 3,
                        "itemTypeName": "SINGLE_SELECTION",
                        "isRequired": false,
                        "researchItemChoiceResponseList": [
                            {
                                "researchItemChoiceId": 11,
                                "content": "itemChoiceContent1_1_edited"
                            },
                            {
                                "researchItemChoiceId": 12,
                                "content": "itemChoiceContent1_2_edited"
                            },
                            {
                                "researchItemChoiceId": 13,
                                "content": "itemChoiceContent1_3_edited"
                            },
                            {
                                "researchItemChoiceId": 14,
                                "content": "itemChoiceContent1_4_edited"
                            },
                            {
                                "researchItemChoiceId": 15,
                                "content": "itemChoiceContent1_5_edited"
                            }
                        ],
                        "answer": 13
                    },
                    {
                        "researchItemId": 6,
                        "name": "itemName2_edited",
                        "description": "itemDescription2_edited",
                        "itemType": 4,
                        "itemTypeName": "MULTIPLE_SELECTION",
                        "isRequired": true,
                        "researchItemChoiceResponseList": [
                            {
                                "researchItemChoiceId": 16,
                                "content": "itemChoiceContent2_1_edited"
                            },
                            {
                                "researchItemChoiceId": 17,
                                "content": "itemChoiceContent2_2_edited"
                            },
                            {
                                "researchItemChoiceId": 18,
                                "content": "itemChoiceContent2_3_edited"
                            },
                            {
                                "researchItemChoiceId": 19,
                                "content": "itemChoiceContent2_4_edited"
                            },
                            {
                                "researchItemChoiceId": 20,
                                "content": "itemChoiceContent2_5_edited"
                            }
                        ],
                        "answer": [
                            17
                        ]
                    },
                    {
                        "researchItemId": 7,
                        "name": "itemName3_edited",
                        "description": "itemDescription3_edited",
                        "itemType": 1,
                        "itemTypeName": "SHORT_ANSWER",
                        "isRequired": true,
                        "researchItemChoiceResponseList": [],
                        "answer": "한글도 입력 됩니다. 222"
                    },
                    {
                        "researchItemId": 8,
                        "name": "itemName4_edited",
                        "description": "itemDescription4_edited",
                        "itemType": 2,
                        "itemTypeName": "LONG_SENTENCE",
                        "isRequired": false,
                        "researchItemChoiceResponseList": [],
                        "answer": "요요"
                    }
                ]
            }
        },
        {
            "researchAnswerId": 2,
            "data": {
                "researchId": 1,
                "title": "title_edited",
                "description": "description_edited",
                "researchAnswerItemResponseList": [
                    {
                        "researchItemId": 5,
                        "name": "itemName1_edited",
                        "description": "itemDescription1_edited",
                        "itemType": 3,
                        "itemTypeName": "SINGLE_SELECTION",
                        "isRequired": false,
                        "researchItemChoiceResponseList": [
                            {
                                "researchItemChoiceId": 11,
                                "content": "itemChoiceContent1_1_edited"
                            },
                            {
                                "researchItemChoiceId": 12,
                                "content": "itemChoiceContent1_2_edited"
                            },
                            {
                                "researchItemChoiceId": 13,
                                "content": "itemChoiceContent1_3_edited"
                            },
                            {
                                "researchItemChoiceId": 14,
                                "content": "itemChoiceContent1_4_edited"
                            },
                            {
                                "researchItemChoiceId": 15,
                                "content": "itemChoiceContent1_5_edited"
                            }
                        ],
                        "answer": 13
                    },
                    {
                        "researchItemId": 6,
                        "name": "itemName2_edited",
                        "description": "itemDescription2_edited",
                        "itemType": 4,
                        "itemTypeName": "MULTIPLE_SELECTION",
                        "isRequired": true,
                        "researchItemChoiceResponseList": [
                            {
                                "researchItemChoiceId": 16,
                                "content": "itemChoiceContent2_1_edited"
                            },
                            {
                                "researchItemChoiceId": 17,
                                "content": "itemChoiceContent2_2_edited"
                            },
                            {
                                "researchItemChoiceId": 18,
                                "content": "itemChoiceContent2_3_edited"
                            },
                            {
                                "researchItemChoiceId": 19,
                                "content": "itemChoiceContent2_4_edited"
                            },
                            {
                                "researchItemChoiceId": 20,
                                "content": "itemChoiceContent2_5_edited"
                            }
                        ],
                        "answer": [
                            16,
                            18
                        ]
                    },
                    {
                        "researchItemId": 7,
                        "name": "itemName3_edited",
                        "description": "itemDescription3_edited",
                        "itemType": 1,
                        "itemTypeName": "SHORT_ANSWER",
                        "isRequired": true,
                        "researchItemChoiceResponseList": [],
                        "answer": "한글도 입력 됩니다."
                    },
                    {
                        "researchItemId": 8,
                        "name": "itemName4_edited",
                        "description": "itemDescription4_edited",
                        "itemType": 2,
                        "itemTypeName": "LONG_SENTENCE",
                        "isRequired": false,
                        "researchItemChoiceResponseList": [],
                        "answer": ""
                    }
                ]
            }
        }
    ]
}
```