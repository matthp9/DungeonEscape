/**
 * Matthew Allen Phillips
 * 17 February 2017
 * Models a Dungeon object.
 */

package model;

import java.util.Arrays;
import java.util.Random;
import java.util.Stack;

/**
 * Defines a Dungeon object.
 * Holds: (i)   enemy count info,
 * 		  (ii)  solutions info,
 *        (iii) dimensional info.
 * 
 * @author Matt Phillips
 * @version 17 February 2017
 */
public class Dungeon {

	/**
	 * Used for filling goblin counts.
	 */
	private static Random MY_RANDOM = new Random();
	
	/**
	 * Stores the goblin count of each room.
	 */
	private int[][] myGoblinCounts;
	
	/**
	 * Stores the subproblem map.
	 */
	private int[][] mySolution;
	
	/**
	 * Stores the best possible moves for the player.
	 */
	private Stack<String> mySolutionStack;
	
	/**
	 * Stores the least amount of goblins the player
	 * will need to fight on the best possible path.
	 */
	private int myLeastGoblinsToFight;
	
	/**
	 * Stores the dungeon dimensions.
	 */
	private int myWidth, myHeight;
	
	/**
	 * Stores the max goblins in each room.
	 */
	private int myMaxGoblins;
	
	/**
	 * Constructs a new Dungeon.
	 * @param theWidth num cells in width.
	 * @param theHeight num cells in height.
	 * @param theGoblins max goblins per room.
	 */
	protected Dungeon(final int theWidth, 
					  final int theHeight, 
			          final int theGoblins) {
		/* Dungeon Dimensions. */
		myWidth   = theWidth;
		myHeight  = theHeight;
		
		/* Goblin Stats.*/
		myMaxGoblins   = theGoblins;
		myGoblinCounts = new int[myHeight][myWidth];
		
		/* Solutions. */
		mySolution            = new int[myHeight][myWidth];
		mySolutionStack       = new Stack<String>();
		myLeastGoblinsToFight = Integer.MAX_VALUE;
		
		this.randomize();
		this.solve();
	}
	
	/**
m	 * {@inheritDoc}
	 */
	@Override
	public String toString() {
		final StringBuilder b = new StringBuilder();
		for (final int[] i : myGoblinCounts) {
			b.append(Arrays.toString(i));
			b.append("\n");
		}
		return b.toString();
	}
	
	/**
	 * Returns a string rep. of the solution matrix.
	 */
	public String solutionToString() {
		final StringBuilder b = new StringBuilder();
		for (final int[] i : mySolution) {
			b.append(Arrays.toString(i));
			b.append("\n");
		}
		return b.toString();
	}
	
	/**
	 * Returns a string rep. of the solution matrix.
	 */
	public String solutionStackToString() {
		final Stack<String> aux = new Stack<String>();
		final StringBuilder b = new StringBuilder();
		b.append("{ ");
		while (!mySolutionStack.isEmpty()) {
			final String s = mySolutionStack.pop();
			aux.push(s);
			b.append(s);
			b.append(" ");
		}
		b.append("}");
		while (!aux.isEmpty()) {
			mySolutionStack.push(aux.pop());
		}
		return b.toString();
	}
	
	/**
	 * Gives each room in the dungeon a random amount
	 * of goblins up to myGoblins maximum.
	 */
	private void randomize() {
		for (final int[] a : myGoblinCounts) {
			for (int i = 0; i < a.length; i++) {
				a[i] = MY_RANDOM.nextInt(myMaxGoblins);
			}
		}
	}
	
	/**
	 * **Core Dynamic Programming Algorithm.**
	 * * Uses a dynamic approach to find best
	 *   path through Dungeon; that is, the path
	 *   with the least goblins encountered.
	 * * Runs in a complexity of the following:
	 *   myWidth * myHeight.
	 */
	private void solve() {
		/* Let P[1...n][1...m] be an empty Dungeon. */
		mySolution[0][0]   = myGoblinCounts[0][0];
		
		/* Directions to move from.*/
		int left = Integer.MAX_VALUE;
		int up   = Integer.MAX_VALUE;
		
		for (int i = 0; i < myHeight; i++) {
			for (int j = 0; j < myWidth; j++) {
				left = Integer.MAX_VALUE;
				up   = Integer.MAX_VALUE;
				if (cb(i - 1, j)) {
					up = mySolution[i - 1][j];
				}
				if (cb(i, j - 1)) {
					left = mySolution[i][j - 1];
				}
				int last = Math.min(up, left);
				if (left > 100 && up > 100) {
					last = 0;
				}
				mySolution[i][j] = last + myGoblinCounts[i][j];
			}
		}
		
		retrace();
	}
	
	/**
	 * Uses a solution matrix to retrace
	 * the best path into a Stack.
	 */
	private void retrace() {
		mySolutionStack = new Stack<String>();
		int i = myHeight - 1, j = myWidth - 1;
		while ((i > 0) || (j > 0)) {
			final int thisRoom = myGoblinCounts[i][j];
			final int upToHere = mySolution[i][j];
			
			final int upIdx = Math.max(i - 1, 0);
			final int leftIdx = Math.max(0, j - 1);
			
			if (!cb(i - 1, j)) {
				mySolutionStack.push("Right");
				j -= 1;
			} else if (!cb(i, j - 1)) {
				mySolutionStack.push("Down");
				i -= 1;
			} else if (upToHere == (mySolution[upIdx][j] + thisRoom)) {
				mySolutionStack.push("Down");
				i -= 1;
			} else if (upToHere == (mySolution[i][leftIdx] + thisRoom)) {
				mySolutionStack.push("Right");
				j -= 1;
			}
		}
		
		System.out.println("Done");
	}
	
	/**
	 * Check bounds function. Ensures that values
	 * passed in are within the Dungeon bounds.
	 */
	private boolean cb(final int theH, final int theW) {
		if (theW < 0 || theH < 0) {
			return false;
		} else if (theW >= myWidth || theH >= myHeight) {
			return false;
		}
		return true;
	}
}
