package com.tool.test.driver.domain.entity;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User implements UserDetails {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  // ユーザ名(一般的にID)
  private String username;

  // パスワード(暗号化)
  private String password;

  // ユーザアカウントが満了されているかの設定
  private boolean accountNonExpired;

  // ユーザアカウントがロックされているかの設定
  private boolean accountNonLocked;

  // ユーザアカウントのクレデンシャルが満了されているかの設定
  private boolean credentialsNonExpired;

  // ユーザアカウントが活性化されているかの設定
  private boolean enabled;

  // 認可のためのユーザのロール
  @ElementCollection(fetch = FetchType.EAGER)
  private List<String> roles = new ArrayList<>();

  // ユーザの認可情報を取得する
  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return this.roles.stream()
        .map(SimpleGrantedAuthority::new)
        .collect(Collectors.toUnmodifiableList());
  }
}
