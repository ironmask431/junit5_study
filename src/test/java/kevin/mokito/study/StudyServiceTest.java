package kevin.mokito.study;

import kevin.junit5_study.StudyStatus;
import kevin.mokito.domain.Member;
import kevin.mokito.domain.Study;
import kevin.mokito.member.MemberService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InOrder;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.*;

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