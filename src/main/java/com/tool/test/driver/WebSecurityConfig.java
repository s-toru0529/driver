package com.tool.test.driver;

import com.tool.test.driver.domain.component.JWT.JWTAuthenticationFilter;
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
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

  // ログイン以降の認証認可のためのFilter
  private final JWTAuthenticationFilter filter;

  @Bean
  PasswordEncoder passwordEncoder() {
    return PasswordEncoderFactories.createDelegatingPasswordEncoder();
  }

  @Autowired
  public WebSecurityConfig(JWTAuthenticationFilter filter) {
    this.filter = filter;
  }

  @Bean
  @Override
  public AuthenticationManager authenticationManagerBean() throws Exception {
    return super.authenticationManagerBean();
  }

  private CorsConfigurationSource corsConfigurationSource() {
    CorsConfiguration corsConfiguration = new CorsConfiguration();
    corsConfiguration.addAllowedMethod(CorsConfiguration.ALL);
    corsConfiguration.addAllowedHeader(CorsConfiguration.ALL);
    corsConfiguration.addExposedHeader("Authorization");
    corsConfiguration.addAllowedOrigin("http://localhost");
    corsConfiguration.setAllowCredentials(true);

    UrlBasedCorsConfigurationSource corsSource = new UrlBasedCorsConfigurationSource();
    corsSource.registerCorsConfiguration("/**", corsConfiguration);

    return corsSource;
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
        .disable()
        // USERではないとどのURLでもアクセスできない
        .authorizeRequests()
        .antMatchers(
            "/",
            "/login",
            "/signup",
            "/h2-console/**",
            "/css/**",
            "/js/**",
            "/favicon.ico",
            "/index.html")
        .permitAll() // H2DBデバッグ用
        .anyRequest()
        .hasRole("USER")
        .and() // デフォルトのFilter設定を変える
        .addFilterBefore(this.filter, UsernamePasswordAuthenticationFilter.class);
    http.headers().frameOptions().disable();
    // CORS対策
    http.cors().configurationSource(this.corsConfigurationSource());
  }
}
