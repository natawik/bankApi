package bootcamp.bankApi.server;

import bootcamp.bankApi.models.Card;
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

import java.util.List;

import static org.junit.Assert.*;

public class CardTest {

    BankServer bankServer;

    @Before
    public void setUp() throws Exception {
        bankServer = new BankServer();
        bankServer.start();
    }

    @Test
    public void cardListGetHttpStatus() throws JsonProcessingException {
        RestTemplate restTemplate = new RestTemplate();
        String resourceUrl = "http://localhost:8000/cards";
        ResponseEntity<String> response = restTemplate.getForEntity(resourceUrl, String.class);

        assertEquals(response.getStatusCode(), HttpStatus.OK);
    }

    @Test
    public void cardListGet() throws JsonProcessingException {
        RestTemplate restTemplate = new RestTemplate();
        String fooResourceUrl = "http://localhost:8000/cards";
        ResponseEntity<String> response = restTemplate.getForEntity(fooResourceUrl, String.class);

        ObjectMapper objectMapper = new ObjectMapper();
        List<Card> cards = objectMapper.readValue(response.getBody(), objectMapper.getTypeFactory().constructCollectionType(List.class, Card.class));

        assertEquals(cards.get(0).getId(), 1);
    }

    @Test
    public void cardListGetById() throws JsonProcessingException {
        RestTemplate restTemplate = new RestTemplate();
        String resourceUrl = "http://localhost:8000/cards?account=2";
        ResponseEntity<String> response = restTemplate.getForEntity(resourceUrl, String.class);

        ObjectMapper objectMapper = new ObjectMapper();
        List<Card> cards = objectMapper.readValue(response.getBody(), objectMapper.getTypeFactory().constructCollectionType(List.class, Card.class));
        assertEquals(cards.size(), 1);
    }

    @Test
    public void cardListPost() throws JsonProcessingException {
        RestTemplate restTemplate = new RestTemplate();
        String resourceUrl = "http://localhost:8000/cards";
        HttpEntity<AccountId> request = new HttpEntity<>(new AccountId(1));
        String responseBody = restTemplate.postForObject(resourceUrl, request, String.class);
        ObjectMapper objectMapper = new ObjectMapper();
        List<Card> cards = objectMapper.readValue(responseBody, objectMapper.getTypeFactory().constructCollectionType(List.class, Card.class));

        assertEquals(cards.get(0).getAccountId(), 1);
    }

    @Test
    public void addCard() throws JsonProcessingException {
        RestTemplate restTemplate = new RestTemplate();
        String resourceUrl = "http://localhost:8000/cards/add";
        HttpEntity<AccountId> request = new HttpEntity<>(new AccountId(1));
        String responseBody = restTemplate.postForObject(resourceUrl, request, String.class);
        ObjectMapper objectMapper = new ObjectMapper();
        ResponseStatus responseStatus = objectMapper.readValue(responseBody, ResponseStatus.class);

        assertEquals(responseStatus.getAdd(), "true");
    }


    @NoArgsConstructor
    @AllArgsConstructor
    @Setter
    @Getter
    static class AccountId {
        private int accountId;
    }

    @NoArgsConstructor
    @AllArgsConstructor
    @Setter
    @Getter
    static class ResponseStatus {
        private String add;
    }

}
//http://localhost:8000/cards?account=?