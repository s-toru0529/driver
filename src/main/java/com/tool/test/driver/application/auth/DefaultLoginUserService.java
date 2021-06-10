package com.tool.test.driver.application.auth;

import com.tool.test.driver.domain.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class DefaultLoginUserService implements UserDetailsService {
  private final UserRepository userRepository;

  public DefaultLoginUserService(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  /**
   * 名前で検索したユーザーのuserエンティティをSimpleLoginUserクラスのインスタンスへ変換する
   *
   * @param name 検索するユーザーのメールアドレス
   * @return 名前で検索できたユーザーのユーザー情報
   * @throws UsernameNotFoundException 名前でユーザーが検索できなかった場合にスローする。
   */
  @Transactional(readOnly = true)
  @Override
  public UserDetails loadUserByUsername(String name) throws UsernameNotFoundException {
    assert (name != null);
    return userRepository
        .findByName(name)
        .map(DefaultLoginUser::new)
        .orElseThrow(() -> new UsernameNotFoundException("User not found by name:[" + name + "]"));
  }
}
