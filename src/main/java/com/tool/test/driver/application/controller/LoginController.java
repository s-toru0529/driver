package com.tool.test.driver.application.controller;

import com.tool.test.driver.application.form.LoginForm;
import com.tool.test.driver.domain.component.JWT.JWTProvider;
import com.tool.test.driver.domain.entity.User;
import com.tool.test.driver.domain.service.LoginService;
import java.util.Objects;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
public class LoginController {

  // クレデンシャルを検証するためのサービスクラス
  private final LoginService service;

  // トークンを作成するためのProvider
  private final JWTProvider provider;

  @Autowired
  public LoginController(LoginService service, JWTProvider provider) {
    this.service = service;
    this.provider = provider;
  }

  // Formデータでクレデンシャルをもらい、認証を行う
  @PostMapping("/login")
  public void login(@Validated @RequestBody LoginForm form, HttpServletResponse response) {
    // クレデンシャルからユーザ情報を取得
    User user = this.service.getUser(form.getUsername(), form.getPassword());
    if (Objects.isNull(user)) {
      // HTTP Statusは200 OK
      response.setStatus(HttpStatus.UNAUTHORIZED.value());
    } else {
      // Headerにトークンを作成して載せる
      response.setHeader("X-AUTH-TOKEN", this.provider.createToken(user));
      // HTTP Statusは200 OK
      response.setStatus(HttpStatus.OK.value());
    }
  }
}
