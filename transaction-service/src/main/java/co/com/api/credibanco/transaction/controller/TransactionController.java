package co.com.api.credibanco.transaction.controller;

import co.com.api.credibanco.web.controller.ReadController;
import co.com.api.credibanco.web.exception.ResponseException;
import co.com.api.credibanco.domain.entity.transaction.TransacctionPurchaseVO;
import co.com.api.credibanco.domain.entity.transaction.Transaction;
import co.com.api.credibanco.domain.entity.transaction.TransactionVO;
import co.com.api.credibanco.domain.service.ReadService;
import co.com.api.credibanco.domain.service.TransactionService;
import com.netflix.discovery.EurekaClient;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
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
    public ResponseEntity<Object> purchase(@RequestBody @Valid TransacctionPurchaseVO transacctionPurchaseVO) {
        try {
            return ResponseEntity.ok().body(service.purchase(transacctionPurchaseVO));
        } catch (Exception e) {
            return ResponseException.exception(e);
        }
    }


    @GetMapping("/{transactionId}")
    @ApiOperation("Transacción de compra")
    @Override
    public ResponseEntity<Object> obterPorId(UUID transactionId) {
        return  ReadController.super.obterPorId(transactionId);
    }

    @PostMapping("/anulation")
    @ApiOperation(" servicio de anulación de transacciones")
    public ResponseEntity<Object> anulation(@RequestBody @Valid TransactionVO transactionVO) {
        try {
            return ResponseEntity.ok().body(service.anulation(transactionVO));
        } catch (Exception e) {
            return ResponseException.exception(e);
        }
    }


}