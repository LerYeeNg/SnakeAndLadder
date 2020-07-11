import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.logging.*;

/**
 * Snakes and Ladders extended from JPanel and implements runnable to handle user input of the game
 * The main play game function, to set up GUI and call game method
 * @author Ler Yee Ng 101991752
 * @version 1.0
 */

public class SnakesAndLadders extends JPanel implements Runnable {
    //load image board for GUI game background
    Image image = Toolkit.getDefaultToolkit().createImage("Board.png"); //load board image 5*6 board
    private JPanel rightPanel = new JPanel(); //panel placed on right responsible of displaying the dice
    private JPanel playerPanel = new JPanel(); //panel placed on right bottom where 2 player icons are shown
    private ImagePanel buttonBoardPanel = new ImagePanel(image); //panel where board is placed
    private JPanel messagePanel = new JPanel(); //panel where message output is placed


    private Player player = new Player(90,80,"Player"); //human player
    private Player computer = new Player(90,80,"Computer"); //computer player
    private Die dice = new Die();
    private Board board = new Board();
    private JLabel messageLabel = new JLabel("<html><h1>"+"Snakes and Ladders"+"</h1></html>",SwingConstants.CENTER); //label where all messages to user will be shown

    private boolean diceRolled = false;
    private boolean correctButtonClicked = false;
    private boolean moreRoll = false;
    private boolean winner = false;
    private int faceValue;

