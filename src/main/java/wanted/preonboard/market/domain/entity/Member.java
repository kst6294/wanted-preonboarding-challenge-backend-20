package wanted.preonboard.market.domain.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Member {
    @JsonIgnore
    private Integer id;
    private String username;
    private String email;
    @JsonIgnore
    private String password;
}
