/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 *
 * @author Mats
 */
public class WeightEntry {

    private Date date;
    private double weight;
    private String comment;

    public WeightEntry() {
    }

    public WeightEntry(Date date, double weight, String comment) {
        this.date = date;
        this.weight = weight;
        this.comment = comment;
    }

    public WeightEntry(WeightEntry oldEntry) {
        this.date = new Date(oldEntry.getDate().getTime());
        this.weight = oldEntry.weight;
        this.comment = oldEntry.comment;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof WeightEntry) {
            WeightEntry we = (WeightEntry) obj;
            
            boolean dateOk = date.equals(we.getDate());
            boolean weightOk = weight == we.getWeight();
            boolean commentOk = comment.equals(we.getComment());
            
            return dateOk && weightOk && commentOk;
        }
        return false;
    }

    public String getChartFriendlyDate() {
        Calendar c = Calendar.getInstance();
        c.setTime(date);

        StringBuilder s = new StringBuilder();

//        s.append(getAsString(c, Calendar.DAY_OF_WEEK));
        s.append(c.get(Calendar.DAY_OF_MONTH));
        s.append(".");
        s.append(c.get(Calendar.MONTH) + 1);

//        s.append(date);
        return s.toString();
    }

    public String getDisplayFriendlyDate() {
        Calendar c = Calendar.getInstance();
        c.setTime(date);

        StringBuilder s = new StringBuilder();

//        s.append(getAsString(c, Calendar.DAY_OF_WEEK));
        s.append(c.get(Calendar.DAY_OF_MONTH));
        s.append(" ");
        s.append(getAsString(c, Calendar.MONTH));

//        s.append(date);
        return s.toString();
    }

    private String getAsString(Calendar c, int field) {
        return c.getDisplayName(field, Calendar.SHORT_FORMAT, Locale.ENGLISH);
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

}
