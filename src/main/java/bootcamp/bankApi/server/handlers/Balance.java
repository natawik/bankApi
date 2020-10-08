package bootcamp.bankApi.server.handlers;

import bootcamp.bankApi.models.Account;
import bootcamp.bankApi.service.AccountService;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/*
GET: http://localhost:8000/balance?account=1 - баланс по счету
POST: {"accountId":1} - баланс по счету
 */
public class Balance extends AbstractHandler {

    AccountService accountService = new AccountService();

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
        Account account = new Account();

        if (method.equals("GET")) {
            account = returnAccountGet(httpExchange.getRequestURI().getQuery(), "account");
        } else if (method.equals("POST")) {
            account = returnAccountPost(httpExchange);
        }

        sendResponse(account, httpExchange);
    }

    private void sendResponse(Account account, HttpExchange httpExchange) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        ObjectNode objectNode = objectMapper.createObjectNode();
        objectNode.put("id", account.getId());
        objectNode.put("balance", account.getBalance());
        String response = objectMapper.writeValueAsString(objectNode);
        sendResponse(response, httpExchange);
    }

    private Account returnAccountGet(String uri, String field) {
        String accountId = getAccountNumber(uri, field);
        if (!accountId.equals("")) {
            return accountService.findById(Integer.valueOf(accountId));
        } else {
            return new Account();
        }
    }

    private String getAccountNumber(String params, String field) {
        if (params == null) return "";

        String[] paramsArr = params.split("&");
        for (String param : paramsArr) {
            if (param.contains(field)) {
                return param.substring(param.indexOf("=") + 1);
            }
        }
        return "";
    }

    private Account returnAccountPost(HttpExchange httpExchange) {
        String request = getRequest(httpExchange);

        if (!request.equals("")) {
            ObjectMapper objectMapper = new ObjectMapper();
            AccountBalance accountBalance;
            try {
                accountBalance = objectMapper.readValue(request, AccountBalance.class);
                return accountService.findById(accountBalance.getAccountId());
            } catch (JsonProcessingException e) {
                return new Account();
            }
        }

        return new Account();
    }

    @NoArgsConstructor
    @Getter
    @Setter
    @JsonAutoDetect
    private static class AccountBalance {
        private int accountId;
    }
}
