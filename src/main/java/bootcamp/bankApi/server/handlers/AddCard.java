package bootcamp.bankApi.server.handlers;

import bootcamp.bankApi.service.CardService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class AddCard implements HttpHandler {
    @Override
    public void handle(HttpExchange httpExchange) throws IOException {

        CardService cardService = new CardService();

        InputStream in = httpExchange.getRequestBody();
        byte[] buffer = new byte[32 * 1024];
        int readBytes = in.read(buffer);
        String request = new String(buffer, 0, readBytes);
        System.out.println(request);
        in.close();

        if (!request.equals("")) {
            ObjectMapper objectMapper = new ObjectMapper();
            CardAccount cardAccount = objectMapper.readValue(request, CardAccount.class);
            System.out.println(cardAccount.accountId);
            cardService.addNewCard(cardAccount.accountId);
        }

        String response = "";
        httpExchange.sendResponseHeaders(200, response.length());
        OutputStream out = httpExchange.getResponseBody();
        out.write(response.getBytes());
        out.close();
    }

    private static class CardAccount {
        private int accountId;

        public CardAccount() {
        }

        public CardAccount(int accountId) {
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
