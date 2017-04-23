package entity;

/**
 *
 * @author Mats
 */
public class Action {

    public enum ActionType {

        ADD, REMOVE, UPDATE;
    }

    private ActionType type;
    private WeightEntry target, oldEntry;

    public Action(ActionType type, WeightEntry target, WeightEntry oldEntry) throws IllegalArgumentException {
        if (type == ActionType.UPDATE && oldEntry == null) {
            throw new IllegalArgumentException("A copy of the old object is needed when updating");
        }
        this.type = type;
        this.target = target;
        this.oldEntry = oldEntry;
    }

    public ActionType getType() {
        return type;
    }

    public void setType(ActionType type) {
        this.type = type;
    }

    public WeightEntry getTarget() {
        return target;
    }

    public void setTarget(WeightEntry target) {
        this.target = target;
    }

    public WeightEntry getOldEntry() {
        return oldEntry;
    }

    public void setOldEntry(WeightEntry oldEntry) {
        this.oldEntry = oldEntry;
    }
    
    
}
