package com.wanted.product.service;

import com.querydsl.core.Tuple;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.wanted.product.dto.ProductDTO;
import java.util.Arrays;
import java.util.List;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService{

	private final JPAQueryFactory queryFactory;

	@Override
	public List<Object> list() {
		// 리스트 담아서 반환

//		QProduct product = QProduct.product;
//
//		List<Tuple> results = queryFactory.select(product.name, product.price, product.amount)
//		                                  .from(product)
//		                                  .fetch();

//		QFile files = QFile.file;
//		QBoard board = QBoard.board;
//
//		List<Tuple> results = queryFactory.select(files.fileName, files.uploadedFileName, files.fno)
//		                                  .from(files)
//		                                  .join(files.board, board)
//		                                  .where(board.bno.eq(bno))
//		                                  .fetch();


		return null;//Arrays.asList(results.toArray());
	}

	@Override
	public List<Object> purchaseList(Long buyer_id) {
		return null;
	}

	@Override
	public List<Object> reserveList(Long id) {
		return null;
	}

	@Override
	public ProductDTO readOne(Long id) {
		return null;
	}
}
