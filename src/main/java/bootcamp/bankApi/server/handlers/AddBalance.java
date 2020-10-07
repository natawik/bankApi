package bootcamp.bankApi.server.handlers;

import bootcamp.bankApi.service.AccountService;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class AddBalance extends AbstractHandler {

    AccountService accountService = new AccountService();

    @Override
    public void handle(HttpExchange httpExchange) {
        try {
            process(httpExchange);
        } catch (JsonProcessingException j) {
            j.printStackTrace();
        } catch (IOException e) {

        }
    }

    private void process(HttpExchange httpExchange) throws JsonProcessingException, IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        String request = getRequest(httpExchange);

        if (!request.equals("")) {
            Changer changer = objectMapper.readValue(request, Changer.class);
            accountService.deposit(changer.getAccountId(), changer.getChangeBalance());
        }

        ObjectNode objectNode = objectMapper.createObjectNode();
        objectNode.put("add", "true");

        String response = objectMapper.writeValueAsString(objectNode);
        sendResponse(response, httpExchange);
    }

    @JsonAutoDetect
    private static class Changer {

        private int accountId;
        private float changeBalance;

        public Changer() {}

        public int getAccountId() {
            return accountId;
        }

        public float getChangeBalance() {
            return changeBalance;
        }
    }
}
