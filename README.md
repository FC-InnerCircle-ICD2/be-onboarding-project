# [IC2_BE] 최다솔 - 설문조사 서비스 과제

# API 주소

[바로가기](http://localhost:12345/swagger-ui/index.html)

```text
http://localhost:12345/swagger-ui/index.html
```

# Jar

[다운로드](https://drive.google.com/file/d/17a-RaT7cnZzKTQGK79m7zO85JdolVZjR/view?usp=sharing)

```java
java -jar survey-0.0.1-SNAPSHOT.jar
```

### 멀티모듈

---

개념

1. 루트 프로젝트
   - 전체 프로젝트를 관리하는 최상위 프로젝트
   - 빌드 설정 파일이 서브모듈을 포함하고 관리
   - 공통 설정 및 플로그인, 종속성, 버전을 정의히여 서브모듈이 상속받을 수 있도록 구성
2. 서브모듈
   - 각각의 모듈은 독립적인 기능을 담당
   - 다른 모듈과 의존관계를 가질 수 있지만 종속성은 명확하게 관리
   - 일반적으로 core, service, web 등의 역할로 나뉨

구현방법

1. 아래와 같이 프로젝트를 생성한다
   ```
   root-project/
   ├── build.gradle
   ├── settings.gradle
   ├── core/
   ├── service/
   └── web/
   ```
2. settings.gradle에 서브 모듈을 포함한다.

   settings.gradle

   ```
   rootProject.name = 'multi-module-project'
   include 'core', 'service', 'web'
   ```

3. build.gradle에 공통 종속성 및 플러그인을 정의한다.

   build.gradle

   ```
   plugins {
   id 'org.springframework.boot' version '3.1.0'
   id 'io.spring.dependency-management' version '1.1.0'
   id 'java'
   }
   // 모든 프로젝트에 포함될 설정
   allprojects {
       group = 'com.example'
       version = '1.0.0'

       repositories {
           mavenCentral()
       }
   }
   // 서브 프로젝트에 포함될 설정
   subprojects {
       apply plugin: 'java'

       dependencies {
           testImplementation 'org.springframework.boot:spring-boot-starter-test'
       }
   }
   ```

4. 서브모듈 설정

   core 모듈은 다른 모듈에서 재 사용하는 공통 기능이 작성된다. (예: DTO, 유틸리티 클래스)
   core/build.gradle

   ```
   dependencies {
    implementation 'org.springframework.boot:spring-boot-starter'
   }
   ```

   service 모듈은 비즈니스 로직을 처리한다. core 모듈을 의존성으로 포함할 수 있다.
   service/build.gradle

   ```
   dependencies {
    implementation project(':core')
    implementation 'org.springframework.boot:spring-boot-starter'
   }
   ```

   web 모듈은 컨트롤러 및 사용자와의 상호작용을 처리하며 service 모듈을 의존성으로 포함한다.

   ```
   plugins {
    id 'org.springframework.boot'
    id 'io.spring.dependency-management'
    }
    dependencies {
        implementation project(':service')
        implementation 'org.springframework.boot:spring-boot-starter-web'
    }
   ```
