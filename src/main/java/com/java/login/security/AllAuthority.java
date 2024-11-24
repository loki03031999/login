package com.java.login.security;

import org.springframework.security.core.GrantedAuthority;

public class AllAuthority implements GrantedAuthority {
  @Override
  public String getAuthority() {
    return "ALL";
  }
}
