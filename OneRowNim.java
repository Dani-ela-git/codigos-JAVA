//Bruna Romero 11913896
//Daniela Costa da Silva 14613625


import java.io.*;

interface IPlayer {
    public String makeAMove(String prompt);
}

interface IGame {
    String getGamePrompt();
    String reportGameState();
}

interface UserInterface {
    public String getUserInput();
    public void report(String s);
    public void prompt(String s);
}

interface CLUIPlayableGame extends IGame {
    public abstract void play(UserInterface ui);
}

class NimPlayerBad implements IPlayer {
    private OneRowNim game;

    public NimPlayerBad(OneRowNim game) {
        this.game = game;
    }

    public String makeAMove(String prompt) {
        return "" + randomMove();
    }

    private int randomMove() {
        int sticksLeft = game.getSticks();
        return 1 + (int) (Math.random() * Math.min(sticksLeft, OneRowNim.MAX_PICKUP));
    }

    public String toString() {
        String className = this.getClass().toString();
        return className.substring(5);
    }
}

abstract class TwoPlayerGame {
    public static final int PLAYER_ONE = 1;
    public static final int PLAYER_TWO = 2;

    protected boolean onePlaysNext = true;
    protected int nComputers = 0;
    protected IPlayer computer1, computer2;

    public void setPlayer(int starter) {
        if (starter == PLAYER_TWO)
            onePlaysNext = false;
        else
            onePlaysNext = true;
    }

    public int getPlayer() {
        if (onePlaysNext)
            return PLAYER_ONE;
        else
            return PLAYER_TWO;
    }

    public void changePlayer() {
        onePlaysNext = !onePlaysNext;
    }

    public int getNComputers() {
        return nComputers;
    }

    public String getRules() {
        return "The rules of this game are: ";
    }

    public void addComputerPlayer(IPlayer player) {
        if (nComputers == 0)
            computer1 = player;
        else if (nComputers == 1)
            computer2 = player;
        else
            return;
        ++nComputers;
    }

    public abstract boolean gameOver();
    public abstract String getWinner();
}

public class OneRowNim extends TwoPlayerGame implements CLUIPlayableGame {
    public static final int MAX_PICKUP = 3;
    public static final int MAX_STICKS = 11;

    private int nSticks = MAX_STICKS;
    private String lastComputerMove = "";

    public OneRowNim() {
    }

    public OneRowNim(int sticks) {
        nSticks = sticks;
    }

    public OneRowNim(int sticks, int starter) {
        nSticks = sticks;
        setPlayer(starter);
    }

    public boolean takeSticks(int num) {
        if (num < 1 || num > MAX_PICKUP || num > nSticks)
            return false;
        else {
            nSticks = nSticks - num;
            return true;
        }
    }

    public int getSticks() {
        return nSticks;
    }

    public String getRules() {
        return "\n*** The Rules of One Row Nim ***\n" +
                "(1) A number of sticks between 7 and " + MAX_STICKS + " is chosen.\n" +
                "(2) Two players alternate making moves.\n" +
                "(3) A move consists of subtracting between 1 and\n\t" +
                MAX_PICKUP + " sticks from the current number of sticks.\n" +
                "(4) A player who cannot leave a positive\n\t" +
                " number of sticks for the other player loses.\n";
    }

    public boolean gameOver() {
        return (nSticks <= 0);
    }

    public String getWinner() {
        if (gameOver()) {
            return "Player " + getPlayer();
        }
        return "The game is not over yet.";
    }

    public String getGamePrompt() {
        return "\nYou can pick up between 1 and " + Math.min(MAX_PICKUP, nSticks) + " : ";
    }

    public String reportGameState() {
        if (!gameOver())
            return ("Sticks left: " + getSticks() + " | Turn: Player " + getPlayer());
        else
            return ("Sticks left: " + getSticks() + " | GAME OVER! Winner: Player " + getWinner());
    }

    //new method for GUI: Makes a move for the human player (if it's their turn)
    public boolean makeMove(int sticks) {
        if (gameOver()) {
            return false;
        }
        
        if (takeSticks(sticks)) {
            changePlayer();
            return true;
        }
        return false;
    }
    
