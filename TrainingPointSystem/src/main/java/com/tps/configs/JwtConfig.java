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

    String[] POST_PUBLIC_ENDPOINT = {
            "/api/login",
            "/api/user/register"
    };

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http.csrf().ignoringAntMatchers("/api/**"); //Tat yeu cau csrf token khi truy cap den api


//        http.authorizeRequests(request ->
//            request.antMatchers(HttpMethod.POST, POST_PUBLIC_ENDPOINT).permitAll()
//            .anyRequest().authenticated());


        http.antMatcher("/api/**")
//                .httpBasic()
//                .authenticationEntryPoint(restServicesEntryPoint())
//                .and()
                .authorizeRequests(request ->
                        request.antMatchers(HttpMethod.POST, POST_PUBLIC_ENDPOINT).permitAll()
                                .anyRequest().authenticated()
                );
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        http.addFilterBefore(corsFilter(), UsernamePasswordAuthenticationFilter.class)
                .addFilterBefore(jwtAuthenticationTokenFilter(), UsernamePasswordAuthenticationFilter.class);
        http.exceptionHandling().accessDeniedHandler(customAccessDeniedHandler());
    }
}
