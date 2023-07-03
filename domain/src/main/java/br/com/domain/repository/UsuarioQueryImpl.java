package br.com.domain.repository;

import br.com.domain.entity.user.UsuarioDTO;
import br.com.domain.repository.query.generic.GenericoQueryImpl;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;

public class UsuarioQueryImpl extends GenericoQueryImpl<UsuarioDTO,Long> implements UsuarioQuery {

    @Override
    public UsuarioDTO obterPorUsername(String username) {
        final StringBuilder sql = new StringBuilder();

        sql.append(getConsulta("u"));
        sql.append(" FROM USUARIO u ");
        sql.append(" WHERE u.USERNAME = :username ");

        final MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("username", username.trim());

        try {
            return (UsuarioDTO) namedParameterJdbcTemplate.queryForObject(sql.toString(), params, getRowMapper());
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }


}
