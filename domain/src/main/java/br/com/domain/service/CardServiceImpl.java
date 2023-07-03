package br.com.domain.service;

import br.com.domain.converters.ConverterCard;
import br.com.domain.entity.Product;
import br.com.domain.entity.card.BalanceCard;
import br.com.domain.entity.card.Card;
import br.com.domain.entity.card.CardVO;
import br.com.domain.entity.card.CardVOid;
import br.com.domain.exception.rules.RuleException;
import br.com.domain.repository.CardRepository;
import br.com.domain.repository.ProductRepository;
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
        }).orElseThrow(() -> new RuleException("No fue posible encontrar la tarjeta"));
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public CardVO add(CardVO card) throws Exception {
        Product product = productRepository.findById(card.getProductId())
                .orElseThrow(() -> new Exception("Producto no encontrado"));
        if(product.getCardId() !=null)
        {
            return repository.findById(product.getCardId()).map(ConverterCard::convertCardToVO)
                    .orElseThrow(() -> new Exception("Problemas con la  tarjeta"));
        }

        String fullName = product.getClient().getFullName();
        String cardNumber = GeneratorCardNumbers.generate(String.valueOf(card.getProductId()));
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
    public CardVO enroll(CardVOid cardVOid) throws Exception {
        return repository.findById(cardVOid.getCardId()).map(card -> {
            card.activateCard();
            Card newCard = repository.save(card);
            return ConverterCard.convertCardToVO(newCard);
        }).orElseThrow(() -> new Exception("No fue posible encontrar la tarjeta"));
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public CardVO balance(BalanceCard balanceCard) throws Exception {
        return repository.findById(balanceCard.getCardId()).map(card -> {
            if (balanceCard.getBalance() > 0 && card.getExpirationDate().isAfter(LocalDate.now())) {
                card.setBalance(card.getBalance() + balanceCard.getBalance());
                Card cardBalanced = repository.save(card);
                return ConverterCard.convertCardToVO(cardBalanced);
            } else {
                throw new IllegalArgumentException(
                        "No es posible realizar una recarga negativa o nula, o está vencida la tarjeta");
            }
        }).orElseThrow(() -> new Exception("No fue posible encontrar la tarjeta"));
    }

    @Override
    public BalanceCard balance(String id) throws Exception {
        return repository.findById(id).map(ConverterCard::convertCardToBalanceCard)
                .orElseThrow(() -> new Exception("No fue posible encontrar la tarjeta"));
    }


}
