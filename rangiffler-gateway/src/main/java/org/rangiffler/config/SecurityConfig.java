package org.rangiffler.config;

import org.rangiffler.cors.CorsCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.web.SecurityFilterChain;


@EnableWebSecurity
@Configuration
public class SecurityConfig {

  private static final String[] IGNORE_MATCHERS = {
          "/open-api-ui", "/open-api-ui/**", "/swagger-ui.html", "/open-api", "/open-api/**", "/swagger-ui/**"};
  private final CorsCustomizer corsCustomizer;

  public SecurityConfig(CorsCustomizer corsCustomizer) {
    this.corsCustomizer = corsCustomizer;
  }

  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    corsCustomizer.corsCustomizer(http);

    http.authorizeHttpRequests(
        authorize ->
            authorize.anyRequest().permitAll()
    ).csrf().disable();
    return http.build();
  }

  @Bean
  public WebSecurityCustomizer webSecurityCustomizer() {
    return (web) -> web.ignoring().requestMatchers(IGNORE_MATCHERS);
  }

}
