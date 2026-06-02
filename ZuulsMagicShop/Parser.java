//Bruna Romero 11913896
//Daniela Costa da Silva 14613625


import java.util.Scanner;

public class Parser {
    private Scanner scanner;
    
    public Parser(Scanner scanner) {
        this.scanner = scanner;
    }
    
    public Command getCommand() {
        String inputLine = scanner.nextLine();
        String[] words = inputLine.trim().split(" ", 2);
        String word1 = words[0];
        String word2 = (words.length > 1) ? words[1] : null;
        
        if (word1.isEmpty()) {
            throw new IllegalArgumentException("Empty command");
        }
        
        CommandWord commandWord;
        try {
            commandWord = CommandWord.valueOf(word1.toUpperCase());
        } catch (IllegalArgumentException e) {
            commandWord = CommandWord.UNKNOWN;
        }
        
        return new Command(commandWord, word2);
    }
}