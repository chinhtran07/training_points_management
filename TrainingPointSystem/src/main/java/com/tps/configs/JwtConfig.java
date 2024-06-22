package com.tps.configs;

import com.tps.filters.CorsFilter;
import com.tps.filters.CustomAccessDeniedHandler;
import com.tps.filters.JwtAuthenticationTokenFilter;
import com.tps.filters.RestAuthenticationEntryPoint;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;


@Configuration
@EnableWebMvc
@EnableTransactionManagement
@Order(1)
@ComponentScan(basePackages = {
        "com.tps.controllers",
        "com.tps.repositories",
        "com.tps.services",
        "com.tps.components"}
)
public class JwtConfig extends WebSecurityConfigurerAdapter {

    @Bean
    public JwtAuthenticationTokenFilter jwtAuthenticationTokenFilter() throws Exception {
        JwtAuthenticationTokenFilter jwtAuthenticationTokenFilter = new JwtAuthenticationTokenFilter();
        jwtAuthenticationTokenFilter.setAuthenticationManager(authenticationManager());
        return jwtAuthenticationTokenFilter;
    }

    @Bean
    public RestAuthenticationEntryPoint restServicesEntryPoint() {
        return new RestAuthenticationEntryPoint();
    }

    @Bean
    public CustomAccessDeniedHandler customAccessDeniedHandler() {
        return new CustomAccessDeniedHandler();
    }

    @Bean
    public CorsFilter corsFilter() {
        return new CorsFilter();
    }

    @Bean
    @Override
    protected AuthenticationManager authenticationManager() throws Exception {
        return super.authenticationManager();
    }

    String[] PUBLIC_ENDPOINTS = {
            "/api/login",
            "/api/user/register"
    };

    String[] STUDENT_READ_ONLY = {
            "/api/activities",
            "/api/activities/**",
            "/api/activities/**/missions",
            "/api/faculties",
            "/api/missions/user-mission",
            "/api/point-groups",
            "/api/posts",
            "/api/posts/**",
            "/api/user/current",
            "/api/students/result-training-point"
    };

    String[] STUDENT_CAN_EDIT = {
            "/api/posts/**/like",
            "/api/posts/**/comments",
            "/api/missions/**/register",
            "/api/missions/**/missing",
            "/api/user/**",
            "/api/posts/comments/**"
    };
    String[] ASSISTANT_API_ENDPOINTS = {
            "/api/point-groups/**/activities",
            "/api/activities/**",
            "/api/posts",
            "/generatePdf",
            "/api/activities/**/missions",
            "/api/missions/**",
            "/api/missing-report/faculty",
            "/api/missing-report/**",
            "/api/missing-report/student",
            "/api/stats/training-points/faculty",
            "/api/stats/training-points/rank"
    };


    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http.csrf().ignoringAntMatchers("/api/**");


        http.authorizeRequests().antMatchers(PUBLIC_ENDPOINTS).permitAll();
//        http.authorizeRequests().antMatchers(HttpMethod.GET, STUDENT_READ_ONLY).access("hasAnyRole(\"ROLE_ASSISTANT\", \"ROLE_STUDENT\")");
        http.authorizeRequests().antMatchers(ASSISTANT_API_ENDPOINTS).access("hasRole('ROLE_ASSISTANT')");
//        http.authorizeRequests().antMatchers(STUDENT_CAN_EDIT).access("hasAnyRole(\"ROLE_ASSISTANT\", \"ROLE_STUDENT\")");
        http.authorizeRequests().antMatchers("/api/stats/training-points").access("hasRole('ROLE_ADMIN')");


        http.antMatcher("/api/**").httpBasic().authenticationEntryPoint(restServicesEntryPoint()).and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and().authorizeRequests()
                .antMatchers(HttpMethod.GET, "/api/**").access("hasRole('ROLE_ADMIN') or hasRole('ROLE_ASSISTANT') or hasRole('ROLE_STUDENT')")
                .antMatchers(HttpMethod.POST, "/api/**").access("hasRole('ROLE_ADMIN') or hasRole('ROLE_ASSISTANT') or hasRole('ROLE_STUDENT')")
                .antMatchers(HttpMethod.DELETE, "/api/**").access("hasRole('ROLE_ADMIN') or hasRole('ROLE_ASSISTANT')")
                .and()
                .addFilterBefore(jwtAuthenticationTokenFilter(), UsernamePasswordAuthenticationFilter.class)
                .exceptionHandling().accessDeniedHandler(customAccessDeniedHandler());
    }
}
