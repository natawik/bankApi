package bootcamp.bankApi.server;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.junit.Before;
import org.junit.Test;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class BalanceTest {

    BankServer bankServer;

    @Before
    public void setUp() throws Exception {
        bankServer = new BankServer();
        bankServer.start();
    }

    @Test
    public void cardBalanceHttpStatus() throws JsonProcessingException {
        RestTemplate restTemplate = new RestTemplate();
        String resourceUrl = "http://localhost:8000/balance?account=1";
        ResponseEntity<String> response = restTemplate.getForEntity(resourceUrl, String.class);

        assertEquals(response.getStatusCode(), HttpStatus.OK);
    }

    @Test
    public void balanceGet() throws JsonProcessingException {
        RestTemplate restTemplate = new RestTemplate();
        String resourceUrl = "http://localhost:8000/balance?account=1";
        ResponseEntity<String> response = restTemplate.getForEntity(resourceUrl, String.class);

        ObjectMapper objectMapper = new ObjectMapper();
        Balance balance = objectMapper.readValue(response.getBody(), Balance.class);

        assertTrue(balance.getBalance() > 0);
    }

    @Test
    public void balanceAdd() throws JsonProcessingException {
        RestTemplate restTemplate = new RestTemplate();
        String resourceUrl = "http://localhost:8000/balance/add";
        HttpEntity<Changer> request = new HttpEntity<>(new Changer(1, 5000f));
        String responseBody = restTemplate.postForObject(resourceUrl, request, String.class);
        ObjectMapper objectMapper = new ObjectMapper();
        ResponseStatus responseStatus = objectMapper.readValue(responseBody, ResponseStatus.class);

        assertEquals(responseStatus.getAdd(), "true");
    }


    @NoArgsConstructor
    @AllArgsConstructor
    @Setter
    @Getter
    static class Balance {
        private int id;
        private float balance;
    }

    @NoArgsConstructor
    @AllArgsConstructor
    @Setter
    @Getter
    static class ResponseStatus {
        private String add;
    }

    @NoArgsConstructor
    @AllArgsConstructor
    @Getter
    @Setter
    private static class Changer {

        private int accountId;
        private float changeBalance;

    }

}