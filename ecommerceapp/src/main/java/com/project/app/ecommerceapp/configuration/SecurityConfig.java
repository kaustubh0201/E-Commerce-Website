package com.project.app.ecommerceapp.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

public class SecurityConfig {
    @Autowired
    GoogleOAuth2SuccessHandler googleOAuth2SuccessHandler;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{

        http
                .authorizeHttpRequests()
                .antMatchers("/", "/shop/**", "/register").permitAll()
                .antMatchers("/admin/**").hasRole("ADMIN")
                .anyRequest()
                .authenticated()
                .and()
                .formLogin()
                .loginPage("/login")
                .permitAll()
                .failureUrl("/login?error=true")
                .defaultSuccessUrl("/")
                .usernameParameter("email")
                .passwordParameter("password")
                .and()
                .oauth2Login()
                .loginPage("/login")
                .successHandler(googleOAuth2SuccessHandler)
                .and()
                .logout()
                .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                .invalidateHttpSession(true)
                .deleteCookies("JSESSIONID")
                .and()
                .exceptionHandling()
                .and()
                .csrf()
                .disable();

        return http.build();
    }

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

//    protected void configure(AuthenticationMangerBuilder auth) throws Exception {
//        auth.userDetailsService(customerUserDetailsService);
//    }

//    public void configure(WebSecurity web) throws Exception {
//        web.ignoring().antMatchers("/resources/**", "/static/**", "/images/**", "/productimages/**", "/css/**", "/js/**");
//    }

}
