package com.wanted.market.global.common.code;

public enum TradeStatusCode {
    REQUEST, ACCEPT, COMPLETE;

    public static boolean isAvailable(String state) {
        for (TradeStatusCode tradeStatusCode : TradeStatusCode.values()) {
            if(tradeStatusCode.name().equals(state)) {
                return true;
            }
        }
        return false;
    }
}
