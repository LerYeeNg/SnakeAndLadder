import java.awt.event.ActionEvent;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.lang.Runnable;


/**
 * Snakes and Ladders game driver. The sole purpose of this class is to run the Snakes and ladders game defined in Snakes And Ladders
 * offer restart option and handle the action and performance of restart button
 * @author Ler Yee Ng 101991752
 * @version 1.0
 */


public class SALgame extends JFrame {
    private SnakesAndLadders game = new SnakesAndLadders();
    private Runnable r = game;
    private JPanel restartPanel = new JPanel(); //panel where restart button is placed
    private JButton restart = new JButton("Restart"); //restart button
    private Thread t = new Thread(r); // creates a thread to run the runnable


    public SALgame() {
        super();
        setSize(1000,850);
        setTitle("Snakes and Ladders");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
        setContentPane(game);

        //add restart option
        restartPanel.add(restart);
        restartPanel.setBounds(625,100,245,45);
        restartPanel.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY,2));
        add(restartPanel);
        t.start(); //starts game

        restart.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                t.interrupt(); //interrupt currently running thread
                game = new SnakesAndLadders(); //reset game data
                r = game;
                t = new Thread(r); //refresh the state of the thread to start another game
                setContentPane(game);

                restartPanel.add(restart);
                restartPanel.setBounds(625,100,245,45);
                restartPanel.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY,2));
                add(restartPanel);

                t.start(); //restart game

            }
        });

    }


    public static void main(String[] args) {

        new SALgame();
    }
}
