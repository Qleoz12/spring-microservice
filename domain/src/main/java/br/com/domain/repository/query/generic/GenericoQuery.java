package br.com.domain.repository.query.generic;

import br.com.domain.exception.errors.ErrorException;

import java.util.List;

public interface GenericoQuery<D, E> {
    D obterPorId(E id) throws ErrorException;

    List<D> listarTodos() throws ErrorException;
}
