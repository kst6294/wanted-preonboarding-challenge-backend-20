package com.example.hs.global.security.userdetails;

import java.util.ArrayList;
import java.util.Collection;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@RequiredArgsConstructor
public class CustomUserDetails implements UserDetails {

  private final transient MemberUserDetailsDomain memberUserDetailsDomain;

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    Collection<GrantedAuthority> authority = new ArrayList<>();
    authority.add(new SimpleGrantedAuthority(memberUserDetailsDomain.authority().name()));
    return authority;
  }

  @Override
  public String getUsername() {
    return memberUserDetailsDomain.loginId();
  }

  @Override
  public String getPassword() {
    return memberUserDetailsDomain.password();
  }

  @Override
  public boolean isAccountNonExpired() {
    return true;
  }

  @Override
  public boolean isAccountNonLocked() {
    return true;
  }

  @Override
  public boolean isCredentialsNonExpired() {
    return true;
  }

  @Override
  public boolean isEnabled() {
    return true;
  }

  public long getId() {
    return memberUserDetailsDomain.id();
  }
}
