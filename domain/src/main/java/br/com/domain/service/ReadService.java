package br.com.domain.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.jpa.repository.JpaRepository;

@SuppressWarnings("unchecked")
public interface ReadService<T, D, E> extends GenericInterface {

    Logger log = LoggerFactory.getLogger(ReadService.class);

    JpaRepository<T, E> getRepository();

    D convert(T t);

    default D obterPorId(E id) throws Exception {
        return getRepository().findById(id)
                .map(x -> convert(x))
                .orElseThrow(() -> new Exception("Erro al obtener por id "));
    }

}
