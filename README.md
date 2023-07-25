### 더 자바, 애플리케이션을 테스트하는 다양한 방법 / 백기선  

Junit, Mokito 를 사용하여 자바 단위테스트 하는법 공부

## 1. JUnit
1. JUnit - 자바진영에서 가장 많이 쓰이는 테스트 프레임워크
2. springboot 2.2 버전 이상부터는 기본적으로 junit5 를 사용할수 있도록 의존성을 지원해줌.

```java

@TestInstance(TestInstance.Lifecycle.PER_CLASS) //테스트 인스턴스의 라이프사이클 설정
// (이 테스트클래스에서는 모든 테스트가 하나의 인스턴스를 공유)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class) //테스트에 순서를 주고자 할때
class StudyTest {
    //springboot 2.2 버전 이상부터는 기본적으로 junit5 를 사용할수 있도록 의존성을 지원해줌.

    //IDE 에서 테스트 코드 실행 시 @DisplayName 표기되지 않는 경우
    //Preferences > Build tool > gradle > Run tests using : IDEA 로 변경하면 나옴.

    int test = 0;

    @Test
    @DisplayName("스터디 만들기") //표기될 테스트메소드 이름 지정
    @Order(2) //테스트에 순서를 주고자 할때
    void create(){
        //Study study = new Study(StudyStatus.END, -10);
        Study study = new Study(StudyStatus.DRAFT, 1);

        assertNotNull(study);

        System.out.println("create");

        assertEquals(StudyStatus.DRAFT, study.getStudyStatus(), "스터디를 처음 만들면 상태값 DRAFT 여야함.");
        //메세지를 설정 해주면 테스트 실패시 사유확인이 수월함.

        //assertEquals(StudyStatus.DRAFT, study.getStudyStatus(), () ->"스터디를 처음 만들면 상태값 "+StudyStatus.DRAFT+" 여야함.");
        //메세지를 supplier 람다식으로 해놓으면 메세지가 필요한 경우만 호출 하기 때문에 (실패) 리소스 낭비 없음.

        assertTrue(study.getLimit() > 0, "스터디의 limit 는 0보다 커야한다.");
        //위 방식은 이전 테스트에서 실패가 되면 다음 테스트는 실행하지 않음.

        //실패여부 관계없이 각 테스트의 결과를 모두 다 보려면 assertAll 사용
        assertAll(
            () -> assertNotNull(study),
            () -> assertEquals(StudyStatus.DRAFT, study.getStudyStatus(), "스터디를 처음 만들면 상태값 DRAFT 여야함."),
            () -> assertTrue(study.getLimit() > 0, "스터디의 limit 는 0보다 커야한다.")
        );
    }

    @Test
    @DisplayName("스터디 만들기 - 예외발생, 실행기간 검증")
    @Order(1) //테스트에 순서를 주고자 할때
    void create_exception_timeout(){
        //예외가 발생하는지 검증
        assertThrows(IllegalArgumentException.class, () -> new Study(StudyStatus.END, -10),"limit 1 미만으로 study 생성 시 IllegalArgumentException 발생해야한다.");

        //예외 메세지 검증
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> new Study(StudyStatus.END, -10));
        assertEquals("limit는 1이상이어야 합니다.", exception.getMessage());

        //실행 시간 검증
        assertTimeout(Duration.ofMillis(10), ()-> {
            new Study(StudyStatus.DRAFT, 10);
            //Thread.sleep(300);
        },"Study 생성은 10ms 이내에 완료되어야 한다.");
    }

    @Test
    @DisplayName("스터디 만들기 - 환경변수 만족시 테스트 실행")
    void create_assume(){
        String env = System.getenv("TEST_ENV");
        System.out.println(env);
        //assumeTrue = 아래 조건 만족시에만 아래 테스트 실행. (Local 환경변수 확인) - 환경변수 설정하는건 패스하자..
        //assumeTrue("LOCAL".equalsIgnoreCase(env));

        Study study = new Study(StudyStatus.END, 1);
        assertNotNull(study);

        //각 조건 만족시 마다 실행할 테스트들을 설정 할 수 있음.
        assumingThat("LOCAL".equalsIgnoreCase(env), () -> {
            System.out.println("local 테스트 실행");
            assertNotNull(study);
        });
        assumingThat("DEV".equalsIgnoreCase(env), () -> {
            System.out.println("dev 테스트 실행");
            assertNotNull(study);
        });
    }

    //내 인텔리제이 버전에서는 어떻게 태깅하여 실행하는 지모르겠음..!!
    @Test
    @DisplayName("스터디 만들기 - 태깅과 필터링 - 테스트를 그룹화 하여 특정그룹만 테스트실행 가능")
    @Tag("fast")
    void create_taging_fast(){
        Study study = new Study(StudyStatus.END, 1);
        assertNotNull(study);
    }

    @Test
    @DisplayName("스터디 만들기 - 태깅과 필터링 - 테스트를 그룹화 하여 특정그룹만 테스트실행 가능")
    @Tag("slow")
    void create_taging_slow(){
        Study study = new Study(StudyStatus.END, 1);
        assertNotNull(study);
    }

    @DisplayName("스터디 만들기 - 테스트 반복실행하기")
    @RepeatedTest(value = 10, name = "{displayName},{currentRepetition}/{totalRepetitions}") //cmd + p 를 누르면 어떤 파라미터를 입력할수 있는지 팝업뜸 꿀팁!!
    void create_repeat(RepetitionInfo repetitionInfo){
        Study study = new Study(StudyStatus.END, 1);
        assertNotNull(study);
        System.out.println("create_repeat + "+ repetitionInfo.getCurrentRepetition() + "/" + repetitionInfo.getTotalRepetitions());
    }

    @DisplayName("스터디 만들기 - 테스트 반복실행하기 - 반복시마다 다른 매개변수 사용하고싶다.")
    @ParameterizedTest(name = "{index}-{displayName} message={0}")
    @ValueSource(strings = {"조유리","송하영","박지원","홍만채"})
    void create_repeat_parameterized(String message){
        System.out.println(message); // "조유리","송하영","박지원","만채" 이 반복 실행된다.
    }

    @Test
    @DisplayName("테스트시 인스턴스가 매번 새로 생성여부 확인1")
    void testPerClass_1(){
        System.out.println(test++);

    }

    @Test
    @DisplayName("테스트시 인스턴스가 매번 새로 생성여부 확인2")
    void testPerClass_2(){
        System.out.println(test++);
        //기본적으로 매 테스트시마다 클래스 인스턴스를 새로 만들어 사용하기 때문에 test 값은 항상 0이다.
        //테스트 마다 하나의 인스턴스를 공유하기위해서 테스트 클래스에 @TestInstance(TestInstance.Lifecycle.PER_CLASS)
        //설정을 줄 수 있다. @TestInstance(TestInstance.Lifecycle.PER_CLASS) 설정후에는 test의 값이 증가한 것을 볼 수 있다.
    }

    @Test
    @DisplayName("스터디 만들기1")
    void create1(){
        System.out.println("create1");
    }

    //@Disabled = 테스트가 실행되지 않도록 할때.
    @Test
    @Disabled
    void create1_disabled(){
        System.out.println("create1_disabled");
    }

    //모든 테스트 시작전 단 1회 - static 으로 선언해야함. (@TestInstance > per_class를 선언해준 경우는 static 필요없음.)
    @BeforeAll
    static void beforeAll(){
        System.out.println("beforeAll");
    }

    //모든 테스트 종료후 단 1회 - static 으로 선언해야함. (@TestInstance > per_class를 선언해준 경우는 static 필요없음.)
    @AfterAll
    static void afterAll(){
        System.out.println("afterAll");
    }

    //각각의 테스트 시작전 1회
    @BeforeEach
    void beforeEach(){
        System.out.println("beforeEach");
    }

    //각각의 테스트 종료후 1회
    @AfterEach
    void afterEach(){
        System.out.println("afterEach");
    }
```

