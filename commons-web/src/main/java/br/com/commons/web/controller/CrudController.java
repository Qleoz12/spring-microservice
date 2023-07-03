//package br.com.commons.web.controller;

//import br.com.commons.web.exception.ResponseException;
//import br.com.domain.entity.Entidade;
//import io.swagger.annotations.ApiOperation;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;

//@RestController
//public interface CrudController<T, D, E> {
//
//    Logger log = LoggerFactory.getLogger(CrudService.class);
//    CrudService<T, D, E> getCrudService();
//
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
//
//    @GetMapping("{id}")
//    @ApiOperation("Obtém o registro por seu id")
//    default ResponseEntity<Object> obterPorId(@PathVariable Long id) {
//        try {
//            return ResponseEntity.ok().body(getCrudService().obterPorId((E) id));
//        } catch (Exception e) {
//            log.error("Erro ao obter por id", e);
//            return ResponseException.exception(e);
//        }
//    }
//
//    @PostMapping
//    @ApiOperation("Cria um novo registro")
//    default ResponseEntity<Object> criar(@RequestBody D dto) {
//        try {
//            final Entidade entidade = (Entidade) getCrudService().salvar(dto);
//            return ResponseEntity.ok().body(getCrudService().obterPorId((E) entidade.getId()));
//        } catch (Exception e) {
//            log.error("Erro ao criar o registro", e);
//            return ResponseException.exception(e);
//        }
//    }
//
//    @PutMapping
//    @ApiOperation("Atualiza os dados do registro")
//    default ResponseEntity<Object> atualizar(@RequestBody D dto) {
//        try {
//            final Entidade entidade = (Entidade) getCrudService().salvar(dto);
//            return ResponseEntity.ok().body(getCrudService().obterPorId((E) entidade.getId()));
//        } catch (Exception e) {
//            log.error("Erro ao atualizar o registro", e);
//            return ResponseException.exception(e);
//        }
//    }
//
//    @DeleteMapping("{id}")
//    @ApiOperation("Deleta o registro")
//    default ResponseEntity<Object> deletar(@PathVariable Long id) {
//        try {
//            getCrudService().excluirPorId((E) id);
//            return ResponseEntity.ok().build();
//        } catch (Exception e) {
//            log.error("Erro ao deletar o registro", e);
//            return ResponseException.exception(e);
//        }
//    }
//
//}
