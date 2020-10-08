package bootcamp.bankApi;

import bootcamp.bankApi.server.BankServer;

public class Application {

    public static void main(String[] args) {
        new BankServer().start();
    }
}

