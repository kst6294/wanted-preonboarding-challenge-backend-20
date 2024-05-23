package wanted.preonboard.market.domain.dto;

import org.springframework.http.ResponseEntity;
import wanted.preonboard.market.domain.entity.Member;
import wanted.preonboard.market.message.ResponseMessage;

import java.util.Map;

public record ResponseOk(Object body) {
    public ResponseEntity<Map<String, Object>> toResponse() {
        if (body.getClass().equals(Member.class)) {
            return ResponseEntity.ok(Map.of(
                ResponseMessage.STATUS.getKey(), ResponseMessage.SUCCESS.getKey(),
                ResponseMessage.DATA.getKey(), body
            ));
        }
        return ResponseEntity.ok(Map.of(
            ResponseMessage.STATUS.getKey(), ResponseMessage.SUCCESS.getKey(),
            ResponseMessage.MESSAGE.getKey(), body
        ));
    }
}