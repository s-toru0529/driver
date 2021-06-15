package com.tool.test.driver.domain.service;

import com.tool.test.driver.domain.entity.User;
import com.tool.test.driver.domain.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserService implements UserDetailsService {

  // UserDetailsを取得できるRepository
  private final UserRepository repository;

  @Autowired
  public UserService(UserRepository repository) {
    this.repository = repository;
  }

  // 認証後にユーザ情報を取得するためのメソッド
  @Override
  public User loadUserByUsername(final String username) throws UsernameNotFoundException {
    return repository.findByUsername(username);
  }
}
