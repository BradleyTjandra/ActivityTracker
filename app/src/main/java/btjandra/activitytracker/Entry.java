/**
 * Created by tjandraca on 5/01/2015.
 */

package btjandra.activitytracker;

public class Entry {

    private long id;
    private int timestamp;
    private String action;
    private int productivity;
    private int energy;

    public long getID() {
        return id;
    }

    public void setID(long id) {
        this.id = id;
    }

    public int getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(int timestamp) {
        this.timestamp = timestamp;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public int getProductivity() {
        return productivity;
    }

    public void setProductivity(int productivity) {
        int MIN_PROD = 1;
        int MAX_PROD = 7;
        if (productivity >= MIN_PROD && productivity <= MAX_PROD) {
            this.productivity = productivity;
        } else {
            // I don't know this shouldn't happen.
            throw new Exception();
        }
    }

    public int getEnergy() {
        return energy;
    }

    public void setEnergy(int energy) {
        int MIN_ENER = 1;
        int MAX_ENER = 7;
        if (energy >= MIN_ENER && energy <= MAX_ENER) {
            this.energy = energy;
        } else {
            // I don't know this shouldn't happen.
            throw new Exception();
        }
    }

}
