package com.Singhify.Singhify.Security;

import com.Singhify.Singhify.Exception.JwtTokenNotValid;
import com.Singhify.Singhify.Services.CustomUserDetailsService;
import com.Singhify.Singhify.Utilities.JWTUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JWTFilter extends OncePerRequestFilter {

    @Autowired
    JWTUtil jwtUtil;

    @Autowired
    CustomUserDetailsService userDetails;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String authHeader=request.getHeader("Authorization");
        String token=null;
        String userName=null;
        Boolean validToken=false;
        UserDetails detailUser;
        if(authHeader!=null && authHeader.startsWith("Bearer "))
        {
            token=jwtUtil.extractToken(authHeader);
            userName=jwtUtil.extractUserName(token);
        }
        else {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);  // Explicitly setting status to 401
            response.getWriter().write("Authorization token is missing.");
            return;
        }
        detailUser=userDetails.loadUserByUsername(userName);
        if(detailUser==null)
        {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);  // Explicitly setting status to 401
            response.getWriter().write("Authorization token is not Valid.");
            return;
        }


        if(userName!=null && SecurityContextHolder.getContext().getAuthentication()==null) {
             validToken = jwtUtil.validateToken(token);
        }

        if(validToken)
        {

            UsernamePasswordAuthenticationToken authToken= new UsernamePasswordAuthenticationToken(detailUser,null,detailUser.getAuthorities());
            authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
            SecurityContextHolder.getContext().setAuthentication(authToken);
        }
        else{
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);  // Explicitly setting status to 401
            response.getWriter().write("Authorization token is not Valid.");
            return;
        }

        filterChain.doFilter(request, response);
    }
    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        String path = request.getRequestURI();
        return path.startsWith("/auth/"); // Bypass the filter for /auth/** endpoints
    }
}
