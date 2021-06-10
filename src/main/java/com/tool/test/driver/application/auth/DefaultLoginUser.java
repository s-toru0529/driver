package com.tool.test.driver.application.auth;

import com.tool.test.driver.domain.entity.UserEntity;
import java.util.Collections;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

public class DefaultLoginUser extends org.springframework.security.core.userdetails.User {
  // DBより検索したUserエンティティ
  // アプリケーションから利用されるのでフィールドに定義
  private UserEntity user;

  /**
   * データベースより検索したuserエンティティよりSpring Securityで使用するユーザー認証情報の インスタンスを生成する
   *
   * @param user userエンティティ
   */
  public DefaultLoginUser(UserEntity user) {
    super(
        user.getEmail(),
        user.getPassword(),
        user.getEnable(),
        true,
        true,
        true,
        convertGrantedAuthorities(user.getRoles()));
    this.user = user;
  }

  public UserEntity getUser() {
    return user;
  }

  /**
   * カンマ区切りのロールをSimpleGrantedAuthorityのコレクションへ変換する
   *
   * @param roles カンマ区切りのロール
   * @return SimpleGrantedAuthorityのコレクション
   */
  static Set<GrantedAuthority> convertGrantedAuthorities(String roles) {
    if (roles == null || roles.isEmpty()) {
      return Collections.emptySet();
    }
    Set<GrantedAuthority> authorities =
        Stream.of(roles.split(",")).map(SimpleGrantedAuthority::new).collect(Collectors.toSet());
    return authorities;
  }
}
