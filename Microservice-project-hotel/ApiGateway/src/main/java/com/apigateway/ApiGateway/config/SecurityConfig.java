package com.apigateway.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;

@Configuration
@EnableWebFluxSecurity
public class SecurityConfig
{

 // @Bean
  //public SecurityWebFilterChain springSecurityFilterChain(ServerHttpSecurity httpSecurity)
 // {
//      httpSecurity.authorizeExchange()
//              .anyExchange()
//              .authenticated()
//              .and()
//              .oauth2Client()
//              .and()
//              .oauth2ResourceServer()
//              .jwt();
//
//      return httpSecurity.build();
      @Bean
      public SecurityWebFilterChain springSecurityFilterChain(ServerHttpSecurity httpSecurity) {
      return httpSecurity
              .authorizeExchange(exchange -> exchange
                      .anyExchange()
                      .authenticated()
              )
              .oauth2Client(Customizer.withDefaults())
              .oauth2ResourceServer(resourceServer -> resourceServer
                      .jwt(Customizer.withDefaults())
              )
              .build();
  }

  }


