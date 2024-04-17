package br.org.sesisenai.ava.security.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;

@AllArgsConstructor

public enum Authority implements GrantedAuthority {

    ADMIN,
    INSTRUTOR,
    USUARIO;

    @Override
    public String getAuthority() {
        return this.name();
    }
}
