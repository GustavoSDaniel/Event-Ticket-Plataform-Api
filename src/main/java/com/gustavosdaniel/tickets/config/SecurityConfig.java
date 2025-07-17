package com.gustavosdaniel.tickets.config;

import com.gustavosdaniel.tickets.user.UserProvisioningFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.server.resource.web.authentication.BearerTokenAuthenticationFilter;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http,
                                                   UserProvisioningFilter userProvisioningFilter)
            throws Exception {

        http
                .authorizeHttpRequests(authorizeRequests ->

                        authorizeRequests.anyRequest().authenticated()) // Todas as requisições exigem autenticação
                .csrf(csrf -> csrf.disable()) // Desabilita CSRF porque a API é stateless e usa JWT
                .sessionManagement(sessionManagement ->
                        sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS)) // Define a aplicação como stateless não mantém sessões no servidor
                .oauth2ResourceServer(oauth2ResourceServer ->
                        oauth2ResourceServer.jwt(Customizer.withDefaults()
                        ))
                .addFilterAfter(userProvisioningFilter, BearerTokenAuthenticationFilter.class); // Adiciona o UserProvisioningFilter após o filtro padrão de autenticação por token e Garante que o token já foi validado antes do nosso filtro executar

        return http.build();
    }
}
