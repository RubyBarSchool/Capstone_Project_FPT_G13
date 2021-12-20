package com.university.fpt.acf.config.security;

import com.university.fpt.acf.config.security.filter.CustomAuthorizationFilter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@Slf4j
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private CustomAuthorizationFilter customAuthorizationFilter;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        //************************************
        // config AuthenticationManagerBuilder với password là BCryptPasswordEncoder
        //************************************
        log.info("configure(AuthenticationManagerBuilder auth) in SecurityConfig");
        auth.userDetailsService(userDetailsService).passwordEncoder(bCryptPasswordEncoder);
    }

    private static final String[] AUTH_WHITELIST = {
            // -- swagger ui
            "/v2/api-docs",
            "/swagger-resources",
            "/swagger-resources/**",
            "/configuration/ui",
            "/configuration/security",
            "/swagger-ui.html",
            "/webjars/**"
            // other public endpoints of your API may be appended to this array
    };

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        //************************************
        // cấu hình phân quyền theo đường dẫn
        //************************************
        log.info("configure(HttpSecurity http) in SecurityConfig");
        http.csrf().disable();
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        http.authorizeRequests().antMatchers(AUTH_WHITELIST).permitAll();
        http.authorizeRequests().antMatchers("/signin").permitAll();
        http.authorizeRequests().antMatchers("/spadmin/**").
                hasAnyAuthority("SP_ADMIN");
        http.authorizeRequests().antMatchers("/admin/**").
                hasAnyAuthority("ADMIN");
        http.authorizeRequests().antMatchers("/employee/**").
                hasAnyAuthority("EMPLOYEE");
        http.authorizeRequests().antMatchers(HttpMethod.POST, "/dashboard/spadmin/**")
                .hasAnyAuthority("SP_ADMIN");
        http.authorizeRequests().antMatchers(HttpMethod.POST, "/dashboard/admin/**")
                .hasAnyAuthority("ADMIN");
        http.authorizeRequests().antMatchers(HttpMethod.POST, "/dashboard/employee/**")
                .hasAnyAuthority("EMPLOYEE");
        http.authorizeRequests().antMatchers(HttpMethod.GET, "/files/**").permitAll();
        http.authorizeRequests().antMatchers(HttpMethod.GET, "/ws/**").permitAll();
        http.authorizeRequests().antMatchers(HttpMethod.GET, "/wse/**").permitAll();
        http.authorizeRequests().anyRequest().authenticated();
        http.addFilterBefore(customAuthorizationFilter, UsernamePasswordAuthenticationFilter.class);
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        log.info("authenticationManagerBean in SecurityConfig");
        return super.authenticationManagerBean();
    }

    @Bean
    BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
