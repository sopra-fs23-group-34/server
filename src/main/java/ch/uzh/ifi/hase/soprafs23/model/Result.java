package ch.uzh.ifi.hase.soprafs23.model;

import java.util.Map;



public record Result(long user_id, Map<String, Integer> playerGuesses) {
}
