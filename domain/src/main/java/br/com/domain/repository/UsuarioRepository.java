package br.com.domain.repository;

import br.com.domain.entity.user.Usuario;
import br.com.domain.entity.user.UsuarioDTO;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsuarioRepository extends JpaRepository<Usuario, Long>,UsuarioQuery {

    Usuario findByUsername(String username);


}
