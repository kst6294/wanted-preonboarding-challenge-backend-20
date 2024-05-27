package com.wanted.preonboarding.module.order.validator;

public interface OrderLockChecker {

    boolean lock(long productId);
}
