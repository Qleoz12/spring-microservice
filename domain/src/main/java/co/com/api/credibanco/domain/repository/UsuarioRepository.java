package co.com.api.credibanco.domain.repository;

import co.com.api.credibanco.domain.entity.user.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsuarioRepository extends JpaRepository<Usuario, Long>,UsuarioQuery {

    Usuario findByUsername(String username);


}
