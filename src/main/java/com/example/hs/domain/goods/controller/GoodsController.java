package com.example.hs.domain.goods.controller;

import com.example.hs.domain.goods.dto.GoodsDto;
import com.example.hs.domain.goods.dto.GoodsEditRequest;
import com.example.hs.domain.goods.dto.GoodsRequest;
import com.example.hs.domain.goods.service.GoodsService;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
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

  @PostMapping()
  public ResponseEntity<GoodsDto> postGoods(@Valid @RequestBody GoodsRequest goodsRequest) {
    return ResponseEntity.ok(goodsService.postGoods(goodsRequest));
  }

  @PutMapping("/{goodsId}")
  public ResponseEntity<GoodsDto> updateGoods(@PathVariable long goodsId, @Valid @RequestBody GoodsEditRequest request) {
    return ResponseEntity.ok(goodsService.updateGoods(goodsId, request));
  }

  @DeleteMapping("/{goodsId}")
  public ResponseEntity<String> deleteGoods(@PathVariable long goodsId) {
    return ResponseEntity.ok(goodsService.deleteGoods(goodsId));
  }

}
