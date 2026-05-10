//Bruna Romero 11913896
//Daniela Costa da Silva 14613625

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * OneRowNimGUI - Graphical User Interface for the One Row Nim game.
 * 
 * ============================================================================
 * DESIGN DECISIONS AND CHALLENGES
 * ============================================================================
 * 
 * CHALLENGE 1: Incompatibility between UserInterface and Event-Driven GUI
 * ----------------------------------------------------------------------------
 * The original UserInterface interface has a getUserInput() method that is
 * BLOCKING - it waits for user input and only returns when something is typed.
 * This works fine for command-line interfaces where the program controls the
 * flow with a while loop, but it DOES NOT work for GUI applications.
 * 
 * Why? Because GUI applications are EVENT-DRIVEN. The program doesn't "wait"
 * for input; instead, it sits idle until the user clicks a button or types
 * something. When an event occurs, an ActionListener is called. The program
 * never "blocks" waiting for input - that would freeze the entire interface.
 * 
 * SOLUTION: Instead of implementing UserInterface, we refactored OneRowNim
 * to provide non-blocking methods:
 * - makeMove(int sticks) - processes one move at a time
 * - computerMove() - executes computer's move automatically
 * - resetGame() - resets the game state
 * - isCurrentPlayerComputer() - checks if we need to auto-move
 * 
 * This allows the GUI to call these methods in response to button clicks,
 * rather than having a blocking loop inside OneRowNim.play().
 * 
 * CHALLENGE 2: Handling Computer Turns Without Blocking
 * ----------------------------------------------------------------------------
 * In the CLI version, when it's the computer's turn, the program just calls
 * computer.makeAMove() immediately within the while loop.
 * 
 * In the GUI, we need to handle computer turns AUTOMATICALLY after a human
 * player's move, WITHOUT requiring another button click. But we also must
 * NOT freeze the GUI while the computer "thinks".
 * 
 * SOLUTION: After each human move, we check if the game is over AND if the
 * next player is a computer. If so, we call computerMove() directly from
 * the ActionListener. To prevent the user from clicking during this process,
 * we temporarily disable the input field and Take button.
 * 
 * CHALLENGE 3: Preventing Illegal Moves and Providing Feedback
 * ----------------------------------------------------------------------------
 * The GUI must validate user input BEFORE calling makeMove() and provide
 * clear error messages without crashing or corrupting the game state.
 * 
 * SOLUTION: We validate the input against:
 * - Must be a valid integer
 * - Between 1 and maxPickup (which is min(MAX_PICKUP, remainingSticks))
 * - Not exceeding remaining sticks
 * 
 * Error messages are displayed in a non-editable JTextField used as a message
 * area.
 * 
 * LAYOUT DECISION (BorderLayout as requested):
 * ----------------------------------------------------------------------------
 * - NORTH: Message area (status, errors, computer moves)
 * - CENTER: Game info (sticks remaining, current player)
 * - EAST: Input controls (text field for sticks, Take button, Reset button)
 * - SOUTH: Instructions for the user
 * 
 * This layout keeps the game status prominent (center) while placing controls
 * conveniently on the right side, following natural reading flow.
 */
public class OneRowNimGUI extends JFrame {

    // Game instance
    private OneRowNim game;

    // GUI Components
    private JLabel sticksLabel;
    private JLabel turnLabel;
    private JTextField messageArea;
    private JTextField sticksInputField;
    private JButton takeButton;
    private JButton resetButton;
    private JLabel instructionsLabel;

    // Computer player flag (can be modified to add computer players)
    private boolean hasComputerPlayer = true; // Default: player vs computer
    private boolean computersTurnProcessing = false; // Prevent recursive computer moves

    // Constructor to set up the GUI
    public OneRowNimGUI() {
        // Initialize the game
        game = new OneRowNim();

        // Add a computer player (Player 2 as computer by default)
        // This makes Player 1 = Human, Player 2 = Computer
        game.setPlayer(OneRowNim.PLAYER_ONE);
        IPlayer computer = new NimPlayerBad(game);
        game.addComputerPlayer(computer);

        // Set up the JFrame
        setTitle("One Row Nim Game");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 400);
        setLocationRelativeTo(null); // Center on screen

        // Create the main panel with BorderLayout
        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Create all sections
        JPanel northPanel = createNorthPanel();
        JPanel centerPanel = createCenterPanel();
        JPanel eastPanel = createEastPanel();
        JPanel southPanel = createSouthPanel();

        // Add to main panel
        mainPanel.add(northPanel, BorderLayout.NORTH);
        mainPanel.add(centerPanel, BorderLayout.CENTER);
        mainPanel.add(eastPanel, BorderLayout.EAST);
        mainPanel.add(southPanel, BorderLayout.SOUTH);

