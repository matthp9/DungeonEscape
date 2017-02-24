/**
 * Matthew Allen Phillips
 * 17 February 2017
 * Defines the Dungeon data visuals.
 */

package view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.Arrays;
import java.util.Stack;
import java.util.concurrent.TimeUnit;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

/**
 * A primary display for Dungeon data
 * in the front end.
 * 
 * @author Matt Phillips
 * @version 22 February 2017
 */
public class DungeonDisplay extends JPanel {

   /**
    * Font used in the Dungeon cells.
    */
   private static final Font FONT = new Font("Font L", Font.PLAIN, 12);
   
   /**
    * Font used in the Dungeon algorithm.
    */
   private static final Font FONT_LG = new Font("Font L", Font.PLAIN, 20);
    
    /**
     * A constant for limiting display calculations in width.
     */
    private static final int WI_RES = 1200;
    
    /**
     * A constant for limiting display calculations in width.
     */
    private static final int HI_RES = 400;
    
    /**
     * Auto-generated (Eclipse IDE).
     */
    private static final long serialVersionUID = -2570358028879503043L;
    
    /**
     * Holds the Dungeon map.
     */
    private int[][] myDungeonMap;
    
    /**
     * Holds the Dungeon's solution map.
     */
    private int[][] mySolutionMap;	  
    
    /**
     * Holds a progress map of best-case solutions.
     */
    private int[][] myProgressMap;
    
    /**
     * Holds the Dungeon's solution stack.
     */
    private Stack<String> mySolutionStack;
    
    /**
     * Stores a 2D point to use as a map solution cursor.
     */
    private Point myCursor;
    
    /**
     * Stores the user's cursor.
     */
    private Point myPlayer;
    
    /**
     * Tracks whether the cursor is tracing or solving.
     */
    private boolean myState;
	    
    /**
     * Constructs a new Dungeon Display.
     */
    protected DungeonDisplay(final int[][] theDungeonMap,
    		                 final int[][] theSolutionMap) {
    	myDungeonMap  = theDungeonMap;
    	mySolutionMap = theSolutionMap;
    	mySolutionStack = new Stack<String>();
    	
    	myProgressMap = new int[theDungeonMap.length][theDungeonMap[0].length];
    	initProgressMap();
    	
    	myCursor = new Point(0, 0);
    	myPlayer = new Point(0, 0);
    	
    	myState  = false;
	    	
    	setBackground(Color.GRAY.darker().darker());
        setPreferredSize(new Dimension(WI_RES, HI_RES + 120));
                
        repaint();
    }
	    
