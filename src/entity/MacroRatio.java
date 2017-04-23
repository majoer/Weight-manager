package entity;

/**
 *
 * @author Mats
 */
public class MacroRatio {
    
    private int proteins, carbohydrates, fats;

    public MacroRatio(int proteins, int carbohydrates, int fats) {
        this.proteins = proteins;
        this.carbohydrates = carbohydrates;
        this.fats = fats;
    }

    public MacroRatio() {
    }
    
    public short getPercentage(Macro m) {
        switch(m) {
            case CARBOHYDRATES:
                return (short)((carbohydrates * 4) / getCalories());
            case FATS:
                return (short)((fats * 4) / getCalories());
            case PROTEINS:
                return (short)((proteins * 4) / getCalories());
        }
        return -1;
    }

    public int getCalories() {
        return proteins * 4 + carbohydrates * 4 + fats * 9;
    }

    public int getProteins() {
        return proteins;
    }

    public void setProteins(int proteins) {
        this.proteins = proteins;
    }

    public int getCarbohydrates() {
        return carbohydrates;
    }

    public void setCarbohydrates(int carbohydrates) {
        this.carbohydrates = carbohydrates;
    }

    public int getFats() {
        return fats;
    }

    public void setFats(int fats) {
        this.fats = fats;
    }
    
    
}
