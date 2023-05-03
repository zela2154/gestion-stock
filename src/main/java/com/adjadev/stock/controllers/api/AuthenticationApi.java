package com.adjadev.stock.controllers.api;

import com.adjadev.stock.dto.auth.AuthenticationRequest;
import com.adjadev.stock.dto.auth.AuthenticationResponse;
import com.adjadev.stock.services.auth.AuthenticationService;
import io.swagger.annotations.Api;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.io.IOException;

import static com.adjadev.stock.utils.Constants.AUTHENTICATION_ENDPOINT;

@Api("authentication")
public interface AuthenticationApi {
 @PostMapping(AUTHENTICATION_ENDPOINT +"/authenticate")
     ResponseEntity<AuthenticationResponse> authenticate(@RequestBody AuthenticationRequest request);
}
