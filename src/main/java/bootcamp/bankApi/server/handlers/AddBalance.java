package bootcamp.bankApi.server.handlers;

import bootcamp.bankApi.service.AccountService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class AddBalance implements HttpHandler {

    AccountService accountService = new AccountService();

    @Override
    public void handle(HttpExchange httpExchange) throws IOException {

        InputStream in = httpExchange.getRequestBody();
        byte[] buffer = new byte[32 * 1024];
        int readBytes = in.read(buffer);
        String request = new String(buffer, 0, readBytes);
        System.out.println(request);
        in.close();

        if (!request.equals("")) {
            ObjectMapper objectMapper = new ObjectMapper();
            Changer changer = objectMapper.readValue(request, Changer.class);

            accountService.deposit(changer.getAccountId(), changer.changeBalance);
        }

        ObjectMapper objectMapper = new ObjectMapper();
        ObjectNode objectNode = objectMapper.createObjectNode();
        objectNode.put("add", "true");

        String response = objectMapper.writeValueAsString(objectNode);
        httpExchange.sendResponseHeaders(200, response.length());
        OutputStream out = httpExchange.getResponseBody();
        out.write(response.getBytes());
        out.close();
    }

    private static class Changer {

        private int accountId;
        private float changeBalance;

        public Changer() {
        }

        public Changer(int accountId, float changeBalance) {
            this.accountId = accountId;
            this.changeBalance = changeBalance;
        }

        public int getAccountId() {
            return accountId;
        }

        public void setAccountId(int accountId) {
            this.accountId = accountId;
        }

        public float getChangeBalance() {
            return changeBalance;
        }

        public void setChangeBalance(float changeBalance) {
            this.changeBalance = changeBalance;
        }
    }
}
