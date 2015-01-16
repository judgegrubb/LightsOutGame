package cs1410;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

// This is the gui class
public class LightsOut extends JFrame {
	
	// member variable to store the board
	private LightsOutBoard board;
	// arraylist to store all the buttons in for easy access anywhere in the program
	// as global variables
	private ArrayList<JButton> buttons = new ArrayList<JButton>();
	// boolean for storing whether or not the game
	// is in manual settings mode
	private boolean manualBool;
	// access to the "Enter/Exit Manual Setup" button
	private JButton manual;
	// boolean to tell whether the program is currently
	// reseting with a brand new board
	// to solve.
	private boolean resetBool;
	// label to display the number of moves
	// the user has made on this try
	private JLabel moves = new JLabel();

	public static void main(String[] args) {
		
		// this makes JButton and setBackground work properly
		// on macs. I hope this doesn't mess anything up on 
		// MS Windows or Linux. :)
		try {
	        UIManager.setLookAndFeel(
	            UIManager.getCrossPlatformLookAndFeelClassName());
	    } 
	    catch (UnsupportedLookAndFeelException e) {
	    }
	    catch (ClassNotFoundException e) {
	    }
	    catch (InstantiationException e) {
	    }
	    catch (IllegalAccessException e) {
	    }
		// start the program
		new LightsOut();
	}
	
	/**
	 * initiates the program
	 */
	public LightsOut() {
		// basic JFrame stuff and gui settings
		JFrame frame = new JFrame();
		frame.setTitle("Lights Out");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		// make the main panel everything will be in
		// basic panel stuff
		JPanel mainPanel = new JPanel();
        frame.setContentPane(mainPanel);
        mainPanel.setPreferredSize(new Dimension(500, 510));
        mainPanel.setLayout(new BorderLayout());
        
        // create the board itself and passes it the controller and the
        // gui. Adds it back to the overall panel.
        board = new LightsOutBoard(new LightsOutGame(this), this);
        mainPanel.add(board, "Center");
        
        // creates the reset button and sets it up to work
        JButton reset = new JButton("Start New Game");
        reset.setName("reset");
        reset.addActionListener(new LightsOutGame(this));
        resetBool = false;
        
        // creates the manual setup button and sets it up to work
        manual = new JButton("Enter Manual Setup");
        manual.setName("manual");
        manualBool = false;
        manual.addActionListener(new LightsOutGame(this));
        moves.setText("Moves: 0");
        
        // adds the moves, manual and reset buttons to their
        // own grid Layout to make them format properly then
        // adds them back to the mainPanel
        JPanel gridPanel = new JPanel();
        gridPanel.setLayout(new GridLayout(2,2));
        gridPanel.add(manual);
        gridPanel.add(reset);
        gridPanel.add(moves);
        mainPanel.add(gridPanel, "South");
        
        // make it appear! (yay!)
        frame.pack();
        frame.setVisible(true);
	}
	
	/**
	 * updates the moveCount label
	 * @param moveCount
	 */
	public void setMoves(int moveCount) {
		moves.setText("Moves: " + moveCount);
	}
	
	/**
	 * gets whether the game is currently resetting
	 * @return
	 */
	public boolean getResetBool() {
		return resetBool;
	}
	
	/**
	 * sets the status of whether we're resetting currently
	 * @param b
	 */
	public void setResetBool(boolean b) {
		resetBool = b;
	}
	
	/**
	 * calls the reset function in the board so the
	 * controller has access to it
	 */
	public void reset() {
		board.reset();
	}
	
	/** 
	 * returns whether we are currently in the manual setup
	 * @return
	 */
	public boolean getManualBool() {
		return manualBool;
	}
	
	/**
	 * switches the status of whether manual is on
	 * and switches the manual setup button's label
	 */
	public void setManualBool(boolean m) {
		if (m) {
			manual.setText("Enter Manual Setup");
			manualBool = false;
		} else {
			manual.setText("Exit Manual Setup");
			manualBool = true;
		}
	}
	
	/**
	 * changes the color of the indicated
	 * button to the opposite of its current
	 * state
	 */
	public void changeColor(int pos) {
		JButton b = buttons.get(pos);
		Color old = b.getBackground();
		if (old.equals(Color.WHITE)) {
			b.setBackground(Color.BLACK);
		} else {
			b.setBackground(Color.WHITE);
		}
	}
	
	/**
	 * adds a button to the overall arraylist
	 * of buttons
	 */
	public void addButtons(JButton b) {
		buttons.add(b);
	}
	
	/**
	 * returns the member variables of buttons so the
	 * controller and board can access them
	 */
	public ArrayList<JButton> getButtons() {
		return buttons;
	}
}

// this is the board class
class LightsOutBoard extends JPanel {
	
	// member variable to store the controller in
	private LightsOutGame controller;
	// member variable to store the gui in
	private LightsOut gui;
	
	
	/**
	 * initialize the board with the controller/actionlistener and the
	 * gui stored in the proper menu variables and creates the 25 buttons
	 * needed for the game.
	 * @param listener
	 * @param lightsOut
	 */
	public LightsOutBoard(LightsOutGame listener, LightsOut lightsOut) {
		gui = lightsOut;
		controller = listener;
		setLayout(new GridLayout(5,5));
		
		// creates the 25 buttons, set's the controller as their
		// actionListener and adds them to the gui's member variable
		// for ease of access
		for (int y = 0; y < 5; y++) {
			for (int x = 0; x < 5; x++) {
				JButton b = new JButton();
				b.setOpaque(true);
				b.setBackground(Color.BLACK);
				b.setName("" + y + x);
				add(b);
				b.addActionListener(listener);
				gui.addButtons(b);
			}
		}
		
		// calls the reset class to provide an initial game to solve
		reset();
	}

	/**
	 * randomly sets up the board for the user to solve
	 */
	public void reset() {
		// makes sure that everything knows that it's currently being rest
		// this is not the user doing this.
		gui.setResetBool(true);
		Random rand1 = new Random();
		int randInt1 = rand1.nextInt(5);
		
		// does this a random amount of times
		while (randInt1 > 0) {
			Random rand2 = new Random();
			int randInt2 = 1;
			// calls the move method on random spaces
			// a random amount of time
			while (randInt2 < 25) {
				controller.move(randInt2);
				randInt2 += rand2.nextInt(5);
			}
			randInt1--;
		}
		
		// makes sure that move count and the
		// move label goes back to 0
		controller.resetMoves();
		gui.setMoves(0);
		
		// resetting is now done. Let everything know.
		gui.setResetBool(false);
	}
}
