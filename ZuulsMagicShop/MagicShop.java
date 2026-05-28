//Bruna Romero 11913896
//Daniela Costa da Silva 14613625

import java.util.ArrayList;
import java.util.HashMap;

public class MagicShop {
    private HashMap<String, Double> itemsForSale;
    private ArrayList<Item> playerInventory;
    
    /**
     * Construtor que inicializa a loja com itens predefinidos e preços.
     * 
     * @pre Nenhum pré-requisito necessário.
     * 
     * @post itemsForSale contém pelo menos 5 itens com seus respectivos preços.
     *       playerInventory é uma lista vazia (jogador começa sem itens).
     *       O objeto MagicShop é criado em estado válido.
     */
    public MagicShop() {
        itemsForSale = new HashMap<>();
        playerInventory = new ArrayList<>();
        
        itemsForSale.put("Magic Potion", 50.0);
        itemsForSale.put("Steel Sword", 200.0);
        itemsForSale.put("Golden Key", 150.0);
        itemsForSale.put("Health Elixir", 30.0);
        itemsForSale.put("Invisibility Cloak", 500.0);
    }
    
    /**
     * Exibe todos os itens disponíveis para compra com seus respectivos preços.
     * 
     * @pre Nenhum pré-requisito.
     * 
     * @post Nenhuma alteração no estado da loja ou inventário.
     *       Apenas saída no console (side effect mínimo e controlado).
     *       Se não houver itens, uma mensagem apropriada é exibida.
     */
    public void listItems() {
        if (itemsForSale.isEmpty()) {
            System.out.println("The shop has no items for sale.");
            return;
        }
        System.out.println("\n=== Zuil's Magic Shop ===");
        System.out.println("Items available:");
        for (String itemName : itemsForSale.keySet()) {
            System.out.printf("- %s: R$ %.2f\n", itemName, itemsForSale.get(itemName));
        }
    }
    
    /**
     * Compra um item da loja, debitando o valor da conta bancária do jogador.
     * 
     * @param itemName Nome do item a ser comprado. Deve existir no catálogo da loja.
     * @param bankAccount Conta bancária do jogador para débito. Não pode ser null.
     * 
     * @pre itemName != null
     *      bankAccount != null
     *      itemName existe em itemsForSale
     *      bankAccount tem saldo suficiente (price <= bankAccount.getBalance())
     * 
     * @post Se a compra for bem-sucedida:
     *       Um novo objeto Item com nome itemName é adicionado ao playerInventory.
     *       O saldo do bankAccount é reduzido pelo preço do item.
     *       Uma mensagem de confirmação é exibida.
     *       Retorna true.
     *       
     *       Se a compra falhar (item não existe, saldo insuficiente, etc.):
     *       playerInventory permanece inalterado.
     *       bankAccount permanece inalterado.
     *       Uma mensagem de erro apropriada é exibida.
     *       Retorna false.
     * 
     * @return true se a compra foi realizada com sucesso,
     *         false caso contrário.
     */
    public boolean buyItem(String itemName, BankAccount bankAccount) {
        if (itemName == null || bankAccount == null) {
            System.out.println("Invalid transaction.");
            return false;
        }
        
        if (!itemsForSale.containsKey(itemName)) {
            System.out.println("Item not found in shop: " + itemName);
            return false;
        }
        
        double price = itemsForSale.get(itemName);
        
        if (bankAccount.withdraw(price)) {
            playerInventory.add(new Item(itemName));
            System.out.printf("You bought %s for R$ %.2f!\n", itemName, price);
            return true;
        }
        
        return false;
    }
    
    /**
     * Vende um item de volta para a loja com depreciação de 50%.
     * 
     * @param itemName Nome do item a ser vendido. Deve existir no inventário do jogador.
     * @param bankAccount Conta bancária do jogador para crédito. Não pode ser null.
     * 
     * @pre itemName != null
     *      bankAccount != null
     *      itemName existe em playerInventory (jogador possui o item)
     *      itemName existe em itemsForSale (loja reconhece o item)
     * 
     * @post Se a venda for bem-sucedida:
     *       O item é removido de playerInventory.
     *       O saldo do bankAccount é aumentado em 50% do preço original do item.
     *       Uma mensagem de confirmação é exibida.
     *       Retorna true.
     *       
     *       Se a venda falhar (jogador não possui o item, loja não compra, etc.):
     *       playerInventory permanece inalterado.
     *       bankAccount permanece inalterado.
     *       Uma mensagem de erro apropriada é exibida.
     *       Retorna false.
     * 
     * @return true se a venda foi realizada com sucesso,
     *         false caso contrário.
     */
    public boolean sellItem(String itemName, BankAccount bankAccount) {
        if (itemName == null || bankAccount == null) {
            System.out.println("Invalid transaction.");
            return false;
        }
        
        Item itemToSell = null;
        for (Item item : playerInventory) {
            if (item.getName().equalsIgnoreCase(itemName)) {
                itemToSell = item;
                break;
            }
        }
        
        if (itemToSell == null) {
            System.out.println("You don't have that item: " + itemName);
            return false;
        }
        
        if (!itemsForSale.containsKey(itemName)) {
            System.out.println("Shop doesn't buy this item.");
            return false;
        }
        
        double originalPrice = itemsForSale.get(itemName);
        double sellPrice = originalPrice * 0.5;
        
        playerInventory.remove(itemToSell);
        bankAccount.deposit(sellPrice);
        System.out.printf("You sold %s for R$ %.2f (50%% of original price).\n", itemName, sellPrice);
        return true;
    }
    
    /**
     * Exibe todos os itens atualmente no inventário do jogador.
     * 
     * @pre Nenhum pré-requisito.
     * 
     * @post Nenhuma alteração no estado da loja ou inventário.
     *       Apenas saída no console (side effect mínimo e controlado).
     *       Se o inventário estiver vazio, uma mensagem apropriada é exibida.
     */
    public void showInventory() {
        if (playerInventory.isEmpty()) {
            System.out.println("Your inventory is empty.");
            return;
        }
        System.out.println("\n=== Your Inventory ===");
        for (Item item : playerInventory) {
            System.out.println("- " + item.getName());
        }
    }
    
    /**
     * Retorna uma cópia do inventário do jogador (acesso controlado).
     * 
     * @pre Nenhum pré-requisito.
     * 
     * @post O inventário original permanece inalterado.
     *       O método retorna uma referência à lista interna (side effect potencial).
     * 
     * @return ArrayList contendo os itens do jogador.
     */
    public ArrayList<Item> getPlayerInventory() {
        return playerInventory;
    }
}