package com.wanted.preonboarding.module.user.repository;

import com.wanted.preonboarding.data.users.UsersModuleHelper;
import com.wanted.preonboarding.document.utils.BaseFetchRepositoryTest;
import com.wanted.preonboarding.module.user.core.BaseUserInfo;
import com.wanted.preonboarding.module.user.entity.Users;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

class UserFindRepositoryImplTest extends BaseFetchRepositoryTest {

    @Autowired
    private UserFindRepositoryImpl userFindRepositoryimpl;
    private String email;


    @BeforeEach
    void setUp() {
        Users users = UsersModuleHelper.toUsers();
        getEntityManager().persist(users);
        flushAndClear();
        email = users.getEmail();
    }


    @Test
    @DisplayName("email로 User Entity 조회")
    void fetchUserEntity() {
        Optional<Users> users = userFindRepositoryimpl.fetchUserEntity(email);
        assertThat(users).isPresent();
        assertThat(users.get().getEmail()).isEqualTo(email);
    }

    @Test
    @DisplayName("email로 BaseUserInfo 조회")
    void fetchUserInfo() {
        Optional<BaseUserInfo> userInfo = userFindRepositoryimpl.fetchUserInfo(email);
        assertThat(userInfo).isPresent();
        assertThat(userInfo.get().getEmail()).isEqualTo(email);
    }




}