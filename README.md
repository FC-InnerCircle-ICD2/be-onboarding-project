## About Project

### 엔티티 설계
- 설문조사의 개념 'Survey', 설문조사 항목의 개념 'SurveyItem', 선택형 항목의 경우 사용자 정의 option의 개념 'ItemAnswerOption', 설문조사 항목 응답의 개념 'ItemAnswer' 로 설계하였습니다.
- @OneToMany 의 연관관계를 사용하였습니다. 물리적으로 생각해 보았을 때 설문조사가 설문조사 항목의 리스트 를 가지고있고, 설문조사 항목이 옵션의 리스트를 가지고있다고 생각하였습니다.
- 저는 설문조사 응답(ItemAnswer) 를 Survey 에 종속시키지 않고 SurveyItem 에 종속시켰습니다.
- '설문조사와 항목들이 수정되어도 기존의 응답은 모두 유지해야 한다' 라고 적어주신 요구사항에 대해서는, Survey 수정 시 기존의 모든 항목들을 disabled 처리하고 요청에 포함된 모든 항목들을 save 하였습니다.
응답자를 위해 설문조사 테이블을 조회해야 할 경우에는 'isDisabled = false' 조건으로 조회하게 되고, 생성자를 위한 설문조사 응답 조회 에서는 disabled 에 조건을 걸지 않고 기존에 작성된 모든 항목들과 응답들을 불러오도록 구성하였습니다.
- 수정(disabled) 된 항목들을 삭제처리하고, 새로운 테이블(DisabledSurveyItem) 을 생성해서 관리하는 방법도 생각했었으나, 설문조사 생성자 입장에서는 '단순 수정' 만 진행했는데 disabled 된 리스트가 따로 저장된다는 부분이 바람직 하지 않다고
판단하여 이런 방식을 선택하였습니다.
- ItemAnswer
1. 단답형, 서술형 응답을 저장하는 String 타입의 answer,
2. 해당 응답이 선택형 항목인지에 대한 정보를 저장하는 Boolean 타입의 isOptioanlAnswer,
3. 선택형 항목의 id 를 참조하기 위한 Long 타입의 optionSeq,
4. 그리고 선택형 항목에 대한 응답의 경우, option 값을 같이 저장하기 위한 String 타입의 optionAnswer
가 포함되어 있습니다.

### 프로젝트 설계
- 디렉토리 구조를 동작으로 먼저 구분하고, 그 내에서 도메인 별로 나누도록 선택하였습니다.(도메인이 많아질 수록 이런 방식이 유리하다고 생각했습니다)
- Controller, service, business, repository, dto, entity, enums 등으로 레이어를 구분하였습니다.
- 응답 처리의 경우, Http Method 별 API 의 근본적인 목적에 대해 생각해보았을 때, GET 을 제외한 메서드들은 모두 '동작' 에 목적이 있다고 생각하고 이에 집중하고자
return type 을 void 로 처리하였습니다. 응답 성공과 실패를 HttpStatus Status 로 구분하고, 자세한 예외 상황에 대한 내용은 ExceptionResponseType.code 를 정의함으로 처리하였습니다.
- 예외의 경우 ApiException 객체를 구현하고 미리 정의해둔 ExceptionResponseType 으로 예외 상황을 구분하여 처리하였습니다.
- 사용자의 IP 주소를 '계정' 의 개념으로 사용하였습니다. IP 당 같은 설문조사 이름을 등록할 수 없도록 처리하였습니다.

### 그 외 참고사항들...
<b>다양한 프로젝트 경험이 없고, 누군가와 코드를 공유하며 작업을 해 본 적이 없기에 지금껏 '동작' 위주의 코딩을 하였습니다. setter 의 남용, 한 메서드에 너무 많은 역할을 주어 코드 재사용성 감소 등...
이번 프로젝트는 가능한 깔끔하게 코드를 작성하기 위해 노력해보았습니다.</b>

- 도메인 모델 패턴 방식으로 코드를 작성해 보았습니다.
- entity 를 생성하는 방식을 dto 를 통하는 방식 만으로 제한을 두어 무결성을 높이고자 하였습니다.
- service 와 business(query, action) 레이어를 구분하여 메서드의 역할을 구분하고 재사용성을 높여보았습니다.
- emun에는 실제 db에 저장 될 code(Integer type)와 개발자, 클라이언트 에서 구분을 편리하게 하기 위한 typString(String type) 으로 구성하였습니다.
- 깔끔한 코드를 작성하는데 집중하다보니, 12.04 단체 리뷰에서 말씀해주신 확장성을 고려한 설계에 대한 고민을 깊게 해보지 못하였습니다.



jar 파일 링크 : https://drive.google.com/file/d/1iXhGGXtXm4vk9wWNJuwdGKYnzsMaRtiF/view?usp=drive_link


api 명세서 및 postman : https://www.postman.com/aviation-geoscientist-88362321/my-workspace/documentation/xbmrsoc/survey?workspaceId=9ec3ee6f-dc14-444d-8807-b812eba77e74
