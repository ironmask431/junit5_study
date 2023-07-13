package kevin.mokito.study;

import kevin.mokito.domain.Member;
import kevin.mokito.domain.Study;
import kevin.mokito.member.MemberService;

import java.util.Optional;

public class StudyService {
    private final MemberService memberService;
    private final StudyRepository studyRepository;

    public StudyService(MemberService memberService, StudyRepository studyRepository) {
        //assert 사용시 조건에 충족하지않으면 assertException 발생함.
        assert memberService != null;
        assert studyRepository != null;
        this.memberService = memberService;
        this.studyRepository = studyRepository;
    }

    public Study createNewStudy(Long memberId, Study study) throws Exception {
        Optional<Member> member = memberService.findById(memberId);
        study.setOwner(member.orElseThrow(() -> new IllegalArgumentException("Member is not exist for id: "+memberId)));
        Study newStudy = studyRepository.save(study);
        memberService.notify(newStudy);
        memberService.notify(member.get());
        return newStudy;
    }

    public Study openStudy(Study study){
        study.open();
        Study opendStudy = studyRepository.save(study);
        memberService.notify(opendStudy);
        return opendStudy;
    }
}
