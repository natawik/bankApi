package bootcamp.bankApi.server.handlers;

import bootcamp.bankApi.models.Account;
import bootcamp.bankApi.service.AccountService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class Balance implements HttpHandler {

    AccountService accountService = new AccountService();

    @Override
    public void handle(HttpExchange httpExchange) throws IOException {
        String method = httpExchange.getRequestMethod();
        Account account = new Account();

        if (method.equals("GET")) {
            account = returnAccountGet(httpExchange.getRequestURI().getQuery());
        } else if (method.equals("POST")) {
            account = returnAccountPost(httpExchange.getRequestBody());
        }

        sendResponse(account, httpExchange);
    }

    private void sendResponse(Account account, HttpExchange httpExchange) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        ObjectNode objectNode = objectMapper.createObjectNode();
        objectNode.put("id", account.getId());
        objectNode.put("balance", account.getBalance());
        String response = objectMapper.writeValueAsString(objectNode);
        httpExchange.sendResponseHeaders(200, response.length());
        OutputStream out = httpExchange.getResponseBody();
        out.write(response.getBytes());
        out.close();
    }

    private Account returnAccountGet(String uri) {
        String accountId = getAccountNumber(uri);
        if (!accountId.equals("")) {
            return accountService.findAccountById(Integer.valueOf(accountId));

        } else {
            return new Account();
        }
    }

    private Account returnAccountPost(InputStream in) throws IOException {
        byte[] buffer = new byte[32 * 1024];
        int readBytes = in.read(buffer);

        String request = new String(buffer, 0, readBytes);
        in.close();

        if (!request.equals("")) {
            ObjectMapper objectMapper = new ObjectMapper();
            AccountBalance accountBalance = objectMapper.readValue(request, AccountBalance.class);
            return accountService.findAccountById(accountBalance.getAccountId());
        }

        return new Account();
    }

    private String getAccountNumber(String params) {
        if (params == null) return "";

        String[] paramsArr = params.split("&");
        for (String param : paramsArr) {
            if (param.contains("account")) {
                return param.substring(param.indexOf("=") + 1);
            }
        }
        return "";
    }

    private static class AccountBalance {
        private int accountId;

        public AccountBalance() {
        }

        public AccountBalance(int accountId) {
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
