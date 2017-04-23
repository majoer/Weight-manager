package gui.west.calendar;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import javax.swing.JPanel;

/**
 *
 * @author Mats
 */
public class TimePickerClock extends JPanel {

    private ClockHand hour, minute, second;
    private int diameter;

    public TimePickerClock() {
//        setBackground(Color.white);
        hour = new ClockHand(ClockHand.Type.HOUR, 1, calculateCenter(), diameter);
        minute = new ClockHand(ClockHand.Type.MINUTE, 30, calculateCenter(), diameter);
        second = new ClockHand(ClockHand.Type.SECOND, 45, calculateCenter(), diameter);
        add(hour);
        add(minute);
        add(second);
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        diameter = getHeight() - 1;
        paintCircle(g);
        paintMiddleCircle(g);
        paintIndicators(g);
        updateHands();
    }

    private void paintCircle(Graphics g) {

        g.setColor(Color.BLACK);
        g.drawOval(0, 0, diameter, diameter);

        g.setColor(Color.WHITE);
        g.fillOval(1, 1, diameter - 2, diameter - 2);
    }

    private void paintMiddleCircle(Graphics g) {
        g.setColor(Color.GRAY);
        Point center = calculateCenter();
        int innerRadius = 5;
        g.fillOval(center.x - innerRadius, center.y - innerRadius, innerRadius * 2, innerRadius * 2);

    }

    private void paintIndicators(Graphics g) {

        int radius = diameter / 2;
        Point center = calculateCenter();
        g.setColor(Color.BLACK);
        for (int s = 0; s < 60; s += 1) {
            int angle = 6 * s; //6 = 360 / 60

            int x = (int) (radius * Math.cos(angle * Math.PI / 180));
            int y = (int) (radius * Math.sin(angle * Math.PI / 180));
            x += center.x;
            y += center.y;

            int shrink;
            
            if (s % 5 == 0) {
                shrink = radius / 5;
            } else {
                shrink = radius / 15;
            }

            int x2 = (int) ((radius - shrink) * Math.cos(angle * Math.PI / 180));
            int y2 = (int) ((radius - shrink) * Math.sin(angle * Math.PI / 180));
            x2 += center.x;
            y2 += center.y;

            g.drawLine(x, y, x2, y2);
//            g.drawOval(x, y, 6, 6);
        }
    }

    private void updateHands() {
        hour.update(1, calculateCenter(), diameter);
        minute.update(30, calculateCenter(), diameter);
        second.update(45, calculateCenter(), diameter);
    }

    private Point calculateCenter() {
        Point upperLeft = getLocation();
        Point center = new Point(upperLeft.x + diameter / 2, upperLeft.y + diameter / 2);
        return center;
    }
}
