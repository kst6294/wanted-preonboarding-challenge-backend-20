package com.market.wanted;

import com.market.wanted.member.entity.Member;
import com.market.wanted.member.repository.MemberRepository;
import com.market.wanted.product.entity.Product;
import com.market.wanted.product.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.security.crypto.password.PasswordEncoder;

@EnableJpaAuditing
@SpringBootApplication
@RequiredArgsConstructor
public class WantedApplication implements CommandLineRunner {

	private final MemberRepository memberRepository;
	private final ProductRepository productRepository;
	private final PasswordEncoder passwordEncoder;

	public static void main(String[] args) {
		SpringApplication.run(WantedApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		Member seller = new Member(passwordEncoder.encode("1234"), "seller", "seller@test.com");
		Member buyer = new Member(passwordEncoder.encode("1234"), "buyer", "buyer@test.com");
		memberRepository.save(seller);
		memberRepository.save(buyer);
		Product product1 = new Product("itemA", 1000, buyer);
		Product product2 = new Product("itemB", 2000, buyer);
		Product product3 = new Product("itemC", 3000, buyer);
		productRepository.save(product1);
		productRepository.save(product2);
		productRepository.save(product3);
	}
}
