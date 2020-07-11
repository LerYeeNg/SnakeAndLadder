
import java.util.*;
import javax.swing.*;
import java.awt.*;

/**
 * A Snakes and Ladders board, set up board with button
 * ladders and snakes are stored in int array
 * @author Ler Yee Ng 101991752
 * @version 1.0
 */

public class Board extends JPanel{
    private final int MAX = 30;

    //Note that the integer 'next position' and String message for each square could be represented by a new class
    private int[] ladderStarts = {4, 12 };
    private int[] ladderEnds =   {14, 22 };
    
    private int[] snakeStarts = {20,16 };
    private int[] snakeEnds =   {7, 5 };
    

    private boolean tracing;
    private ArrayList<JButton> boardArray = new ArrayList<JButton>();

    /**
     * Board constructor
     */
    public Board() {
        super(new GridLayout(0,5)); //set grid layout for 5*6
        this.setPreferredSize(new Dimension(500,500));
  
        //Using Jbutton to set up board
        for(int i=0;i<MAX;i++)
        {
            boardArray.add(new JButton(String.valueOf(i+1)));
            boardArray.get(i).setBorderPainted(false);
            boardArray.get(i).setFocusPainted( false );
            boardArray.get(i).setContentAreaFilled(false);
            add(boardArray.get(i));
        }

        //alter the number flow on the board from left to right, buttom up
        setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
        for(int i=0;i<boardArray.size();i++)
        {
            setComponentZOrder(boardArray.get(i),boardArray.size()-1-i);
        }
    }

    /**
     * getter and setter
     */

    /**
     * getter for ladderStarts
     * @return list of numbers where ladder starts from
     */
    public int[] getLadderStarts() {
        return ladderStarts;
    }

    public void setLadderStarts(int[] ladderStarts) {
        this.ladderStarts = ladderStarts;
    }
    /**
     * getter for ladderEnds
     * @return list of numbers where ladder ended
     */
    public int[] getLadderEnds() {
        return ladderEnds;
    }

    public void setLadderEnds(int[] ladderEnds) {
        this.ladderEnds = ladderEnds;
    }

    /**
     * getter for snakeStarts
     * @return list of numbers where snacks starts from
     */
    public int[] getSnakeStarts() {
        return snakeStarts;
    }

    public void setSnakeStarts(int[] snakeStarts) {
        this.snakeStarts = snakeStarts;
    }

    /**
     * getter for snakeEnd
     * @return list of numbers where snacks ended
     */
    public int[] getSnakeEnds() {
        return snakeEnds;
    }

    public void setSnakeEnds(int[] snakeEnds) {
        this.snakeEnds = snakeEnds;
    }

    /**
     * getter for boardArray
     * @return Arraylist of board buttons
     */
    public ArrayList<JButton> getBoardArray() {

        return this.boardArray;
    }

    public void setBoardArray(ArrayList<JButton> boardArray) {
        this.boardArray = boardArray;
    }

    public void setTracing(boolean on) {
        tracing = on;
    }

}
