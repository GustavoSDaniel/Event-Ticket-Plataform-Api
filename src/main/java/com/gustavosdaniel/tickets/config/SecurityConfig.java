package com.gustavosdaniel.tickets.config;

import com.gustavosdaniel.tickets.user.UserProvisioningFilter;
import org.springframework.boot.web.servlet.filter.OrderedHiddenHttpMethodFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.server.resource.web.authentication.BearerTokenAuthenticationFilter;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http,
                                                   UserProvisioningFilter userProvisioningFilter,
                                                   JwtAuthenticationConverter jwtAuthenticationConverter
    ) throws Exception {

        http
                .authorizeHttpRequests(authorizeRequests ->

                        authorizeRequests
                                .requestMatchers(HttpMethod.GET,"/api/v1/published-events/**").permitAll() //Indica que este endpoint é público, acessível por qualquer pessoa, sem necessidade de login ou permissões específicas.
                                // ** para náo mostra o id do evento an bara de pesquisa
                                .requestMatchers("/api/v1/events").hasRole("ORGANIZER") // SÓ OS ORGANIZADORES TEM ACESSO A ESSE ENDPOINTER
                                .requestMatchers(HttpMethod.PUT, "/api/v1/events/**/updateEvent").hasRole("STAFF")   // OS FUNCIONARIOS TEMA CESSO PARA ATUALIZAR OS EVENTOS
                                .anyRequest().authenticated()) // Todas as requisições exigem autenticação (usado para o organizador)
                .csrf(csrf -> csrf.disable()) // Desabilita CSRF porque a API é stateless e usa JWT
                .sessionManagement(sessionManagement ->
                        sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS)) // Define a aplicação como stateless não mantém sessões no servidor
                .oauth2ResourceServer(oauth2ResourceServer ->
                        oauth2ResourceServer.jwt( jwtConfigurer ->
                                jwtConfigurer.jwtAuthenticationConverter(jwtAuthenticationConverter)
                        ))
                .addFilterAfter(userProvisioningFilter, BearerTokenAuthenticationFilter.class); // Adiciona o UserProvisioningFilter após o filtro padrão de autenticação por token e Garante que o token já foi validado antes do nosso filtro executar

        return http.build();
    }
}
