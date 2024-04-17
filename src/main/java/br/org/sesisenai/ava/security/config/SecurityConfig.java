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

                    //User
//                    Get (Um): Permite visualizar seus próprios detalhes de usuário.
//                            Put: Permite atualizar seus próprios dados de usuário.
//                            Delete: Permite excluir sua própria conta de usuário.
//                            Patch: Permite fazer atualizações parciais em seus próprios dados de
//                    usuário.
//                            Aula (Apenas de Cursos que o Usuário Está Inscrito):
//                    Get (Um): Permite visualizar detalhes de uma aula de um curso em que
//                    está inscrito.
//                    Get (Todos): Permite listar todas as aulas de cursos em que está
//                    inscrito.
//                            Inscrição (Apenas Inscrição e Cancelamento do Próprio Usuário):
//                    Post (Inscrição): Permite que um usuário se inscreva em um curso.
//                            Post (Cancelamento): Permite que um usuário cancele sua inscrição em
//                    um curso.
                    auth.requestMatchers(HttpMethod.GET, "/api/usuarios/{id}").authenticated();//o proprio usuario
                    auth.requestMatchers(HttpMethod.PUT, "/api/usuarios/{id}").authenticated();//o proprio usuario
                    auth.requestMatchers(HttpMethod.PATCH, "/api/usuarios/{id}").authenticated();//o proprio usuario
                    auth.requestMatchers(HttpMethod.DELETE, "/api/usuarios/{id}").authenticated();//o proprio usuario
                    auth.requestMatchers(HttpMethod.GET, "/api/cursos/{cursoId}/aulas/{aulaId}").authenticated();//cursos em que esta inscrito
                    auth.requestMatchers(HttpMethod.GET, "/api/cursos/{cursoId}/aulas").authenticated();//cursos em que esta inscrito
                    auth.requestMatchers(HttpMethod.POST, "/api/cursos/{cursoId}/incricoes").authenticated();//precisa ter o curso e ser o user
                    auth.requestMatchers(HttpMethod.POST, "/api/cursos/{cursoId}/incricoes/cancelar").authenticated();//precisa ter o curso e ser o user

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
