package co.com.api.credibanco.domain.service;

import co.com.api.credibanco.domain.entity.DTO;
import co.com.api.credibanco.domain.exception.errors.ErrorException;
import co.com.api.credibanco.domain.exception.rules.RuleException;
import co.com.api.credibanco.domain.repository.query.generic.GenericoQuery;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@SuppressWarnings("unchecked")
public interface CrudService<T, D,E>  extends GenericInterface{

    Logger log = LoggerFactory.getLogger(CrudService.class);

    JpaRepository<T, E> getRepository();
    void validarDados(D dto) throws RuleException;

    void customDelete(E id) throws RuleException;


    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = Exception.class)
    default T salvar(D dto) throws RuleException, ErrorException {
        try {
            validarDados(dto);
            return getRepository().save(((DTO<T>) dto).toEntidade());
        } catch (RuleException e) {
            throw e;
        } catch (Exception e) {
            throw new ErrorException(log, "Erro ao salvar a entidade " + ((DTO<T>) dto).getEntidade().getClass(), e);
        }
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = Exception.class)
    default void excluirPorId(E id) throws ErrorException {
        try {
            if (getRepository().existsById(id)) {
                customDelete(id);
            }
        } catch (Exception e) {
            throw new ErrorException(log, "Erro ao deletar a entidade", e);
        }
    }

    default List<D> listarTodos() throws ErrorException {
        try {
            return ((GenericoQuery<D,E>) getRepository()).listarTodos();
        } catch (Exception e) {
            throw new ErrorException(log, "Erro ao listar todos.", e);
        }
    }

    default D obterPorId(E id) throws ErrorException {
        try {
            return ((GenericoQuery<D,E>) getRepository()).obterPorId(id);
        } catch (Exception e) {
            throw new ErrorException(log, "Erro ao obter por id", e);
        }
    }

}
