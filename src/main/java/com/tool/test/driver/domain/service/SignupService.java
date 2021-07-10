package com.tool.test.driver.domain.service;

import com.tool.test.driver.domain.entity.User;
import com.tool.test.driver.domain.exception.UserDuplicateException;
import com.tool.test.driver.domain.repository.UserRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class SignupService {

  // UserDetailsを取得できるRepository
  private final UserRepository repository;

  @Autowired PasswordEncoder passwordEncoder;

  @Autowired
  public SignupService(UserRepository repository) {
    this.repository = repository;
  }

  @Transactional
  public void register(String username, String password, List<String> roles)
      throws UserDuplicateException {
    User user = new User();

    user.setUsername(username);
    user.setPassword(passwordEncoder.encode(password));
    user.setAccountNonExpired(Boolean.TRUE);
    user.setAccountNonLocked(Boolean.TRUE);
    user.setCredentialsNonExpired(Boolean.TRUE);
    user.setEnabled(Boolean.TRUE);
    user.setRoles(roles);

    // 登録
    repository.saveAndFlush(user);
  }
}
