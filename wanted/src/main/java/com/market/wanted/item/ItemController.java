package com.market.wanted.item;

import com.market.wanted.security.MemberDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ItemController {

    private final ItemService itemService;

    @PostMapping("/item")
    public ResponseEntity<Long> add(@AuthenticationPrincipal MemberDetails memberDetails,
                                    @RequestBody ItemAddDto itemAddDto){
        Long itemId = itemService.add(itemAddDto, memberDetails.getId());
        return ResponseEntity.ok(itemId);
    }
}
