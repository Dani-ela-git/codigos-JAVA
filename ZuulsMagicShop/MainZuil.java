//Bruna Romero 11913896
//Daniela Costa da Silva 14613625


import java.util.Scanner;

/**
 * Classe principal que executa o jogo "Dungeon Escape".
 * Gerencia o loop principal do jogo e a interação com o jogador.
 * 
 * @author Bruna Romero
 * @author Daniela Costa da Silva
 * @version 1.0
 */
public class MainZuil {
    public static void main(String[] args) {
        Game game = new Game();
        Scanner scanner = new Scanner(System.in);
        
        System.out.println("Welcome to the Dungeon Escape");
        System.out.println("Commands: go [direction], look, back, show, list, buy [item], sell [item], deposit [amount], withdraw [amount], quit");
        System.out.println("\n" + game.getCurrentRoom());
        boolean finished = false;
        while (!finished) {
            System.out.print("\n> ");
            String line = scanner.nextLine();
            
            String[] words = line.trim().split(" ", 2);
            String word1 = words[0];
            String word2 = (words.length > 1) ? words[1] : null;
            
            if (word1.equalsIgnoreCase("quit")) {
                finished = true;
                game.processCommand("quit", null);
            } else {
                game.processCommand(word1, word2);
            }
        }
        scanner.close();
    }
}