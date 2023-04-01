package ch.uzh.ifi.hase.soprafs23.model;

public interface Notifier {

    public void publishTurnScores();

    public void publishGameScores();

    public void publishFood(Food food);

    public void publishTimer(int timer);

}
