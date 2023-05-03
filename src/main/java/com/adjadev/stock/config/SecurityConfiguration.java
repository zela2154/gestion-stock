package com.adjadev.stock.config;


import com.adjadev.stock.services.auth.ApplicationUserDetailsService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.filter.CorsFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;

import static com.adjadev.stock.model.Role.ADMIN;
import static com.adjadev.stock.model.Role.MANAGER;
import static com.adjadev.stock.utils.Constants.AUTHENTICATION_ENDPOINT;
import static com.adjadev.stock.utils.Permissions.*;
import static org.springframework.http.HttpMethod.*;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
//public class SecurityConfiguration extends WebSecurityConfigurerAdapter{
public class SecurityConfiguration {
    private final JwtAuthenticationFilter jwtAuthFilter;
    private final AuthenticationProvider authenticationProvider;
    private final LogoutHandler logoutHandler;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf()
                .disable()
                .authorizeHttpRequests()
                //.requestMatchers("/api/v1/auth/**")
                .requestMatchers(
                        AUTHENTICATION_ENDPOINT+"/auth/**",
                        //"/**/register",
                        //"/api/access/**",
                        "/h2-console/**",
                        "/v2/api-docs",
                        "/v3/api-docs",
                        "/v3/api-docs/**",
                        "/swagger-resources",
                        "/swagger-resources/**",
                        "/configuration/ui",
                        "/configuration/security",
                        "/swagger-ui/**",
                        "/webjars/**",
                        "/swagger-ui.html")
                .permitAll()
                .requestMatchers(AUTHENTICATION_ENDPOINT+"/management/**").hasAnyRole(ADMIN.name(), MANAGER.name())
                .requestMatchers(POST, AUTHENTICATION_ENDPOINT+"/management/**").hasAnyAuthority(ADMIN_CREATE.name(), MANAGER_CREATE.name())
                .requestMatchers(PUT, AUTHENTICATION_ENDPOINT+"/management/**").hasAnyAuthority(ADMIN_UPDATE.name(), MANAGER_UPDATE.name())
                .requestMatchers(DELETE, AUTHENTICATION_ENDPOINT+"/management/**").hasAnyAuthority(ADMIN_DELETE.name(), MANAGER_DELETE.name())
                .anyRequest()
                .authenticated()
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authenticationProvider(authenticationProvider)
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class)
                .logout()
                //.logoutUrl("/api/v1/auth/logout")
                .logoutUrl(AUTHENTICATION_ENDPOINT+"/auth/logout")
                .addLogoutHandler(logoutHandler)
                .logoutSuccessHandler((request, response, authentication) -> SecurityContextHolder.clearContext())
        ;

        return http.build();
    }

    @Bean
    public CorsFilter corsFilter() {
        final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        final CorsConfiguration config = new CorsConfiguration();
        config.setAllowCredentials(true);
        config.setAllowedOrigins(Arrays.asList("http://localhost:4200", "http://locahost:5300/node"));
        config.setAllowedHeaders(Arrays.asList("Origin", "Content-Type", "Accept", "Authorization"));
        config.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS"));
        source.registerCorsConfiguration("/**", config);
        return new CorsFilter(source);
    }
  /* @Autowired
    private ApplicationUserDetailsService applicationUserDetailsService;
   @Autowired
   private ApplicationRequestFilter applicationRequestFilter;

   protected void configure(AuthenticationManagerBuilder auth) throws Exception{
       auth.userDetailsService(applicationUserDetailsService)
               .passwordEncoder(passwordEncoder());
   }

   protected void configure(HttpSecurity http) throws Exception{
     http.csrf().disable()*/
            // .authorizeHttpRequests().requestMatchers("/**/authenticate").permitAll()
            /* .anyRequest().authenticated()
             .and().sessionManagement()
             .sessionCreationPolicy(SessionCreationPolicy.STATELESS);
     http.addFilterBefore(applicationRequestFilter, UsernamePasswordAuthenticationFilter.class);
   }

  /* @Bean
    public AuthenticationManager customAuthenticationManager() throws Exception{
       return super.authenticationManagerBean();
   }*/
   /*@Bean
    public PasswordEncoder passwordEncoder(){
       return new BCryptPasswordEncoder();
   }*/
}
