package kevin.mokito.domain;

import lombok.Getter;

@Getter
public class Member {
    private Long id;
    private String email;

    public Member(Long id, String email) {
        this.id = id;
        this.email = email;
    }
}
