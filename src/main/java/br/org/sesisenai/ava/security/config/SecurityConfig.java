package br.org.sesisenai.ava.security.config;

import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.context.SecurityContextRepository;

@Configuration
@AllArgsConstructor

public class SecurityConfig {

    private SecurityContextRepository repo;

    @Bean
    public SecurityFilterChain config(HttpSecurity http) throws Exception {
        http.csrf(AbstractHttpConfigurer::disable);
        http.authorizeHttpRequests(
                auth -> {
                    auth.requestMatchers(HttpMethod.POST, "/login");
                    auth.requestMatchers(HttpMethod.GET, "/login");
                    auth.requestMatchers(HttpMethod.POST, "/api/instrutor").permitAll();
                    auth.requestMatchers(HttpMethod.POST, "/api/usuarios").permitAll();
                    auth.requestMatchers(HttpMethod.GET, "/api/cursos").permitAll();
                    auth.requestMatchers(HttpMethod.GET, "/api/cursos/{id}").permitAll();
                    auth.requestMatchers(HttpMethod.GET, "/api").permitAll();
                    auth.requestMatchers(HttpMethod.GET, "/swagger-ui/**").permitAll();
                    auth.anyRequest().authenticated();
                }
        );
        http.securityContext((context) -> context.securityContextRepository(repo));
        http.sessionManagement(config -> {
            config.sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        });
//        http.addFilterBefore(filterAuthentication, UsernamePasswordAuthenticationFilter.class);

        http.formLogin(Customizer.withDefaults());
        http.logout(AbstractHttpConfigurer::disable);
        return http.build();
    }

}
