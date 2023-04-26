package ch.uzh.ifi.hase.soprafs23.messages;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.ArrayList;
import java.util.HashMap;

@AllArgsConstructor
public class FoodMessage implements Message{
    @Getter
    private String topic;
    private HashMap<String, String> food = new HashMap();
    public FoodMessage(String topic, String name, String image_link) {
        this.topic=topic;
        this.food.put("name",name);
        this.food.put("imageLink", image_link);
    }
    public HashMap<String, String> getContent(){
        return food;
    }
}


