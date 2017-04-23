package gui.west.calendar;

import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.LayoutManager;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.JPanel;

/**
 *
 * @author Mats
 */
public class ClockHand extends JPanel implements MouseListener {

    public enum Type {

        HOUR, MINUTE, SECOND
    };

    private Type type;
    private int position, maxLength;
    private Point center;

    public ClockHand(Type type, int position, Point center, int maxLength) {
        this.type = type;
        this.position = position;
        this.center = center;
        this.maxLength = maxLength;
        addMouseListener(this);
    }

    public final void update(int position, Point center, int maxLength) {
        this.position = position;
        this.center = center;
        this.maxLength = maxLength;
        repaint();
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);

        int length, width;

        switch (type) {
            case HOUR:
                width = 5;
//                length = parentDiameter / 2;
                break;
            case MINUTE:
                width = 5;
//                length = parentDiameter / 4;
                break;
            case SECOND:
                width = 5;
//                length = (int)(parentDiameter / 1.7);
                break;
            default:
                length = 0;
                width = 0;
        }
        int[] coords = calculateCoordinates();
        g.setColor(Color.YELLOW);
        g.drawLine(coords[0], coords[1], coords[2], coords[3]);
//        g.fill3DRect(parentDiameter / 2, parentDiameter / 2, width, length, true);
    }

    private int[] calculateCoordinates() {
        int x, y, x2, y2;

        x = center.x;
        y = center.y;

        int angle = 6 * position;
        x2 = (int) ((maxLength) * Math.cos(angle * Math.PI / 180));
        y2 = (int) ((maxLength) * Math.sin(angle * Math.PI / 180));
        x2 += center.x;
        y2 += center.y;

        System.out.println("x: " + x + ", y: " + y + "x2: " + x2 + "y2: " + y2);

        return new int[]{x, y, x2, y2};
    }

    @Override
    public void mouseClicked(MouseEvent e) {
    }

    @Override
    public void mousePressed(MouseEvent e) {
    }

    @Override
    public void mouseReleased(MouseEvent e) {
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        setCursor(new Cursor(Cursor.HAND_CURSOR));
    }

    @Override
    public void mouseExited(MouseEvent e) {
        setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
    }
}
