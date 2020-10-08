package bootcamp.bankApi.server.handlers;

import bootcamp.bankApi.service.AccountService;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.sun.net.httpserver.HttpExchange;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.IOException;


/*
http://localhost:8000/balance/add
POST {"accountId":1,"changeBalance":15000} - увеличить баланс на указанную сумму
 */
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

    @NoArgsConstructor
    @Getter
    @Setter
    @JsonAutoDetect
    private static class Changer {

        private int accountId;
        private float changeBalance;

    }
}