        // Add main panel to frame
        add(mainPanel);

        // Initial update
        updateDisplay();

        // Check if computer goes first
        checkAndProcessComputerTurn();
    }

    // create the north panel (message area)
    private JPanel createNorthPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createTitledBorder("Game Messages"));

        messageArea = new JTextField();
        messageArea.setEditable(false);
        messageArea.setFont(new Font("Monospaced", Font.PLAIN, 12));
        messageArea.setText("Welcome to One Row Nim! Make your move.");

        panel.add(messageArea, BorderLayout.CENTER);
        return panel;
    }

    // create the center panel (game status)
    private JPanel createCenterPanel() {
        JPanel panel = new JPanel(new GridLayout(2, 1, 10, 20));
        panel.setBorder(BorderFactory.createTitledBorder("Game Status"));

        // Sticks remaining
        sticksLabel = new JLabel("Sticks Remaining: ", JLabel.CENTER);
        sticksLabel.setFont(new Font("SansSerif", Font.BOLD, 18));

        // Current player turn
        turnLabel = new JLabel("Current Turn: ", JLabel.CENTER);
        turnLabel.setFont(new Font("SansSerif", Font.BOLD, 16));

        panel.add(sticksLabel);
        panel.add(turnLabel);

        return panel;
    }

    // cretae the east panel (controls)
    private JPanel createEastPanel() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(BorderFactory.createTitledBorder("Controls"));

        // Input panel (for taking sticks)
        JPanel inputPanel = new JPanel(new GridLayout(2, 1, 5, 5));
        inputPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JLabel inputLabel = new JLabel("Number of sticks to take (1-3):", JLabel.CENTER);
        sticksInputField = new JTextField();
        takeButton = new JButton("Take Sticks");

        inputPanel.add(inputLabel);
        inputPanel.add(sticksInputField);

        // Button panel
        JPanel buttonPanel = new JPanel(new FlowLayout());
        resetButton = new JButton("Reset Game");
        buttonPanel.add(takeButton);
        buttonPanel.add(resetButton);

        panel.add(inputPanel, BorderLayout.CENTER);
        panel.add(buttonPanel, BorderLayout.SOUTH);

        // Add action listeners
        takeButton.addActionListener(new TakeSticksListener());
        resetButton.addActionListener(new ResetGameListener());

        return panel;
    }

    /// crette the south panel (instructions)
    private JPanel createSouthPanel() {
        JPanel panel = new JPanel();
        panel.setBorder(BorderFactory.createTitledBorder("Instructions"));

        instructionsLabel = new JLabel("Take 1-3 sticks per turn. The player who takes the last stick wins!");
        panel.add(instructionsLabel);

        return panel;
    }

    // updates the display based on the current game state
    private void updateDisplay() {
        // Update sticks display
        sticksLabel.setText("Sticks Remaining: " + game.getSticks());

        // Update turn display
        if (!game.gameOver()) {
            String turnText;
            if (game.getPlayer() == OneRowNim.PLAYER_ONE) {
                turnText = "Current Turn: Player 1 (YOU)";
            } else {
                turnText = "Current Turn: Player 2 (COMPUTER)";
            }
            turnLabel.setText(turnText);
            turnLabel.setForeground(Color.BLACK);
        } else {
            String winner = game.getWinner();
            String winnerText = winner.equals("Player 1") ? "YOU WIN!" : "COMPUTER WINS!";
            turnLabel.setText("GAME OVER - " + winnerText);
            turnLabel.setForeground(Color.RED);
        }

        // Update message area color based on game state
        if (game.gameOver()) {
            messageArea.setBackground(Color.LIGHT_GRAY);
        } else {
            messageArea.setBackground(Color.WHITE);
        }

        // Enable/disable input based on game state and turn
        boolean gameActive = !game.gameOver();
        boolean isHumanTurn = (gameActive && game.getPlayer() == OneRowNim.PLAYER_ONE);

        sticksInputField.setEnabled(gameActive && isHumanTurn && !computersTurnProcessing);
        takeButton.setEnabled(gameActive && isHumanTurn && !computersTurnProcessing);
    }

    // display messages to the user
    private void displayMessage(String message) {
        messageArea.setText(message);
    }

    // error mensage
    private void displayError(String error) {
        messageArea.setText("ERROR: " + error);
        messageArea.setBackground(new Color(255, 200, 200));
        // Reset background after 2 seconds
        Timer timer = new Timer(2000, e -> {
            if (!game.gameOver()) {
                messageArea.setBackground(Color.WHITE);
            }
            updateDisplay();
        });
        timer.setRepeats(false);
        timer.start();
    }

    // Checks if it's the computer's turn and processes it automatically
    private void checkAndProcessComputerTurn() {
        // Don't process if game is over or we're already processing
        if (game.gameOver() || computersTurnProcessing) {
            return;
        }

        // Only process computer turn if it's Player 2's turn
        if (game.getPlayer() != OneRowNim.PLAYER_TWO) {
            // It's human's turn (Player 1), just enable input
            computersTurnProcessing = false;
            updateDisplay();
            if (!game.gameOver()) {
                displayMessage("Your turn! Enter number of sticks (1-" + game.getMaxPickup() + ")");
            }
            return;
        }

        // Check if current player is a computer
        if (game.isCurrentPlayerComputer()) {
            // Disable input while computer is "thinking"
            computersTurnProcessing = true;
            updateDisplay();
            displayMessage("Computer is thinking...");

            // Use Swing Timer to simulate computer "thinking" time
            // This prevents GUI freezing and gives visual feedback
            Timer timer = new Timer(500, new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    // Computer makes its move
                    boolean moveMade = game.computerMove();

                    if (moveMade) {
                        String computerMoveMsg = game.getLastComputerMove();
                        displayMessage(computerMoveMsg);

                        // Update display after computer move
                        updateDisplay();

                        // Check if game ended after computer move
                        if (game.gameOver()) {
                            displayMessage("GAME OVER! " + game.reportGameState());
                            updateDisplay();
                        } else {
                            // Check if it's still computer's turn (e.g., multiple computers?)
                            if (game.isCurrentPlayerComputer()) {
                                displayMessage("Computer moving again...");
                                // Recursive call for next computer
                                computersTurnProcessing = false;
                                checkAndProcessComputerTurn();
                            } else {
                                // Human's turn now
                                displayMessage("Your turn! Enter number of sticks (1-" + game.getMaxPickup() + ")");
                                computersTurnProcessing = false;
                                updateDisplay();
                            }
                        }
                    } else {
                        displayError("Computer move failed!");
                        computersTurnProcessing = false;
                        updateDisplay();
                    }
                }
            });
            timer.setRepeats(false);
            timer.start();
        } else {
            // Human's turn - enable input
            computersTurnProcessing = false;
            updateDisplay();
            if (!game.gameOver()) {
                displayMessage("Your turn! Enter number of sticks (1-" + game.getMaxPickup() + ")");
            }
        }
    }

    private class TakeSticksListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            // Don't process if game is over or computer is playing
            if (game.gameOver() || computersTurnProcessing) {
                displayError("Cannot make a move right now!");
                return;
            }

            // Get input from text field
            String input = sticksInputField.getText().trim();

            if (input.isEmpty()) {
                displayError("Please enter a number of sticks to take!");
                return;
            }

            try {
                int sticksToTake = Integer.parseInt(input);

                // Validate move
                if (sticksToTake < 1) {
                    displayError("You must take at least 1 stick!");
                    return;
                }

                if (sticksToTake > OneRowNim.MAX_PICKUP) {
                    displayError("You can take at most " + OneRowNim.MAX_PICKUP + " sticks!");
                    return;
                }

                if (sticksToTake > game.getSticks()) {
                    displayError("There are only " + game.getSticks() + " sticks left!");
                    return;
                }

                // Make the move
                boolean moveValid = game.makeMove(sticksToTake);

                if (moveValid) {
                    displayMessage("You took " + sticksToTake + " sticks.");
                    updateDisplay();

                    // Check if game is over after human move
                    if (game.gameOver()) {
                        displayMessage("GAME OVER! " + game.reportGameState());
                        updateDisplay();
                        sticksInputField.setText("");
                    } else {
                        // Clear input field
                        sticksInputField.setText("");

                        // Check if it's computer's turn now
                        checkAndProcessComputerTurn();
                    }
                } else {
                    displayError("Invalid move! Please try again.");
                }

            } catch (NumberFormatException ex) {
                displayError("Please enter a valid number!");
            }
        }
    }

    private class ResetGameListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            // Reset game state
            game.resetGame();
            game.setPlayer(OneRowNim.PLAYER_ONE);

            // Reset processing flag
            computersTurnProcessing = false;

            // Clear input field
            sticksInputField.setText("");

            // Update display
            updateDisplay();
            displayMessage("Game reset! Starting new game.");

            // If it's computer's turn at start, process it
            checkAndProcessComputerTurn();
        }
    }

    public static void main(String[] args) {
        // Use SwingUtilities.invokeLater for thread safety
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                OneRowNimGUI gui = new OneRowNimGUI();
                gui.setVisible(true);
            }
        });
    }
}