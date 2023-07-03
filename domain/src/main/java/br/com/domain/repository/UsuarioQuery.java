package br.com.domain.repository;

import br.com.domain.entity.user.UsuarioDTO;
import br.com.domain.repository.query.generic.GenericoQuery;

public interface UsuarioQuery extends GenericoQuery<UsuarioDTO,Long> {

    UsuarioDTO obterPorUsername(String username);
}
