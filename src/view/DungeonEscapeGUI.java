/*
 * Matthew Allen Phillips
 * 10 February 2017
 * Primary GUI for Coin Counter.
 */

package view;

import java.awt.BorderLayout;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;

import model.Dungeon;

/**
 * Defines the primary GUI system for Dungeon Escape.
 * 
 * @author Matt Phillips
 * @version 10 February 2016
 */
public class DungeonEscapeGUI extends JFrame {

    /**
	 * Auto-generated (Eclipse IDE).
	 */
	private static final long serialVersionUID = -3684758772306301709L;

	/**
	 * Stores this GUI's dungeon.
	 */
	private Dungeon myDungeon;
	
	/**
	 * Stores this GUI's dungeon display.
	 */
	private DungeonDisplay myDungeonDisplay;
	
	/**
	 * Stores this GUI's input display.
	 */
	private InputDisplay myInputDisplay;
	
	/**
     * Creates a new GUI.
     */
    public DungeonEscapeGUI() {
        super("The Dungeon Escape Problem");
    }
    
    /**
     * Invokes the GUI startup.
     */
    public void start() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setIconImage(new ImageIcon("images/admin/header.png").getImage());
        
        myDungeon = new Dungeon(6, 6, 2);

        myDungeonDisplay = new DungeonDisplay(myDungeon.getMap(),
        		                              myDungeon.getSolutions());
        myInputDisplay = new InputDisplay(this);
        
        add(new HeaderDisplay(), BorderLayout.NORTH);
        add(myDungeonDisplay);
        add(myInputDisplay, BorderLayout.SOUTH);
        
        pack();
        setLocationRelativeTo(null);
        setResizable(false);
        setVisible(true);
    }
    
    /**
     * Builds a new dungeon and resets the GUI to it.
     * @param theH height of the new dungeon
     * @param theW width of the new dungeon
     * @param theG max goblins of the new dungeon
     */
    public void build(final int theH,
    		          final int theW,
    		          final int theG) {
    	
    	myDungeon = new Dungeon(theW, theH, theG);
    	
    	/* Resets the dungeon display's data. */
    	myDungeonDisplay.setDungeonMap(myDungeon.getMap());
    	myDungeonDisplay.setSolutionMap(myDungeon.getSolutions());
    	
    }

    /**
     * Steps the solution cursor forward.
     */
	public void solve() {
		myDungeonDisplay.solve();
	}
	
	/**
	 * Takes user cursor movement and shifts the display.
	 */
	public void movePlayer(final int theX, final int theY) {
		myDungeonDisplay.movePlayer(theX, theY);		
	}
}
