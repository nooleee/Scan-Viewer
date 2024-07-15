package com.pacs.scanviewer.config;

import com.pacs.scanviewer.SCV.User.service.MyUserDetailsService;
import com.pacs.scanviewer.Util.JwtRequestFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.firewall.HttpFirewall;
import org.springframework.security.web.firewall.StrictHttpFirewall;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final MyUserDetailsService myUserDetailsService;
    private final JwtRequestFilter jwtRequestFilter;

    public SecurityConfig(MyUserDetailsService myUserDetailsService, JwtRequestFilter jwtRequestFilter) {
        this.myUserDetailsService = myUserDetailsService;
        this.jwtRequestFilter = jwtRequestFilter;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .authorizeRequests()
                .antMatchers( "/css/**","/script/login.js","/script/checkUserCode.js",
                        "/webjars/**", "/favicon.ico", "/user/login","/user/signup","/user/joinProcess",
                        "/user/loginProcess","/user/checkUserCode","/ws/**","/webapp/WEB-INF/views/commons/**").permitAll()
                .anyRequest().authenticated()
                .and().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        http.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }

    @Bean
    public HttpFirewall allowUrlEncodedSlashHttpFirewall() {
        StrictHttpFirewall firewall = new StrictHttpFirewall();
        firewall.setAllowUrlEncodedDoubleSlash(true); // "//" 허용
        return firewall;
    }

    public void configure(org.springframework.security.config.annotation.web.builders.WebSecurity web) throws Exception {
        web.httpFirewall(allowUrlEncodedSlashHttpFirewall());
    }
}
