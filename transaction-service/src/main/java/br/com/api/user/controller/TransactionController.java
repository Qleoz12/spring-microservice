package br.com.api.user.controller;

import br.com.commons.web.controller.ReadController;
import br.com.commons.web.exception.ResponseException;
import br.com.domain.entity.transaction.TransacctionPurchaseVO;
import br.com.domain.entity.transaction.Transaction;
import br.com.domain.entity.transaction.TransactionVO;
import br.com.domain.service.ReadService;
import br.com.domain.service.TransactionService;
import com.netflix.discovery.EurekaClient;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.UUID;

@RestController
@Slf4j
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@RequestMapping("/v1/transaction")
@Api("Servicio de manejo de transacciones relacionadas con la tarjeta")
public class TransactionController implements ReadController<Transaction, TransactionVO, UUID> {

    private final HttpServletRequest request;
    private final TransactionService service;
    private final EurekaClient eurekaClient;

    @Override
    public ReadService<Transaction, TransactionVO, UUID> getCrudService() {
        return service;
    }

    @GetMapping("/instanceId")
    @ApiOperation("Obtener codigo de instancia de eureka")
    public String getInstanceId() {
        final String instanceId = eurekaClient.getApplicationInfoManager().getInfo().getInstanceId();
        log.info(instanceId);
        request.getSession().setAttribute("username", request.getHeader("username"));
        log.info(request.getHeader("username"));
        return instanceId;
    }

    @PostMapping("/purchase")
    @ApiOperation("Transacción de compra")
    public ResponseEntity<Object> purchase(@RequestBody TransacctionPurchaseVO transacctionPurchaseVO) {
        try {
            return ResponseEntity.ok().body(service.purchase(transacctionPurchaseVO));
        } catch (Exception e) {
            return ResponseException.exception(e);
        }
    }

    @GetMapping("/{transactionId}")
    @ApiOperation("Transacción de compra")
    public ResponseEntity<Object> get(@PathVariable String transactionId) {
        try {
            return ResponseEntity.ok().body(service.obterPorId(UUID.fromString(transactionId)));

        } catch (Exception e) {
            return ResponseException.exception(e);
        }
    }

    @PostMapping("/anulation")
    @ApiOperation(" servicio de anulación de transacciones")
    public ResponseEntity<Object> anulation(@RequestBody TransactionVO transactionVO) {
        try {
            return ResponseEntity.ok().body(service.anulation(transactionVO));
        } catch (Exception e) {
            return ResponseException.exception(e);
        }
    }


}