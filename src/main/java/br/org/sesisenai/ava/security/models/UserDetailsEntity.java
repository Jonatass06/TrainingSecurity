package br.org.sesisenai.ava.security.models;

import br.org.sesisenai.ava.entity.Usuario;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Collection;
import java.util.List;

@Data
@AllArgsConstructor
@Entity
@NoArgsConstructor
public class UserDetailsEntity implements UserDetails {

    @Id
    private String username;
    @OneToOne(mappedBy = "details")
    @JsonIgnore
    private Usuario usuario;
    private String password;

    @Column(updatable = false)
    private Collection<? extends GrantedAuthority> authorities;
    private boolean accountNonExpired = true;
    private boolean accountNonLocked = true;
    private boolean credentialsNonExpired = true;
    private boolean enabled = true;
    private boolean admin = false;


    public void setPassword(String senha) {
        this.password = new BCryptPasswordEncoder().encode(senha);
    }

}
