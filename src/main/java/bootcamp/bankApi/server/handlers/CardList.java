package bootcamp.bankApi.server.handlers;

import bootcamp.bankApi.models.Card;
import bootcamp.bankApi.service.CardService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

public class CardList implements HttpHandler {

    CardService cardService = new CardService();

    @Override
    public void handle(HttpExchange httpExchange) throws IOException {
        String method = httpExchange.getRequestMethod();

        List<Card> cards = new ArrayList<>();
        if (method.equals("GET")) {
            cards = returnCardsGet(httpExchange.getRequestURI().getQuery());
        } else if (method.equals("POST")) {
            cards = returnCardsPost(httpExchange.getRequestBody());
        }

        sendResponse(cards, httpExchange);
    }

    private void sendResponse(List<Card> cards, HttpExchange httpExchange) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        String response = objectMapper.writeValueAsString(cards);
        httpExchange.sendResponseHeaders(200, response.length());
        OutputStream out = httpExchange.getResponseBody();
        out.write(response.getBytes());
        out.close();
    }

    private List<Card> returnCardsGet(String uri) {
        String accountId = getAccountNumber(uri);
        if (!accountId.equals("")) {
            return cardService.findCardsByAccount(Integer.valueOf(accountId));
        } else {
            return cardService.findAllCards();
        }
    }

    private List<Card> returnCardsPost(InputStream in) throws IOException {
        byte[] buffer = new byte[32 * 1024];
        int readBytes = in.read(buffer);

        String request = new String(buffer, 0, readBytes);
        in.close();

        if (!request.equals("")) {
            ObjectMapper objectMapper = new ObjectMapper();
            AccountId accountId = objectMapper.readValue(request, AccountId.class);
            return cardService.findCardsByAccount(accountId.getAccountId());
        } else {
            return cardService.findAllCards();
        }
    }

    private String getAccountNumber(String params) {
        if (params == null) return "";

        String[] paramsArr = params.split("&");
        System.out.println(paramsArr[0]);
        for (String param : paramsArr) {
            if (param.contains("account")) {
                return param.substring(param.indexOf("=") + 1);
            }
        }
        return "";
    }

    private static class AccountId {
        private int accountId;

        public AccountId() {
        }

        public AccountId(int accountId) {
            this.accountId = accountId;
        }

        public int getAccountId() {
            return accountId;
        }

        public void setAccountId(int accountId) {
            this.accountId = accountId;
        }
    }
}

//{"accountId":1}
