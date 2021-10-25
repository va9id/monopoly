import java.util.*;

/**
 * The Player object for monopoly
 *
 * @author Noah Hammoud
 */
public class Player {
    private final String IDENTIFIER;
    private int money;
    private int position;
    private Set<Property> properties;
    private boolean tookTurn;
    private boolean isBankrupt;
    private String playerImageFile;


    /**
     * The constructor for Player.
     *
     * @param identifier String identifier of the Player
     * @param playerImageFile String containing the file path to the player image
     */
    public Player(String identifier, String playerImageFile) {
        this.IDENTIFIER = identifier;
        this.money = 1500;
        this.position = 0;
        this.properties = new HashSet<>();
        this.tookTurn = false;
        this.isBankrupt = false;
        this.playerImageFile = playerImageFile;
    }

    /**
     * Gets the file path to the player image.
     * @return the playerImageFile String
     */
    public String getPlayerImageFile() {
        return this.playerImageFile;
    }


    /**
     * Gets the identifier of the Player.
     *
     * @return the String identifier
     */
    public String getIdentifier() {
        return this.IDENTIFIER;
    }


    /**
     * Gets the Players money.
     *
     * @return the int amount
     */
    public int getMoney() {
        return this.money;
    }


    /**
     * Gets the Players current position.
     *
     * @return the int position
     */
    public int getPosition() {
        return this.position;
    }


    /**
     * Gets isBankrupt.
     *
     * @return boolean, isBankrupt value
     */
    public boolean getIsBankrupt() {
        return this.isBankrupt;
    }


    /**
     * Sets Players Position.
     *
     * @param pos int position of Player
     */
    public void setPosition(int pos) {
        this.position = pos;
    }


    /**
     * Adds money to Player.
     *
     * @param money int amount of money being added
     */
    public void addMoney(int money) {
        this.money += money;
    }


    /**
     * Removes money from Player.
     *
     * @param money int amount of money being removed
     */
    private void removeMoney(int money) {
            this.money -= money;
    }


    /**
     * Checks if player can pay amount of money.
     *
     * @param money int amount of money to pay
     * @return boolean, true if player can pay
     */
    private boolean canPay(int money) {
        return this.money - money >= 0;
    }


    /**
     * purchases property.
     *
     * @param p the Property object that the Player wants to purchase
     */
    public boolean purchaseProperty(PropertyStreet p) {
        int cost = p.getPrice();
        if (this.canPay(cost)) {
            this.removeMoney(cost);
            System.out.println("You have purchased " + p.getName() + " for $" + p.getPrice() + "!");
            this.properties.add(p);
            return true;
        }
        else {
            System.out.println("You do not have enough money to buy this property!");
            return false;
        }
    }


    /**
     * Pays rent.
     *
     * @param cost int amount of money to pay
     * @return int amount of money actual payed. (If player does not have enough pay all of money)
     */
    public int payRent(int cost) {
        if (canPay(cost)) {
            this.removeMoney(cost);
            return cost;
        }
        else {
            int remainingMoney = this.money;
            this.removeMoney(this.money);
            this.isBankrupt = true;
            return remainingMoney;
        }
    }


    /**
     * Checks if Player owns property.
     *
     * @param p the Property object that is being checked
     * @return boolean true if Player owns property
     */
    public boolean ownsProperty(Property p) {
        return this.properties.contains(p);
    }


    /**
     * Gets a set of all the colours that player owns a full group of.
     *
     * @param board the Monopoly board that is being used
     * @return a set of all the player's property groups
     */
    public Set<String> getPropertyGroups(MonopolyBoard board) { //Function will be used in Milestone 3
        Set<String> propertyGroups = new HashSet<>();
        for (Property p : this.properties) {
            if (p instanceof PropertyStreet && !propertyGroups.contains(((PropertyStreet) p).getColour())) {
                String colour = ((PropertyStreet) p).getColour();
                propertyGroups.add(colour);
                for (PropertyStreet p2 : board.getPropertyGroup(colour)) {
                    if (!this.properties.contains(p2)) {
                        propertyGroups.remove(colour);
                        break;
                    }
                }
            }
        }
        return propertyGroups;
    }


    /**
     * Gets a string of all the properties that the player owns.
     *
     * @return a String containing the players properties separated by a comma
     */
    public String getPropertyString() {
        String propertyString = "";
        for (Property p : this.properties) {
            propertyString += p + ", ";
        }
        return propertyString.replaceAll(", $", ""); //removes trailing comma
    }

    /**
     * Checks if a player took their turn.
     *
     * @return true if a player took their turn, and false otherwise
     */
    public boolean isTookTurn() {
        return this.tookTurn;
    }


    /**
     * Sets whether a player took their turn.
     *
     * @param tookTurn the boolean value to be set
     */
    public void setTookTurn(boolean tookTurn) {
        this.tookTurn = tookTurn;
    }


    /**
     * Overrides the equals method from the Object class.
     *
     * @param o The object that is being compared
     * @return true if the object is a player and the identifier of the player is the same, and false otherwise
     */
    @Override
    public boolean equals(Object o) {
        return (o instanceof Player) && (((Player) o).IDENTIFIER.equals(this.IDENTIFIER));
    }
}