    private int difference;
    /**
     * Constructor
     * to set up SAL game layout on GUI
     */
    public SnakesAndLadders() {
        super();
        setLayout(null);
        board.setOpaque(false);
        buttonBoardPanel.add(board);
        buttonBoardPanel.setBounds(25,50,600,550);
        add(buttonBoardPanel);

        rightPanel.add(dice);
        playerPanel.setLayout(new GridLayout(0,2));
        playerPanel.add(player);
        playerPanel.add(computer);
        rightPanel.add(playerPanel);
        rightPanel.setBounds(625,200,245,245);
        rightPanel.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY,4));
        rightPanel.setLayout(new GridLayout(2,0));
        add(rightPanel);


        messagePanel.setBounds(50,600,850,80);
        messagePanel.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY,2));
        messagePanel.setBackground(Color.LIGHT_GRAY);
        messagePanel.add(messageLabel);
        add(messagePanel);
    }

    /**
     * To choose the first player to start the game
     * @param player,computer the pre-define player class
     * @return returns a player who got the higher number to start game or null if both roll the same number
     */
    public Player chooseFirstPlayer(Player player,Player computer) {
        int playerFace,computerFace;
        
        diceRolled = false;
        messageLabel.setText("<html><h1> Welcome! " + player.getName() + " roll the dice to start game</h1></html>");
        dice.getDiceButton().setEnabled(true);

        while(diceRolled == false) {
            dice.getDiceButton().addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    diceRolled = dice.diceButtonClicked();
                }
            });
            delayedPrint(3000);
            messageLabel.setText("<html><h1>Player: " + player.getName() + " roll your dice!</h1></html>");
            player.setBorder(BorderFactory.createLineBorder(Color.RED,2));
        } //a loop until player roll the die
        
        playerFace = dice.getFaceValue();
        if (diceRolled == true){
            diceRolled = false;
            messageLabel.setText("<html><h1>" + player.getName() + " rolled: " + playerFace +"</h1></html>");
            delayedPrint(1300);
            player.setBackground(new JButton().getBackground());
            player.setBorder(new JButton().getBorder());
            dice.getDiceButton().setEnabled(false);
        }


        //computer's turn to roll dice
        messageLabel.setText("<html><h1>" + computer.getName() + " is rolling the dice....</h1></html>");
        computer.setBorder(BorderFactory.createLineBorder(Color.BLUE,2));
        dice.diceButtonClicked();
        delayedPrint(400);

        computerFace = dice.getFaceValue();
        messageLabel.setText("<html><h1>" + computer.getName() + " rolled: " + computerFace +"</h1></html>");
        computer.setBackground(new JButton().getBackground());
        computer.setBorder(new JButton().getBorder());
        delayedPrint(1300);

        //check if which player has higher face value
        if(playerFace>computerFace)
        {
            messageLabel.setText("<html><h1>" + player.getName() + " goes first!</h1></html>");
            return player;
        }
        else if(computerFace>playerFace)
        {
            messageLabel.setText("<html><h1>" + computer.getName() + " goes first!</h1></html>");
            return computer;
        }
        else //handles the tie situation
        {
            messageLabel.setText("<html><h1>Tied! Roll again to decide the first player!</h1></html>");
            dice.getDiceButton().setEnabled(true);
            return null;
        }
    }

    /**
     * sequence method when computer plays the game
     */
    public void computerTurn() {
        computer.setBorder(BorderFactory.createLineBorder(Color.BLUE,2));
        //computer's turn computer rolls the dice
        messageLabel.setText("<html><h1>" + computer.getName() + "'s Turn</h1></html>");
        delayedPrint(500);
        messageLabel.setText("<html><h1>" + computer.getName() + " is rolling the dice.....</h1></html>");
        delayedPrint(1000);
        dice.diceButtonClicked();
        faceValue = dice.getFaceValue(); // roll value of computer is set here
       
        // if roll value + current position of player is less than or equal to 30, move computer to the face value position and check for snake and ladders
        if((faceValue + computer.getPosition())<=30)
        {
            computer.setPrevPosition(computer.getPosition());
            computer.setPosition(computer.getPosition()+faceValue);
            messageLabel.setText("<html><h1>" + computer.getName() + " rolled: " + faceValue + "</h1></html>");
            delayedPrint(1000);
            messageLabel.setText("<html><h1>" + computer.getName() + " to position: " + computer.getPosition() + "</h1></html>");
            if(computer.getPrevPosition()!=0)
            {
                board.getBoardArray().get(computer.getPrevPosition()-1).setBorderPainted(false);//resets the previous button
                board.getBoardArray().get(computer.getPrevPosition()-1).setFocusPainted( false );
                board.getBoardArray().get(computer.getPrevPosition()-1).setContentAreaFilled(false);
            }
            if(player.getPosition()!=0 && computer.getPrevPosition()==player.getPosition())
            {
                board.getBoardArray().get(player.getPosition()-1).setBorderPainted(true);
                board.getBoardArray().get(player.getPosition()-1).setBorder(BorderFactory.createLineBorder(Color.RED,2));
            }
            board.getBoardArray().get(computer.getPosition()-1).setBorderPainted(true);
            board.getBoardArray().get(computer.getPosition()-1).setBorder(BorderFactory.createLineBorder(Color.BLUE,2));

            delayedPrint(1000);
            computer.setBackground(new JButton().getBackground());
            computer.setBorder(new JButton().getBorder());
            checkSnakesAndLadders(computer);
        } else { // when it is >30  
            difference = computer.getPosition()+faceValue - 30;
            computer.setPrevPosition(computer.getPosition());
            computer.setPosition(30-difference);
            messageLabel.setText("<html><h1>" + computer.getName() + " rolled: " + faceValue + "</h1></html>");
            delayedPrint(1000);
            messageLabel.setText("<html><h1> Oh.. It is more than 30. " + computer.getName() + " going backwards to position: " + computer.getPosition() + "</h1></html>");
            if(computer.getPrevPosition()!=0)
            {
                board.getBoardArray().get(computer.getPrevPosition()-1).setBorderPainted(false);//resets the previous button
                board.getBoardArray().get(computer.getPrevPosition()-1).setFocusPainted( false );
                board.getBoardArray().get(computer.getPrevPosition()-1).setContentAreaFilled(false);
            }
            if(player.getPosition()!=0 && computer.getPrevPosition()==player.getPosition())
            {
                board.getBoardArray().get(player.getPosition()-1).setBorderPainted(true);
                board.getBoardArray().get(player.getPosition()-1).setBorder(BorderFactory.createLineBorder(Color.RED,2));
            }
            board.getBoardArray().get(computer.getPosition()-1).setBorderPainted(true);
            board.getBoardArray().get(computer.getPosition()-1).setBorder(BorderFactory.createLineBorder(Color.BLUE,2));

            delayedPrint(1000);
            computer.setBackground(new JButton().getBackground());
            computer.setBorder(new JButton().getBorder());
            checkSnakesAndLadders(computer);
        }
        // handles case when roll value + current position of player is over 30
       /* else {
            messageLabel.setText("<html><h1>" + computer.getName() + " rolled: " + faceValue + "</h1></html>");
            delayedPrint(1000);
            messageLabel.setText("<html><h1> So Close! Computer must land exactly on the last square to win.</h1></html>");
            delayedPrint(800);
            computer.setBackground(new JButton().getBackground());
            computer.setBorder(new JButton().getBorder());
        }*/

        // if roll value is 6, allocate extra turn
        if(computer.getPosition() < 30) {
            if (faceValue == 6) {
                messageLabel.setText("<html><h1>" + computer.getName() + " rolled: " + faceValue + " and earned an extra turn.</h1></html>");
                delayedPrint(500);
                moreRoll = true;
            } else {
                moreRoll = false;
            }
        }
    }

    /**
     * sequence method when players plays the game
     */
    public void playerTurn() {

        diceRolled = false;
        player.setBorder(BorderFactory.createLineBorder(Color.RED,2));
        ActionListener wrongClick, rightClick;

        while(diceRolled == false) {
            delayedPrint(900);
            messageLabel.setText("<html><h1>" + player.getName() + "'s turn</h1></html>");
            dice.getDiceButton().addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    diceRolled = dice.diceButtonClicked();
                }
            });
            if (diceRolled == false){
                delayedPrint(1500);
                messageLabel.setText("<html><h1>" + player.getName() + " roll your dice!</h1></html>");
            }
            //delayedPrint(1500);
            //messageLabel.setText("<html><h1>" + player.getName() + " roll your dice!</h1></html>");
        }// a loop until human player to click the roll button

        faceValue = dice.getFaceValue(); // sets the player roll value
        dice.getDiceButton().setEnabled(false);

        // Handle user response to board when roll value + current position of player is less than or equal to 30
        if((faceValue + player.getPosition())<=30) {
            messageLabel.setText("<html><h1>" + player.getName() + " rolled: " + faceValue +"</h1></html>");
            delayedPrint(1000);
            player.setPrevPosition(player.getPosition());
            player.setPosition(player.getPosition()+faceValue);

            //action when user clicked on wrong position to land
            wrongClick = new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    messageLabel.setText("<html><h1> No No! Wrong position to land </h1></html>");
                }
            };

            //action when user clicked on right position to land
            rightClick = new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if(player.getPrevPosition()!=0) {
                        board.getBoardArray().get(player.getPrevPosition()-1).setBorderPainted(false);//resets the previous button
                        board.getBoardArray().get(player.getPrevPosition()-1).setFocusPainted( false );
                        board.getBoardArray().get(player.getPrevPosition()-1).setContentAreaFilled(false);
                    }

                    board.getBoardArray().get(player.getPosition()-1).setBorderPainted(true);
                    board.getBoardArray().get(player.getPosition()-1).setBorder(BorderFactory.createLineBorder(Color.RED,2));

                   /* //if computer was in the same position, leaves the JButton as computer color
                    if(player.getPosition()!=0 && computer.getPosition()!=0 && player.getPrevPosition()==computer.getPosition()) {
                        board.getBoardArray().get(computer.getPosition()-1).setBorderPainted(true);
                        board.getBoardArray().get(computer.getPosition()-1).setBorder(BorderFactory.createLineBorder(Color.BLUE,2));
                    }*/
                    correctButtonClicked = true;
                    player.setBackground(new JButton().getBackground());
                    player.setBorder(new JButton().getBorder());
                }
            };


            correctButtonClicked = false;
            while(correctButtonClicked == false) {
                messageLabel.setText("<html><h1>" + player.getName() + " to position: " + player.getPosition() + "</h1></html>");
               
                board.getBoardArray().get(player.getPosition()-1).addActionListener(rightClick);

                //sets all other JButtons as wrong button
                for (int i = 0; i < board.getBoardArray().size(); i++) {
                    if (i != player.getPosition() - 1) {
                        board.getBoardArray().get(i).addActionListener(wrongClick);
                    }
                }

                delayedPrint(1000);
            }

            for (int i = 0; i < board.getBoardArray().size(); i++) {
                for(ActionListener g : board.getBoardArray().get(i).getActionListeners()) {
                    board.getBoardArray().get(i).removeActionListener(g);
                }
            }

            checkSnakesAndLadders(player); //checks for snakes and ladders for current player

        }
        else {

            difference = player.getPosition()+faceValue - 30;
            player.setPrevPosition(player.getPosition());
            player.setPosition(30-difference);

            //action when user clicked on wrong position to land
            wrongClick = new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    messageLabel.setText("<html><h1> No No! Wrong position to land </h1></html>");
                }
            };

            //action when user clicked on right position to land
            rightClick = new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if(player.getPrevPosition()!=0) {
                        board.getBoardArray().get(player.getPrevPosition()-1).setBorderPainted(false);//resets the previous button
                        board.getBoardArray().get(player.getPrevPosition()-1).setFocusPainted( false );
                        board.getBoardArray().get(player.getPrevPosition()-1).setContentAreaFilled(false);
                    }

                    board.getBoardArray().get(player.getPosition()-1).setBorderPainted(true);
                    board.getBoardArray().get(player.getPosition()-1).setBorder(BorderFactory.createLineBorder(Color.RED,2));

                    correctButtonClicked = true;
                    player.setBackground(new JButton().getBackground());
                    player.setBorder(new JButton().getBorder());
                }
            };


            correctButtonClicked = false;
            while(correctButtonClicked == false) {
                messageLabel.setText("<html><h1>" + player.getName() + " rolled: " + faceValue +"</h1></html>");
                delayedPrint(1000);
                messageLabel.setText("<html><h1> Oh.. It is more than 30. " + player.getName() + " going backwards to position: " + player.getPosition() + "</h1></html>");
                delayedPrint(1000);
                messageLabel.setText("<html><h1>" + player.getName() + " to position: " + player.getPosition() + "</h1></html>");
                delayedPrint(800);
                board.getBoardArray().get(player.getPosition()-1).addActionListener(rightClick);

                //sets all other JButtons as wrong button
                for (int i = 0; i < board.getBoardArray().size(); i++) {
                    if (i != player.getPosition() - 1) {
                        board.getBoardArray().get(i).addActionListener(wrongClick);
                    }
                }

                delayedPrint(1000);
            }

            for (int i = 0; i < board.getBoardArray().size(); i++) {
                for(ActionListener g : board.getBoardArray().get(i).getActionListeners()) {
                    board.getBoardArray().get(i).removeActionListener(g);
                }
            }
            /*messageLabel.setText("<html><h1>" + player.getName() + " rolled: " + faceValue + "</h1></html>");
            delayedPrint(1000);
            messageLabel.setText("<html><h1>So close! You must land exactly on the last square to win. </h1></html>");
            delayedPrint(800);
            player.setBackground(new JButton().getBackground());
            player.setBorder(new JButton().getBorder());*/
        }

        // if roll value is 6, allocate extra turn
        if(player.getPosition()<30) {
            if (faceValue == 6) {
                messageLabel.setText("<html><h1>" + player.getName() + " rolled: " + faceValue + " and earned an extra turn.</h1></html>");
                delayedPrint(1250);
                moreRoll = true;
            } else {
                moreRoll = false;
            }
        }
    }

    /**
     * checks if the current player position is on any start of snake or ladder
     * @param currentPlayer the player to check for snake and ladder
     */
    public void checkSnakesAndLadders(Player currentPlayer)
    {
        //checks for ladders after player moves to position
        for(int i=0;i<board.getLadderStarts().length;i++)
        {
            if(currentPlayer.getPosition()==board.getLadderStarts()[i])
            {
                currentPlayer.setPrevPosition(currentPlayer.getPosition());
                currentPlayer.setPosition(board.getLadderEnds()[i]);
                messageLabel.setText("<html><h1>Yeah! " + currentPlayer.getName() + " found a ladder, climbing to " + currentPlayer.getPosition() + "</h1></html>");
                delayedPrint(1200);
                board.getBoardArray().get(currentPlayer.getPrevPosition()-1).setBorderPainted(false);//resets the previous button
                board.getBoardArray().get(currentPlayer.getPrevPosition()-1).setFocusPainted( false );
                board.getBoardArray().get(currentPlayer.getPrevPosition()-1).setContentAreaFilled(false);
                board.getBoardArray().get(currentPlayer.getPosition()-1).setBorderPainted(true);
                if (currentPlayer.getName().equals("Player")) {
                    board.getBoardArray().get(currentPlayer.getPosition() - 1).setBorder(BorderFactory.createLineBorder(Color.RED, 2));
                } else {
                    board.getBoardArray().get(currentPlayer.getPosition() - 1).setBorder(BorderFactory.createLineBorder(Color.BLUE, 2));
                }

            }
        }

        //checks for snake after player moves to position
        for(int i=0;i<board.getSnakeStarts().length;i++)
        {
            if(currentPlayer.getPosition()==board.getSnakeStarts()[i])
            {
                currentPlayer.setPrevPosition(currentPlayer.getPosition());
                currentPlayer.setPosition(board.getSnakeEnds()[i]);
                messageLabel.setText("<html><h1> OH NO!!! There is a snake! " + currentPlayer.getName() + " falling down to " + currentPlayer.getPosition() + "</h1></html>");
                delayedPrint(1000);
                board.getBoardArray().get(currentPlayer.getPrevPosition()-1).setBorderPainted(false);//resets the previous button
                board.getBoardArray().get(currentPlayer.getPrevPosition()-1).setFocusPainted( false );
                board.getBoardArray().get(currentPlayer.getPrevPosition()-1).setContentAreaFilled(false);
                board.getBoardArray().get(currentPlayer.getPosition()-1).setBorderPainted(true);
                if (currentPlayer.getName().equals("Player")) {
                    board.getBoardArray().get(currentPlayer.getPosition() - 1).setBorder(BorderFactory.createLineBorder(Color.RED, 2));
                } else {
                    board.getBoardArray().get(currentPlayer.getPosition() - 1).setBorder(BorderFactory.createLineBorder(Color.BLUE, 2));
                }

            }
        }

        if (currentPlayer.getName().equals("Player") && currentPlayer.getPosition()== computer.getPosition()){
            computer.setPrevPosition(computer.getPosition());
            computer.setPosition(1);
            messageLabel.setText("<html><h1> OH NO!!! Crash! " + computer.getName() + " restart from " + computer.getPosition() + " again!</h1></html>");
            delayedPrint(1000);
            board.getBoardArray().get(computer.getPrevPosition()-1).setBorderPainted(false);//resets the previous button
            board.getBoardArray().get(computer.getPrevPosition()-1).setFocusPainted( false );
            board.getBoardArray().get(computer.getPrevPosition()-1).setContentAreaFilled(false);
            board.getBoardArray().get(computer.getPosition()-1).setBorderPainted(true);
            board.getBoardArray().get(computer.getPosition() - 1).setBorder(BorderFactory.createLineBorder(Color.BLUE, 2));
            board.getBoardArray().get(player.getPosition()-1).setBorderPainted(true);
            board.getBoardArray().get(player.getPosition() - 1).setBorder(BorderFactory.createLineBorder(Color.RED, 2));
        } else if (currentPlayer.getName().equals("Computer") && currentPlayer.getPosition()== player.getPosition()){
            player.setPrevPosition(player.getPosition());
            player.setPosition(1);
            messageLabel.setText("<html><h1> OH NO!!! Crash! " + player.getName() + " restart from " + player.getPosition() + " again!</h1></html>");
            delayedPrint(1000);
            board.getBoardArray().get(player.getPrevPosition()-1).setBorderPainted(false);//resets the previous button
            board.getBoardArray().get(player.getPrevPosition()-1).setFocusPainted( false );
            board.getBoardArray().get(player.getPrevPosition()-1).setContentAreaFilled(false);
            board.getBoardArray().get(player.getPosition()-1).setBorderPainted(true);
            board.getBoardArray().get(player.getPosition() - 1).setBorder(BorderFactory.createLineBorder(Color.RED, 2));
            board.getBoardArray().get(computer.getPosition()-1).setBorderPainted(true);
            board.getBoardArray().get(computer.getPosition() - 1).setBorder(BorderFactory.createLineBorder(Color.BLUE, 2));

        }
    }

    /**
     * delays the process by value entered as parameter
     * reference https://stackoverflow.com/questions/19882885/making-text-appear-delayed
     * @param milliSeconds millisecond as an int
     */
    public void delayedPrint(int milliSeconds){
        try{
            Thread.sleep(milliSeconds);
        }catch(InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    /**
     * @override
     * Runnable to overrides the current run method and start the game
     */
    public void run() {
        playGame();
    }

    /**
     * check win - a sequence to check if any player win the game
     */
    public void checkWin(){
        if(player.getPosition()==30 || computer.getPosition()==30) {
            moreRoll = false;
            winner = true;
        }
    }

    /**
     * a sequence method of SAL game to handle player's turn and winner
     */
    public void playGame() {
        Player newPlayer=null;
        //Roll the dice until the first player is decided 
        while(newPlayer==null) {
            newPlayer = chooseFirstPlayer(player,computer);
        }

        if(newPlayer!=null && newPlayer.equals(player)) {
            while(true) {
                //player's turn
                checkWin();
                if (winner){
                    break;
                }
                dice.getDiceButton().setEnabled(true);
                playerTurn();
                System.out.println(player.getPosition());
                while(moreRoll){
                    checkWin();
                    if (winner){
                        break;
                    }
                    dice.getDiceButton().setEnabled(true);
                    playerTurn();
                    System.out.println(player.getPosition());
                }
                //computer's turn
                
                dice.getDiceButton().setEnabled(false);
                checkWin();
                if (winner){
                   break;
                }

                computerTurn();
                System.out.println(computer.getPosition());
                while(moreRoll){
                    checkWin();
                    if (winner){
                        break;
                    }
                    computerTurn();
                    System.out.println(computer.getPosition());
                }
            }

            //check winner
            if(player.getPosition()==30) {
                messageLabel.setText("<html><h1> CONGRATULATIONS! You are escaped from the snake!</h1></html>");
            }
            if(computer.getPosition()==30) {
                messageLabel.setText("<html><h1> COMPUTER WIN... Good luck for the next game! </h1></html>");
            }            
        }
        else { //if the first turn is computer
            while(true) {

                //computer's turn
            
                dice.getDiceButton().setEnabled(false);
                checkWin();
                if (winner){
                    break;
                }
                computerTurn();
                System.out.println(computer.getPosition());

                while(moreRoll){
                    checkWin();
                    if (winner){
                        break;
                    }
                    computerTurn();
                    System.out.println(computer.getPosition());
                }
                //player's turn
                checkWin();
                if (winner){
                    break;
                }
                dice.getDiceButton().setEnabled(true);
                playerTurn();
                System.out.println(player.getPosition());
                checkWin();
                if (winner){
                    break;
                }
                while(moreRoll){
                    checkWin();
                    if (winner){
                        break;
                    }
                    dice.getDiceButton().setEnabled(true);
                    playerTurn();
                    System.out.println(player.getPosition());
                }
            }

            //check win
            if(computer.getPosition()==30) {
                messageLabel.setText("<html><h1> COMPUTER WIN... Good luck for the next game!  </h1></html>");
            }
            if(player.getPosition()==30) {
                messageLabel.setText("<html><h1> CONGRATULATIONS! You are escaped from the snake!</h1></html>");
            }
        }
        
    }


}