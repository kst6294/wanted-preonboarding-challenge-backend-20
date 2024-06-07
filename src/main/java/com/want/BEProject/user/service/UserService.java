package com.want.BEProject.user.service;

import com.want.BEProject.user.dto.UserLoginRequest;
import com.want.BEProject.user.dto.UserSignupRequest;
import com.want.BEProject.user.entity.Users;
import com.want.BEProject.user.repository.UserRepository;
import com.want.BEProject.utils.SessionUtil;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public void signup(UserSignupRequest userSignupRequest){
        Users users = userRepository.findByUserId(userSignupRequest.getUserId()).orElse(null);

        if(users != null){
            throw new IllegalArgumentException("이미 존재하는 아이디입니다.");
        }

        Users newUsers = new Users(userSignupRequest.getUserId(), userSignupRequest.getPassword(), userSignupRequest.getName());
        userRepository.save(newUsers);
    }

    public void login(UserLoginRequest userLoginRequest, HttpSession session) {
        Users users = userRepository.findByUserId(userLoginRequest.getUserId()).orElse(null);

        if(users == null){
            throw new IllegalArgumentException("존재하지 않는 아이디입니다.");
        }

        if(!users.getPassword().equals(userLoginRequest.getPassword())){
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
        }

        SessionUtil.setLoginId(session, users.getUserId());

        System.out.println(SessionUtil.getLoginId(session));

    }


}
