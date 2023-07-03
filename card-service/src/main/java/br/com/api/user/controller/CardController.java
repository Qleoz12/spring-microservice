package br.com.api.user.controller;

import br.com.commons.web.controller.DeleteController;
import br.com.commons.web.exception.ResponseException;
import br.com.domain.entity.card.BalanceCard;
import br.com.domain.entity.card.Card;
import br.com.domain.entity.card.CardVO;
import br.com.domain.entity.card.CardVOid;
import br.com.domain.enums.EnumTypeCard;
import br.com.domain.exception.errors.ErrorException;
import br.com.domain.service.CardService;
import br.com.domain.service.DeleteService;
import com.netflix.discovery.EurekaClient;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

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
        } catch (ErrorException e) {
            return ResponseException.exception(e);
        } catch (Exception e) {
            return ResponseException.exception(e);
        }
    }

    @PostMapping("/enroll")
    @ApiOperation("servicio de activacion de tarjeta")
    public ResponseEntity<Object> enroll(@RequestBody CardVOid cardVO) {
        try {
            return ResponseEntity.ok().body(service.enroll(cardVO));
        } catch (Exception e) {
            return ResponseException.exception(e);
        }

    }

    @PostMapping("/balance")
    @ApiOperation("servicio de recarga de saldo")
    public ResponseEntity<Object> rechargeBalance(@RequestBody BalanceCard card) {
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