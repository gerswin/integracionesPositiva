package com.prolinktic.sgdea.configuration;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.session.SessionAuthenticationStrategy;
import org.springframework.security.web.authentication.session.SessionFixationProtectionStrategy;
  /*
@Configuration
@EnableWebSecurity
public class CookieConfig extends WebSecurityConfigurerAdapter {


    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .sessionManagement()
                .sessionAuthenticationStrategy(sessionAuthenticationStrategy());

    }
    */


    /*
    *
       http
                .sessionManagement()
                .sessionAuthenticationStrategy(sessionAuthenticationStrategy())
                .and()
                .csrf().csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse()); // Configura el token CSRF con HttpOnly deshabilitado
    }
    * */


    /*
    @Bean
    public SessionAuthenticationStrategy sessionAuthenticationStrategy() {
        return new SessionFixationProtectionStrategy();
    }
}



*/


/*

import org.springframework.context.annotation.Configuration;
import org.springframework.session.web.http.CookieSerializer;
import org.springframework.session.web.http.DefaultCookieSerializer;

@Configuration
public class CookieConfig {

    public CookieSerializer cookieSerializer() {
        DefaultCookieSerializer serializer = new DefaultCookieSerializer();
        serializer.setUseSecureCookie(true); // Configura las cookies como seguras
        serializer.setSameSite("None"); // Configura la política SameSite como None para permitir el uso en cross-site requests
        return serializer;
    }
}

*/
