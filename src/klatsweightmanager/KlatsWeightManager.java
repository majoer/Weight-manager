/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package klatsweightmanager;

import gui.Mainframe;
import interfaces.ILogic;
import java.io.File;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;
import logic.io.Parser;
import logic.io.WeightTrackerParser;

/**
 *
 * @author Mats
 */
public class KlatsWeightManager {

    public KlatsWeightManager() throws URISyntaxException {
        String path = "/resources/myfile.txt";

        Parser p = new Parser(path, new WeightTrackerParser());
        p.start();

        Mainframe mf = new Mainframe() {

            @Override
            public void dispose() {
                super.dispose();
                p.terminate();
            }

        };
        mf.setVisible(true);
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        try {
            new KlatsWeightManager();
        } catch (URISyntaxException ex) {
            Logger.getLogger(KlatsWeightManager.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
