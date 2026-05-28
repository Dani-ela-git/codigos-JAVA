//Bruna Romero 11913896
//Daniela Costa da Silva 14613625


import java.util.HashMap;

//Logica da sala, incluindo descrição, itens e saídas para outras salas.
//Mostra a localização dentro do jogo e o que tem nela.

public class Room {
    private String description;
    private Item item;
    private HashMap<String, Room> exits = new HashMap<>();

    public Room(String description, Item item) {
        this.description = description;
        this.item = item;
    }

    public void setExit(String direction, Room neighbor) {
        exits.put(direction, neighbor);
    }

    public Room getExit(String direction) {
        return exits.get(direction);
    }

    /**
     * Retorna a descrição completa da sala incluindo itens e saídas.
     * 
     * @return String com descrição da sala
     */
    public String getLongDescription() {  // ← APENAS UM MÉTODO!
        String itemText = "";
        if (item != null) {
            itemText = "\nYou see an item here: " + item.getName();
        }
        return "You are " + description + "." + itemText + "\nExits: " + exits.keySet();
    }
    
}