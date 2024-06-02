package com.backend.market;

import com.backend.market.DAO.Entity.Member;
import com.backend.market.Repository.MemberRepository;
import com.backend.market.Repository.ProductRepository;
import com.backend.market.Service.Product.ProductService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class MarketApplicationTests {

	@Test
	void contextLoads() {
	}

	@Autowired
	private MemberRepository memberRepository;

	@Autowired
	private ProductRepository productRepository;

	@Autowired
	private ProductService productService;

	//TODO: Product API테스트
	@Test
	void testJPA()
	{
//		Member member = new Member();
//		member.setName("홍길동");
//		member.setPassword("1234");
//		this.memberRepository.save(member);
//
//		Product product = new Product();
//		product.setProduct_name("햄버거");
//		product.setPrice(123);
//		product.setStatus(Status.sale);
//		product.setMember(member);
//		product.setCreaeDate(LocalDate.now());
//		this.productRepository.save(product);

		List<Member> all = this.memberRepository.findAll();
		assertEquals(2,all.size());
	}





}
