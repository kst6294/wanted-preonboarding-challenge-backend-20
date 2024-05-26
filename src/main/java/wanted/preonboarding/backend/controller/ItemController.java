package wanted.preonboarding.backend.controller;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import wanted.preonboarding.backend.auth.Session;
import wanted.preonboarding.backend.dto.request.ItemSaveRequest;
import wanted.preonboarding.backend.service.ItemService;

@RestController
@RequestMapping("/items")
@RequiredArgsConstructor
public class ItemController {

    private final ItemService itemService;

    /**
     * 제품 등록
     */
    @PostMapping
    public void applyItem(@RequestBody final ItemSaveRequest request,
                          final HttpSession session) {
        Long memberId = (Long) session.getAttribute(Session.MEMBER);
        itemService.applyItem(memberId, request);
    }
}
