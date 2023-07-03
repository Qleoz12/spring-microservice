package br.com.domain.service;

import br.com.domain.converters.ConverterTransaction;
import br.com.domain.entity.card.Card;
import br.com.domain.entity.transaction.TransacctionPurchaseVO;
import br.com.domain.entity.transaction.Transaction;
import br.com.domain.entity.transaction.TransactionVO;
import br.com.domain.repository.CardRepository;
import br.com.domain.repository.TransactionRepository;
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
    public <T extends TransactionVO> TransactionVO purchase(TransacctionPurchaseVO transactionVO) throws Exception {
        Card card = cardrepository.findById(transactionVO.getCardId()).orElseThrow(() -> new Exception("No se encontró la tarjeta de la compra"));
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
    public TransactionVO anulation(TransactionVO transactionVO) throws Exception {
        Card card = cardrepository.findById(transactionVO.getCardId()).orElseThrow(() -> new Exception("No se encontró la tarjeta de la compra"));
        Transaction transaction = repository.findById(UUID.fromString(transactionVO.getTransactionId()))
                .orElseThrow(() -> new Exception("No se encontró la compra"));
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
