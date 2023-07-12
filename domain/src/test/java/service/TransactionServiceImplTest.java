package service;

import co.com.api.credibanco.domain.entity.card.Card;
import co.com.api.credibanco.domain.entity.transaction.TransacctionPurchaseVO;
import co.com.api.credibanco.domain.entity.transaction.Transaction;
import co.com.api.credibanco.domain.entity.transaction.TransactionResultVO;
import co.com.api.credibanco.domain.entity.transaction.TransactionVO;
import co.com.api.credibanco.domain.repository.CardRepository;
import co.com.api.credibanco.domain.repository.TransactionRepository;
import co.com.api.credibanco.domain.service.TransactionServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
class TransactionServiceImplTest {

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
    void testPurchase() throws Exception {
        // Define the cardId and price for testing
        String cardId = "some-card-id";
        int price = 100;

        ArgumentCaptor<Transaction> captor = ArgumentCaptor.forClass(Transaction.class);
        when(cardRepository.findById(cardId)).thenReturn(Optional.of(card));
        when(cardRepository.save(card)).thenReturn(card);

        // Create a mock Transaction object

        when(repository.save(any())).thenReturn(transaction);

        // Create a mock TransacctionPurchaseVO object
        TransacctionPurchaseVO transactionVO = new TransacctionPurchaseVO();
        transactionVO.setCardId(cardId);
        transactionVO.setPrice(price);

        // Call the purchase() method
        TransactionResultVO result = (TransactionResultVO) service.purchase(transactionVO);

        // Verify the result
        assertNotNull(result);
        verify(repository, times(1)).save(captor.capture());
        assertEquals(price, captor.getValue().getPrice());
        assertTrue(result.getIsValid());
    }

    @Test
    void testPurchaseInsufficientBalance() throws Exception {
        // Define the cardId and price for testing
        String cardId = "some-card-id";
        int price = 100;

        // Create a mock Card object
        card.setBalance(0);
        when(cardRepository.findById(cardId)).thenReturn(Optional.of(card));

        // Create a mock TransacctionPurchaseVO object
        TransacctionPurchaseVO transactionVO = new TransacctionPurchaseVO();
        transactionVO.setCardId(cardId);
        transactionVO.setPrice(price);

        // Call the purchase() method and expect an IllegalArgumentException
        assertThrows(IllegalArgumentException.class, () -> service.purchase(transactionVO));
    }

    @Test
    void testAnulation() throws Exception {
        // Define the cardId and transactionId for testing
        String cardId = "some-card-id";
        String transactionId = UUID.randomUUID().toString();

        // Create a mock Card object
        when(cardRepository.findById(cardId)).thenReturn(Optional.of(card));
        when(cardRepository.save(card)).thenReturn(card);

        // Create a mock Transaction object
        transaction.setTransactionDate(LocalDateTime.now().minusHours(12)); // Set transaction date within 24 hours
        when(repository.findById(UUID.fromString(transactionId))).thenReturn(Optional.of(transaction));
        when(repository.save(transaction)).thenReturn(transaction);

        // Create a mock TransactionVO object
        TransactionVO transactionVO = new TransactionVO();
        transactionVO.setCardId(cardId);
        transactionVO.setTransactionId(transactionId);

        // Call the anulation() method
        TransactionResultVO result = (TransactionResultVO) service.anulation(transactionVO);
        // Verify the result
        assertNotNull(result);
        assertFalse(result.getIsValid());
        // Add additional assertions for the remaining properties of TransactionVO, if applicable
    }


    @Test
    void testGetRepository() {
        JpaRepository<Transaction, UUID> result = service.getRepository();

        assertNotNull(result);
        assertEquals(repository, result);
    }

    @Test
    void testConvert() {
        // Create a mock Transaction object

        TransactionResultVO result = (TransactionResultVO) service.convert(transaction);

        assertNotNull(result);
        assertEquals(result.getTransactionId(), transaction.getId().toString());
    }

}
