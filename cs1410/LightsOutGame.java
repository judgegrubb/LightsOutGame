package cs1410;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JOptionPane;

// This is the controller class
public class LightsOutGame implements ActionListener {
	
	/**
	 * member variable for the LightsOut gui
	 * that initiates this game/controller
	 */
	private LightsOut gui;
	/**
	 * member variable to keep track of the
	 * number of moves a user makes on this particular
	 * game.
	 */
	private int moves;
	
	/**
	 * initializes this controller
	 * sets the income LightsOut object to the gui
	 * variable and initiates moves to 0
	 * @param lightsOut
	 */
	public LightsOutGame(LightsOut lightsOut) {
		this.gui = lightsOut;
		moves = 0;
	}

	
	/**
	 * the default method called when any of the buttons
	 * in the LightOut class are clicked.
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		// Get the button that was clicked
		JButton button = (JButton)e.getSource();

		// Check if it was the start new game button
		// and if so reset the game.
		if (button.getName().equals("reset")) {
			gui.reset();
		// check if it was the enter manual setups
		// if so, changes the state so that only 1 button
		// at a time is changed.
		} else if (button.getText().equals("Enter Manual Setup")) {
			gui.setManualBool(false);
		// if the button is exit manual setup
		// changes the game back to normal mode
		} else if (button.getText().equals("Exit Manual Setup")) {
			gui.setManualBool(true);
		} else {
			
			// else it was a game button
			// here it figures out which one it was
			// and calls move() on that button.
			int row = button.getName().charAt(0) - '0';
			int col = button.getName().charAt(1) - '0';
			try {
				move(row*5+col);
			}
			catch (IllegalArgumentException ex) {
				return;
			}
		}
		
	}
	
	/**
	 * takes in the position of the button clicked
	 * by the user and depending on manual says which
	 * buttons need to change their colors.
	 * @param pos
	 */
	public void move(int pos) {
		if (pos < 0 || pos > 24) {
			throw new IllegalArgumentException();
		}
		
		gui.changeColor(pos);
		
		// only implements these ones if it's
		// not in manual setup mode.
		if (!gui.getManualBool()) {
			
			// makes sure there is a square above it 
			// and changes it if there is
			if (pos > 4) {
				gui.changeColor(pos - 5);
			}
			
			// makes sure there is a square below it
			// and changes it if there is
			if (pos < 20) {
				gui.changeColor(pos + 5);
			}
			
			// makes sure there is a square to the left
			// and changes it if there is
			if (pos % 5 != 0) {
				if (pos > 0) {
					gui.changeColor(pos - 1);
				}
			}
			
			// makes sure there is a square to the right
			// and changes it if there is
			if ((pos + 1) % 5 != 0) {
				if (pos < 24) {
					gui.changeColor(pos + 1);
				}
			}
		}
		
		// Makes sure that the game is not in
		// the middle of resetting
		if (!gui.getResetBool()) {
			
			// increases the movecount
			moves++;
			// updates the moves label
			gui.setMoves(moves);
			
			// checks if the person has won now 
			// and displays a message box if they have
			if (isWon()) {
				JOptionPane.showMessageDialog(gui, "Congratulations! You win!");
			}
		}
	}
	
	/**
	 * returns the move count
	 * for the label that tells the user their
	 * number of moves.
	 * @return
	 */
	public int getMoves() {
		return moves;
	}
	
	/**
	 * resets the number of moves
	 * mainly used when the user clicks the
	 * "Start New Game" button
	 */
	public void resetMoves() {
		moves = 0;
	}
	
	/**
	 * checks whether all the buttons are dark and if so
	 * returns true because you've won!
	 * @return
	 */
	private boolean isWon() {
		// gets the array of buttons to change
		ArrayList<JButton> buttons = gui.getButtons();
		// checks if any of them are on
		// if they are returns false
		// else returns true
		for (JButton b : buttons) {
			if (b.getBackground().equals(Color.WHITE)) {
				return false;
			}
		}
		return true;
	}
}
