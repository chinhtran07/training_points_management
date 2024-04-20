package com.tps.filters;

import com.tps.components.JwtService;
import com.tps.pojo.User;
import com.tps.services.UserService;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.text.ParseException;

public class JwtAuthenticationTokenFilter extends UsernamePasswordAuthenticationFilter {
    private static final String TOKEN_HEADER = "authorization";

    @Autowired
    private JwtService jwtService;

    @Autowired
    private UserService userService;

    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) req;
        String authToken = httpRequest.getHeader(TOKEN_HEADER);
//        authToken = authToken.substring(7);
//        System.out.println(authToken);

        try {
            if (jwtService.validateTokenLogin(authToken)) {
                String username = jwtService.getUsernameFormToken(authToken);

                User user = userService.getUserByUsername(username);
                if (user != null) {
                    boolean enabled = true;
                    boolean accountNonExpired = true;
                    boolean credentialsNonExpired = true;
                    boolean accountNonLocked = true;

                    UserDetails userDetails = new org.springframework.security.core.userdetails.User(
                            username, user.getPassword(), enabled, accountNonExpired, credentialsNonExpired, accountNonLocked, user.getAuthorities()
                    );

                    UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails,
                            null, userDetails.getAuthorities());
                    authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(httpRequest));
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                }
            }
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }

        chain.doFilter(req, res);
    }
}
