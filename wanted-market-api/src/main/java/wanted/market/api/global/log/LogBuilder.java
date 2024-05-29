package wanted.market.api.global.log;

import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class LogBuilder {

    public String modifiedPriceLog(Long productId, Long before, Long after){
        StringBuilder sb = new StringBuilder();
        sb.append("["+ LocalDateTime.now()+"] ");
        sb.append("Modified Price.");
        sb.append("productId = "+productId);
        sb.append(", before: "+before);
        sb.append(", after: "+after);
        return sb.toString();
    }
}
