package co.com.api.credibanco.web.controller;

import co.com.api.credibanco.domain.service.DeleteService;
import co.com.api.credibanco.web.exception.ResponseException;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;

/*
T entity
D dto
E id

 */
public interface DeleteController<T, D, E> {

    Logger log = LoggerFactory.getLogger(DeleteController.class);

    DeleteService<T, D, E> getCrudService();

    @DeleteMapping("{id}")
    @ApiOperation("borrado generico")
    default ResponseEntity<Object> delete(@PathVariable E id) {
        try {
            var response = getCrudService().delete(id);
            return ResponseEntity.ok().body(response);
        } catch (Exception e) {
            log.error("Erro ao deletar o registro", e);
            return ResponseException.exception(e);
        }
    }


}
