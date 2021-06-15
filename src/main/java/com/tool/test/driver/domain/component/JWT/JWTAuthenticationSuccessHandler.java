package com.tool.test.driver.domain.component.JWT;

import com.tool.test.driver.domain.entity.User;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

@Component
public class JWTAuthenticationSuccessHandler implements AuthenticationSuccessHandler {
  // トークンを作成するためのProvider
  private final JWTProvider provider;

  @Autowired
  public JWTAuthenticationSuccessHandler(JWTProvider provider) {
    this.provider = provider;
  }

  @Override
  public void onAuthenticationSuccess(
      HttpServletRequest request, HttpServletResponse response, Authentication authentication)
      throws IOException, ServletException {
    // すでにレスポンスで情報を返した場合は何もしない
    if (response.isCommitted()) {
      return;
    }
    // ログインに成功したユーザ情報を取得する
    User user = (User) authentication.getPrincipal();
    // Headerにトークンを作成して載せる
    response.setHeader("X-AUTH-TOKEN", this.provider.createToken(user));
    // HTTP Statusは200 OK
    response.setStatus(HttpStatus.OK.value());
  }
}
