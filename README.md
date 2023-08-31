### 더 자바, 애플리케이션을 테스트하는 다양한 방법 / 백기선  

Junit, Mokito 를 사용하여 자바 단위테스트 하는법 공부

## 1. JUnit
1. JUnit - 자바진영에서 가장 많이 쓰이는 테스트 프레임워크
2. springboot 2.2 버전 이상부터는 기본적으로 junit5 를 사용할수 있도록 의존성을 지원해줌.

참고소스
https://github.com/ironmask431/junit5_study/blob/master/src/test/java/kevin/junit5_study/StudyTest.java

## 2. Mokito 
1. Mock : 진짜 객체와 비슷하게 동작하지만 개발자가 직접 그 객체의 행동을 관리할 수 있는 객체
2. Mokito : Mock 객체를 쉽게만들고 관리하고 검증할수 있는 방법 제공
3. 스프링 부트 2.2+ 프로젝트 생성시 spring-boot-starter-test에서 자동으로 Mockito 추가해 줌

참고 소스 
https://github.com/ironmask431/junit5_study/blob/master/src/test/java/kevin/junit5_study/mokito/study/StudyServiceTest.java





