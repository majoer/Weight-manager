package logic;

import entity.Action;
import java.util.LinkedList;

/**
 *
 * @author Mats
 */
public class HistoryManager extends LinkedList<Action> {
    private int index = 0;

    public Action redo() {
        if(index > 0) {
            return get(--index);
        } 
        return null;
    }
    
    public Action undo() {
        if(index < size()) {
            return get(index++);
        }
        return null;
    }
    
    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    @Override
    public void push(Action e) {
        index = 0;
        super.push(e);
    }
    
    
    
}
