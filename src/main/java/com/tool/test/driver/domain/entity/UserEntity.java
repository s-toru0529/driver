package com.tool.test.driver.domain.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Table(name = "user")
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = {"email", "password"})
public class UserEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "name", length = 60, nullable = false, unique = true)
  private String name;

  @Column(name = "email", length = 120, nullable = false)
  private String email;

  @Column(name = "password", length = 120, nullable = false)
  private String password;

  @Column(name = "roles", length = 120)
  private String roles;

  @Column(name = "enable_flag", nullable = false)
  private Boolean enable;
}
