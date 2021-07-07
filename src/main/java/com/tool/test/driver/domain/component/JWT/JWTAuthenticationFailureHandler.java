package com.tool.test.driver.domain.component.JWT;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

@Component
public class JWTAuthenticationFailureHandler implements AuthenticationFailureHandler {

  @Override
  public void onAuthenticationFailure(
      HttpServletRequest request, HttpServletResponse response, AuthenticationException exception)
      throws IOException, ServletException {
    // HTTP Statusは401 OK
    if (exception instanceof BadCredentialsException) {
      // 資格情報が無効であるために認証リクエストが拒否
      response.setStatus(HttpStatus.UNAUTHORIZED.value());
    } else {
      response.setStatus(HttpStatus.UNAUTHORIZED.value());
    }
  }
}
