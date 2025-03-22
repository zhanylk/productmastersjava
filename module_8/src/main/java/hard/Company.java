package hard;

import lombok.Builder;
import lombok.Data;
import java.util.List;

@Data
@Builder
public class Company {
    private String name;
    private List<Person> employees;
}