## 2. Mokito 
1. Mock : 진짜 객체와 비슷하게 동작하지만 개발자가 직접 그 객체의 행동을 관리할 수 있는 객체
2. Mokito : Mock 객체를 쉽게만들고 관리하고 검증할수 있는 방법 제공
3. 스프링 부트 2.2+ 프로젝트 생성시 spring-boot-starter-test에서 자동으로 Mockito 추가해 줌

```java
//방법2. @ExtendWith(MockitoExtension.class) 와 @Mock 애노테이션을 이용해 mock 객체를 만들 수 있다.
@ExtendWith(MockitoExtension.class)
class StudyServiceTest {

    @Mock
    MemberService memberService;

    @Mock
    StudyRepository studyRepository;

    @Test
    void createStudyService(){

        //방법1. mock을 이용해 임의로 인터페이스의 가상 구현체를 만들 수 있다.
//        MemberService memberService = mock(MemberService.class);
//        StudyRepository studyRepository = mock(StudyRepository.class);

        //StudyService 를 생성하는데 필요한 MemberService 와 StudyRepository 인터페이스 가 구현체가 없으므로 studyService 객체를 생성할 수가 없다.
        //이런 경우 mocking 을 해서 생성 할 수있다.
        StudyService studyService = new StudyService(memberService, studyRepository);

        assertNotNull(studyService);
    }

    /**
     * mock 객체 stubbing
     * 모든 mock 객체의 행동
     * 1. null을 리턴한다.(Optional 은 Optional.empty리턴)
     * 2. Primitive 타입은 모두 기본 Primitive 값.
     * 3. 콜렉션은 비어있는 콜렉션
     * 4. void 메소드는 예외를 던지지않고 아무런일도 발생하지 않음.
     *
     * mock 객체를 조작해서
     * 1. 특정 매개변수를 받은 경우 특정값을 리턴하거나 예외를 던지게 만들 수 있다.
     * 2. 메소드가 동일한 매개변수로 여러번 호출될때 각기 다르게 행동하도록 조작할 수 있다.
     */
    @Test
    void mockStubbing() {

        Member member = new Member(1L,"leesh@naver.com");
        //mock객체 memberService.findById(1L) 실행 시 위에서 생성한 member를 리턴하도록 설정

        // 방법1.
        when(memberService.findById(1L)).thenReturn(Optional.of(member));
        // 방법2.
        //given(memberService.findById(any())).willReturn(Optional.of(member));

        Optional<Member> findMember = memberService.findById(1L);

        //같음을 알 수있다.
        assertEquals(findMember.get().getEmail(), "leesh@naver.com");

        //특정 메소드 + 매개변수 시 예외를 던지도록 stubbing
        when(memberService.findById(1L)).thenThrow(new RuntimeException());
        doThrow(new IllegalArgumentException()).when(memberService).validate(1L);

        assertThrows(RuntimeException.class, () -> memberService.findById(1L));
        assertThrows(IllegalArgumentException.class, () -> memberService.validate(1L));
    }

    /**
     * mock 객체 stubbing 연습문제
     */
    @Test
    void mockStubbingTest() throws Exception {
        //given
        StudyService studyService = new StudyService(memberService, studyRepository);

        Member member = new Member(1L,"leesh@naver.com");
        Study study = new Study(10, "스터디테스트");

        //stubbing
        //todo. memberServcie.findById(1L) 하면 member 객체를 리턴하도록 stubbing
        when(memberService.findById(1L)).thenReturn(Optional.of(member));

        //todo. studyRepository.save(study) 하면 study 객체 그대로 리턴하도록 stubbing
        when(studyRepository.save(study)).thenReturn(study);

        //when
        Study resultStudy = studyService.createNewStudy(1L, study);

        //then
        assertAll(
            () -> assertNotNull(study),
            () -> assertEquals(study.getOwner(), resultStudy.getOwner()),
            () -> assertEquals(study.getLimit(), resultStudy.getLimit()),
            () -> assertEquals(study.getName(), resultStudy.getName())
        );
    }


    /**
     * mock verifying - mock 객체가 어떤 행동을 했는지 확인
     */
    @Test
    void verifying() throws Exception {

        //given
        StudyService studyService = new StudyService(memberService, studyRepository);

        Member member = new Member(1L,"leesh@naver.com");
        Study study = new Study(10, "스터디테스트");

        //stubbing
        when(memberService.findById(1L)).thenReturn(Optional.of(member));
        when(studyRepository.save(study)).thenReturn(study);

        //when => given으로 변환 (BDD 스타일)
        given(memberService.findById(1L)).willReturn(Optional.of(member));
        given(studyRepository.save(study)).willReturn(study);

        //when
        Study resultStudy = studyService.createNewStudy(1L, study);

        //then
        //memberService.notify() 가 1번 실행됐는지 검증
        verify(memberService, times(1)).notify(study);

        //verify => then으로 변환 (BDD 스타일)
        then(memberService).should(times(1)).notify(study);

        //memberService.validate() 가 실행되지 않았는지 검증
        verify(memberService, never()).validate(any());

        //메소드 호출 순서 검증
        InOrder inOrder = inOrder(memberService);
        inOrder.verify(memberService).notify(study);
        inOrder.verify(memberService).notify(member);
    }

    @DisplayName("다른 사용자가 볼 수 있도록 스터디를 공개한다.")
    @Test
    void mokitoPractice(){
        //given
        StudyService studyService = new StudyService(memberService, studyRepository);

        Study study = new Study(10, "더 자바, 테스트");

        //todo. studyRepository Mock 객체의 save 메소드 호출 시 study 리턴하도록 만들기
        given(studyRepository.save(study)).willReturn(study);

        //when
        studyService.openStudy(study);

        //then
        //todo. study의 status 가 OPENED  로 변경됬는지 확인
        assertEquals(study.getStudyStatus(), StudyStatus.OPENED);
        //todo. study의 openedDateTime 이 null이 아닌지 확인
        assertNotNull(study.getOpenedDateTime());
        //todo. memberService 의 notify(study)가 호출 됬는지 확인
        verify(memberService, times(1)).notify(study);
        then(memberService).should().notify(study);
    }
}
```




