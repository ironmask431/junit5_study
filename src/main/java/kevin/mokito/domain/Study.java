package kevin.mokito.domain;

import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Service;

@Getter
@Setter
public class Study {
    Member owner;
    int limit;
    String name;

    public Study(int limit, String name) {
        this.limit = limit;
        this.name = name;
    }
}
