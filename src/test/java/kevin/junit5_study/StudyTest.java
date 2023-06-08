package kevin.junit5_study;

import org.junit.jupiter.api.*;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.*;

class StudyTest {
    //springboot 2.2 버전 이상부터는 기본적으로 junit5 를 사용할수 있도록 의존성을 지원해줌.

    //IDE 에서 테스트 코드 실행 시 @DisplayName 표기되지 않는 경우
    //Preferences > Build tool > gradle > Run tests using : IDEA 로 변경하면 나옴.

    @Test
    @DisplayName("스터디 만들기") //표기될 테스트메소드 이름 지정
    void create(){
        Study study = new Study(StudyStatus.END, -10);

        assertNotNull(study);

        System.out.println("create");

        assertEquals(StudyStatus.DRAFT, study.getStudyStatus(), "스터디를 처음 만들면 상태값 DRAFT 여야함.");
        //메세지를 설정 해주면 테스트 실패시 사유확인이 수월함.

        //assertEquals(StudyStatus.DRAFT, study.getStudyStatus(), () ->"스터디를 처음 만들면 상태값 "+StudyStatus.DRAFT+" 여야함.");
        //메세지를 supplier 람다식으로 해놓으면 메세지가 필요한 경우만 호출 하기 때문에 (실패) 리소스 낭비 없음.

        assertTrue(study.getLimit() > 0, "스터디의 limit 는 0보다 커야한다.");
        //위 방식은 이전 테스트에서 실패가 되면 다음 테스트는 실행하지 않음.

        //실패여부 관계없이 각 테스트의 결과를 다 보려면 assertAll 사용
        assertAll(
            () -> assertNotNull(study),
            () -> assertEquals(StudyStatus.DRAFT, study.getStudyStatus(), "스터디를 처음 만들면 상태값 DRAFT 여야함."),
            () -> assertTrue(study.getLimit() > 0, "스터디의 limit 는 0보다 커야한다.")
        );
    }

    @Test
    @DisplayName("스터디 만들기 - 예외발생")
    void create_exception(){
        //예외가 발생하는지 검증
        assertThrows(IllegalArgumentException.class, () -> new Study(StudyStatus.END, -10),"limit 1 미만으로 study 생성 시 IllegalArgumentException 발생해야한다.");

        //예외 메세지 검증
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> new Study(StudyStatus.END, -10));
        assertEquals("limit는 1이상이어야 합니다.", exception.getMessage());

        //실행 시간 검증
        assertTimeout(Duration.ofMillis(10), ()-> {
            new Study(StudyStatus.DRAFT, 10);
            Thread.sleep(300);
        },"Study 생성은 10ms 이내에 완료되어야 한다.");
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