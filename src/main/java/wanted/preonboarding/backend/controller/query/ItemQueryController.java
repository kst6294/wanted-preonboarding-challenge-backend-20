package wanted.preonboarding.backend.controller.query;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import wanted.preonboarding.backend.auth.Session;
import wanted.preonboarding.backend.dto.response.ItemDetailResponse;
import wanted.preonboarding.backend.dto.response.ItemListResponse;
import wanted.preonboarding.backend.service.ItemService;

@RestController
@RequestMapping("/items")
@RequiredArgsConstructor
public class ItemQueryController {

    private final ItemService itemService;

    /**
     * 제품 목록 조회 - 최신순
     */
    @GetMapping("/list")
    public ItemListResponse getItemList() {
        return itemService.getItemList();
    }

    /**
     * 제품 상세 조회
     */
    @GetMapping("{itemId}")
    public ItemDetailResponse getItem(@PathVariable final Long itemId,
                                      final HttpSession session) {
        Long memberId = (Long) session.getAttribute(Session.MEMBER);
        return itemService.getItem(memberId, itemId);
    }
}
