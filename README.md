### 더 자바, 애플리케이션을 테스트하는 다양한 방법 / 백기선  

Junit, Mokito 를 사용하여 자바 단위테스트 하는법 공부

## 1. JUnit
1. JUnit - 자바진영에서 가장 많이 쓰이는 테스트 프레임워크
2. JUnit5 - 스프리부트 2.2 이상 사용 시 기본으로 의존성 추가됨.
3. 기본 어노테이션 - @Test, @BeforeAll, @AfterAll, @BeforeEach, @AfterEach, @Disable

## 2. JUnit 테스트 이름 표시
1. @DisplayName

## 3. JUnit5 : Assertion
1. 실제 값이 기대한 값과 같은지 확인   
`assertEqulas(expected, actual)`
2. 값이 null이 아닌지 확인   
`assertNotNull(actual)`
3. 다음 조건이 참(true)인지 확인   
`assertTrue(boolean)`
4. 모든 확인 구문 확인   
`assertAll(executables...)`
5. 예외 발생 확인   
`assertThrows(expectedType, executable)`
6. 특정 시간 안에 실행이 완료되는지 확인   
`assertTimeout(duration, executable)`





