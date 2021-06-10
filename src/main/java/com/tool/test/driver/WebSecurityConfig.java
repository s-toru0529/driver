package com.tool.test.driver;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

  // アカウント登録時のパスワードエンコードで利用するためDI管理する。
  @Bean
  PasswordEncoder passwordEncoder() {
    return PasswordEncoderFactories.createDelegatingPasswordEncoder();
  }

  @Override
  public void configure(WebSecurity web) throws Exception {
    web.debug(false)
        .ignoring()
        .antMatchers("/favicon.ico", "/images/**", "/js/**", "/css/**", "/fonts/**");
  }

  @Override
  protected void configure(HttpSecurity http) throws Exception {
    http.authorizeRequests()
        .mvcMatchers("/signup", "/login", "/error", "/login-error")
        .permitAll()
        .antMatchers("/h2-console/**")
        .permitAll() // H2DBデバッグ用
        .anyRequest()
        .authenticated()
        .and()
        .formLogin()
        .loginProcessingUrl("/login") // ログイン処理をするURL
        .failureUrl("/login-error")
        .defaultSuccessUrl("/")
        .and()
        .logout()
        .invalidateHttpSession(true)
        .deleteCookies("JSESSIONID")
        .logoutSuccessUrl("/login");
    // H2DBデバッグ用
    http.csrf().disable();
    http.headers().frameOptions().disable();
  }
}
