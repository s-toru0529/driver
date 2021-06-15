package com.tool.test.driver;

import com.tool.test.driver.domain.component.JWT.JWTAuthenticationFailureHandler;
import com.tool.test.driver.domain.component.JWT.JWTAuthenticationFilter;
import com.tool.test.driver.domain.component.JWT.JWTAuthenticationSuccessHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

  // ログイン以降の認証認可のためのFilter
  private final JWTAuthenticationFilter filter;

  // ログインが成功した場合の処理のためのHandler
  private final JWTAuthenticationSuccessHandler successHandler;

  // ログインが失敗した場合の処理のためのHandler
  private final JWTAuthenticationFailureHandler failureHandler;

  @Bean
  PasswordEncoder passwordEncoder() {
    return PasswordEncoderFactories.createDelegatingPasswordEncoder();
  }

  @Autowired
  public WebSecurityConfig(
      JWTAuthenticationFilter filter,
      JWTAuthenticationSuccessHandler successHandler,
      JWTAuthenticationFailureHandler failureHandler) {
    this.filter = filter;
    this.successHandler = successHandler;
    this.failureHandler = failureHandler;
  }

  @Bean
  @Override
  public AuthenticationManager authenticationManagerBean() throws Exception {
    return super.authenticationManagerBean();
  }

  @Override
  protected void configure(HttpSecurity http) throws Exception {
    http
        // Basic認証を使わない
        .httpBasic()
        .disable()
        // CSRF設定を使わない
        .csrf()
        .disable()
        // セッションはStatelessなので使わない
        .sessionManagement()
        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
        .and()
        // formLogin設定
        .formLogin()
        // signinは誰でもアクセス可能
        .loginProcessingUrl("/signin")
        .permitAll()
        // formからパラメータ取得
        .usernameParameter("username")
        .passwordParameter("password")
        // ログイン成功した場合
        .successHandler(successHandler)
        .failureHandler(failureHandler)
        .and()
        // USERではないとどのURLでもアクセスできない
        .authorizeRequests()
        .antMatchers("/signup", "/h2-console/**")
        .permitAll() // H2DBデバッグ用
        .anyRequest()
        .hasRole("USER")
        .and()
        // デフォルトのFilter設定を変える
        .addFilterBefore(this.filter, UsernamePasswordAuthenticationFilter.class);
    http.headers().frameOptions().disable();
  }
}
