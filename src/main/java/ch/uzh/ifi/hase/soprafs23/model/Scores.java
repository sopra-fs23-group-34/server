package ch.uzh.ifi.hase.soprafs23.model;

import java.util.*;
import static java.lang.Math.max;

public class Scores {
    private final Map<String,ArrayList<Map<String,Double>>> roundScore;
    private Map<String,Map<String,ArrayList<Map<String,Double>>>> roundScoresAllPlayer;
    private Map<String, Integer> playersPoints;

    public Scores() {
        this.playersPoints = new HashMap<>();
        this.roundScore = new HashMap<>();
        this.roundScoresAllPlayer = new HashMap<>();
    }

    public void updateRoundScore(Map<String,Double> playerGuesses, String username, Food food){
        int playerPoints = 0;
        for ( String playerGuessFoodKey : playerGuesses.keySet()){
            int absoluteDeviation = (int)Math.abs(playerGuesses.get(playerGuessFoodKey) - food.getNutritionValues().get(playerGuessFoodKey));
            playerPoints += max(100-(absoluteDeviation*absoluteDeviation),0);

            ArrayList<Map<String,Double>> roundFoodScore = new ArrayList<>();
            Map<String,Double> realValues = new HashMap<>();
            realValues.put("actualValues", food.getNutritionValues().get(playerGuessFoodKey));
            Map<String,Double> guessedValues = new HashMap<>();
            guessedValues.put("guessedValues", playerGuesses.get(playerGuessFoodKey));
            Map<String,Double> deviation = new HashMap<>();
            deviation.put("deviations", (double)absoluteDeviation);

            roundFoodScore.add(realValues);
            roundFoodScore.add(guessedValues);
            roundFoodScore.add(deviation);
            roundScore.put(playerGuessFoodKey,roundFoodScore);
        }
        ArrayList<Map<String,Double>> roundPointsTotal = new ArrayList<>();
        Map<String,Double> roundPointsTotalMap = new HashMap<>();
        roundPointsTotalMap.put("points",(double) playerPoints);
        roundPointsTotal.add(roundPointsTotalMap);
        roundScore.put("points",roundPointsTotal);
        playersPoints.putIfAbsent(username, 0);
        playersPoints.put(username, playersPoints.get(username) + playerPoints);
        //deep copy the roundScore map
        Map<String,ArrayList<Map<String,Double>>> copyRoundScore = new HashMap<>();
        for(String o : roundScore.keySet()){
            copyRoundScore.put(o, roundScore.get(o));
        }
        copyRoundScore = Collections.unmodifiableMap(copyRoundScore);
        //put into the final variable
        roundScoresAllPlayer.put(username,copyRoundScore);
        //sort roundScores descending
        roundScoresAllPlayer = SortByRoundScores.sortByPointsAllScores(roundScoresAllPlayer);

    }

    public Map<String,Map<String,ArrayList<Map<String,Double>>>> getRoundScore(){
        return roundScoresAllPlayer;
    }

    public Map<String,Integer> getPlacement(){
        playersPoints = SortByRoundScores.sortPlayerPoints(playersPoints);
        return playersPoints;
    }

    public void resetRoundScores(){
        this.roundScoresAllPlayer.clear();
    }

}





