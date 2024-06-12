package org.example.preonboarding;

import org.example.preonboarding.member.repository.MemberRepository;
import org.example.preonboarding.order.repository.OrderRepository;
import org.example.preonboarding.product.repository.ProductRepository;
import org.example.preonboarding.product.service.ProductNumberFactory;
import org.example.preonboarding.stock.repository.StockRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;

@ActiveProfiles("test")
@DataJpaTest
@Import(ProductNumberFactory.class)
public abstract class DataJPATestSupport {

    @Autowired
    protected StockRepository stockRepository;

    @Autowired
    protected MemberRepository memberRepository;

    @Autowired
    protected ProductRepository productRepository;

    @Autowired
    protected OrderRepository orderRepository;

    @Autowired
    protected ProductNumberFactory productNumberFactory;
}
