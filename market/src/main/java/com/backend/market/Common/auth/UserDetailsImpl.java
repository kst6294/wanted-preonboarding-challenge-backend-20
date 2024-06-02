package com.backend.market.Common.auth;

import com.backend.market.DAO.Entity.Member;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Getter
@NoArgsConstructor
public class UserDetailsImpl implements UserDetails{

    @Autowired
    Member member;
    List<GrantedAuthority> roles = new ArrayList<>();

    public UserDetailsImpl(Member member) {
        super();
        this.member = member;
    }

    public Member getUser() {
        return this.member;
    }

    @Override
    public String getPassword() {
        return null;
    }

    @Override
    public String getUsername() {
        return member.getName();
    }

    @Override
    public boolean isAccountNonExpired() {
        return false;
    }

    @Override
    public boolean isAccountNonLocked() {
        return false;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return false;
    }

    @Override
    public boolean isEnabled() {
        return false;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.roles;
    }
    public void setAuthorities(List<GrantedAuthority> roles) {
        this.roles = roles;
    }
}
