package co.com.api.credibanco.domain.service;

import co.com.api.credibanco.domain.exception.errors.ErrorException;
import co.com.api.credibanco.domain.repository.query.generic.GenericoQuery;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

@SuppressWarnings("unchecked")
public interface ReadService<T, D, E> extends GenericInterface {

    Logger log = LoggerFactory.getLogger(ReadService.class);

    JpaRepository<T, E> getRepository();

    D convert(T t);
    default List<D> listarTodos() throws ErrorException {
        try {
            return ((GenericoQuery<D,E>) getRepository()).listarTodos();
        } catch (Exception e) {
            throw new ErrorException(log, "Erro ao listar todos.", e);
        }
    }

    default D obterPorId(E id) throws Exception {
        return getRepository().findById(id)
                .map(this::convert)
                .orElseThrow(() -> new Exception("Erro al obtener por id "));
    }

}
