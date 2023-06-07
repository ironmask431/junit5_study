package kevin.junit5_study;

import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

class StudyTest {
    //springboot 2.2 버전 이상부터는 기본적으로 junit5 를 사용할수 있도록 의존성을 지원해줌.

    @Test
    @DisplayName("스터디 만들기") //표기될 테스트메소드 이름 지정
    void create(){
        Study study = new Study();
        assertNotNull(study);
        System.out.println("create");
    }

    @Test
    @DisplayName("스터디 만들기1")
    void create1(){
        System.out.println("create1");
    }

    //@Disabled = 테스트실행되지 않도록 할때.
    @Test
    @Disabled
    void create1_disabled(){
        System.out.println("create1_disabled");
    }

    //모든 테스트 시작전 1회
    @BeforeAll
    static void beforeAll(){
        System.out.println("beforeAll");
    }

    //모든 테스트 종료후 1회
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

    //실행결과
//    beforeAll
//    beforeEach
//    create
//    afterEach
//    beforeEach
//    create1
//    afterEach
//    afterAll


}