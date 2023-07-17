package service;

import co.com.api.credibanco.domain.entity.card.Card;
import co.com.api.credibanco.domain.entity.transaction.Transaction;
import co.com.api.credibanco.domain.entity.transaction.TransactionVO;
import co.com.api.credibanco.domain.repository.CardRepository;
import co.com.api.credibanco.domain.repository.TransactionRepository;
import co.com.api.credibanco.domain.service.TransactionServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ReadServiceTest {

    private TransactionServiceImpl service;

    @Mock
    private TransactionRepository repository;

    @Mock
    private CardRepository cardRepository;

    Card card;

    Transaction transaction;

    @BeforeEach
    void setUp() {
        repository = mock(TransactionRepository.class);
        cardRepository = mock(CardRepository.class);
        service = new TransactionServiceImpl(repository, cardRepository);

        card = new Card();
        card.setCardId("123456789");
        card.setProductId("102030");
        card.setTypeOfCard("Credit");
        card.setTitularName("John Doe");
        card.setExpirationDate(LocalDate.of(2023, 12, 31));
        card.setIsActivated(true);
        card.setIsBlocked(false);
        card.setBalance(500);
        card.setPreviusbalance(0);

        transaction = Transaction.builder()
                .card(card)
                .id(UUID.randomUUID())
                .isValid(true)
                .transactionDate(LocalDateTime.now())
                .price(100)
                .build();
    }

    @Test
    void obterPorId_ValidId_ReturnsConvertedObject() throws Exception {
        // Arrange
        UUID id = UUID.randomUUID();


        // Mocking behavior
        transaction.setId(id);
        when(repository.findById(id)).thenReturn(Optional.of(transaction));

        // Act
        TransactionVO result = service.obterPorId(id);

        // Assert
        assertNotNull(result);
        assertEquals(id.toString(), result.getTransactionId());
        // Add more assertions as needed
    }

//    @Test
//    void obterPorId_InvalidId_ThrowsException() {
//        // Arrange
//        Long id = 1L;
//
//        // Mocking behavior
//        when(repository.findById(id)).thenReturn(Optional.empty());
//
//        // Act & Assert
//        assertThrows(Exception.class, () -> readService.obterPorId(id));
//    }

}
