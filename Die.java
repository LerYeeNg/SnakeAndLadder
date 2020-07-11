import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Random;
import javax.swing.border.*;


/**
 * Represents a game die with between 4 and max int faces.
 * @author Ler Yee Ng 101991752
 * @version 1.0
 */

public class Die extends JPanel {
    //Class data (constants)

    /**
     * The default number of faces on a {@code Die}.
     */
    public static final int DEFAULT_FACES = 6;
    /**
     * Minimum possible number of faces on a die with flat faces.
     */
    public static final int MIN_FACES = 4;

    //Instance data

    /**
     * The number of faces on the die.
     */
    private int numFaces;
    /**
     * The currently displayed face (the side that is 'up').
     */
    private int faceValue;
    /**
     * Source of next face.
     */
    private Random generator;

    private JButton diceButton = new JButton("Roll");

    private JLabel diceMessage = new JLabel("");

    private JPanel dice = new JPanel();


    //Constructors

    /**
     * Creates a new Die with the {@linkplain #DEFAULT_FACES default number} of
     * faces (6) and an initial face value of 1.
     */
    public Die() {
        // The this keyword can be used to call another of the class's constructors.
        this(DEFAULT_FACES);
    }

    /**
     * Creates a new Die with the given number of faces and an initial value
     * of 1. If the requested number of faces is below the {@linkplain
     * #MIN_FACES minimum} then it is set to the default number of faces (6).
     *
     * @param faces the number of faces of the new Die
     */
    public Die(int faces){
        super();

        if (faces >= MIN_FACES) {
            numFaces = faces;
        } else {
            numFaces = DEFAULT_FACES;
        }
        faceValue = 1;
        generator = new Random();

        setPreferredSize(new Dimension(250, 110)); // sets the preferred size of dice panel

        JLabel diceInstruction = new JLabel("<html>" + "Please click roll to roll the dice." + "</html>");
        add(diceInstruction); // adds the click the dice to roll message in the dice panel

        diceButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                diceButtonClicked();
            }
        });

        dice.setLayout(new GridBagLayout());
        dice.setBorder(new EmptyBorder(10, 10, 10, 10));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.anchor = GridBagConstraints.NORTH;

        gbc.anchor = GridBagConstraints.CENTER;
        gbc.fill = GridBagConstraints.HORIZONTAL;

        dice.add(diceButton, gbc);
        dice.add(diceMessage, gbc);

        add(dice);

    }

    //Getters

    /**
     * Returns the Die's current face value (the face up value of dice)
     * @return the current face value
     */
    public int getFaceValue() {
        return faceValue;
    }

    /**
     * Rolling the die by generating a new face number between 1 and numFaces. Returns the new face.
     * @return the new value of the Die
     */
    public int roll() {
        faceValue = generator.nextInt(numFaces) + 1;
        return faceValue;
    }

    /**
     * Returns the number of faces on the Die.
     * @return the number of faces
     */
    public int getFaces() {
        return numFaces;
    }

    /**
     * To handle action when dice button is clicked
     * @return the number of faces
     */
    public Boolean diceButtonClicked() {
        roll();
        diceMessage.setText("<html><div style='text-align: center;'><font color='Green' size='5'>" + "Rolled: " + faceValue + "</font></div></html>");
        return true;
    }

    /**
     * getter for dice button
     * @return Jbutton
     */
    public JButton getDiceButton()
    {
        return this.diceButton;
    }
}