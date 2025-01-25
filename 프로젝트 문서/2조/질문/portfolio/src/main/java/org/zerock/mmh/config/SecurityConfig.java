package org.zerock.mmh.config;

import lombok.extern.log4j.Log4j2;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@Log4j2
public class SecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(auth -> auth
                        // 모든 요청에 대해 인증 없이 접근 허용
                        .anyRequest().permitAll()  // 모든 URL에 대해 인증 없이 접근 허용
                )
                .formLogin(form -> form
                        .loginPage("/userMemberController/loginForm")  // 로그인 페이지 URL
                        .defaultSuccessUrl("/userMemberController/loginSuccess", true)
                        .failureUrl("/userMemberController/loginForm?error=true")
                )

                .logout(logout -> logout
                        // 로그아웃 후 이동할 URL
                        .logoutSuccessUrl("/userMemberController/loginForm")
                        .permitAll()
                )
                // CSRF 보호 비활성화 (필요시)
                .csrf(csrf -> csrf.disable());

        return http.build();
    }
}
