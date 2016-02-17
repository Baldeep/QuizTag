package baldeep.quiztagapp.backend;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

public class PowerUps extends Observable implements Serializable{
    private int points;
    private int hints;
    private int skips;
    private List<Observer> observers;

    /**
     * This class holds the basic data for the powers ups to be used in order to decouple them from
     * the quiz master class
     * @param points The number of points earned by the player
     * @param hints The number of hints available for the player
     * @param skips The number of skips available for the player
     */
    public PowerUps(int points, int hints, int skips){
        observers = new ArrayList<Observer>();
        this.points = points;
        this.hints = hints;
        this.skips = skips;
    }

    public int getHints() {
        return hints;
    }

    public String getHintsAsString() {
        return (hints + "");
    }

    public void setHints(int hints) {
        this.hints = hints;
        notifyAllObservers();
    }

    public int getPoints() {
        return points;
    }

    public String getPointsAsString(){
        return (points + "");
    }

    public void setPoints(int points) {
        this.points = points;
        notifyAllObservers();
    }

    public int getSkips() {
        return skips;
    }

    public String getSkipsAsString(){
        return (skips + "");
    }

    public void setSkips(int skips) {
        this.skips = skips;
        notifyAllObservers();
    }

    public void attach(Observer o){
        observers.add(o);
    }

    private void notifyAllObservers(){
        for(Observer o : observers)
            o.update(this, null);
    }
}
