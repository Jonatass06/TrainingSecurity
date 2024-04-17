package br.org.sesisenai.ava.service;

import br.org.sesisenai.ava.dto.abstraction.AtualizacaoRequestConversorDTO;
import br.org.sesisenai.ava.dto.implementation.usuario.UsuarioPostRequestDTO;
import br.org.sesisenai.ava.dto.implementation.usuario.UsuarioResponseDTO;
import br.org.sesisenai.ava.entity.Usuario;
import br.org.sesisenai.ava.repository.UsuarioRepository;
import br.org.sesisenai.ava.security.models.Authority;
import br.org.sesisenai.ava.security.models.UserDetailsEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Data
@AllArgsConstructor
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    public UsuarioResponseDTO criarUsuario(UsuarioPostRequestDTO usuarioDTO) {
        Usuario usuario = usuarioDTO.toEntity();
        usuario.getDetails().setAuthorities(List.of(Authority.USUARIO));
        usuario = usuarioRepository.save(usuario);
        return usuario.toDTO();
    }

    public UsuarioResponseDTO atualizarUsuario(
            Long id, AtualizacaoRequestConversorDTO<Usuario> usuarioDTO) {
        Usuario usuario = usuarioRepository.findById(id).get();
        usuario = usuarioDTO.toEntity(usuario);
        usuario = usuarioRepository.save(usuario);
        return usuario.toDTO();
    }

    public Page<UsuarioResponseDTO> buscarUsuarios(Pageable pageable) {
        return usuarioRepository.findAll(pageable)
                .map(Usuario::toDTO);
    }

    public UsuarioResponseDTO buscarUsuario(Long id) {
        return usuarioRepository.findById(id).get().toDTO();
    }

    public void deletarUsuario(Long id) {
        usuarioRepository.deleteById(id);
    }
}