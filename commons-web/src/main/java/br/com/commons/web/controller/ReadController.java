package br.com.commons.web.controller;

import br.com.commons.web.exception.ResponseException;
import br.com.domain.service.ReadService;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/*
T entity
D dto
E id

 */
public interface ReadController<T, D, E> {

    Logger log = LoggerFactory.getLogger(ReadController.class);

    ReadService<T, D, E> getCrudService();

//    @GetMapping
//    @ApiOperation("Lista todos registros")
//    default ResponseEntity<Object> listarTodos() {
//        try {
//            return ResponseEntity.ok().body(getCrudService().listarTodos());
//        } catch (Exception e) {
//            log.error("Erro ao listar todos", e);
//            return ResponseException.exception(e);
//        }
//    }

    @GetMapping("{id}")
    @ApiOperation("Obtém o registro por seu id")
    default ResponseEntity<Object> obterPorId(@PathVariable Long id) {
        try {
            return ResponseEntity.ok().body(getCrudService().obterPorId((E) id));
        } catch (Exception e) {
            log.error("Erro ao obter por id", e);
            return ResponseException.exception(e);
        }
    }


}
