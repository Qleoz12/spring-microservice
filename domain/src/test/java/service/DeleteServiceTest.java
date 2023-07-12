package service;

import co.com.api.credibanco.domain.entity.card.Card;
import co.com.api.credibanco.domain.entity.card.CardVO;
import co.com.api.credibanco.domain.enums.EnumErrors;
import co.com.api.credibanco.domain.enums.EnumTypeCard;
import co.com.api.credibanco.domain.exception.errors.ErrorException;
import co.com.api.credibanco.domain.exception.rules.RuleException;
import co.com.api.credibanco.domain.repository.CardRepository;
import co.com.api.credibanco.domain.repository.ProductRepository;
import co.com.api.credibanco.domain.service.CardServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DeleteServiceTest {

    private CardServiceImpl service;

    @Mock
    private CardRepository repository;

    @Mock
    private ProductRepository productRepository;

    CardVO cardVO;

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

        CardVO cardVO = new CardVO();
        cardVO.setCardId("102030");
        cardVO.setTypeOfCard(EnumTypeCard.DEBIT);
        cardVO.setProductId("102030");
    }

    @Test
    void delete_ValidId_Success() throws RuleException, ErrorException {
        // Arrange
        String id = "";

        // Mocking behavior
        when(repository.findById(id)).thenReturn(Optional.ofNullable(card));
        when(repository.save(any())).thenReturn(card);
        when(repository.existsById(id)).thenReturn(true);

        // Act
        CardVO result = service.delete(id);

        // Assert
        assertNotNull(result);
        assertTrue(result.getIsBlocked());
        assertFalse(result.getIsActivated());
    }

    @Test
    void delete_InvalidId_ReturnsNull() throws RuleException, ErrorException {
        // Arrange
        String id = "";

        // Mocking behavior
        when(repository.existsById(id)).thenReturn(false);

        assertThrows(ErrorException.class, () -> service.delete(""));
    }

    @Test
    void delete_CustomDeleteThrowsException_ErrorExceptionThrown() throws RuleException {
        // Arrange
        String id = "";

        // Mocking behavior
        when(repository.existsById(id)).thenReturn(true);
        RuleException exception = new RuleException(EnumErrors.CARDNOTFOUND.getCodigo());
        var spyservice = Mockito.spy(service);
        doThrow(exception).when(spyservice).customDelete(id);

        // Act & Assert
        assertThrows(ErrorException.class, () -> spyservice.delete(id));
    }

}
