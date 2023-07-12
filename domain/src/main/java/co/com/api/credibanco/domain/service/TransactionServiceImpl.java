package co.com.api.credibanco.domain.service;

import co.com.api.credibanco.domain.converters.ConverterTransaction;
import co.com.api.credibanco.domain.entity.card.Card;
import co.com.api.credibanco.domain.entity.transaction.TransacctionPurchaseVO;
import co.com.api.credibanco.domain.entity.transaction.Transaction;
import co.com.api.credibanco.domain.entity.transaction.TransactionVO;
import co.com.api.credibanco.domain.exception.rules.RuleException;
import co.com.api.credibanco.domain.repository.CardRepository;
import co.com.api.credibanco.domain.repository.TransactionRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class TransactionServiceImpl implements TransactionService {

    private final TransactionRepository repository;
    private final CardRepository cardrepository;

    @Override
    public JpaRepository<Transaction, UUID> getRepository() {
        return repository;
    }

    @Override
    public TransactionVO convert(Transaction transaction) {
        return ConverterTransaction.convertTransactionToTransactionData(transaction);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public <T extends TransactionVO> TransactionVO purchase(TransacctionPurchaseVO transactionVO) throws RuleException {
        Card card = cardrepository.findById(transactionVO.getCardId()).orElseThrow(() -> new RuleException("No se encontró la tarjeta de la compra"));
        if (card.getBalance() < transactionVO.getPrice()) {
            throw new IllegalArgumentException("No cuenta con el saldo suficiente para realizar la compra");
        }
        card.setPreviusbalance(card.getBalance());
        card.setBalance(card.getBalance() - transactionVO.getPrice());

        cardrepository.save(card);
        Transaction transaction = Transaction.builder()
                .transactionDate(LocalDateTime.now())
                .card(card)
                .isValid(true)
                .price(transactionVO.getPrice())
                .build();
        transaction = repository.save(transaction);

        return ConverterTransaction.convertTransactionToTransactionData(transaction);
    }

    @Override
    public TransactionVO anulation(TransactionVO transactionVO) throws RuleException {
        Card card = cardrepository.findById(transactionVO.getCardId()).orElseThrow(() -> new RuleException("No se encontró la tarjeta de la compra"));
        Transaction transaction = repository.findById(UUID.fromString(transactionVO.getTransactionId()))
                .orElseThrow(() -> new RuleException("No se encontró la compra"));
        var diff = ChronoUnit.HOURS.between(transaction.getTransactionDate(), LocalDateTime.now());
        if (diff < 24) {
            transaction.invalidTransaction();
            card.setBalance(card.getBalance() + transaction.getPrice());
            transaction.setCard(card);
            cardrepository.save(card);
            transaction = repository.save(transaction);
        }
        return ConverterTransaction.convertTransactionToTransactionData(transaction);

    }

}