    /**
     * {@inheritDoc}
     */
    @Override
    public void paintComponent(final Graphics theGraphics) {
        super.paintComponent(theGraphics);
        final Graphics2D g = (Graphics2D) theGraphics;
        g.setFont(FONT);
        g.setColor(Color.WHITE);
        
        g.drawImage(new ImageIcon("images/art/background.png").getImage(), 
        		                            0, 0, getWidth(), getHeight(), null);
        
        g.drawString("Your Dungeon Map", 10, 20);
	        
        final int mapH = myDungeonMap.length;
        final int mapW = myDungeonMap[0].length;
        
        final int maxdm = Math.max(mapH, mapW);
        final int scale = Math.min((HI_RES / maxdm), 
        		                   (WI_RES / maxdm));
        final int offset = scale / 6;
        
        int x = 0; // Track horizontal placement.
        int y = 30; // Track vertical placement.
        
        /* Display the regular dungeon map. */
        for (int i = 0; i < mapH; i++) {
        	for (int j = 0; j < mapW; j++) {
        		x = (j * scale) + 10;
        		y = (i * scale) + 40;
        		if (myDungeonMap[i][j] > 0) {
        			g.drawImage(new ImageIcon("images/art/goblin.png").getImage(), x, y, 
            				scale, scale, null);
        		}
        		g.drawString("" + myDungeonMap[i][j], x + offset, 
        				                              y + scale - offset);
        		g.setColor(Color.WHITE);
        		g.drawRect(x, y, scale, scale);
        	}
        }
        
        /* Draws player cursor, finish line cursor. */
        g.setColor(new Color(255, 254, 206, 100));
        g.fillRect(10 + (scale * myPlayer.x), 40 + (scale * myPlayer.y),
        		   scale, scale);
        g.setColor(new Color(255, 151, 99, 100));
        g.fillRect(x, 40 + scale * (myDungeonMap.length - 1), scale, scale);
                
        
        int margin = x + scale + 50;
        
        /* Draws solution cursor. */
        g.setColor(new Color(255, 224, 219, 120));
		g.fillRect(margin + myCursor.x * scale, 40 + myCursor.y * scale, 
				   scale, scale);
		
		g.setColor(new Color(163, 255, 255, 100));
        /* Draws solution helpers during solving. */
		if (cb(myCursor.y - 1, myCursor.x)) {
	       	g.fillRect(margin + myCursor.x * scale, 40 + (myCursor.y - 1) * scale, 
	 			   scale, scale);
		}
	    if (cb(myCursor.y, myCursor.x - 1)) {
	       	g.fillRect(margin + (myCursor.x - 1) * scale, 40 + myCursor.y * scale, 
	 			   scale, scale);
	    }
		
        g.setColor(Color.WHITE);
        
        g.drawString("Least Goblins to Any Point", margin, 20);
        
        final int solH = mySolutionMap.length;
        final int solW = mySolutionMap[0].length;
        
        for (int i = 0; i < solH; i++) {
        	for (int j = 0; j < solW; j++) {
        		x = (j * scale) + margin;
        		y = (i * scale) + 40;
        		if (mySolutionMap[i][j] == -1) {
        			g.drawString("?", x + offset, 
                            y + scale - offset);
        		} else {
        			g.drawString("" + mySolutionMap[i][j], x + offset, 
        				                          y + scale - offset);
        		}
        		g.setColor(Color.WHITE);
        		g.drawRect(x, y, scale, scale);
        	}
        }
        
        margin = x + scale + 50;
        
        x = margin;
        y = 10;
        
        final int yu = 40;
        final int yl = HI_RES + 20;
        final int xr = x + 150;
        
        /* Draw stack. */
       g.drawLine(x, yu, x, yl);
       g.drawLine(x, yl, xr, yl);
       g.drawLine(xr, yl, xr, yu);
       g.drawString("Move Stack", x + 40, yl + 20);
       
       g.setColor(Color.GREEN);
       final String[] moves = solutionStackToString().split(" ");
       for (int i = 0; i < moves.length; i++) {
    	   g.drawString(moves[i], x + 60, yl - (moves.length * 14) + (14 * i));
       }
       
       if (myState && myCursor.x == 0 && myCursor.y == 0) {
    	   System.out.println("X");
    	   myState = false;
       }
       
       /* Draws algorithm steps. */
       g.setFont(FONT_LG);
       g.setColor(Color.WHITE);

       if (myState) {
    	   g.drawString("Now that the Solution Map is complete,"
    			        + " the algorithm retraces the best choices.", 
    			        10, HI_RES + 70);
	       g.setColor(new Color(255, 224, 219));
	       
	       String betterRoom = "- - -";
	       int betterValue = Integer.MAX_VALUE;
	       if (cb(myCursor.y - 1, myCursor.x)) {
		       	betterRoom = "(" + (myCursor.x + 1) + ", " + (myCursor.y) + ")";
		       	betterValue = mySolutionMap[myCursor.y - 1][myCursor.x];
			}
		    if (cb(myCursor.y, myCursor.x - 1)) {
		    	if (mySolutionMap[myCursor.y][myCursor.x - 1] < 
		    	    betterValue) {
		    		betterRoom = "(" + (myCursor.x) + ", " + (myCursor.y + 1) + ")";
		    	}
		    }
		    
		    String goblinSet = "";
		    
		    if (cb(myCursor.y - 1, myCursor.x)) {
		       	goblinSet += mySolutionMap[myCursor.y - 1][myCursor.x];
		       	goblinSet += " ";
			}
		    if (cb(myCursor.y, myCursor.x - 1)) {
		    	goblinSet += mySolutionMap[myCursor.y][myCursor.x - 1];
		    	goblinSet += " ";
		    }
		    
		    g.drawString("We push " + mySolutionStack.peek() + " onto the Move Stack."
		    		     + " Now we are considering the better (less total goblins)"
		    		     + " sub-solution from the set { " + goblinSet + "}.", 
		    		     10, HI_RES + 95);
	       
    	   g.drawString("The best sub-solution directly prior to cell ("
    	 	             + (myCursor.x + 1) + ", " + (myCursor.y + 1)
    		             + ") is in cell "
    	 	             + betterRoom + ".",
			        10, HI_RES + 120);
			g.setColor(new Color(163, 255, 255));

       } else {
	       g.setColor(new Color(255, 224, 219));
	       g.drawString("The least goblins fought to get to room (" 
	                    + (myCursor.x + 1) + ", " + (myCursor.y + 1)
	                    + ") is " + mySolutionMap[myCursor.y][myCursor.x]
	                    + " goblin(s).",
	    		        10, HI_RES + 70);
			g.setColor(new Color(163, 255, 255));
			
			String append = ", to ";
			String leftRoomCount = "";
			String upRoomCount = "";
			
			if (cb(myCursor.y - 1, myCursor.x)) {
		       	leftRoomCount += mySolutionMap[myCursor.y - 1][myCursor.x];
		       	leftRoomCount += " ";
			}
		    if (cb(myCursor.y, myCursor.x - 1)) {
		    	upRoomCount += mySolutionMap[myCursor.y][myCursor.x - 1];
		    	upRoomCount += " ";
		    }
		    
		    if (myCursor.x > 0 || myCursor.y > 0) {
				append += "the minimum of the set { "
					    + leftRoomCount + upRoomCount + "}.";
			} else {
				append += "zero.";
			}
	
			g.drawString("We calculated this value by adding this room's"
					    + " goblin count, " 
					    + myDungeonMap[myCursor.y][myCursor.x]
					    + append,
			            10, HI_RES + 95);
			
			g.setColor(Color.WHITE);
			g.drawString("In the Solution Map, we assign cell (" 
	                    + (myCursor.x + 1) + ", " + (myCursor.y + 1)
	                    + ") a value of "
	                    + mySolutionMap[myCursor.y][myCursor.x]
	                    + " to be used for the rest of the solution.",
					    10, HI_RES + 120);
       }
    }

