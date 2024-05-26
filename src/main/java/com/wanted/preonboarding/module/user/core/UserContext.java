package com.wanted.preonboarding.module.user.core;

import com.wanted.preonboarding.module.user.entity.Users;

public interface UserContext {

    void setUsers(Users users);
    Users getUsers();
}
