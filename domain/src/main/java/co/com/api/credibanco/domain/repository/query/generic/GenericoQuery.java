package co.com.api.credibanco.domain.repository.query.generic;

import co.com.api.credibanco.domain.exception.errors.ErrorException;

import java.util.List;

public interface GenericoQuery<D, E> {
    D obterPorId(E id) throws ErrorException;

    List<D> listarTodos() throws ErrorException;
}
