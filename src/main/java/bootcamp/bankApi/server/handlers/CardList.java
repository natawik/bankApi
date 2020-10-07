package bootcamp.bankApi.server.handlers;

import bootcamp.bankApi.models.Card;
import bootcamp.bankApi.service.CardService;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

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
            cards = returnCardsGet(httpExchange.getRequestURI().getQuery(), "account");
        } else if (method.equals("POST")) {
            cards = returnCardsPost(httpExchange);
        }

        ObjectMapper objectMapper = new ObjectMapper();
        String response = objectMapper.writeValueAsString(cards);
        sendResponse(response, httpExchange);
    }

    private List<Card> returnCardsGet(String uri, String field) {
        String accountId = getFieldValue(uri, field);
        if (!accountId.equals("")) {
            return cardService.findCardsByAccount(Integer.valueOf(accountId));
        } else {
            return cardService.findAllCards();
        }
    }

    private List<Card> returnCardsPost(HttpExchange httpExchange) throws IOException {

        String request = getRequest(httpExchange);

        if (!request.equals("")) {
            ObjectMapper objectMapper = new ObjectMapper();
            AccountId accountId = objectMapper.readValue(request, AccountId.class);
            return cardService.findCardsByAccount(accountId.getAccountId());
        } else {
            return cardService.findAllCards();
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

    @JsonAutoDetect
    private static class AccountId {
        private int accountId;

        public AccountId() { }

        public int getAccountId() {
            return accountId;
        }
    }
}

