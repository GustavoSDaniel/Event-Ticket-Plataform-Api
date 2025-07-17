package com.gustavosdaniel.tickets.user;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class UserProvisioningFilter extends OncePerRequestFilter { // OncePerRequestFilter garante que um filtro seja executado apenas uma vez por requisição HTTP

    private final UserRepository userRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain
    ) throws ServletException, IOException {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null // se o autentificador nao for nulo
                && authentication.isAuthenticated() // e ele estiver autenticado
                && authentication.getPrincipal() instanceof Jwt jwt) { // e estiver no formato de Jwt

            UUID keycloakUserId = UUID.fromString(jwt.getSubject()); // extrai o ID do usuário do token JWT (armazenado no campo sub/subject)

            if (!userRepository.existsById(keycloakUserId)) { // Se o usuário não existe no banco

                User user = new User();
                user.setId(keycloakUserId);
                user.setFullName(jwt.getClaimAsString("preferred_username"));
                user.setEmail(jwt.getClaimAsString("email"));

                userRepository.save(user);
            }
        }

        filterChain.doFilter(request, response);

    }

}
