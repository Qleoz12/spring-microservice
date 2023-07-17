package co.com.api.credibanco.domain.service;

import co.com.api.credibanco.domain.converters.ConverterCard;
import co.com.api.credibanco.domain.entity.Product;
import co.com.api.credibanco.domain.entity.card.BalanceCard;
import co.com.api.credibanco.domain.entity.card.Card;
import co.com.api.credibanco.domain.entity.card.CardVO;
import co.com.api.credibanco.domain.entity.card.CardVOid;
import co.com.api.credibanco.domain.enums.EnumErrors;
import co.com.api.credibanco.domain.enums.EnumNumerics;
import co.com.api.credibanco.domain.exception.rules.RuleException;
import co.com.api.credibanco.domain.repository.CardRepository;
import co.com.api.credibanco.domain.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

@Service
@Slf4j
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class CardServiceImpl implements CardService {

    private final CardRepository repository;
    private final ProductRepository productRepository;

    @Override
    public JpaRepository<Card, String> getRepository() {
        return repository;
    }


    @Override
    public CardVO customDelete(String id) throws RuleException {
        return repository.findById(id).map(card -> {
            card.blockCard();
            Card newCard = repository.save(card);
            return ConverterCard.convertCardToVO(newCard);
        }).orElseThrow(() -> new RuleException(EnumErrors.CARDNOTFOUND.getCodigo()));
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public CardVO add(CardVO card) throws RuleException {
        Product product = productRepository.findById(card.getProductId())
                .orElseThrow(() -> new RuleException("Producto no encontrado"));
        if (product.getCardId() != null) {
            return repository.findById(product.getCardId()).map(ConverterCard::convertCardToVO)
                    .orElseThrow(() -> new RuleException("Problemas con la  tarjeta"));
        }

        String fullName = product.getClient().getFullName();

        //logic to avoid retry generate this number
        Long totalcards = repository.count();
        if (EnumNumerics.MIDID.getCodigo() <= totalcards) //magic number
            log.warn("posible dificultad al generar numeros por la cantidad copada " + totalcards);
        String cardNumber = GeneratorCardNumbers.generate(String.valueOf(card.getProductId()));
        //avoid re calls the endpoint
        int retrys=0;
        while (repository.findById(totalcards.toString()).isPresent() || retrys<10) {
            GeneratorCardNumbers.generate(String.valueOf(card.getProductId()));
            retrys++;
        }

        LocalDate expirationDate = LocalDate.now().plusYears(3);

        Card newCard = repository.save(Card.builder()
                .cardId(cardNumber)
                .titularName(fullName)
                .balance(0)
                .productId(product.getProductId())
                .typeOfCard(card.getTypeOfCard().getCodigo())
                .expirationDate(expirationDate)
                .previusbalance(0)
                .isActivated(false)
                .isBlocked(false)
                .build());
        product.setCardId(newCard.getCardId());
        productRepository.save(product);
        return ConverterCard.convertCardToVO(newCard);

    }

    @Override
    public CardVO enroll(CardVOid cardVOid) throws RuleException {
        return repository.findById(cardVOid.getCardId()).map(card -> {
            card.activateCard();
            Card newCard = repository.save(card);
            return ConverterCard.convertCardToVO(newCard);
        }).orElseThrow(() -> new RuleException(EnumErrors.CARDNOTFOUND.getCodigo()));
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public CardVO balance(BalanceCard balanceCard) throws RuleException {
        return repository.findById(balanceCard.getCardId()).map(card -> {
            if (balanceCard.getBalance() > 0 && card.getExpirationDate().isAfter(LocalDate.now())) {
                card.setBalance(card.getBalance() + balanceCard.getBalance());
                Card cardBalanced = repository.save(card);
                return ConverterCard.convertCardToVO(cardBalanced);
            } else {
                throw new IllegalArgumentException(
                        "No es posible realizar una recarga negativa o nula, o está vencida la tarjeta");
            }
        }).orElseThrow(() -> new RuleException(EnumErrors.CARDNOTFOUND.getCodigo()));
    }

    @Override
    public BalanceCard balance(String id) throws RuleException {
        return repository.findById(id).map(ConverterCard::convertCardToBalanceCard)
                .orElseThrow(() -> new RuleException(EnumErrors.CARDNOTFOUND.getCodigo()));
    }


}
