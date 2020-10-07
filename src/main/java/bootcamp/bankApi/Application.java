package bootcamp.bankApi;

import bootcamp.bankApi.server.BankServer;

public class Application {

    public static void main(String[] args) {
        new BankServer().start();
    }
}



/*
http://localhost:8000/cards/ - {"accountId":1} // Список карт по номеру счета
http://localhost:8000/cards/add - {"accountId":1} // Добавить карту
http://localhost:8000/cards?account=1 // Список карт по номеру счета (GET)
http://localhost:8000/cards // Список карт
http://localhost:8000/balance?account=1 // баланс по счету

 */
