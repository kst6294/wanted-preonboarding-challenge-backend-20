package com.wanted.preonboarding.init;

import com.wanted.preonboarding.data.users.UsersModuleHelper;
import com.wanted.preonboarding.module.user.entity.Users;
import com.wanted.preonboarding.module.user.repository.UserFindRepository;
import com.wanted.preonboarding.module.user.repository.UserJdbcRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * 항상 master 계정이 맨처음 저장
 * master 계정은 test@wanted.com
 * 테스트를 용이하게 하게 하기 위해 모든 유저의 비밀번호는 123456789
 */

@Transactional
@Component
@RequiredArgsConstructor
public class UserDataInitializer extends AbstractDataInitializerTracker implements DataGenerator {

    private final UserFindRepository userFindRepository;
    private final UserJdbcRepository userJdbcRepository;
    private final PasswordEncoder passwordEncoder;
    private static final String USER_ENTITY= "USERS";

    @Override
    public void generate(int size) {
        super.start(USER_ENTITY);
        initUser(size);
        super.end(USER_ENTITY);
    }

    private void initUser(int size) {
        List<Users> users = new ArrayList<>();
        Users masterUser = createAndEncodeUser(UsersModuleHelper.toMasterUser());

        Optional<Users> masterUserOpt = userFindRepository.fetchUserEntity(masterUser.getEmail());
        if(masterUserOpt.isEmpty()){
            users.add(masterUser);
        }

        for (int i = 0; i < size; i++) {
            Users user = createAndEncodeUser(UsersModuleHelper.toUsers());
            users.add(user);
        }
        userJdbcRepository.saveAll(users);
    }

    private Users createAndEncodeUser(Users user) {
        String encodedPassword = passwordEncoder.encode(user.getPasswordHash());
        user.setPasswordHash(encodedPassword);
        return user;
    }

}
