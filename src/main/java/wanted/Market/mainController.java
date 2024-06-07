package wanted.Market;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import wanted.Market.global.common.ResponseDto;

@RestController
public class mainController {
    @GetMapping("/main")
    public ResponseEntity<ResponseDto<String>> main() {
        return ResponseEntity.status(200).body(ResponseDto.of("성공", null));
    }
}
