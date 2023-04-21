package ch.uzh.ifi.hase.soprafs23.messages;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.ArrayList;
@AllArgsConstructor
public class FoodMessage implements Message{
    @Getter
    private String topic;

    private ArrayList<String> food;

    public FoodMessage(String food, String name, String image) {
        this.topic=food;
        this.food.add(name);
        this.food.add(image);
    }

    public ArrayList<String> getContent(){
        return food;
    }
}


