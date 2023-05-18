package ch.uzh.ifi.hase.soprafs23.messages;

import lombok.AllArgsConstructor;
import lombok.Getter;
import java.util.HashMap;

@AllArgsConstructor
public class FoodMessage implements Message{
    @Getter
    private String topic;
    private HashMap<String, String> food = new HashMap<>();
    public FoodMessage(String topic, String name, String imageLink) {
        this.topic=topic;
        this.food.put("name",name);
        this.food.put("imageLink", imageLink);
    }
    public HashMap<String, String> getContent(){
        return food;
    }

    @Override
    public String getType() {
        return null;
    }
}


