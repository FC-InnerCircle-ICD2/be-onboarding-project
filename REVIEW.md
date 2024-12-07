# 의존성
- 아키텍처 설계
    - 세로로 쪼개는 방법 : 모듈로 쪼갠다.
    - 가로로 쪼개는 방법 : 도메인 별로 쪼갠다.
    - 세로, 가로 둘다 쪼개는 방법
    - 아키텍처 설계는 trade off가 발생하기에 내가 감당할 수 있는 범위 안에서 쪼개는 것이 좋다.
- DTO는 왠만하면 common에 넣지 않는 것을 추천
- import가 많다는 것은 해당 클래스가 변경에 매우 취약하다는 것을 의미한다. DIP를 활용해서 해당 문제를 해결해라
- 의존성 관련 이슈를 수정할때는 import를 보면서 진행해야한다.
- 하나의 모듈안에 있다고해서 같은 레벨에 위치하는 것이 아니다. 패키지 밖으로 나가면 외부 모듈이라고 생각해야한다.
- Infrastructure layer를 모듈로 관리(redis, h2, mysql 관련 모듈)해서 해당 모듈로 의존성을 주입받는다.(해당 모듈에는 코드가 없어도 괜찮다)

# 비즈니스 로직 관련 리뷰
- 현재 비즈니스 로직은 코드 영향을 많이 받는다.
    - 예를들어, a -> b(validation) -> c(register) 가 있다고 하면 b를 주석처리하게 될 경우 등록관련 테스트코드가 정상작동하게 된다.
    - c에서 b를 강제호출하는 구조로 진행해야한다.

# AnswerEntity

- 현재 답변을 List<String>으로 관리하고 있는데 이는 매우 취약한 형태이다.
- 설계 할때는 단순히 해당 로직만 생각하고 설계하면 안된다.
- 추후 확장성을 고려한 설계를 진행해야한다.
- 위와 같은 경우 크게 다음과 같은 방법으로 해결이 가능 할 것 같음.
    - JPA 상속 기능 활용
    - @Embeddable @Embedded 사용
    - @ElementCollection

      | 방법                   | 장점                                                       | 단점                                                       |
      |------------------------|------------------------------------------------------------|------------------------------------------------------------|
      | **JPA 상속 기능**       | 다형성 지원, 객체 지향 설계, 공통 필드 재사용              | 설계 복잡성, 성능 이슈, 확장성 문제                         |
      | **@Embeddable/@Embedded** | 간단한 설계, 유연성, 객체 간 관계 표현, 성능 효율적         | 다형성 미지원, 복잡성 증가, 스키마 변경 어려움             |
      | **@ElementCollection**   | 유연한 컬렉션 저장, 간단한 설계, 데이터베이스 효율성        | 제약이 많고 복잡한 데이터 구조 표현 제한, 성능 저하 우려    |