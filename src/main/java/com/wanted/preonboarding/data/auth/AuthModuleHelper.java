package com.wanted.preonboarding.data.auth;

import com.wanted.preonboarding.auth.core.JwtAuthToken;
import com.wanted.preonboarding.auth.dto.CreateAuthToken;
import com.wanted.preonboarding.data.EasyRandomUtils;
import com.wanted.preonboarding.module.user.core.BaseUserInfo;
import com.wanted.preonboarding.module.user.entity.Users;
import com.wanted.preonboarding.module.user.enums.MemberShip;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.jeasy.random.EasyRandom;
import org.jeasy.random.randomizers.text.StringRandomizer;

import javax.crypto.spec.SecretKeySpec;
import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class AuthModuleHelper {


    public static CreateAuthToken toCreateAuthToken(){
        StringRandomizer stringRandomizer = new StringRandomizer(10);
        Map<String, Object> stringObjectMap = new HashMap<>();
        String email = stringRandomizer.getRandomValue();
        stringObjectMap.put("email", email + "@wanted.com");
        EasyRandom instance = EasyRandomUtils.getInstance(stringObjectMap);
        return instance.nextObject(CreateAuthToken.class);
    }

    public static JwtAuthToken toJwtAuthToken(){
        Map<String, Object> stringObjectMap = new HashMap<>();
        Key key = new SecretKeySpec("MySecretKey12345".getBytes(), "HmacSHA256");
        stringObjectMap.put("key", key);
        EasyRandom instance = EasyRandomUtils.getInstance(stringObjectMap);
        return instance.nextObject(JwtAuthToken.class);
    }


    public static JwtAuthToken toJwtAuthToken_another_constructor(String email){
        Key key = Keys.hmacShaKeyFor("36bddb74-043c-4fcd-a17d-d7089bf65b90".getBytes());
        Date issue = new Date();
        long expirationTime =3600000L;
        Date expiry = new Date(issue.getTime() + expirationTime);
        return new JwtAuthToken(email, MemberShip.NORMAL.name(), issue, expiry, key);
    }


    public static JwtAuthToken toJwtAuthToken_another_constructor(){
        Key key = Keys.hmacShaKeyFor("36bddb74-043c-4fcd-a17d-d7089bf65b90".getBytes());
        String email = generateEmail();

        Date issue = new Date();
        long expirationTime =3600000L;
        Date expiry = new Date(issue.getTime() + expirationTime);

        return new JwtAuthToken(email, MemberShip.NORMAL.name(), issue, expiry, key);
    }

    public static JwtAuthToken toExpireJwtAuthToken_another_constructor(){
        Key key = Keys.hmacShaKeyFor("36bddb74-043c-4fcd-a17d-d7089bf65b90".getBytes());
        String email = generateEmail();

        Date issue = new Date();
        long expirationTime = 3600000L;
        Date expiry = new Date(issue.getTime() - expirationTime);

        return new JwtAuthToken(email, MemberShip.NORMAL.name(), issue, expiry, key);

    }

    public static BaseUserInfo toBaseUserInfo(){
        Map<String, Object> stringObjectMap = new HashMap<>();
        stringObjectMap.put("email", generateEmail());
        EasyRandom instance = EasyRandomUtils.getInstance(stringObjectMap);
        return instance.nextObject(BaseUserInfo.class);
    }


    public static BaseUserInfo toBaseUserInfo(Users users){
        Map<String, Object> stringObjectMap = new HashMap<>();
        stringObjectMap.put("phoneNumber", users.getPhoneNumber());
        stringObjectMap.put("email", users.getEmail());
        stringObjectMap.put("passwordHash", users.getPasswordHash());
        stringObjectMap.put("memberShip", users.getMemberShip());

        EasyRandom instance = EasyRandomUtils.getInstance(stringObjectMap);
        return instance.nextObject(BaseUserInfo.class);
    }


    private static String generateEmail(){
        StringRandomizer stringRandomizer = new StringRandomizer(10);
        String email = stringRandomizer.getRandomValue();
        return email+"@wanted.com";
    }


}
