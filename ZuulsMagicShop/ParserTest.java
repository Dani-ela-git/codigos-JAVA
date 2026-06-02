//Bruna Romero 11913896
//Daniela Costa da Silva 14613625


import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;
import java.io.ByteArrayInputStream;
import java.util.Scanner;

public class ParserTest {
    
    private Parser parser;
    
    /**
     * Cria um Scanner mock que simula entrada do usuário
     */
    private Scanner createMockScanner(String input) {
        return new Scanner(new ByteArrayInputStream(input.getBytes()));
    }
    
    @Test
    public void testSingleWordCommand() {
        // Teste para comando de uma única palavra
        parser = new Parser(createMockScanner("quit\n"));
        Command command = parser.getCommand();
        assertEquals(CommandWord.QUIT, command.getCommandWord());
        assertFalse(command.hasSecondWord());
    }
    
    @Test
    public void testTwoWordCommand() {
        // Teste para comando de duas palavras
        parser = new Parser(createMockScanner("go east\n"));
        Command command = parser.getCommand();
        assertEquals(CommandWord.GO, command.getCommandWord());
        assertTrue(command.hasSecondWord());
        assertEquals("east", command.getSecondWord());
    }
    
    @Test
    public void testEmptyInput() {
        // Teste para entrada vazia - deve lançar exceção
        parser = new Parser(createMockScanner("\n"));
        
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            parser.getCommand();
        });
        assertEquals("Empty command", exception.getMessage());
    }
    
    @Test
    public void testWhitespaceOnlyInput() {
        // Teste para entrada com apenas espaços
        parser = new Parser(createMockScanner("   \n"));
        
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            parser.getCommand();
        });
        assertEquals("Empty command", exception.getMessage());
    }
    
    @Test
    public void testUnknownCommand() {
        // Teste para comando desconhecido
        parser = new Parser(createMockScanner("xyzabc\n"));
        Command command = parser.getCommand();
        assertEquals(CommandWord.UNKNOWN, command.getCommandWord());
        assertFalse(command.hasSecondWord());
    }
    
    @Test
    public void testCommandWithSpaces() {
        // Teste para comando com espaços extras
        parser = new Parser(createMockScanner("  go    north  \n"));
        Command command = parser.getCommand();
        assertEquals(CommandWord.GO, command.getCommandWord());
        assertEquals("north", command.getSecondWord());
    }
    
    @Test
    public void testMultipleCommands() {
        // Teste para múltiplos comandos sequenciais
        String input = "look\nshow\nbuy potion\nquit\n";
        parser = new Parser(createMockScanner(input));
        
        Command cmd1 = parser.getCommand();
        assertEquals(CommandWord.LOOK, cmd1.getCommandWord());
        
        Command cmd2 = parser.getCommand();
        assertEquals(CommandWord.SHOW, cmd2.getCommandWord());
        
        Command cmd3 = parser.getCommand();
        assertEquals(CommandWord.BUY, cmd3.getCommandWord());
        assertEquals("potion", cmd3.getSecondWord());
        
        Command cmd4 = parser.getCommand();
        assertEquals(CommandWord.QUIT, cmd4.getCommandWord());
    }
    
    @Test
    public void testCaseInsensitivity() {
        // Teste para comandos em diferentes caixas (maiúsculas/minúsculas)
        parser = new Parser(createMockScanner("GO NORTH\n"));
        Command command = parser.getCommand();
        assertEquals(CommandWord.GO, command.getCommandWord());
        assertEquals("north", command.getSecondWord());
    }
    
    @Test
    public void testDepositCommand() {
        // Teste específico para comando deposit
        parser = new Parser(createMockScanner("deposit 100\n"));
        Command command = parser.getCommand();
        assertEquals(CommandWord.DEPOSIT, command.getCommandWord());
        assertEquals("100", command.getSecondWord());
    }
    
    @Test
    public void testWithdrawCommand() {
        // Teste específico para comando withdraw
        parser = new Parser(createMockScanner("withdraw 50.50\n"));
        Command command = parser.getCommand();
        assertEquals(CommandWord.WITHDRAW, command.getCommandWord());
        assertEquals("50.50", command.getSecondWord());
    }
    
    @Test
    public void testBuyCommandWithTwoWordsItem() {
        // Teste para item com duas palavras (ex: "Magic Potion")
        parser = new Parser(createMockScanner("buy Magic Potion\n"));
        Command command = parser.getCommand();
        assertEquals(CommandWord.BUY, command.getCommandWord());
        assertEquals("Magic Potion", command.getSecondWord());
    }
}