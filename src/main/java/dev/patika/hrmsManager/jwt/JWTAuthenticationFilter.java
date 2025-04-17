package dev.patika.hrmsManager.jwt;

import java.io.IOException;

import dev.patika.hrmsManager.core.exception.NotFoundException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@Component

public class JWTAuthenticationFilter extends OncePerRequestFilter{

    private final JWTService jwtService;

    private final UserDetailsService userDetailsService;

    public JWTAuthenticationFilter(JWTService jwtService, UserDetailsService userDetailsService) {
        this.jwtService = jwtService;
        this.userDetailsService = userDetailsService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        String header = request.getHeader("Authorization");
        String token;
        String username;

        if(header==null) {
            filterChain.doFilter(request, response);;
            return;
        }
        token = header.substring(7);
        try {
            username=	jwtService.getUsernameByToken(token);
            if(username!=null && SecurityContextHolder.getContext().getAuthentication()==null) {
                UserDetails userDetails = userDetailsService.loadUserByUsername(username);
                if(userDetails!=null && jwtService.isTokenExpired(token)) {
                    UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(username, null, userDetails.getAuthorities());
                    authenticationToken.setDetails(userDetails);
                    SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                }
            }
        }
        catch(ExpiredJwtException ex) {
            throw new NotFoundException("TOKEN_IS_EXPIRED");
        }
        catch (Exception e) {
            throw new NotFoundException("GENERAL_EXCEPTION");
        }
        filterChain.doFilter(request, response);
    }

}
