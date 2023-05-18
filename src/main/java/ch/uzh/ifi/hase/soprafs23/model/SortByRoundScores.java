package ch.uzh.ifi.hase.soprafs23.model;

import java.util.*;

public class SortByRoundScores {

    public static Map<String, Map<String, ArrayList<Map<String, Double>>>> sortByPointsAllScores(Map<String, Map<String, ArrayList<Map<String, Double>>>> unsortedMap) {

        List<Map.Entry<String, Map<String, ArrayList<Map<String, Double>>>>> list = new LinkedList<>(unsortedMap.entrySet());
        String value = "points";
        list.sort((o1, o2) -> {
            Double points1 = o1.getValue().get(value).get(0).get(value);
            Double points2 = o2.getValue().get(value).get(0).get(value);
            return points2.compareTo(points1);
        });

        Map<String, Map<String, ArrayList<Map<String, Double>>>> sortedMap = new LinkedHashMap<>();
        for (Map.Entry<String, Map<String, ArrayList<Map<String, Double>>>> entry : list) {
            sortedMap.put(entry.getKey(), entry.getValue());
        }

        return sortedMap;
    }
    public static Map<String, Integer> sortPlayerPoints(Map<String, Integer> unsortedMap) {

        List<Map.Entry<String, Integer>> list = new LinkedList<>(unsortedMap.entrySet());

        list.sort((o1, o2) -> o2.getValue().compareTo(o1.getValue()));

        Map<String, Integer> sortedMap = new LinkedHashMap<>();
        for (Map.Entry<String, Integer> entry : list) {
            sortedMap.put(entry.getKey(), entry.getValue());
        }
        return sortedMap;
    }

}
