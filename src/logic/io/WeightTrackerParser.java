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
import java.util.logging.Level;
import java.util.logging.Logger;
import entity.WeightEntry;
import java.util.Calendar;
import java.util.Date;
import logic.util.Cleaner;

/**
 *
 * @author Mats
 */
public class WeightTrackerParser implements ParserInterface {

    @Override
    public WeightEntry[] process(String line) {
        if(line == null) return null;
        WeightEntry[] entries = new WeightEntry[1];

        String[] split = line.split(",");
        if(split.length != 3) return null;

        //Weight
        double weight = readWeight(split[0]);
        if(weight == -1) return null;
        
        //Date
        Date d = readDate(split[1]);
        if(d == null) return null;
        
        //Comment
        String comment = split[2];
        if(comment == null) return null;

        entries[0] = new WeightEntry(d, weight, comment.replaceAll("\"", ""));
        return entries;
    }

    private double readWeight(String s) {
        try {
            return Double.parseDouble(s);
        } catch (NumberFormatException e) {
            return -1;
        }
    }
    
    private Date readDate(String s) {
        String[] dateTime = s.split(" ");
        if(dateTime.length != 2) return null;
        
        //Date
        String[] date = dateTime[0].split("-");
        if(date.length != 3) return null;
        
        int year = readInt(date[0]);
        int month = readInt(date[1]);
        int day = readInt(date[2]);
        
        //Time
        String[] time = dateTime[1].split(":");
        if(time.length != 3) return null;
        
        int hour = readInt(time[0]);
        int min = readInt(time[1]);
        int sec = readInt(time[2]);
        
        if(year == -1 || month == -1 || day == -1) return null;
        if(hour == -1 || min == -1 || sec == -1) return null;
        
        Calendar c = Calendar.getInstance();
        c.set(year, month - 1, day, hour, min, sec);
        return new Date(c.getTime().getTime());
    }
    
    private int readInt(String s) {
        try {
            return Integer.parseInt(s);
        } catch (NumberFormatException e) {
            return -1;
        }
    }
}
