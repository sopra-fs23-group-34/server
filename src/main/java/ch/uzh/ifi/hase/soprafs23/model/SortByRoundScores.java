package ch.uzh.ifi.hase.soprafs23.model;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Map;

public class SortByRoundScores implements Comparator<Map<String,Map<String, ArrayList<Map<String,Double>>>>> {
    @Override
    public int compare(Map<String, Map<String, ArrayList<Map<String, Double>>>> o1, Map<String, Map<String, ArrayList<Map<String, Double>>>> o2) {
        int one = 1;
        return one;
    }
}
