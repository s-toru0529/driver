package com.tool.test.driver.domain.service;

import com.tool.test.driver.domain.entity.UserEntity;
import com.tool.test.driver.domain.repository.UserRepository;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AccountService {
  private final UserRepository userRepository;
  private final PasswordEncoder passwordEncoder;

  public AccountService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
    this.userRepository = userRepository;
    this.passwordEncoder = passwordEncoder;
  }

  @Transactional(readOnly = true)
  public List<UserEntity> findAll() {
    return userRepository.findAll();
  }

  @Transactional
  public void register(String name, String email, String password, String[] roles) {
    if (userRepository.findByName(name).isPresent()) {
      throw new RuntimeException("invalid name or email");
    }
    String encodedPassword = passwordEncode(password);
    String joinedRoles = joinRoles(roles);

    UserEntity user = new UserEntity(null, name, email, encodedPassword, joinedRoles, Boolean.TRUE);
    userRepository.saveAndFlush(user);
  }

  /**
   * @param rawPassword 平文のパスワード
   * @return 暗号化されたパスワード
   */
  private String passwordEncode(String rawPassword) {
    return passwordEncoder.encode(rawPassword);
  }

  /**
   * @param roles ロール文字列の配列
   * @return カンマ区切りのロール文字列
   */
  private String joinRoles(String[] roles) {
    if (roles == null || roles.length == 0) {
      return "";
    }
    return Stream.of(roles)
        .map(String::trim)
        .map(String::toUpperCase)
        .collect(Collectors.joining(","));
  }
}
