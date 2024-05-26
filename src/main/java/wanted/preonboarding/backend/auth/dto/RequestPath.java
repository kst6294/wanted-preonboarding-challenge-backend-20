package wanted.preonboarding.backend.auth.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpMethod;

@Getter
@AllArgsConstructor
public class RequestPath {
    private String path;
    private HttpMethod httpMethod;
}
