package kr.co.wanted.market;

import com.navercorp.fixturemonkey.ArbitraryBuilder;
import com.navercorp.fixturemonkey.FixtureMonkey;
import com.navercorp.fixturemonkey.api.introspector.ConstructorPropertiesArbitraryIntrospector;
import com.navercorp.fixturemonkey.jakarta.validation.plugin.JakartaValidationPlugin;
import kr.co.wanted.market.common.global.enums.Role;
import kr.co.wanted.market.member.entity.Member;
import kr.co.wanted.market.product.entity.Product;
import kr.co.wanted.market.product.enums.ProductState;
import kr.co.wanted.market.trade.entity.Trade;
import kr.co.wanted.market.trade.enums.TradeState;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.support.TransactionTemplate;

import java.util.Collections;
import java.util.Set;

import static com.navercorp.fixturemonkey.api.instantiator.Instantiator.constructor;
import static kr.co.wanted.market.common.global.constants.Constant.*;
import static net.jqwik.api.Arbitraries.*;

@Slf4j
public final class TestUtil {

    private static final FixtureMonkey FIXTURE_MONKEY = FixtureMonkey.builder()
            .objectIntrospector(ConstructorPropertiesArbitraryIntrospector.INSTANCE)
            .plugin(new JakartaValidationPlugin())
            .build();

    private static final ArbitraryBuilder<Member> MEMBER_ARBITRARY_BUILDER = FIXTURE_MONKEY.giveMeBuilder(Member.class)
            .instantiate(
                    constructor()
                            .parameter(String.class, "loginId")
                            .parameter(String.class, "password")
                            .parameter(Role.class, "role"))
            .set("loginId", strings().alpha().numeric().ofMinLength(MEMBER_ID_LENGTH_MIN).repeatChars(0).ofMaxLength(MEMBER_ID_LENGTH_MAX))
            .set("password", strings().alpha().ofMinLength(MEMBER_ID_LENGTH_MIN).ofMaxLength(MEMBER_PASSWORD_LENGTH_MAX))
            .set("role", Role.ROLE_USER);

    private static final ArbitraryBuilder<Product> PRODUCT_ARBITRARY_BUILDER = FIXTURE_MONKEY.giveMeBuilder(Product.class)
            .instantiate(
                    constructor()
                            .parameter(Member.class, "seller")
                            .parameter(String.class, "name")
                            .parameter(Long.class, "price")
                            .parameter(Long.class, "quantity"))
            .set("name", strings().alpha().ofMinLength(PRODUCT_NAME_MIN).ofMaxLength(PRODUCT_NAME_MAX))
            .set("price", longs().between(PRODUCT_PRICE_MIN, PRODUCT_PRICE_MAX))
            .set("quantity", longs().between(PRODUCT_QUANTITY_MIN, PRODUCT_QUANTITY_MAX))
            .set("state", ProductState.SALE);

    private static final ArbitraryBuilder<Trade> TRADE_ARBITRARY_BUILDER = FIXTURE_MONKEY.giveMeBuilder(Trade.class)
            .instantiate(
                    constructor()
                            .parameter(Member.class, "buyer")
                            .parameter(Member.class, "seller")
                            .parameter(Product.class, "product")
                            .parameter(Long.class, "price")
                            .parameter(Long.class, "quantity"))
            .set("state", TradeState.REQUEST);


    public static ArbitraryBuilder<Member> getMemberArbitraryBuilder() {

        return MEMBER_ARBITRARY_BUILDER.copy();
    }


    public static ArbitraryBuilder<Product> getProductArbitraryBuilder() {

        return PRODUCT_ARBITRARY_BUILDER.copy();
    }


    public static ArbitraryBuilder<Trade> getTradeArbitraryBuilder() {

        return TRADE_ARBITRARY_BUILDER.copy();
    }


    public static void setContextMember(Long id, Role role) {

        log.debug("< Context Member 설정 | ID: [{}], ROLE: [{}] >", id, role);
        SimpleGrantedAuthority authority = new SimpleGrantedAuthority(role.name());
        Set<SimpleGrantedAuthority> authorities = Collections.singleton(authority);

        UsernamePasswordAuthenticationToken authenticated = UsernamePasswordAuthenticationToken.authenticated(String.valueOf(id), "", authorities);

        SecurityContextHolder.getContext().setAuthentication(authenticated);
    }


    public static void clearContextMember() {

        SecurityContextHolder.clearContext();
    }


    public static TransactionTemplate getTransaction(PlatformTransactionManager tm, int definition) {

        TransactionTemplate transactionTemplate = new TransactionTemplate(tm);
        transactionTemplate.setPropagationBehavior(definition);
        return transactionTemplate;
    }

}
