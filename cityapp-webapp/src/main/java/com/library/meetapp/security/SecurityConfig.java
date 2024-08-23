package com.library.meetapp.security;

import com.library.meetapp.entity.User;
import com.library.meetapp.repository.UserRepository;
import com.library.meetapp.util.AppCtx;
import com.vaadin.flow.spring.security.VaadinWebSecurity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.security.web.savedrequest.RequestCache;
import org.springframework.stereotype.Component;

@EnableWebSecurity
@ComponentScan
@Configuration
public class SecurityConfig extends VaadinWebSecurity {

    private static final String LOGIN_PROCESSING_URL = "/login";
    private static final String LOGIN_FAILURE_URL = "/login?error";
    private static final String LOGIN_URL = "/login";
    private static final String LOGOUT_SUCCESS_URL = "/";


    @Autowired
    private RestServiceAuthenticationFilter restServiceAuthenticationFilter;

    @Bean
    @Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
    public User currentUser(UserRepository userRepository) {
        final String username = SecurityUtils.getUsername();
        User user = username != null ? userRepository.findByUserNameIgnoreCase(username) : null;
        return user;
    }

//    @Bean
//    public RequestCache customRequestCache() {
//        return new HttpSessionRequestCache();
//    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(csrf -> csrf.disable())
                .requestCache(requestCache -> requestCache.requestCache(new CustomRequestCache()))
                .addFilterBefore(restServiceAuthenticationFilter, BasicAuthenticationFilter.class)
                .authorizeHttpRequests(authz -> authz
                        .requestMatchers("/settings").permitAll()
                        .requestMatchers(SecurityUtils::isFrameworkInternalRequest).permitAll()
                        .anyRequest().hasAnyAuthority("User"))
                .formLogin(formLogin -> formLogin
                        .loginPage(LOGIN_URL).permitAll()
                        .loginProcessingUrl(LOGIN_PROCESSING_URL)
                        .failureUrl(LOGIN_FAILURE_URL)
                        .successHandler(new SavedRequestAwareAuthenticationSuccessHandler()))
                .logout(logout -> logout.logoutSuccessUrl(LOGOUT_SUCCESS_URL));

        return http.build();
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().requestMatchers(
                "/VAADIN/**",
                "/favicon.ico",
                "/robots.txt",
                "/icons/**",
                "/images/**",
                "/frontend/**",
                "/webjars/**",
                "/h2-console/**",
                "/frontend-es5/**",
                "/frontend-es6/**");
    }
}
