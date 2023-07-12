package co.com.api.credibanco.transaction.controller;

import co.com.api.credibanco.web.controller.DeleteController;
import co.com.api.credibanco.web.exception.ResponseException;
import co.com.api.credibanco.domain.entity.card.BalanceCard;
import co.com.api.credibanco.domain.entity.card.Card;
import co.com.api.credibanco.domain.entity.card.CardVO;
import co.com.api.credibanco.domain.entity.card.CardVOid;
import co.com.api.credibanco.domain.enums.EnumTypeCard;
import co.com.api.credibanco.domain.service.CardService;
import co.com.api.credibanco.domain.service.DeleteService;
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


@RestController
@Slf4j
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@RequestMapping("/v1/card")
@Api("Servicio de maneo de transacciones relacionadas con la tarjeta")
public class CardController implements DeleteController<Card, CardVO, String> {

    private final HttpServletRequest request;
    private final CardService service;
    private final EurekaClient eurekaClient;
    @Override
    public DeleteService<Card, CardVO, String> getCrudService() {
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

    @GetMapping("/{productId}/number")
    @ApiOperation("Generar Tarjeta apartir de codigo de producto y tipo asignado")
    public ResponseEntity<Object> generateNumber(@PathVariable String productId) {
        try {
            CardVO cardVO = new CardVO();
            cardVO.setProductId(productId);
            cardVO.setTypeOfCard(EnumTypeCard.DEBIT);
            return ResponseEntity.ok().body(service.add(cardVO));
        } catch (Exception e) {
            return ResponseException.exception(e);
        }
    }

    @PostMapping("/enroll")
    @ApiOperation("servicio de activacion de tarjeta")
    public ResponseEntity<Object> enroll(@RequestBody @Valid CardVOid cardVO) {
        try {
            return ResponseEntity.ok().body(service.enroll(cardVO));
        } catch (Exception e) {
            return ResponseException.exception(e);
        }

    }

    @PostMapping("/balance")
    @ApiOperation("servicio de recarga de saldo")
    public ResponseEntity<Object> rechargeBalance(@RequestBody @Valid BalanceCard card) {
        try {
            return ResponseEntity.ok().body(service.balance(card));
        } catch (Exception e) {
            return ResponseException.exception(e);
        }
    }

    @GetMapping("/balance/{cardId}")
    @ApiOperation("servicio de consulta de saldo")
    public ResponseEntity<Object> consultBalance(@PathVariable String cardId) {
        try {
            return ResponseEntity.ok().body(service.balance(cardId));
        } catch (Exception e) {
            return ResponseException.exception(e);
        }
    }

}