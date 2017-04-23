/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logic.io;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.logging.Level;
import java.util.logging.Logger;
import entity.WeightEntry;
import java.io.InputStream;
import java.io.InputStreamReader;
import logic.util.Cleaner;

/**
 *
 * @author Mats
 */
public class Parser extends Thread {

    private LinkedBlockingQueue<String> parseQueue;
    private String file;
    private ParserInterface pi;

    private Processer p;
    private boolean running;

    public Parser(String file, ParserInterface pi) {
        this.file = file;
        this.pi = pi;
        parseQueue = new LinkedBlockingQueue<>();
    }

    public void terminate() {
        running = false;
        p.terminate();
        try {
            this.join();
        } catch (InterruptedException ex) {
            Logger.getLogger(Parser.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void run() {
        InputStreamReader reader = null;
        BufferedReader br = null;
        p = new Processer(parseQueue, pi);
        p.start();
        try {
            reader = new InputStreamReader(getClass().getResourceAsStream(file));
            br = new BufferedReader(reader);

            String line;

            running = true;
            while (running && (line = br.readLine()) != null) {
                parseQueue.add(line);
            }
            Logger.getLogger(WeightTrackerParser.class.getName()).log(Level.INFO, "Done reading file");
        } catch (FileNotFoundException e) {
            //Handle
        } catch (IOException ex) {
            Logger.getLogger(WeightTrackerParser.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            p.wrapUp();

            Cleaner.close(reader);
            Cleaner.close(br);
        }
        Logger.getLogger(WeightTrackerParser.class.getName()).log(Level.INFO, "Parser finished");
    }
}
