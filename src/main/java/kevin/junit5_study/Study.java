package kevin.junit5_study;

import lombok.Getter;

@Getter
public class Study {
    //cmd + shift + t : 해당클래스 테스트클래스 자동생성.

    private StudyStatus studyStatus;

    private int limit;

    public Study(StudyStatus studyStatus, int limit) {
        this.studyStatus = studyStatus;
        if(limit < 1){
            throw new IllegalArgumentException("limit는 1이상이어야 합니다.");
        }
        this.limit = limit;
    }
}
