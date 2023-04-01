package ch.uzh.ifi.hase.soprafs23.model;

import ch.uzh.ifi.hase.soprafs23.constant.FoodCategory;
import lombok.AllArgsConstructor;
import lombok.Getter;


public record GameConfig(Integer roundLimit, FoodCategory foodCategory) {
}
