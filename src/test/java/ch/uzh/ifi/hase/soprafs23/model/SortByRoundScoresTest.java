package ch.uzh.ifi.hase.soprafs23.model;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class SortByRoundScoresTest {
    @Test
    public void testUnsortedMap(){
        Map<String, Integer>unsortedMap = new HashMap<>();
        unsortedMap.put("max",1);
        unsortedMap.put("maxi", 3);
        unsortedMap.put("mila",2);

        SortByRoundScores sortByRoundScores = new SortByRoundScores();
        unsortedMap = sortByRoundScores.sortPlayerPoints(unsortedMap);
        ArrayList<Integer>valuesSorted = new ArrayList<>();
        valuesSorted.addAll(unsortedMap.values());

        assertEquals(valuesSorted.get(0),3);
        assertEquals(valuesSorted.get(1),2);
        assertEquals(valuesSorted.get(2),1);
    }
}
