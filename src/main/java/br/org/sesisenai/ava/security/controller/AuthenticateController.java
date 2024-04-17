package br.org.sesisenai.ava.security.controller;

import br.org.sesisenai.ava.security.models.UserLogin;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

public class AuthenticateController {

    private AuthenticationManager authenticationManager;

    //    private final CookieUtil cookieUtil = new CookieUtil();
//    private final UserRepository repository;
    @PostMapping("/login")
    public ResponseEntity<String> authenticate(@RequestBody UserLogin userLogin, HttpServletRequest request, HttpServletResponse response) {
        try {

            UsernamePasswordAuthenticationToken token =
                    new UsernamePasswordAuthenticationToken(userLogin.getNome(), userLogin.getSenha());

            Authentication authentication = authenticationManager.authenticate(token);
            UserDetails user = (UserDetails) authentication.getPrincipal();
//            Cookie cookie = cookieUtil.gerarCookieJwt(user)
//            response.addCookie(cookie);

            return ResponseEntity.ok("User authenticated");

        } catch (AuthenticationException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("User not authenticated");
        }

    }
}