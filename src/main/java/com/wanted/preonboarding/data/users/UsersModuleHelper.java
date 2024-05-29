package com.wanted.preonboarding.data.users;

import com.wanted.preonboarding.data.EasyRandomUtils;
import com.wanted.preonboarding.data.FourDigitRandomizer;
import com.wanted.preonboarding.module.user.entity.Users;
import com.wanted.preonboarding.module.user.enums.MemberShip;
import org.jeasy.random.EasyRandom;
import org.jeasy.random.randomizers.text.StringRandomizer;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Random;

public class UsersModuleHelper {

    public static Users toMasterUser(){
        Map<String, Object> stringObjectMap = new HashMap<>();
        stringObjectMap.put("id", 0L);
        stringObjectMap.put("email", "test@wanted.com");
        stringObjectMap.put("passwordHash", "123456789");
        stringObjectMap.put("phoneNumber", generatePhoneNumber());
        stringObjectMap.put("memberShip", MemberShip.NORMAL);
        stringObjectMap.put("products", new HashSet<>());

        EasyRandom instance = EasyRandomUtils.getInstance(stringObjectMap);
        return instance.nextObject(Users.class);
    }


    public static Users toUsers(){
        Map<String, Object> stringObjectMap = new HashMap<>();
        stringObjectMap.put("id", 0L);
        stringObjectMap.put("email", generateEmail());
        stringObjectMap.put("passwordHash", "123456789");
        stringObjectMap.put("phoneNumber", generatePhoneNumber());
        stringObjectMap.put("memberShip", MemberShip.NORMAL);
        stringObjectMap.put("products", new HashSet<>());

        EasyRandom instance = EasyRandomUtils.getInstance(stringObjectMap);
        return instance.nextObject(Users.class);
    }


    public static Users toUsersWithId(){
        Map<String, Object> stringObjectMap = new HashMap<>();
        stringObjectMap.put("id", new FourDigitRandomizer().getRandomValue());
        stringObjectMap.put("email", generateEmail());
        stringObjectMap.put("phoneNumber", generatePhoneNumber());
        stringObjectMap.put("memberShip", MemberShip.NORMAL);
        stringObjectMap.put("products", new HashSet<>());

        EasyRandom instance = EasyRandomUtils.getInstance(stringObjectMap);
        return instance.nextObject(Users.class);
    }



    private static String generateEmail(){
        Random random = new Random();

        StringRandomizer stringRandomizer = new StringRandomizer(3, 15, random.nextInt());
        String email = stringRandomizer.getRandomValue();
        return email+"@wanted.com";
    }

    private static String generatePhoneNumber() {
        FourDigitRandomizer randomizer = new FourDigitRandomizer();
        String firstValue = String.format("%04d", randomizer.getRandomValue());
        String secondValue = String.format("%04d", randomizer.getRandomValue());

        return "010-" + firstValue + "-" + secondValue;
    }


}
