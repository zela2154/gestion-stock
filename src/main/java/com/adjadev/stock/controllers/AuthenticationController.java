package com.adjadev.stock.controllers;

import com.adjadev.stock.config.JwtService;
import com.adjadev.stock.controllers.api.AuthenticationApi;
import com.adjadev.stock.dto.UtilisateurDto;
import com.adjadev.stock.dto.auth.AuthenticationRequest;
import com.adjadev.stock.dto.auth.AuthenticationResponse;
import com.adjadev.stock.dto.auth.RegisterRequest;
import com.adjadev.stock.model.auth.ExtendedUser;
import com.adjadev.stock.services.auth.ApplicationUserDetailsService;
import com.adjadev.stock.services.auth.AuthenticationService;
//import com.adjadev.stock.utils.JwtUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

import static com.adjadev.stock.utils.Constants.AUTHENTICATION_ENDPOINT;

@RestController
@RequestMapping(AUTHENTICATION_ENDPOINT)
@RequiredArgsConstructor
public class AuthenticationController{
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private ApplicationUserDetailsService userDetailsService;
    @Autowired
    private JwtService jwtService;
    @Autowired
    private AuthenticationService service;
    /*@Override
    public ResponseEntity<AuthenticationResponse> authenticate(@RequestBody AuthenticationRequest request){*/
   // public ResponseEntity<AuthenticationResponse> authenticate(@RequestBody UtilisateurDto request){
       /* authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );
        final UserDetails userDetails = userDetailsService.loadUserByUsername(request.getEmail());

       // final String jwt = jwtUtil.generateToken((ExtendedUser) userDetails);
        final String jwt = jwtService.generateToken(userDetails);

        return ResponseEntity.ok(AuthenticationResponse.builder().accessToken(jwt).build());*/
      // service.authenticate(request);
   // }
    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> register(
            @RequestBody RegisterRequest request
    ) {
        return ResponseEntity.ok(service.register(request));
    }
    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationResponse> authenticate(
            @RequestBody AuthenticationRequest request
    ) {
        return ResponseEntity.ok(service.authenticate(request));
    }
    @PostMapping("/refresh-token")
        public void refreshToken(
                HttpServletRequest request,
                HttpServletResponse response
        ) throws IOException {
            service.refreshToken(request, response);
        }
}
