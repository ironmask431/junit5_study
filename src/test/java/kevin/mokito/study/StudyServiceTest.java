package kevin.mokito.study;

import kevin.mokito.domain.Member;
import kevin.mokito.exception.MemberNotFoundException;
import kevin.mokito.member.MemberService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
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






}