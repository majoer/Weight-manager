package gui.utils;

import java.awt.Image;
import java.awt.Toolkit;
import java.io.IOException;
import java.io.InputStream;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

/**
 *
 * @author Mats
 */
public class Utils {
    public static ImageIcon getIcon(String s) {
        try {
            InputStream stream = Utils.class.getResourceAsStream(s);
            Image i = ImageIO.read(stream);
            return new ImageIcon(i);
        } catch (IOException ex) {
            Logger.getLogger(Utils.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
}
