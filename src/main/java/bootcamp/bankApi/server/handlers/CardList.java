package bootcamp.bankApi.server.handlers;

import bootcamp.bankApi.models.Card;
import bootcamp.bankApi.service.CardService;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.net.httpserver.HttpExchange;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;


/*
GET: http://localhost:8000/cards/ - список всех карт
GET: http://localhost:8000/cards?account=? - список карт по номеру счета
POST: {"accountId":1} // Список карт по номеру счета
 */
public class CardList extends AbstractHandler {

    CardService cardService = new CardService();

    @Override
    public void handle(HttpExchange httpExchange) {
        try {
            process(httpExchange);
        } catch (JsonProcessingException j) {
            j.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void process(HttpExchange httpExchange) throws JsonProcessingException, IOException {
        String method = httpExchange.getRequestMethod();
        List<Card> cards = new ArrayList<>();

        if (method.equals("GET")) {
            URI uri = httpExchange.getRequestURI();
            cards = returnCardsGet(httpExchange.getRequestURI().getQuery(), "account");
        } else if (method.equals("POST")) {
            cards = returnCardsPost(httpExchange);
        }

        ObjectMapper objectMapper = new ObjectMapper();
        String response = objectMapper.writeValueAsString(cards);
        httpExchange.getResponseHeaders().set("Content-Type", "application/xml");
        sendResponse(response, httpExchange);
    }

    private List<Card> returnCardsGet(String uri, String field) {
        String accountId = getFieldValue(uri, field);
        if (!accountId.equals("")) {
            return cardService.findCardsByAccount(Integer.valueOf(accountId));
        } else {
            return cardService.findAll();
        }
    }

    private List<Card> returnCardsPost(HttpExchange httpExchange) throws IOException {

        String request = getRequest(httpExchange);

        if (!request.equals("")) {
            ObjectMapper objectMapper = new ObjectMapper();
            AccountId accountId = objectMapper.readValue(request, AccountId.class);
            return cardService.findCardsByAccount(accountId.getAccountId());
        } else {
            return cardService.findAll();
        }
    }

    private String getFieldValue(String params, String field) {
        if (params == null) return "";

        String[] paramsArr = params.split("&");
        System.out.println(paramsArr[0]);
        for (String param : paramsArr) {
            if (param.contains(field)) {
                return param.substring(param.indexOf("=") + 1);
            }
        }
        return "";
    }

    @NoArgsConstructor
    @Getter
    @Setter
    @JsonAutoDetect
    private static class AccountId {
        private int accountId;
    }
}

