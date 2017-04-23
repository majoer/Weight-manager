package logic;

import entity.WeightEntry;
import java.util.ArrayList;

/**
 *
 * @author Mats
 */
public class DateSorter {

    private ArrayList<WeightEntry> entries;

    public DateSorter(ArrayList<WeightEntry> entries) {
        this.entries = entries;
    }

    public int sort(WeightEntry target) {
        if (entries.size() < 2) {
            return -1;
        }
        int pos = entries.indexOf(target);
        if (pos == -1) {
            return -1;
        }

        try {
            pos = sortLeft(pos);
            System.out.println(pos);
        } catch (IndexOutOfBoundsException e) {
        }

        try {
            pos = sortRight(pos);
        } catch (IndexOutOfBoundsException e) {
        }
        
        return pos;
    }

    public int sort(int oldPos, WeightEntry oldEntry, WeightEntry newEntry) {
        int newPos;
        if (newEntry.getDate().after(oldEntry.getDate())) {
            newPos = sortLeft(oldPos);
        } else if (newEntry.getDate().before(oldEntry.getDate())) {
            newPos = sortRight(oldPos);
        } else {
            newPos = oldPos;
        }
        System.out.println("Moved index " + oldPos + " to " + newPos);
        return newPos;
    }

    private int sortLeft(int index) {
        if (index < 1) {
            return index;
        }
        WeightEntry newEntry = entries.get(index);
        WeightEntry front = entries.get(index - 1);
        if (newEntry.getDate().after(front.getDate())) {
            swap(index, --index);
            return sortLeft(index);
        }
        return index - 1;
    }

    private int sortRight(int index) {
        if (index > entries.size() - 2) {
            return index;
        }
        WeightEntry newEntry = entries.get(index);
        WeightEntry rear = entries.get(index + 1);

        if (newEntry.getDate().before(rear.getDate())) {
            swap(index, ++index);
            return sortRight(index);
        }
        return index + 1;
    }

    private void swap(int a, int b) {
        WeightEntry wa = entries.get(a);
        WeightEntry wb = entries.set(b, wa);
        entries.set(a, wb);
    }
}
