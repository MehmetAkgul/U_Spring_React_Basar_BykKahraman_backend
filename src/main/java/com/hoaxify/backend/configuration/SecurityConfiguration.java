package com.hoaxify.backend.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {


    @Autowired
    UserAuthService userAuthService;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        /**
         *CSRF (Cross Site Request Forgery)
         * CSRF (Siteler Arası İstek Sahteciliği)
         * ayrıca www-authenticate : Basic realm="Realm" headerı popup çıkmasını sağlıyor
         */


        http.csrf().disable();

        http.httpBasic()// hangi tür yetkilendirme kullanıyorsun burada basic yetkilendirme kullandık
                .authenticationEntryPoint(new AuthEntryPoint());

        http.authorizeRequests()// istekleri kontrol et
                .antMatchers(HttpMethod.POST, "/api/1.0/auth") // bu adresten gelen postlara bak
                .authenticated() // bunlar yetkilendirilmiş olmalı
                .and()
                .authorizeRequests() // ve yine  isteklere bak
                .anyRequest()// herhangi bir istek olabilir
                .permitAll(); // hepsine izin ver.

        /**
         * Securty ile ilgli sesion üretimi yapmsını engelliyoruz.
         */
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

    }

    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userAuthService).passwordEncoder(passwordEncoder());
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


}
