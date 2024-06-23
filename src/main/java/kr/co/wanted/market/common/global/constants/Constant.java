package kr.co.wanted.market.common.global.constants;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class Constant {

    /* Paging */
    public static final int PAGE_NUMBER_MIN = 0;
    public static final int PAGE_NUMBER_MAX = 100;

    public static final int PAGE_SIZE_MIN = 5;
    public static final int PAGE_SIZE_MAX = 20;


    /* Member */
    public static final int MEMBER_ID_LENGTH_MIN = 5;
    public static final int MEMBER_ID_LENGTH_MAX = 15;

    public static final int MEMBER_PASSWORD_LENGTH_MIN = 5;
    public static final int MEMBER_PASSWORD_LENGTH_MAX = 100;


    /* Product */
    public static final int PRODUCT_NAME_MIN = 5;
    public static final int PRODUCT_NAME_MAX = 50;

    public static final long PRODUCT_PRICE_MIN = 0;
    public static final long PRODUCT_PRICE_MAX = Integer.MAX_VALUE;

    public static final long PRODUCT_QUANTITY_MIN = 1;
    public static final long PRODUCT_QUANTITY_MAX = 100;

}
