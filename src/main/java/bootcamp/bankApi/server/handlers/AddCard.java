package bootcamp.bankApi.server.handlers;

import bootcamp.bankApi.service.CardService;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.net.httpserver.HttpExchange;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.IOException;

/*
http://localhost:8000/cards/add
POST {"accountId":1} - Выпустить новую карту для указанного счета
 */
public class AddCard extends AbstractHandler {

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
        CardService cardService = new CardService();
        String request = getRequest(httpExchange);

        ObjectMapper objectMapper = new ObjectMapper();
        if (!request.equals("")) {
            CardAccount cardAccount = objectMapper.readValue(request, CardAccount.class);
            cardService.addNewCard(cardAccount.getAccountId());
        }

        String response = "";
        sendResponse(response, httpExchange);
    }

    @NoArgsConstructor
    @Getter
    @Setter
    @JsonAutoDetect
    private static class CardAccount {
        private int accountId;
    }
}
