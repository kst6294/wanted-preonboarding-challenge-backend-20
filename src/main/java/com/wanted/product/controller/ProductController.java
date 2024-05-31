package com.wanted.product.controller;

import com.wanted.common.dto.ResultDTO;
import com.wanted.product.dto.ProductDTO;
import com.wanted.product.service.ProductService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/products")
@Log4j2
@RequiredArgsConstructor
public class ProductController {

	private final ProductService productService;

	@GetMapping()
	public ResponseEntity<ResultDTO<Object>> list() {

		List<Object> productList = productService.list();

		return ResponseEntity.ok(ResultDTO.res(HttpStatus.OK, "상품 리스트 반환 성공", productList));
	}

	@GetMapping("/{id}")
	public ResponseEntity<ResultDTO<Object>> read(@PathVariable("id") Long id) {
		ProductDTO productDTO = productService.readOne(id);

		return ResponseEntity.ok(ResultDTO.res(HttpStatus.OK, HttpStatus.OK.toString(), productDTO));
	}

	@GetMapping("/purchase")
	public ResponseEntity<ResultDTO<Object>> purchaseList() {
		List<Object> productList = productService.purchaseList(1L);

		return ResponseEntity.ok(ResultDTO.res(HttpStatus.OK, "상품 리스트 반환 성공", productList));
	}

	@GetMapping("/reserve")
	public ResponseEntity<ResultDTO<Object>> reserveList() {
		List<Object> productList = productService.reserveList(1L);

		return ResponseEntity.ok(ResultDTO.res(HttpStatus.OK, "상품 리스트 반환 성공", productList));
	}
}
