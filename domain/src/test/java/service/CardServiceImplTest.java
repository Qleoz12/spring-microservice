package service;

import co.com.api.credibanco.domain.entity.Client;
import co.com.api.credibanco.domain.entity.Product;
import co.com.api.credibanco.domain.entity.card.BalanceCard;
import co.com.api.credibanco.domain.entity.card.Card;
import co.com.api.credibanco.domain.entity.card.CardVO;
import co.com.api.credibanco.domain.entity.card.CardVOid;
import co.com.api.credibanco.domain.enums.EnumTypeCard;
import co.com.api.credibanco.domain.exception.rules.RuleException;
import co.com.api.credibanco.domain.repository.CardRepository;
import co.com.api.credibanco.domain.repository.ProductRepository;
import co.com.api.credibanco.domain.service.CardServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
class CardServiceImplTest {

    private CardServiceImpl service;

    @Mock
    private CardRepository repository;

    @Mock
    private ProductRepository productRepository;

    Card card;

    @BeforeEach
    void setUp() {
        repository = mock(CardRepository.class);
        productRepository = mock(ProductRepository.class);
        service = new CardServiceImpl(repository, productRepository);

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
    }

    @Test
    void testCustomDelete() throws RuleException {

        // Create a mock Card object
        ArgumentCaptor<Card> captor = ArgumentCaptor.forClass(Card.class);
        when(repository.findById(card.getCardId())).thenReturn(Optional.of(card));
        when(repository.save(any(Card.class))).thenReturn(card);

        // Call the customDelete() method
        CardVO result = service.customDelete(card.getCardId());

        // Verify the result
        assertNotNull(result);
        verify(repository, times(1)).save(captor.capture());
        assertEquals(result.getCardId(),card.getCardId());
        assertTrue(captor.getValue().getIsBlocked());
        assertEquals(card.getPreviusbalance(), captor.getValue().getPreviusbalance());
    }

    @Test
    void testCustomDeleteException() {
        // Define the cardId for testing
        String cardId = "some-card-id";

        // Mock the repository's findById() method to return an empty Optional
        when(repository.findById(cardId)).thenReturn(Optional.empty());

        // Call the customDelete() method and expect a RuleException
        assertThrows(RuleException.class, () -> service.customDelete(cardId));
    }

    @Test
    void testAdd() throws Exception {
        // Create a mock CardVO object
        CardVO cardVO = new CardVO();
        cardVO.setCardId("102030");
        cardVO.setTypeOfCard(EnumTypeCard.DEBIT);
        cardVO.setProductId("102030");

        // Create a mock Product object
        ArgumentCaptor<Card> captor = ArgumentCaptor.forClass(Card.class);
        Product product = new Product();
        product.setProductId("102030");
        Client client = new Client();
        client.setClientId("102030");
        client.setFullName("demo");
        product.setClient(client);
        when(productRepository.findById(any())).thenReturn(Optional.of(product));

        when(repository.save(any())).thenReturn(card);

        // Call the add() method
        CardVO result = service.add(cardVO);

        // Verify the result
        assertNotNull(result);
        verify(repository, times(1)).save(captor.capture());
        assertEquals(cardVO.getCardId(), captor.getValue().getCardId().substring(0, 6));
        assertEquals(LocalDate.now().plusYears(3), captor.getValue().getExpirationDate());
        assertFalse(result.getIsBlocked());
        assertEquals(client.getFullName(), captor.getValue().getTitularName());


    }

    @Test
    void testAddException() {
        // Create a mock CardVO object
        CardVO cardVO = mock(CardVO.class);

        // Define the productId for testing
        String productId = "some-product-id";

        // Mock the productRepository's findById() method to return an empty Optional
        when(productRepository.findById(productId)).thenReturn(Optional.empty());

        // Call the add() method and expect an Exception
        assertThrows(Exception.class, () -> service.add(cardVO));
    }

    @Test
    void testEnroll() throws Exception {
        // Define the cardId for testing
        String cardId = "some-card-id";

        // Create a mock CardVOid object
        CardVOid cardVOid = new CardVOid();
        cardVOid.setCardId(cardId);

        // Create a mock Card object
        var spycard = Mockito.spy(card);
        when(repository.findById(cardId)).thenReturn(Optional.of(spycard));
        when(repository.save(spycard)).thenReturn(card);

        // Call the enroll() method
        CardVO result = service.enroll(cardVOid);

        // Verify the result
        assertNotNull(result);
        verify(spycard, times(1)).activateCard();
        assertEquals(result.getCardId(),card.getCardId());
        assertTrue(result.getIsActivated());
    }

    @Test
    void testEnrollException() {
        // Define the cardId for testing
        String cardId = "some-card-id";

        // Create a mock CardVOid object
        CardVOid cardVOid = new CardVOid();
        cardVOid.setCardId(cardId);

        // Mock the repository's findById() method to return an empty Optional
        when(repository.findById(cardId)).thenReturn(Optional.empty());

        // Call the enroll() method and expect an Exception
        assertThrows(Exception.class, () -> service.enroll(cardVOid));
    }

    @Test
    void testBalance() throws Exception {
        // Define the cardId for testing
        String cardId = "some-card-id";

        // Create a mock BalanceCard object
        BalanceCard balanceCard = new BalanceCard();
        balanceCard.setCardId(cardId);
        balanceCard.setBalance(100);

        when(repository.findById(cardId)).thenReturn(Optional.of(card));
        when(repository.save(card)).thenReturn(card);
        Integer initialValue = card.getBalance();
        // Call the balance() method
        CardVO result = service.balance(balanceCard);

        // Verify the result
        assertNotNull(result);
        assertEquals(result.getCardId(), card.getCardId());
        assertEquals(initialValue + balanceCard.getBalance(), result.getBalance());
    }

    @Test
    void testBalanceException() {
        // Define the cardId for testing
        String cardId = "some-card-id";

        // Create a mock BalanceCard object
        BalanceCard balanceCard = new BalanceCard();
        balanceCard.setCardId(cardId);
        balanceCard.setBalance(-100);

        // Create a mock Card object
        Card card = mock(Card.class);
        when(repository.findById(cardId)).thenReturn(Optional.of(card));

        // Call the balance() method and expect an Exception
        assertThrows(IllegalArgumentException.class, () -> service.balance(balanceCard));
    }

    @Test
    void testBalanceid() throws Exception {


        // Create a mock BalanceCard object
        BalanceCard expectedBalanceCard = new BalanceCard();
        expectedBalanceCard.setCardId(card.getCardId());

        // Mock the repository's findById() method to return the Card object
        when(repository.findById(card.getCardId())).thenReturn(Optional.of(card));

        // Call the balance() method
        BalanceCard result = service.balance(card.getCardId());

        // Verify the result
        assertNotNull(result);
        assertEquals(expectedBalanceCard.getCardId(), result.getCardId());
    }

    @Test
    void testBalanceidNotFound() {
        // Define the cardId for testing
        String cardId = "some-card-id";

        // Mock the repository's findById() method to return an empty Optional
        when(repository.findById(cardId)).thenReturn(Optional.empty());

        // Call the balance() method and expect an Exception
        assertThrows(Exception.class, () -> service.balance(cardId));
    }

}
