package kevin.mokito.domain;

import kevin.junit5_study.StudyStatus;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Getter
@Setter
public class Study {
    private Member owner;
    private int limit;
    private String name;

    private StudyStatus studyStatus;

    private LocalDateTime openedDateTime;

    public Study(int limit, String name) {
        this.limit = limit;
        this.name = name;
        this.studyStatus = StudyStatus.DRAFT;
    }

    public void open(){
        this.studyStatus = StudyStatus.OPENED;
        this.openedDateTime = LocalDateTime.now();
    }
}
