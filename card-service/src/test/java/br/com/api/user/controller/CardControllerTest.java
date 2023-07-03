package br.com.api.user.controller;

import br.com.domain.entity.card.BalanceCard;
import br.com.domain.entity.card.CardVO;
import br.com.domain.entity.card.CardVOid;
import br.com.domain.enums.EnumTypeCard;
import br.com.domain.service.CardService;
import com.netflix.appinfo.ApplicationInfoManager;
import com.netflix.appinfo.InstanceInfo;
import com.netflix.discovery.EurekaClient;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CardControllerTest {

    @Mock
    private HttpServletRequest request;

    @Mock
    private CardService service;

    @Mock
    private EurekaClient eurekaClient;

    @Mock
    private HttpSession session;


    private CardController cardController;

    @BeforeEach
    void setUp() {
        cardController = new CardController(request, service, eurekaClient);
    }

    @Test
    void getInstanceId_ShouldReturnInstanceId() {
        // Arrange
        String expectedInstanceId = "exampleInstanceId";
        ApplicationInfoManager mockAppInfoManager = mock(ApplicationInfoManager.class);

        InstanceInfo mockInstance = mock(InstanceInfo.class);
        when(eurekaClient.getApplicationInfoManager()).thenReturn(mockAppInfoManager);
        when(mockAppInfoManager.getInfo()).thenReturn(mockInstance);
        when(mockInstance.getInstanceId()).thenReturn(expectedInstanceId);

        when(request.getSession()).thenReturn(session);
        when(request.getHeader("username")).thenReturn("exampleUsername");
        // Act
        String instanceId = cardController.getInstanceId();

        // Assert
        assertEquals(expectedInstanceId, instanceId);
        verify(request.getSession()).setAttribute("username", "exampleUsername");
        verify(request, times(2)).getHeader("username");
    }

    @Test
    void testGenerateNumber() throws Exception {


        // Define the expected productId and card type
        String productId = "exampleProductId";
        EnumTypeCard expectedCardType = EnumTypeCard.DEBIT;

        // Mock the service's add() method to return a mocked CardVO object
        CardVO mockedCard = new CardVO();
        when(service.add(any())).thenReturn(mockedCard);

        // Call the generateNumber() method
        ResponseEntity<Object> response = cardController.generateNumber(productId);

        // Verify the response status code and body
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(mockedCard, response.getBody());
    }


    @Test
    void generateNumber_InvalidProductId_ShouldReturnErrorResponse() throws Exception {
        // Arrange
        String productId = "exampleProductId";

        when(service.add(any(CardVO.class))).thenThrow(new Exception("Invalid product"));

        // Act
        ResponseEntity<Object> response = cardController.generateNumber(productId);

        // Assert
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        verify(service).add(any(CardVO.class));
        verifyNoMoreInteractions(service);
    }

    @Test
    void enroll_ValidCardVOid_ShouldReturnOkResponse() throws Exception {
        // Arrange
        CardVOid cardVOid = new CardVOid();
        var responsed = new CardVO();
        when(service.enroll(cardVOid)).thenReturn(responsed);

        // Act
        ResponseEntity<Object> response = cardController.enroll(cardVOid);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(service).enroll(cardVOid);
    }

    @Test
    void enroll_InvalidCardVOid_ShouldReturnErrorResponse() throws Exception {
        // Arrange
        CardVOid cardVOid = new CardVOid();

        when(service.enroll(any(CardVOid.class))).thenThrow(new Exception("Invalid card"));

        // Act
        ResponseEntity<Object> response = cardController.enroll(cardVOid);

        // Assert
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        verify(service).enroll(any(CardVOid.class));
        verifyNoMoreInteractions(service);
    }

    @Test
    void rechargeBalance_ValidBalanceCard_ShouldReturnOkResponse() throws Exception {
        // Arrange
        BalanceCard balanceCard = new BalanceCard();
        var responsed = new CardVO();
        when(service.balance(balanceCard)).thenReturn(responsed);

        // Act
        ResponseEntity<Object> response = cardController.rechargeBalance(balanceCard);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(service).balance(balanceCard);
    }

    @Test
    void rechargeBalance_InvalidBalanceCard_ShouldReturnErrorResponse() throws Exception {
        // Arrange
        BalanceCard balanceCard = new BalanceCard();

        when(service.balance(any(BalanceCard.class))).thenThrow(new Exception("Invalid balance card"));

        // Act
        ResponseEntity<Object> response = cardController.rechargeBalance(balanceCard);

        // Assert
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        verify(service).balance(any(BalanceCard.class));
        verifyNoMoreInteractions(service);
    }

    @Test
    void testConsultBalance() throws Exception {


        // Define the expected cardId and mocked balance response
        String cardId = "exampleCardId";
        Integer expectedBalance = 100;
        BalanceCard responsed = BalanceCard.builder()
                .cardId(cardId)
                .balance(expectedBalance)
                .build();
        // Mock the service's balance() method to return the expected balance
        when(service.balance(cardId)).thenReturn(responsed);

        // Call the consultBalance() method
        ResponseEntity<Object> response = cardController.consultBalance(cardId);

        // Verify the response status code and body
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(responsed, response.getBody());
    }

    @Test
    void testConsultBalanceException() throws Exception {

        // Define the expected cardId and a simulated exception
        String cardId = "exampleCardId";
        Exception simulatedException = new Exception("Simulated exception");

        // Mock the service's balance() method to throw the simulated exception
        when(service.balance(cardId)).thenThrow(simulatedException);

        // Call the consultBalance() method
        ResponseEntity<Object> response = cardController.consultBalance(cardId);

        // Verify the response status code and body
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        // You may want to customize the response body depending on your exception handling logic
    }

}