package com.adil.eshop.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.PasswordEncoder;

@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    public void authConfigure(AuthenticationManagerBuilder auth,
                              UserAuthService userAuthService,
                              PasswordEncoder passwordEncoder) throws Exception {
        auth.inMemoryAuthentication()
                .withUser("test_user")
                .password(passwordEncoder.encode("password"))
                .roles("ADMIN");

        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(userAuthService);
        provider.setPasswordEncoder(passwordEncoder);
        auth.authenticationProvider(provider);
    }

    @Configuration
    public static class UiWebSecurityConfigurationAdapter extends WebSecurityConfigurerAdapter {

        @Override
        protected void configure(HttpSecurity http) throws Exception {
            http.authorizeRequests()
                    .antMatchers("/users/**").hasRole("ADMIN")
                    .anyRequest().authenticated()
                    .and()
                    .formLogin();
        }
    }
}
