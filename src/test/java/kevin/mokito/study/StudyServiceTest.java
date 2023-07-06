package kevin.mokito.study;

import kevin.mokito.member.MemberService;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

class StudyServiceTest {

    @Test
    void createStudyService(){

        //mock을 이용해 임의로 인터페이스의 구현체를 만들 수 있다.
        MemberService memberService = mock(MemberService.class);
        StudyRepository studyRepository = mock(StudyRepository.class);

        //StudyService 를 생성하는데 필요한 MemberService 와 StudyRepository 가 구현체가 없는 인터페이스이므로 생성할 수가 없다.
        //이런 경우 mocking 을 해서 생성 할 수있다.
        StudyService studyService = new StudyService(memberService, studyRepository);

        assertNotNull(studyService);
    }
}