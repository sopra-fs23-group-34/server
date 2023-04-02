package ch.uzh.ifi.hase.soprafs23.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Map;


@NoArgsConstructor
@Getter
@Setter
public class PlayerGuesses {
    private Map<String,Integer> content;
}