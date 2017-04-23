/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logic.io;

import entity.Event;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.logging.Level;
import java.util.logging.Logger;
import entity.WeightEntry;
import logic.Logic;

/**
 *
 * @author Mats
 */
public class Processer extends Thread {

    private LinkedBlockingQueue<String> queue;
    private ParserInterface pi;
    private boolean running, wrappingUp;

    public Processer(LinkedBlockingQueue queue, ParserInterface pi) {
        this.queue = queue;
        this.pi = pi;
    }

    @Override
    public synchronized void start() {
        running = true;
        super.start();
    }

    @Override
    public void run() {
        try {
            while (running || (wrappingUp && !queue.isEmpty())) {
                
                WeightEntry[] entries = pi.process(queue.take());
                if (entries == null) continue;
                
                for (WeightEntry e : entries) {
                    Logic.getInstance().newWeightEntry(e, false, false, false);
                }
//                Thread.sleep(100);
            }
            
        } catch (InterruptedException ex) {
            Logger.getLogger(Processer.class.getName()).log(Level.INFO, "Forefully shutting down Processer", ex);
        } 
        
        Logger.getLogger(Processer.class.getName()).log(Level.INFO, "Processer finished");
    }

    public void terminate() {
        running = false;
        wrappingUp = false;
        this.interrupt();

        try {
            this.join();
        } catch (InterruptedException ex) {
            Logger.getLogger(Processer.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void wrapUp() {
        wrappingUp = true;
        running = false;
        try {
            this.join();
        } catch (InterruptedException ex) {
            Logger.getLogger(Processer.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
