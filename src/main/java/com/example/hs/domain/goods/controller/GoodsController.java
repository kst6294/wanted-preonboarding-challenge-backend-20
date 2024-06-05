package com.example.hs.domain.goods.controller;

import com.example.hs.domain.goods.dto.GoodsDto;
import com.example.hs.domain.goods.service.GoodsService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/goods")
public class GoodsController {
  private final GoodsService goodsService;

  @GetMapping()
  public ResponseEntity<List<GoodsDto>> getAllGoods() {
    return ResponseEntity.ok(goodsService.getAll());
  }

  @GetMapping("/{goodsId}")
  public ResponseEntity<GoodsDto> getGoodsDetail(@PathVariable long goodsId) {
    return ResponseEntity.ok(goodsService.getGoodsDetail(goodsId));
  }

}
