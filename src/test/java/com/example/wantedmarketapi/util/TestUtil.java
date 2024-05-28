package com.example.wantedmarketapi.util;

import com.example.wantedmarketapi.domain.member.Member;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;

public class TestUtil {

    public static void setField(Object object, String fieldName, Object value) {
        try {
            Field field = object.getClass().getDeclaredField(fieldName);
            field.setAccessible(true);
            field.set(object, value);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    public static Member createMemberWithReflection() {
        try {
            Constructor<Member> constructor = Member.class.getDeclaredConstructor();
            constructor.setAccessible(true);
            return constructor.newInstance();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
