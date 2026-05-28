//Bruna Romero 11913896
//Daniela Costa da Silva 14613625

import java.util.Stack;

public class Game {

    private Room currentRoom;
    private Stack<Room> roomHistory = new Stack<>();
    private BankAccount bankAccount; // NOVO
    private MagicShop magicShop; // NOVO
    private Room shopRoom; // Sala onde fica a loja

    public Game() {
        // Criando as salas
        Room corridor = new Room("in a dark stone corridor", new Item("rusty torch"));
        Room armory = new Room("in an old castle armory", new Item("steel sword"));
        Room treasureRoom = new Room("inside the royal vault", new Item("bag of gold coins"));
        Room shop = new Room("in Zuil's Magic Shop", null); // NOVA sala da loja

        // Configurando saídas
        corridor.setExit("east", armory);
        corridor.setExit("south", treasureRoom);
        corridor.setExit("west", shop); // Saída para a loja
        armory.setExit("west", corridor);
        treasureRoom.setExit("north", corridor);
        shop.setExit("east", corridor);

        currentRoom = corridor;
        shopRoom = shop; // Guarda referência da loja

        // Inicializando banco e loja
        bankAccount = new BankAccount();
        magicShop = new MagicShop();
    }

    public void processCommand(String inputWord, String secondWord) {
        CommandWord command;
        try {
            command = CommandWord.valueOf(inputWord.toUpperCase());
        } catch (IllegalArgumentException e) {
            command = CommandWord.UNKNOWN;
        }

        switch (command) {
            case GO ->
                goRoom(secondWord);
            case LOOK ->
                System.out.println("\n" + currentRoom.getLongDescription());
            case BACK ->
                back();
            case SHOW ->
                showStatus(); // NOVO
            case LIST ->
                handleList(); // NOVO
            case BUY ->
                handleBuy(secondWord); // NOVO
            case SELL ->
                handleSell(secondWord); // NOVO
            case DEPOSIT ->
                handleDeposit(secondWord); // NOVO
            case WITHDRAW ->
                handleWithdraw(secondWord); // NOVO
            case QUIT ->
                System.out.println("Thank you for playing. Bye!");
            case UNKNOWN ->
                System.out.println("I don't know what you mean...");
        }
    }

    private void goRoom(String direction) {
        if (direction == null) {
            System.out.println("Go where? (ex: go east)");
            return;
        }

        Room nextRoom = currentRoom.getExit(direction);
        if (nextRoom == null) {
            System.out.println("There is no passage that way!");
            return;
        }

        roomHistory.push(currentRoom);
        currentRoom = nextRoom;
        System.out.println("\n" + currentRoom.getLongDescription());

        // BÔNUS: Recompensa por explorar novas salas (opcional)
        // bankAccount.deposit(10.0);
    }

    private void back() {
        if (roomHistory.isEmpty()) {
            System.out.println("You are at the starting point. You cannot go back any further!");
            return;
        }
        currentRoom = roomHistory.pop();
        System.out.println("\nYou retreated.\n" + currentRoom.getLongDescription());
    }

    // NOVOS MÉTODOS
    /**
     * Exibe o status atual do jogador: saldo bancário e inventário.
     *
     * @pre Nenhum pré-requisito.
     *
     * @post Nenhuma alteração no estado do jogo. Apenas saída no console
     * mostrando informações atuais.
     */
    private void showStatus() {
        System.out.printf("\n=== Player Status ===\n");
        System.out.printf("Balance: R$ %.2f\n", bankAccount.getBalance());
        magicShop.showInventory();
    }

