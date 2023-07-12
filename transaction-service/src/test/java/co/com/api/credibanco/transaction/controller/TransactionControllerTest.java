package co.com.api.credibanco.transaction.controller;

import co.com.api.credibanco.web.exception.ResponseException;
import co.com.api.credibanco.domain.entity.card.CardPurchase;
import co.com.api.credibanco.domain.entity.transaction.TransacctionPurchaseVO;
import co.com.api.credibanco.domain.entity.transaction.TransactionResultVO;
import co.com.api.credibanco.domain.entity.transaction.TransactionVO;
import co.com.api.credibanco.domain.exception.rules.RuleException;
import co.com.api.credibanco.domain.service.TransactionService;
import com.netflix.appinfo.ApplicationInfoManager;
import com.netflix.appinfo.InstanceInfo;
import com.netflix.discovery.EurekaClient;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TransactionControllerTest {

    @Mock
    private HttpServletRequest request;

    @Mock
    private TransactionService service;

    @Mock
    private EurekaClient eurekaClient;

    @Mock
    private HttpSession session;


    private TransactionController transactionController;

    @BeforeEach
    void setUp() {
        transactionController = new TransactionController(request, service, eurekaClient);
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
        String instanceId = transactionController.getInstanceId();

        // Assert
        assertEquals(expectedInstanceId, instanceId);
        verify(request.getSession()).setAttribute("username", "exampleUsername");
        verify(request, times(2)).getHeader("username");
    }

    @Test
    void testPurchase() throws Exception {
        // Define the expected request body and mocked response
        TransacctionPurchaseVO requestBody = new TransacctionPurchaseVO();
        TransactionResultVO transactionResultVO = new TransactionResultVO();
        transactionResultVO.setTransactionId("id");
        transactionResultVO.setCardId("id");
        transactionResultVO.setPrice(1);
        transactionResultVO.setIsValid(true);
        CardPurchase cardPurchase = new CardPurchase();
        cardPurchase.setCardId("id");
        cardPurchase.setBalance(1);
        transactionResultVO.setCardVO(cardPurchase);
        ResponseEntity<Object> expectedResponse = ResponseEntity.ok().body(transactionResultVO);

        // Mock the service's purchase() method to return the expected response
        when(service.purchase(requestBody)).thenReturn(transactionResultVO);

        // Call the purchase() method
        ResponseEntity<Object> response = transactionController.purchase(requestBody);
        TransactionResultVO responsed = (TransactionResultVO) expectedResponse.getBody();

        // Verify the response
        assertEquals(expectedResponse.getStatusCode(), response.getStatusCode());
        assertEquals(expectedResponse.getBody(), response.getBody());
        assertNotNull(transactionResultVO.getCardVO());
        assertTrue(transactionResultVO.getIsValid());
        assertEquals(responsed.getPrice(), transactionResultVO.getPrice());
        assertEquals(responsed.getCardVO().getCardId(), cardPurchase.getCardId());
        assertEquals(responsed.getCardVO().getTypeOfCard(), cardPurchase.getTypeOfCard());
        assertEquals(responsed.getCardVO().getProductId(), cardPurchase.getProductId());
        assertEquals(responsed.getCardVO().getIsActivated(), cardPurchase.getIsActivated());
        assertEquals(responsed.getCardVO().getIsBlocked(), cardPurchase.getIsBlocked());
        assertEquals(responsed.getCardVO().getBalance(), cardPurchase.getBalance());

    }

    @Test
    void testPurchaseException() throws Exception {
        // Create a mock TransacctionPurchaseVO
        TransacctionPurchaseVO transacctionPurchaseVO = mock(TransacctionPurchaseVO.class);

        // Define the expected exception and response entity
        Exception exception = new RuleException("Some exception");
        ResponseEntity<Object> expectedResponse = ResponseException.exception(exception);

        // Mock the service's purchase() method to throw an exception
        when(service.purchase(transacctionPurchaseVO)).thenThrow(exception);

        // Call the purchase() method
        ResponseEntity<Object> response = transactionController.purchase(transacctionPurchaseVO);

        // Verify the response
        assertEquals(expectedResponse.getStatusCode(), response.getStatusCode());
    }

    @Test
    void testGet() throws Exception {
        // Mock necessary dependencies and create an instance of the controller
        TransactionController controller = new TransactionController(request, service, null);

        // Define the expected transactionId and mocked response
        String transactionId = UUID.randomUUID().toString();
        TransactionResultVO transactionResultVO = new TransactionResultVO();
        transactionResultVO.setTransactionId("id");
        transactionResultVO.setCardId("id");
        transactionResultVO.setPrice(1);
        transactionResultVO.setIsValid(true);
        CardPurchase cardPurchase = new CardPurchase();
        cardPurchase.setCardId("id");
        cardPurchase.setBalance(1);
        transactionResultVO.setCardVO(cardPurchase);
        ResponseEntity<Object> expectedResponse = ResponseEntity.ok().body(transactionResultVO);

        // Mock the service's obterPorId() method to return the expected response
        when(service.obterPorId(any())).thenReturn(transactionResultVO);

        // Call the get() method
        ResponseEntity<Object> response = controller.obterPorId(UUID.fromString(transactionId));
        TransactionResultVO responsed = (TransactionResultVO) expectedResponse.getBody();

        // Verify the response
        assertEquals(expectedResponse.getStatusCode(), response.getStatusCode());
        assertEquals(expectedResponse.getBody(), response.getBody());
        // Verify the response
        assertEquals(expectedResponse.getStatusCode(), response.getStatusCode());
        assertEquals(expectedResponse.getBody(), response.getBody());
        assertNotNull(transactionResultVO.getCardVO());
        assertTrue(transactionResultVO.getIsValid());
        assertEquals(responsed.getPrice(), transactionResultVO.getPrice());
    }

    @Test
    void testGetException() throws Exception {
        // Define the transactionId for testing
        String transactionId = UUID.randomUUID().toString();

        // Define the expected exception and response entity
        Exception exception = new Exception("Some exception");
        ResponseEntity<Object> expectedResponse = ResponseException.exception(exception);

        // Mock the service's obterPorId() method to throw an exception
        when(service.obterPorId(UUID.fromString(transactionId))).thenThrow(exception);

        // Call the get() method
        ResponseEntity<Object> response = transactionController.obterPorId(UUID.fromString(transactionId));

        // Verify the response
        assertEquals(expectedResponse.getStatusCode(), response.getStatusCode());
    }

    @Test
    void testAnulation() throws Exception {
        // Mock necessary dependencies and create an instance of the controller
        TransactionController controller = new TransactionController(request, service, null);

        // Define the expected request body and mocked response
        TransactionVO requestBody = new TransactionVO();
        TransactionResultVO transactionResultVO = new TransactionResultVO();
        transactionResultVO.setTransactionId("id");
        transactionResultVO.setCardId("id");
        transactionResultVO.setPrice(1);
        transactionResultVO.setIsValid(true);
        CardPurchase cardPurchase = new CardPurchase();
        cardPurchase.setCardId("id");
        cardPurchase.setBalance(1);
        transactionResultVO.setCardVO(cardPurchase);
        ResponseEntity<Object> expectedResponse = ResponseEntity.ok().body(transactionResultVO);

        // Mock the service's anulation() method to return the expected response
        when(service.anulation(requestBody)).thenReturn(transactionResultVO);

        // Call the anulation() method
        ResponseEntity<Object> response = controller.anulation(requestBody);

        // Verify the response
        assertEquals(expectedResponse.getStatusCode(), response.getStatusCode());
        assertEquals(expectedResponse.getBody(), response.getBody());
    }

    @Test
    void testAnulationException() throws Exception {
        // Create a mock TransactionVO object
        TransactionVO transactionVO = mock(TransactionVO.class);

        // Define the expected exception and response entity
        Exception exception = new RuleException("Some exception");
        ResponseEntity<Object> expectedResponse = ResponseException.exception(exception);

        // Mock the service's anulation() method to throw an exception
        when(service.anulation(transactionVO)).thenThrow(exception);

        // Call the anulation() method
        ResponseEntity<Object> response = transactionController.anulation(transactionVO);

        // Verify the response
        assertEquals(expectedResponse.getStatusCode(), response.getStatusCode());
    }

}