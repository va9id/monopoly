import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

/**
 * MonopolyView class which handles the GUI.
 * @author Connor Marcus
 */
public class MonopolyView extends JFrame implements MonopolyObserver {
    private BoardPanel boardPanel;
    private SidePanel sidePanel;
    private MonopolyModel model;

    /**
     * Constructor for MonopolyView class.
     * @param title String title of JFrame
     */
    public MonopolyView(String title) {
        super(title);
        String[] options = {"4", "3", "2"};
        int choice = JOptionPane.showOptionDialog(null, "How many players would like to play:",
                "Choose Players",
                JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, options, options[2]);
        if (choice == -1) {
            System.exit(0);
        }
        this.model = new MonopolyModel(Integer.parseInt(options[choice]));
        this.model.addMonopolyObserver(this);
        this.boardPanel = new BoardPanel(this.model.getBoard(), this.model.getPlayerList());
        this.sidePanel = new SidePanel(this.model);
        this.setLayout(new FlowLayout());
        this.add(this.boardPanel);
        this.add(this.sidePanel);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setResizable(false);
        this.pack();
        this.setVisible(true);
    }

    /**
     * Handles what happens when a player takes their turn.
     * @param player The Player who took their turn
     * @param roll The player's roll
     * @param propertyLandedOn The property that the player landed on
     */
    @Override
    public void handleTakeTurn(Player player, int roll, Property propertyLandedOn) {
        JOptionPane.showMessageDialog(null, "Player " + player.getIdentifier() + " has rolled a " + roll + ". They are now on " + propertyLandedOn + ".");
        this.boardPanel.updatePlayerLabelPosition(player);
        this.sidePanel.enableButton("Pass");
    }

    /**
     * Handles what happens when a player passes their turn.
     * @param player The Player who passed their turn.
     */
    @Override
    public void handlePassTurn(Player player) {
        JOptionPane.showMessageDialog(null, "Player " + player.getIdentifier() + " has finished their turn.");
        this.sidePanel.enableButton("Roll");
    }

    /**
     * Handles what happens when a player becomes bankrupt.
     * @param player The Player who has gone bankrupt
     */
    @Override
    public void handleBankrupt(Player player) {
        JOptionPane.showMessageDialog(null, "Player " + player.getIdentifier() + "has gone bankrupt! They are out of the game.");
        this.boardPanel.removePlayerLabel(player);
        this.sidePanel.removePlayerInfo(player);
    }

    /**
     * Handles what happens when a player wins.
     * @param player The Player who has won
     */
    @Override
    public void handleWinner(Player player) {
        JOptionPane.showMessageDialog(null, "Player " + player.getIdentifier() + " has won the game!");
        System.exit(0);
    }

    @Override
    public void handlePlayerUpdate(Player player) {
        this.sidePanel.updatePlayerInfo(player);
    }

    /**
     * Main method to run game.
     * @param args String[] input for main method
     */
    public static void main(String[] args) {
        MonopolyView mv = new MonopolyView("Monopoly");
    }
}