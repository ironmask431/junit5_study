package kevin.mokito.member;

import kevin.mokito.domain.Member;
import kevin.mokito.exception.MemberNotFoundException;

import java.util.Optional;

public interface MemberService {
    Optional<Member> findById(Long memberId) throws MemberNotFoundException;
}