    /**
     * Setter...
     * @param theMap new dungeon map to set.
     */
    public void setDungeonMap(final int[][] theMap) {
    	myDungeonMap = theMap;
    	
    	repaint();
    }
    
    /**
     * Setter...
     * @param theMap new dungeon solution map to set.
     */
    public void setSolutionMap(final int[][] theMap) {
    	mySolutionMap = theMap;
    	
    	repaint();
    }
    
    /**
     * Setter...
     * @param theStack new solution stack to set.
     */
    public void setSolutionStack(final Stack<String> theStack) {
    	mySolutionStack = theStack;
    	
    	repaint();
    }
	
	/**
	 * Utility function. Steps the grid solver forward one step.
	 */
	public void solve() {
		int w = myDungeonMap[0].length;
		int h = myDungeonMap.length;

		if (myState) {
			trace();
		} else {
			if (myCursor.x < w && myCursor.y < h) {
				if (myCursor.x == w - 1) {
					if (myCursor.y != h - 1) {
						myCursor.y++;
						myCursor.x = 0;
					} else {
						myState = !myState;
						mySolutionStack.clear();
						trace();
					}
				} else if (myCursor.y != h) {
					myCursor.x++;
				}
			} 
		}
		
		repaint();
	}
	
	/**
	 * Moves the player's cursor by an input amount.
	 * @param theX x value to move (+ = right, - = left).
	 * @param theY y value to move (+ = down, - = up).
	 */
	public void movePlayer(final int theX, final int theY) {
		boolean flag = false;
		if (myPlayer.x < myDungeonMap[0].length - 1) {
			myPlayer.x += theX;
			flag = true;
		}
		if (myPlayer.y < myDungeonMap.length - 1) {
			myPlayer.y += theY;
			flag = true;
		}
		if (!flag) {
			myPlayer.x = 0;
			myPlayer.y = 0;
		}
		repaint();
	}
	
	/**
	 * Utility function. Steps the grid tracer back one step.
	 */
	private void trace() {
		
		int j = myCursor.x;
		int i = myCursor.y;
		
		final int thisRoom = myDungeonMap[i][j];
		final int upToHere = mySolutionMap[i][j];
		
		final int upIdx = Math.max(i - 1, 0);
		final int leftIdx = Math.max(0, j - 1);
		
		if (!cb(i - 1, j)) {
			mySolutionStack.push("Right");
			myCursor.x -= 1;
		} else if (!cb(i, j - 1)) {
			mySolutionStack.push("Down");
			myCursor.y -= 1;
		} else if (upToHere == (mySolutionMap[upIdx][j] + thisRoom)) {
			mySolutionStack.push("Down");
			myCursor.y -= 1;
		} else if (upToHere == (mySolutionMap[i][leftIdx] + thisRoom)) {
			mySolutionStack.push("Right");
			myCursor.x -= 1;
		}
		
		repaint();
	}
	
	/**
	 * Check bounds function. Ensures that values
	 * passed in are within the Dungeon bounds.
	 */
	private boolean cb(final int theH, final int theW) {
		if (theW < 0 || theH < 0) {
			return false;
		} else if (theW >= myDungeonMap.length 
				|| theH >= myDungeonMap[0].length) {
			return false;
		}
		return true;
	}
	
	/**
	 * Initializer function. Fills progress map with -1.
	 */
	private void initProgressMap() {
		for (int a = 0; a < myProgressMap.length; a++) {
    		Arrays.fill(myProgressMap[a], -1);
    	}
	}
	
	/**
	 * Returns a string rep. of the solution matrix.
	 */
	private String solutionStackToString() {
		final Stack<String> aux = new Stack<String>();
		final StringBuilder b = new StringBuilder();
		while (!mySolutionStack.isEmpty()) {
			final String s = mySolutionStack.pop();
			aux.push(s);
			b.append(s);
			b.append(" ");
		}
		while (!aux.isEmpty()) {
			mySolutionStack.push(aux.pop());
		}
		return b.toString();
	}
}