    // NEW METHOD FOR GUI: Makes a move for the computer player (if it's their turn)
    public boolean computerMove() {
        if (gameOver()) {
            return false;
        }
        
        IPlayer computer = null;
        switch (getPlayer()) {
            case PLAYER_ONE:
                computer = computer1;
                break;
            case PLAYER_TWO:
                computer = computer2;
                break;
        }
        
        if (computer != null) {
            int sticks = Integer.parseInt(computer.makeAMove(""));
            lastComputerMove = computer.toString() + " takes " + sticks + " sticks.";
            if (takeSticks(sticks)) {
                changePlayer();
                return true;
            }
        }
        return false;
    }
    
    //new method for GUI to get the last computer move (for display purposes)
    public String getLastComputerMove() {
        return lastComputerMove;
    }
    
    //new method for GUI to reset the game to initial state
    public void resetGame() {
        nSticks = MAX_STICKS;
        onePlaysNext = true;
        lastComputerMove = "";
    }
    
    //new method for GUI to check if current player is a computer
    public boolean isCurrentPlayerComputer() {
        switch (getPlayer()) {
            case PLAYER_ONE:
                return computer1 != null;
            case PLAYER_TWO:
                return computer2 != null;
            default:
                return false;
        }
    }
    
    //new method for GUI to get the maximum number of sticks that can be picked up (for input validation)
    public int getMaxPickup() {
        return Math.min(MAX_PICKUP, nSticks);
    }
    
    //new method for GUI to check if it's player one's turn
    public boolean isPlayerOneTurn() {
        return getPlayer() == PLAYER_ONE;
    }
    
    //new mwthod for GUI to check if it's player two's turn
    public boolean isPlayerTwoTurn() {
        return getPlayer() == PLAYER_TWO;
    }

    public void play(UserInterface ui) {
        int sticks = 0;
        ui.report(getRules());
        if (computer1 != null)
            ui.report("\nPlayer 1 is a " + computer1.toString());
        if (computer2 != null)
            ui.report("\nPlayer 2 is a " + computer2.toString());

        while (!gameOver()) {
            IPlayer computer = null;
            ui.report(reportGameState());
            switch (getPlayer()) {
                case PLAYER_ONE:
                    computer = computer1;
                    break;
                case PLAYER_TWO:
                    computer = computer2;
                    break;
            }

            if (computer != null) {
                sticks = Integer.parseInt(computer.makeAMove(""));
                ui.report(computer.toString() + " takes " + sticks + " sticks.\n");
            } else {
                ui.prompt(getGamePrompt());
                sticks = Integer.parseInt(ui.getUserInput());
            }
            if (takeSticks(sticks))
                changePlayer();
        }
        ui.report(reportGameState());
    }

    public String submitUserMove(String theMove) {
        int sticks = Integer.parseInt(theMove);
        if (takeSticks(sticks)) {
            changePlayer();
            if (gameOver()) {
                return reportGameState() + "\nGame won by player" + getWinner() + "\n";
            } else {
                return reportGameState() + getGamePrompt();
            }
        }
        return "\nOops. " + sticks + " is an illegal move." + getGamePrompt();
    }

    public static void main(String args[]) {
        KeyboardReader kb = new KeyboardReader();
        CLUIPlayableGame game = new OneRowNim();

        kb.prompt("How many computers are playing, 0, 1, or 2? ");
        int m = kb.getKeyboardInteger();
        for (int k = 0; k < m; k++) {
            IPlayer computer = new NimPlayerBad((OneRowNim) game);
            ((TwoPlayerGame) game).addComputerPlayer(computer);
        }
        game.play(kb);
    }
}

class KeyboardReader implements UserInterface {
    private BufferedReader reader;

    public KeyboardReader() {
        reader = new BufferedReader(new InputStreamReader(System.in));
    }

    public String getKeyboardInput() {
        return readKeyboard();
    }

    public int getKeyboardInteger() {
        return Integer.parseInt(readKeyboard());
    }

    public double getKeyboardDouble() {
        return Double.parseDouble(readKeyboard());
    }

    public String getUserInput() {
        return getKeyboardInput();
    }

    public void prompt(String s) {
        System.out.print(s);
    }

    public void report(String s) {
        System.out.print(s);
    }

    public void display(String s) {
        System.out.print(s);
    }

    private String readKeyboard() {
        String line = "";
        try {
            line = reader.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return line;
    }
}