package co.com.api.credibanco.domain.service;

import co.com.api.credibanco.domain.enums.EnumErrors;
import co.com.api.credibanco.domain.exception.errors.ErrorException;
import co.com.api.credibanco.domain.exception.rules.RuleException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@SuppressWarnings("unchecked")
public interface DeleteService<T, D, E> extends GenericInterface {

    Logger log = LoggerFactory.getLogger(DeleteService.class);

    JpaRepository<T, E> getRepository();

    D customDelete(E id) throws RuleException;

    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = Exception.class)
    default D delete(E id) throws ErrorException {
        try {
            if (getRepository().existsById(id)) {
                return customDelete(id);
            }
        } catch (Exception e) {
            throw new ErrorException(log, EnumErrors.DELETE.getCodigo(), e);
        }
        throw new ErrorException(log, EnumErrors.DELETE.getCodigo(), null);
    }


}
