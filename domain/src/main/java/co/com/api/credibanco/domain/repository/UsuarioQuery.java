package co.com.api.credibanco.domain.repository;

import co.com.api.credibanco.domain.entity.user.UsuarioDTO;
import co.com.api.credibanco.domain.repository.query.generic.GenericoQuery;

public interface UsuarioQuery extends GenericoQuery<UsuarioDTO,Long> {

    UsuarioDTO obterPorUsername(String username);
}
