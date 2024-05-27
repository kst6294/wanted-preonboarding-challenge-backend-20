package com.wanted.preonboarding.module.utils;

import com.wanted.preonboarding.module.common.enums.OrderType;
import com.wanted.preonboarding.module.common.mapper.CursorValueProvider;

import java.util.Comparator;

public class SortUtils {

    public static String setCursorValue(CursorValueProvider cursorValueProvider, OrderType orderType) {
        if (orderType == null) return null;

        return switch (orderType) {
            case HIGH_PRICE, LOW_PRICE -> String.valueOf(cursorValueProvider.getPrice());
            default -> null;
        };
    }


    public static Comparator<CursorValueProvider> getComparatorBasedOnOrderType(OrderType orderType) {
        if (orderType == OrderType.LOW_PRICE) {
            return Comparator.comparingDouble(CursorValueProvider::getPrice);
        } else if (orderType == OrderType.HIGH_PRICE) {
            return Comparator.comparingDouble(CursorValueProvider::getPrice).reversed();
        } else {
            return null;
        }
    }


}
