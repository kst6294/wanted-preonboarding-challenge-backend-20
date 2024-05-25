package com.wanted.preonboarding.document.utils;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

public interface DocumentLinkGenerator {

    static String generateLinkCode(DocUrl docUrl) {
        return String.format("link:common/%s.html[%s %s,role=\"popup\"]", docUrl.pageId, docUrl.text, "코드");
    }

    static String generateText(DocUrl docUrl) {
        return String.format("%s %s", docUrl.text, "코드명");
    }


    @RequiredArgsConstructor
    enum DocUrl {
        YN("yn", "Y 또는 N 플래그"),
        REDIS_KEY("redisKey", "레디스 키"),
        JWT_ENUM_ERROR("jwtErrorEnum", "JWT 에러 메세지"),
        MEMBERSHIP("membership", "회원 유형"),



        ;

        private final String pageId;
        @Getter
        private final String text;
    }
}
