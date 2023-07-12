package kevin.mokito.member;

import kevin.mokito.domain.Member;
import kevin.mokito.domain.Study;
import kevin.mokito.exception.MemberNotFoundException;

import java.util.Optional;

public interface MemberService {
    Optional<Member> findById(Long memberId);

    void validate(Long memberId);

    void notify(Study study);

    void notify(Member member);

}
