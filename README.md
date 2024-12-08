# 이너써클 BE 온보딩 프로젝트

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

---

# 프로젝트 설명

## 컨벤션

### 브랜치 관리 전략

⚙️ github-flow

![image](https://github.com/user-attachments/assets/9ec9ad98-a991-44d7-8613-1966725b85ab)

* **main(master)**: 서비스을 직접 배포하는 역할을 하는 브랜치입니다.
* **feature(기능)**: 각 기능 별 개발 브랜치입니다.
* **develop(개발)**: feature에서 개발된 내용을 가지고 있는 브랜치입니다.
* **release(배포)**: 배포를 하기 전 내용을 QA(품질 검사)하기 위한 브랜치입니다.
* **hotfix(빨리 고치기)**: main 브랜치로 배포를 하고 나서 버그가 생겼을 때 빨리 고치기 위한 브랜치입니다.

## git 커밋 컨벤션

### 1. Commit Type
타입은 태그와 제목으로 구성되고, 태그는 영어로 쓰되 첫 문자는 대문자입니다.

태그 : 제목의 형태이며, :뒤에만 space가 있습니다.

* feat : 새로운 기능 추가
* fix : 버그 수정
* docs : 문서 수정
* style : 코드 포맷팅, 세미콜론 누락, 코드 변경이 없는 경우
* refactor : 코드 리펙토링
* test : 테스트 코드, 리펙토링 테스트 코드 추가
* chore : 빌드 업무 수정, 패키지 매니저 수정

## DB 모델링

![image](https://github.com/user-attachments/assets/d45879fb-7280-4be3-a257-f216dd553cb1)

### 개요

설문조사 서비스를 개발하기 위해 데이터베이스를 모델링했습니다. 주요 요구사항은 다음과 같습니다:

* **설문조사 생성, 수정, 응답 제출, 응답 조회** 기능을 지원해야 합니다.
* **설문조사 수정 시 기존 응답이 유지**되어야 하므로 **버전 관리**가 필요합니다.
* 설문 항목은 다양한 **입력 형태**를 가지며, **선택형의 경우 선택 후보**를 포함해야 합니다.
* 응답 조회 시 **설문 항목의 이름과 응답 값을 기반으로 검색**할 수 있어야 합니다.

---

### 엔티티 설명

주요 엔티티는 다음과 같습니다:

1. **Survey**: 설문조사의 기본 정보
2. **SurveyVersion**: 설문조사의 버전 관리
3. **SurveyItem**: 설문 항목(질문)
4. **SurveyItemOption**: 선택형 항목의 선택지
5. **Response**: 설문 응답
6. **ResponseItem**: 각 설문 응답의 개별 항목 응답

---

### 엔티티 관계도 (ERD)

```xml
Survey (1) ──── (N) SurveyVersion
SurveyVersion (1) ──── (N) SurveyItem
SurveyItem (1) ──── (N) SurveyItemOption
SurveyVersion (1) ──── (N) Response
Response (1) ──── (N) ResponseItem
SurveyItem (1) ──── (N) ResponseItem
```

* **Survey - SurveyVersion**: 1 대 N 관계 (하나의 설문조사는 여러 버전을 가질 수 있음)
* **SurveyVersion - SurveyItem**: 1 대 N 관계 (하나의 설문 버전은 여러 설문 항목을 가질 수 있음)
* **SurveyItem - SurveyItemOption**: 1 대 N 관계 (하나의 설문 항목은 여러 선택지를 가질 수 있음)
* **SurveyVersion - Response**: 1 대 N 관계 (하나의 설문 버전에 여러 응답이 있을 수 있음)
* **Response - ResponseItem**: 1 대 N 관계 (하나의 응답은 여러 응답 항목을 가짐)
* **SurveyItem - ResponseItem**: 1 대 N 관계 (하나의 설문 항목에 여러 응답이 있을 수 있음)

---

### 동작 흐름 설명

#### 1. **설문조사 생성 API**

* **클라이언트**가 설문조사 생성 API에 요청을 보냅니다.
* **서버**는 **Survey**와 **SurveyVersion**에 레코드를 생성합니다.
* **SurveyItem**과 **SurveyItemOption**을 **SurveyVersion**에 연결하여 저장합니다.

#### 2. **설문조사 수정 API**

* **클라이언트**가 설문조사 수정 API에 요청을 보냅니다.
* **서버**는 새로운 **SurveyVersion**을 생성합니다.
* 변경된 내용에 따라 **SurveyItem**과 **SurveyItemOption**을 생성 또는 복사합니다.
* 기존 응답은 이전 **SurveyVersion**에 연결되어 영향을 받지 않습니다.

#### 3. **설문조사 응답 제출 API**

* 사용자는 특정 **SurveyVersion**에 대한 응답을 제출합니다.
* **Response** 레코드가 생성되고, 해당하는 **ResponseItem**들이 저장됩니다.
* 응답 제출 시 클라이언트는 현재 **SurveyVersion**의 정보를 기반으로 합니다.

#### 4. **설문조사 응답 조회 API**

* 특정 **Survey**의 모든 **SurveyVersion**에 대한 응답을 조회할 수 있습니다.
* 응답 조회 시 **SurveyItem**과 **ResponseItem**을 조인하여 항목 이름과 응답 값을 가져옵니다.
* 고급 검색 기능은 항목 이름과 응답 값을 조건으로 필터링합니다.

---

### 주요 고려 사항

#### 1. **버전 관리**

* 설문조사 수정 시 **버전 번호**를 증가시켜 관리함으로써 응답 데이터의 일관성을 유지합니다.
* 각 응답은 해당 시점의 **SurveyVersion**에 연결되어 과거 응답이 변경되지 않습니다.

#### 2. **선택형 항목의 선택지 관리**

* 선택형 항목의 경우 **SurveyItemOption** 테이블에서 선택지를 관리합니다.
* 선택지의 순서와 내용을 저장하여 응답 시 정확한 매칭이 가능합니다.

#### 3. **응답 데이터의 유효성 검사**

* 서버에서 추가적인 검증을 통해 데이터 무결성을 보장합니다.

#### 4. **고급 검색 기능 구현**

* 응답 조회 시 **ResponseItem**과 **SurveyItem**을 조인하여 항목 이름 기반으로 검색합니다.
* 향후 인덱스를 적절히 활용하여 검색 성능을 향상시킵니다.
