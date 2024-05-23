package wanted.preonboard.market.domain.dto;

import org.springframework.http.ResponseEntity;
import wanted.preonboard.market.message.ResponseMessage;

import java.util.Map;

public record ResponseBad(Object body) {
    public ResponseEntity<Map<String, Object>> toResponse() {
        return ResponseEntity.badRequest().body(Map.of(
            ResponseMessage.STATUS.getKey(), ResponseMessage.FAILED.getKey(),
            ResponseMessage.MESSAGE.getKey(), body
        ));
    }
}
