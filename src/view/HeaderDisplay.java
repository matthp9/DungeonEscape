/**
 * Matthew Allen Phillips
 * 22 February 2017
 * Displays generic header.
 */

package view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

/**
 * Generic header display pane.
 * 
 * @author Matt Phillips
 * @version 22 February 2017
 */
public class HeaderDisplay extends JPanel {
    
    /**
     * Auto-generated (Eclipse IDE).
     */
    private static final long serialVersionUID = 6260537442349739577L;

    /**
     * Creates a new HeaderDisplay panel.
     */
    public HeaderDisplay() {
        super();
        
        setBackground(Color.BLACK);
        setPreferredSize(new Dimension(250, 200));
        
        repaint();
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public void paintComponent(final Graphics theGraphics) {
        super.paintComponent(theGraphics);
        final Graphics2D g = (Graphics2D) theGraphics;
       
        g.drawImage(new ImageIcon("images/admin/title.png").getImage(), 
                    0, 0, null);
        g.drawImage(new ImageIcon("images/admin/metadata.png").getImage(),
                    getSize().width - 300, 5, null);
    }
}
