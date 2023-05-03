package com.adjadev.stock.config;

import com.adjadev.stock.services.auth.ApplicationUserDetailsService;
import com.adjadev.stock.utils.JwtUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
@Component
public class ApplicationRequestFilter extends OncePerRequestFilter {
    @Autowired
    private JwtUtil jwtUtil;
    @Autowired
    private ApplicationUserDetailsService userDetailsService;
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws ServletException, IOException {
      final String authHeader = request.getHeader("Authorization");
      String userEmail = null;
      String jwt = null;
      String idEntreprise = null;

      if(authHeader != null && authHeader.startsWith("Bearer ")){
          jwt = authHeader.substring(7);
          userEmail = jwtUtil.extractUsername(jwt);
          idEntreprise = jwtUtil.extractIdEntreprise(idEntreprise);
      }
      if(userEmail != null && SecurityContextHolder.getContext().getAuthentication() == null){
          UserDetails userDetails = userDetailsService.loadUserByUsername(userEmail);
          if (jwtUtil.validateToken(jwt, userDetails)){
              UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                      new UsernamePasswordAuthenticationToken(
                              userDetails,
                              null,
                              userDetails.getAuthorities()
                      );
              usernamePasswordAuthenticationToken.setDetails(
                      new WebAuthenticationDetailsSource().buildDetails(request)
              );
              SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
          }
      }
       MDC.put("idEntreprise", idEntreprise);
       chain.doFilter(request, response);
    }
}
