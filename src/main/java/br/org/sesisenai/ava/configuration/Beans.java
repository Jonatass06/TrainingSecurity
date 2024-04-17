package br.org.sesisenai.ava.configuration;

import br.org.sesisenai.ava.security.service.AuthenticationService;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.security.web.context.SecurityContextRepository;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

@Configuration
@AllArgsConstructor
public class Beans {
    private final AuthenticationService authenticationService;
    @Bean
    public CorsConfigurationSource corsConfig(){
        CorsConfiguration corsConfig = new CorsConfiguration();
        corsConfig.setAllowedOrigins(List.of("http://localhost:3000", "https://proxy.hoppscotch.io/")); // all aplications that are allowed
        corsConfig.setAllowedMethods(List.of("POST","GET", "PUT", "PATCH", "DELETE")); // all requests that are allowed
        corsConfig.setAllowCredentials(true);
        corsConfig.setAllowedHeaders(List.of("*"));
        UrlBasedCorsConfigurationSource corsConfigurationSource =
                new UrlBasedCorsConfigurationSource();
        corsConfigurationSource.registerCorsConfiguration("/**",corsConfig);
        return corsConfigurationSource;
    }

    @Bean
    public SecurityContextRepository securityContextRepository(){
        return new HttpSessionSecurityContextRepository(); // Mantém a seção do úsuario na requisição
    }

    @Bean
    public AuthenticationManager authenticationManager() throws Exception {
        DaoAuthenticationProvider dao = new DaoAuthenticationProvider();
        dao.setPasswordEncoder(new BCryptPasswordEncoder());
        dao.setUserDetailsService(authenticationService);
        return new ProviderManager(dao);
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
}
