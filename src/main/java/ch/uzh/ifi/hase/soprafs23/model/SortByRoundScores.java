package ch.uzh.ifi.hase.soprafs23.model;

import java.util.*;

public class SortByRoundScores {

    public static Map<String, Map<String, ArrayList<Map<String, Double>>>> sortByPointsAllScores(Map<String, Map<String, ArrayList<Map<String, Double>>>> unsortedMap) {

        List<Map.Entry<String, Map<String, ArrayList<Map<String, Double>>>>> list = new LinkedList<>(unsortedMap.entrySet());

        Collections.sort(list, new Comparator<Map.Entry<String, Map<String, ArrayList<Map<String, Double>>>>>() {
            public int compare(Map.Entry<String, Map<String, ArrayList<Map<String, Double>>>> o1, Map.Entry<String, Map<String, ArrayList<Map<String, Double>>>> o2) {
                Double points1 = o1.getValue().get("points").get(0).get("points");
                Double points2 = o2.getValue().get("points").get(0).get("points");
                return points2.compareTo(points1);
            }
        });

        Map<String, Map<String, ArrayList<Map<String, Double>>>> sortedMap = new LinkedHashMap<>();
        for (Map.Entry<String, Map<String, ArrayList<Map<String, Double>>>> entry : list) {
            sortedMap.put(entry.getKey(), entry.getValue());
        }

        return sortedMap;
    }
    public static Map<String, Integer> sortPlayerPoints(Map<String, Integer> unsortedMap) {

        List<Map.Entry<String, Integer>> list = new LinkedList<>(unsortedMap.entrySet());

        Collections.sort(list, new Comparator<Map.Entry<String, Integer>>() {
            public int compare(Map.Entry<String, Integer> o1, Map.Entry<String, Integer> o2) {
                return o2.getValue().compareTo(o1.getValue());
            }
        });

        Map<String, Integer> sortedMap = new LinkedHashMap<>();
        for (Map.Entry<String, Integer> entry : list) {
            sortedMap.put(entry.getKey(), entry.getValue());
        }

        return sortedMap;
    }

}
