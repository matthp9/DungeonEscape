/**
 * Matthew Allen Phillips
 * 11 February 2017
 * Provides currency input.
 */

package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JPanel;

/**
 * Allows the user to manually enter...
 * Dungeon dimensions, statistics, and moves.
 * 
 * @author Matt Phillips
 * @version 22 February 2017
 */
public class InputDisplay extends JPanel {

    /**
     * Font used in the input buttons.
     */
    private static final Font FONT = 
                    new Font("Font L", Font.PLAIN, 20);
    
    /**
     * Font used in the input buttons.
     */
    private static final Font INSTRUCTIONS = 
                    new Font("Font L", Font.PLAIN, 16);
    
    /**
     * Auto-generated (Eclipse IDE).
     */
    private static final long serialVersionUID = 1444827836717787490L;
    
    /**
     * Tracks the user's next value to add.
     */
    private StringBuilder myCurrentValueToAdd;
    
    /**
     * Stores new dungeon width.
     */
    private int myDungeonWidth;
    
    /**
     * Stores new dungeon height.
     */
    private int myDungeonHeight;
    
    /**
     * Stores new dungeon max goblins per room.
     */
    private int myDungeonMaxGoblins;
    
    /**
     * Tethers this input display to a GUI.
     */
    private final DungeonEscapeGUI myGUI;
    
    /**
     * Internal display panel.
     */
    private JPanel myPanel;

    public InputDisplay(final DungeonEscapeGUI theGUI) {
        
        myCurrentValueToAdd = new StringBuilder(4);
        
        myPanel             = new JPanel();
        myGUI               = theGUI;
        
        myDungeonWidth = 6;
        myDungeonHeight = 6;
        myDungeonMaxGoblins = 2;
        
        setPreferredSize(new Dimension(1300, 300));
        setBackground(Color.GRAY.darker().darker().darker());
        
        init();
        
        repaint();
    }
    
    /**
     * {@inheritDoc}
     */
    public void paintComponent(final Graphics theGraphics) {
        super.paintComponent(theGraphics);
        final Graphics2D g = (Graphics2D) theGraphics;
        
        int x1 = 50;
        int y1 = 10;
        int y2 = 110;
        int y3 = 160;
        int y4 = 210;
        
        int offsetX  = 10;
        int offsetY  = 30;
                        
        int w  = 400;
        int h = 40;
        
        g.setColor(Color.WHITE);
        g.fillRect(x1, y1, w, h);
        g.fillRect(x1, y2, w, h);
        g.fillRect(x1, y3, w, h);
        g.fillRect(x1, y4, w, h);
        
        g.setColor(Color.BLACK);
        g.drawRect(x1, y1, w, h);
        g.drawRect(x1, y2, w, h);
        g.drawRect(x1, y3, w, h);
        g.drawRect(x1, y4, w, h);
        
        g.setFont(FONT);
        g.drawString("Input: " + myCurrentValueToAdd.toString(), 
                     x1 + offsetX, y1 + offsetY);
        g.drawString("My Dungeon Width (2-10): " + myDungeonWidth,
                     x1 + offsetX, y2 + offsetY);
        g.drawString("My Dungeon Height (2-10): " + myDungeonHeight,
                     x1 + offsetX, y3 + offsetY);
        g.drawString("My Max Goblins per Room (1-10): " + myDungeonMaxGoblins,
                x1 + offsetX, y4 + offsetY);
        
        g.setFont(INSTRUCTIONS);
        g.setColor(Color.WHITE);
        g.drawString("Instructions: " , 
                     850, y1 + offsetY);
        g.drawString("1. Choose dungeon dimensions.", 
                     850, y1 + offsetY + 20);
        g.drawString("   * HI sets input as the dungeon height", 
                     850, y1 + offsetY + 40);
        g.drawString("   * WI sets input as the dungeon width", 
                     850, y1 + offsetY + 60);
        g.drawString("2. Choose max goblins per dungeon room.", 
                     850, y1 + offsetY + 80);
        g.drawString("   * GOB sets input as max goblins per room.", 
                     850, y1 + offsetY + 100);
        g.drawString("3. Select GO! to build your dungeon.", 
                     850, y1 + offsetY + 120);
        g.drawString("   * CLR clears your input.", 
                     850, y1 + offsetY + 140);
        g.drawString("   * SOL steps you through the solution.", 
                     850, y1 + offsetY + 160);
        g.drawString("4. Use the right (>) and down (v) cursors", 
                     850, y1 + offsetY + 180);
        g.drawString("   to step through your dungeon on your own!", 
                     850, y1 + offsetY + 200);
        g.drawRect(825, y1, 370, 265);
    }
    
