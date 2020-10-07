package bootcamp.bankApi.server;

import bootcamp.bankApi.server.handlers.AddBalance;
import bootcamp.bankApi.server.handlers.AddCard;
import bootcamp.bankApi.server.handlers.Balance;
import bootcamp.bankApi.server.handlers.CardList;
import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.concurrent.Executor;

public class BankServer {

    private static final int PORT = 8000;

    public void start() {
        System.out.println("[starting..]");
        try {
            HttpServer httpServer = HttpServer.create(new InetSocketAddress(PORT), 0);
            httpServer.createContext("/cards", new CardList());
            httpServer.createContext("/cards/add", new AddCard());
            httpServer.createContext("/balance", new Balance());
            httpServer.createContext("/balance/add", new AddBalance());
            httpServer.setExecutor(null);
            httpServer.start();
        } catch (IOException e) {
            System.out.println("[ERROR] " + e);
        }
        System.out.println("[started]");
    }

    static class MyExecutor implements Executor {

        @Override
        public void execute(Runnable command) {

        }
    }
}
