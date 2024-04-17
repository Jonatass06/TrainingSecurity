package br.org.sesisenai.ava.entity;

import br.org.sesisenai.ava.dto.abstraction.ResponseConversorDTO;
import br.org.sesisenai.ava.dto.implementation.usuario.UsuarioResponseDTO;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Set;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Usuario implements ResponseConversorDTO<UsuarioResponseDTO> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String nome;
    private String email;
    private String senha;

    @Column(name = "data_cadastro")
    private LocalDateTime dataCadastro;

    @OneToMany(mappedBy = "usuario")
    private Set<Certificado> certificados;

    @OneToMany(mappedBy = "usuario")
    private Set<Inscricao> inscricoes;

    public UsuarioResponseDTO toDTO() {
        UsuarioResponseDTO usuarioResponseDTO = new UsuarioResponseDTO();
        usuarioResponseDTO.setId(this.id);
        usuarioResponseDTO.setNome(this.nome);
        usuarioResponseDTO.setEmail(this.email);
        usuarioResponseDTO.setDataCadastro(this.dataCadastro);
        return usuarioResponseDTO;
    }

    public Usuario(Long id) {
        this.id = id;
    }

    public void setSenha(String senha) {
        this.senha = new BCryptPasswordEncoder().encode(senha);
    }
}










