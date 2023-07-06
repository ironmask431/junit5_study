package kevin.mokito.study;


import kevin.mokito.domain.Study;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudyRepository extends JpaRepository<Study, Long> {
    Study save(Study study);
}
