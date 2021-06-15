package com.tool.test.driver.domain.repository;

import com.tool.test.driver.domain.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
  User findByUsername(String username);

  User findByUsernameAndPassword(String username, String encryptPassword);
}
