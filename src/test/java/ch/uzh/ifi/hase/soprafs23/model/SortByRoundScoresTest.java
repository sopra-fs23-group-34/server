package ch.uzh.ifi.hase.soprafs23.model;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

class SortByRoundScoresTest {
    @Test
    void testUnsortedMap(){
        Map<String, Integer>unsortedMap = new HashMap<>();
        unsortedMap.put("max",1);
        unsortedMap.put("maxi", 3);
        unsortedMap.put("mila",2);

        SortByRoundScores sortByRoundScores = new SortByRoundScores();
        unsortedMap = sortByRoundScores.sortPlayerPoints(unsortedMap);
        ArrayList<Integer>valuesSorted = new ArrayList<>();
        valuesSorted.addAll(unsortedMap.values());

        assertEquals(3, valuesSorted.get(0));
        assertEquals(2, valuesSorted.get(1));
        assertEquals(1, valuesSorted.get(2));
    }
}
