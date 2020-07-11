import java.awt.*;
import javax.swing.*;

/**
 * Player class represents a player which has a name, position, previous position and color property
 * @author Ler Yee Ng 101991752
 * @version 1.0
 */
public class Player extends JButton {
    //instance variables
    private String name;
    private int position = 0;
    private int prevPos = 0;

    /**
     * Constructor for player. takes in width, height, name to set initial data
     */
    public Player(int width,int height,String name) {
        super(name);
        this.name = name;
        setSize(new Dimension(width,height));
    }


    /**
     * Getter for the name of the player
     * @return name of the player
     */
    public String getName()
    {
        return this.name;
    }

    /**
     * Setter for the position of the player by taking int format
     * @param toSet value to be set
     */
    public void setPosition(int toSet)
    {
        this.position = toSet;
    }

    /**
     * Getter for the position of the player
     * @return position of player
     */
    public int getPosition()
    {
        return this.position;
    }

    /**
     * Setter for the previous position in int format
     * @param toSet previous position 
     */
    public void setPrevPosition(int toSet)
    {
        this.prevPos = toSet;
    }

    /**
     * Returns the previous position of the player
     * @return previous position of player
     */
    public int getPrevPosition()
    {
        return this.prevPos;
    }
}