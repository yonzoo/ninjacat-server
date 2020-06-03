package com.server.ninjacat.security.jwt;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.server.ninjacat.data.model.users.ApplicationUser;
import com.server.ninjacat.security.roles.ApplicationUserRole;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.crypto.SecretKey;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public class JwtUsernameAndPasswordAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

  private final AuthenticationManager authenticationManager;
  private final JwtConfig jwtConfig;
  private final SecretKey secretKey;

  public JwtUsernameAndPasswordAuthenticationFilter(AuthenticationManager authenticationManager,
                                                    JwtConfig jwtConfig,
                                                    SecretKey secretKey) {
    this.jwtConfig = jwtConfig;
    this.secretKey = secretKey;
    this.authenticationManager = authenticationManager;
  }

  @Override
  public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
    try {
      UsernameAndPasswordAuthenticationRequest authenticationRequest =
          new ObjectMapper().readValue(request.getInputStream(), UsernameAndPasswordAuthenticationRequest.class);
      Authentication authentication = new UsernamePasswordAuthenticationToken(
          authenticationRequest.getUsername(),
          authenticationRequest.getPassword()
      );
      return authenticationManager.authenticate(authentication);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }


  @Override
  protected void successfulAuthentication(HttpServletRequest request,
                                          HttpServletResponse response,
                                          FilterChain chain,
                                          Authentication authResult) throws IOException {
    //Subject can be any field
    String token = Jwts.builder()
        .setSubject(authResult.getName())
        .claim("authorities", authResult.getAuthorities())
        .setIssuedAt(new Date())
        .setExpiration(java.sql.Date.valueOf(LocalDate.now().plusDays(jwtConfig.getTokenExpirationAfterDays())))
        .signWith(secretKey)
        .compact();
    ObjectMapper mapper = new ObjectMapper();
    ApplicationUser applicationUser = (ApplicationUser) authResult.getPrincipal();
    String role = null;
    List<String> authorities = applicationUser.getAuthorities().stream().map(Object::toString).collect(Collectors.toList());
    if (authorities.contains("ROLE_CLIENT")) {
      role = "CLIENT";
    } else if (authorities.contains("ROLE_ADMIN")) {
      role = "ADMIN";
    }
    response.getWriter().write(mapper.writeValueAsString(new UserResponse(applicationUser.getUsername(), role)));
    response.addHeader(jwtConfig.getAuthorizationHeader(), jwtConfig.getTokenPrefix() + token);
  }
}
