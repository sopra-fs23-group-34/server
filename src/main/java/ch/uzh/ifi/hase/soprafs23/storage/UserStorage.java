package ch.uzh.ifi.hase.soprafs23.storage;
import org.springframework.stereotype.Component;
import java.security.SecureRandom;

import java.io.Serializable;
import java.util.*;

@Component
public class UserStorage implements Serializable {
    private static UserStorage instance;

    private transient HashMap<String, ArrayList<Integer>> guestUsers;
    private final SecureRandom rand = new SecureRandom();
    private UserStorage() {
        guestUsers = new HashMap<>();
        for (String username : usernames) {
            guestUsers.put(username, new ArrayList<>());
        }
    }
    private final List<String> usernames = Arrays.asList("JohnLemon", "Madonnalds", "BroccoliObama",
            "YouCorn", "EggSheeran", "PotatobeyMaguire", "TeachingAssistant", "RiceWitherspoon",
            "BettyWheat", "InsulinShot", "PeanutButterPitt", "ScarlettYogurt-hansson", "SamuelLJackfruit",
            "BenedictCucumberbatch", "HughJackmango", "FreddieMer-curry", "LeonardoDiCappuccino");

    public static synchronized UserStorage getInstance()
    {
        if (instance == null)
            instance = new UserStorage();
        return instance;
    }

    private String[] splitUsername (String username) { return username.split("(?<=\\D)(?=\\d)"); }

    private boolean isUsernameFree(String username) {
        String[] parts = splitUsername(username);
        String usernameString = parts[0];
        Integer usernameCode = Integer.parseInt(parts[1]);
        List<Integer> usersCode = guestUsers.get(usernameString);
        return !usersCode.contains(usernameCode);
    }

    public List<String> getUsernamesString() {
        return usernames;
    }

    private String nextUsername() {
        int usernameStringIndex = rand.nextInt(usernames.size());
        String usernameString = usernames.get(usernameStringIndex);
        int usernameCode = rand.nextInt(1000000);
        return usernameString + usernameCode;
    }

    public String createNewGuestUser() {
        String username = nextUsername();
        while (true) {
            if (isUsernameFree(username)) {
                String[] parts = splitUsername(username);
                String usernameString = parts[0];
                Integer usernameCode = Integer.parseInt(parts[1]);
                List<Integer> codes = guestUsers.get(usernameString);
                codes.add(usernameCode);
                guestUsers.put(usernameString, new ArrayList<>(codes));
                return username;
            }
            else {
                username = nextUsername();
            }
        }
    }

    public void removeUsername(String username) {
        String[] parts = splitUsername(username);
        String usernameString = parts[0];
        Integer usernameCode = Integer.parseInt(parts[1]);
        List<Integer> codes = guestUsers.get(usernameString);
        codes.remove(usernameCode);
        guestUsers.put(usernameString, new ArrayList<>(codes));

    }

}