    /**
     * Exibe a lista de itens disponíveis na loja mágica. O comando só funciona
     * se o jogador estiver na sala da loja.
     *
     * @pre Nenhum pré-requisito.
     *
     * @post Se o jogador estiver na loja: lista de itens é exibida. Se não
     * estiver na loja: mensagem de erro é exibida. Nenhuma alteração no estado
     * do jogo.
     */
    private void handleList() {
        if (currentRoom == shopRoom) {
            magicShop.listItems();
        } else {
            System.out.println("You are not in a shop! Find Zuil's Magic Shop to buy items.");
            System.out.println("Hint: Try going west from the corridor.");
        }
    }

    /**
     * Processa o comando de compra de item.
     *
     * @param itemName Nome do item a ser comprado. Pode ser null.
     *
     * @pre itemName pode ser null (tratado no método) currentRoom deve ser a
     * sala da loja para comprar
     *
     * @post Se a compra for bem-sucedida: item adicionado ao inventário, saldo
     * do jogador reduzido. Se falhar: nenhuma alteração de estado. Mensagens
     * apropriadas são exibidas.
     */
    private void handleBuy(String itemName) {
        if (itemName == null) {
            System.out.println("Buy what? Use: buy <item name>");
            return;
        }

        if (currentRoom != shopRoom) {
            System.out.println("You can only buy items when you're in Zuil's Magic Shop!");
            return;
        }

        magicShop.buyItem(itemName, bankAccount);
    }

    /**
     * Processa o comando de venda de item.
     *
     * @param itemName Nome do item a ser vendido. Pode ser null.
     *
     * @pre itemName pode ser null (tratado no método) currentRoom deve ser a
     * sala da loja para vender O jogador deve possuir o item no inventário
     *
     * @post Se a venda for bem-sucedida: item removido do inventário, saldo do
     * jogador aumentado (50% do valor). Se falhar: nenhuma alteração de estado.
     * Mensagens apropriadas são exibidas.
     */
    private void handleSell(String itemName) {
        if (itemName == null) {
            System.out.println("Sell what? Use: sell <item name>");
            return;
        }

        if (currentRoom != shopRoom) {
            System.out.println("You can only sell items when you're in Zuil's Magic Shop!");
            return;
        }

        magicShop.sellItem(itemName, bankAccount);
    }

    /**
     * Processa o comando de depósito na conta bancária.
     *
     * @param amountStr String representando o valor a depositar.
     *
     * @pre amountStr deve ser um número válido convertível para double.
     * amountStr pode ser null (tratado no método)
     *
     * @post Se o depósito for bem-sucedido: saldo do jogador aumentado. Se
     * falhar (valor inválido): nenhuma alteração. Mensagens apropriadas são
     * exibidas.
     */
    private void handleDeposit(String amountStr) {
        if (amountStr == null) {
            System.out.println("Deposit how much? Use: deposit <amount>");
            return;
        }

        try {
            double amount = Double.parseDouble(amountStr);
            bankAccount.deposit(amount);
        } catch (NumberFormatException e) {
            System.out.println("Invalid amount! Use numbers (ex: deposit 100)");
        }
    }

    /**
     * Processa o comando de saque da conta bancária.
     *
     * @param amountStr String representando o valor a sacar.
     *
     * @pre amountStr deve ser um número válido convertível para double.
     * amountStr pode ser null (tratado no método) O valor do saque não pode
     * exceder o saldo atual.
     *
     * @post Se o saque for bem-sucedido: saldo do jogador reduzido. Se falhar
     * (valor inválido ou saldo insuficiente): nenhuma alteração. Mensagens
     * apropriadas são exibidas. O saldo nunca fica negativo (garantido pelo
     * BankAccount).
     */
    private void handleWithdraw(String amountStr) {
        if (amountStr == null) {
            System.out.println("Withdraw how much? Use: withdraw <amount>");
            return;
        }

        try {
            double amount = Double.parseDouble(amountStr);
            bankAccount.withdraw(amount);
        } catch (NumberFormatException e) {
            System.out.println("Invalid amount! Use numbers (ex: withdraw 50)");
        }
    }

    public Room getCurrentRoom() {
        return currentRoom;
    }
}
