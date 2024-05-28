package com.wanted.preonboarding.module.common.enums;

import com.querydsl.core.types.Order;
import lombok.AllArgsConstructor;
import lombok.Getter;

import static com.querydsl.core.types.Order.ASC;
import static com.querydsl.core.types.Order.DESC;

@Getter
@AllArgsConstructor
public enum OrderType implements EnumType{

    LATEST("id", DESC),
    PAST("id", ASC);

    private final String field;
    private final Order direction;

    @Override
    public String getName() {
        return name();
    }

    @Override
    public String getDescription() {
        return String.format("%s %s", field, direction.name());
    }

    public boolean isAscending(){
        return this.direction.equals(ASC);
    }

}
