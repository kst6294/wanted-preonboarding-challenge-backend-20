package org.example.preonboarding.member.model.payload.response;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class WithdrawResponse {
    private int deletedCount;
}
