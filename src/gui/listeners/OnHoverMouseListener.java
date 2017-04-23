package gui.listeners;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.ImageIcon;
import javax.swing.JButton;

/**
 *
 * @author Mats
 */
public class OnHoverMouseListener extends MouseAdapter {
    
    private final JButton button;
    private final ImageIcon small, big;
    
    public OnHoverMouseListener(JButton button, ImageIcon small, ImageIcon big) {
        this.button = button;
        this.small = small;
        this.big = big;
    }

    @Override
    public void mouseExited(MouseEvent e) {
        super.mouseExited(e);
        button.setIcon(small);

    }

    @Override
    public void mouseEntered(MouseEvent e) {
        super.mouseEntered(e);
        button.setIcon(big);
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        super.mouseReleased(e);
        button.setIcon(big);
    }

    @Override
    public void mousePressed(MouseEvent e) {
        super.mousePressed(e);
        button.setIcon(small);
    }
    
    
}
