package com.tool.test.driver.domain.service;

import com.tool.test.driver.domain.entity.User;
import com.tool.test.driver.domain.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class LoginService {

  // UserDetailsを取得できるRepository
  private final UserRepository repository;

  @Autowired PasswordEncoder passwordEncoder;

  @Autowired
  public LoginService(UserRepository repository) {
    this.repository = repository;
  }

  public User getUser(String username, String password) throws UsernameNotFoundException {
    User user = repository.findByUsername(username);
    if (!passwordEncoder.matches(password, user.getPassword())) {
      // パスワードがマッチしない場合、nullにする
      user = null;
    }
    return user;
  }
}
