package com.wanted.preonboarding;

import com.wanted.preonboarding.data.users.UsersModuleHelper;
import com.wanted.preonboarding.module.user.entity.Users;
import com.wanted.preonboarding.module.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class UserDataInitializer extends AbstractDataInitializer {

    private final UserRepository userRepository;

    @Override
    public void initialize() {
        super.initialize();
    }

    private void initUser(){
        List<Users> users = new ArrayList<>();
        for(int i=0; i < 20; i ++){
            users.add(UsersModuleHelper.toUsers());
            userRepository.saveAll(users);
        }
    }

}