    /**
     * Initializes the display. Gives listeners
     * to buttons, adds buttons to the input panel.
     */
    private void init() {
        final ActionListener insert = new InsertAction();
        final ClearAction clear     = new ClearAction();
        final HiAction hi           = new HiAction();
        final WiAction wi           = new WiAction();
        final GobAction gob         = new GobAction();
        final RightAction right     = new RightAction();
        final DownAction down       = new DownAction();
        final SolveAction sol       = new SolveAction();
        final GoAction go           = new GoAction();
        
        myPanel.setLayout(new GridLayout(0, 3));
        
        addButton("7", insert, Color.WHITE);
        addButton("8", insert, Color.WHITE);
        addButton("9", insert, Color.WHITE);
        addButton("4", insert, Color.WHITE);
        addButton("5", insert, Color.WHITE);
        addButton("6", insert, Color.WHITE);
        addButton("1", insert, Color.WHITE);
        addButton("2", insert, Color.WHITE);
        addButton("3", insert, Color.WHITE);
        
        addButton("CLR", clear, new Color(230, 242, 255));
        addButton("0", insert, Color.WHITE);
        addButton("GO!", go, new Color(230, 255, 230));
        
        addButton("HI", hi, new Color(255, 255, 230));
        addButton("WI", wi, new Color(255, 230, 255));
        addButton("GOB", gob, new Color(153, 230, 153));
        
        addButton(">", right, new Color(235, 250, 250));
        addButton("SOL", sol, new Color(235, 250, 250));
        addButton("v", down, new Color(255, 230, 230));
        
        add(myPanel, BorderLayout.WEST);
    }
    
    /**
     * Adds a button to the center panel.
     * @param theLabel label to stick on the button
     * @param theListener action taken by the button
     */
    private final void addButton(final String theLabel, 
                                 final ActionListener theListener,
                                 final Color theColor) {
        final JButton button = new JButton(theLabel);
        if (theListener == null) {
            button.setEnabled(false);
        }
        button.addActionListener(theListener);
        button.setFocusable(false);
        button.setFont(FONT);
        button.setPreferredSize(new Dimension(80, 45));
        button.setBackground(theColor);
        
        myPanel.add(button);
    }
    
    
    /* Listener Classes. */
    
    /**
     * Listens for numerical input (to the GUI console).
     * @author Matt Phillips
     * @version 13 February 2017
     */
    private final class InsertAction implements ActionListener {

        @Override
        public void actionPerformed(final ActionEvent theEvent) {
            final String input = theEvent.getActionCommand();
            if (myCurrentValueToAdd.length() < 2) {
                myCurrentValueToAdd.append(input);
            }
            
            repaint();
        }
    }
    
    /**
     * Listens for "CLR" button input.
     * @author Matt Phillips
     * @version 13 February 2017
     */
    private final class ClearAction implements ActionListener {

        @Override
        public void actionPerformed(final ActionEvent theEvent) {
            myCurrentValueToAdd = new StringBuilder(4);
            
            repaint();
        }
    }
    
    /**
     * Listens for "HI" button input.
     * @author Matt Phillips
     * @version 22 February 2017
     */
    private final class HiAction implements ActionListener {

        @Override
        public void actionPerformed(final ActionEvent theEvent) {
        	int toAdd = -1;
            try {
                toAdd = Integer.parseInt(myCurrentValueToAdd.toString());
            } catch (final Exception e) {
                // Failed, do nothing.
            }
            if (toAdd > 1 && toAdd <= 10) {
                myDungeonHeight = toAdd;
                myCurrentValueToAdd = new StringBuilder(4);
            }
        	
            repaint();
        }
    }
    
    /**
     * Listens for "WI" button input.
     * @author Matt Phillips
     * @version 22 February 2017
     */
    private final class WiAction implements ActionListener {

        @Override
        public void actionPerformed(final ActionEvent theEvent) {
        	int toAdd = -1;
            try {
                toAdd = Integer.parseInt(myCurrentValueToAdd.toString());
            } catch (final Exception e) {
                // Failed, do nothing.
            }
            if (toAdd > 1 && toAdd <= 10) {
            	myDungeonWidth = toAdd;
            	myCurrentValueToAdd = new StringBuilder(4);
            }
            
            repaint();
        }
    }
    
    /**
     * Listens for "GOB" button input.
     * @author Matt Phillips
     * @version 22 February 2017
     */
    private final class GobAction implements ActionListener {

        @Override
        public void actionPerformed(final ActionEvent theEvent) {
        	int toAdd = -1;
            try {
                toAdd = Integer.parseInt(myCurrentValueToAdd.toString());
            } catch (final Exception e) {
                // Failed, do nothing.
            }
            if (toAdd <= 10 && toAdd > 0) {
                myDungeonMaxGoblins = toAdd;
                myCurrentValueToAdd = new StringBuilder(4);
            }
            
            repaint();
        }
    }
    
    /**
     * Listens for ">" button input.
     * @author Matt Phillips
     * @version 22 February 2017
     */
    private final class RightAction implements ActionListener {

        @Override
        public void actionPerformed(final ActionEvent theEvent) {
            myGUI.movePlayer(1, 0);
            repaint();
        }
    }
    
    /**
     * Listens for "v" button input.
     * @author Matt Phillips
     * @version 22 February 2017
     */
    private final class DownAction implements ActionListener {

        @Override
        public void actionPerformed(final ActionEvent theEvent) {
            myGUI.movePlayer(0, 1);
            repaint();
        }
    }
    
    /**
     * Listens for "SOL" button input.
     * @author Matt Phillips
     * @version 22 February 2017
     */
    private final class SolveAction implements ActionListener {

        @Override
        public void actionPerformed(final ActionEvent theEvent) {
            myGUI.solve();
        	
            repaint();
        }
    }

    /**
     * Listens for "GO!" button input.
     * @author Matt Phillips
     * @version 13 February 2017
     */
    private final class GoAction implements ActionListener {

        @Override
        public void actionPerformed(final ActionEvent theEvent) {
            myCurrentValueToAdd = new StringBuilder(4);
            myGUI.build(myDungeonHeight, 
            		    myDungeonWidth, 
            		    myDungeonMaxGoblins);
            repaint();
        }
    }
}
