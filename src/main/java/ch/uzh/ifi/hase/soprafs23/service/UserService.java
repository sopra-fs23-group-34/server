package ch.uzh.ifi.hase.soprafs23.service;

import ch.uzh.ifi.hase.soprafs23.constant.UserStatus;
import ch.uzh.ifi.hase.soprafs23.entity.LeaderBoard;
import ch.uzh.ifi.hase.soprafs23.entity.PlayerScore;
import ch.uzh.ifi.hase.soprafs23.entity.PlayerStatistics;
import ch.uzh.ifi.hase.soprafs23.entity.User;
import ch.uzh.ifi.hase.soprafs23.model.Scores;
import ch.uzh.ifi.hase.soprafs23.repository.PlayerScoreRepository;
import ch.uzh.ifi.hase.soprafs23.repository.UserRepository;
import ch.uzh.ifi.hase.soprafs23.storage.UserStorage;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;
import java.util.*;


/**
 * User Service
 * This class is the "worker" and responsible for all functionality related to
 * the user
 * (e.g., it creates, modifies, deletes, finds). The result will be passed back
 * to the caller.
 */
@AllArgsConstructor
@Service
@Transactional
public class UserService {

    private final Logger log = LoggerFactory.getLogger(UserService.class);
    private final UserRepository userRepository;
    private final PlayerScoreRepository playerScoreRepository;
    private UserStorage guestUserStorage = UserStorage.getInstance();

    @Autowired
    public UserService(@Qualifier("userRepository") UserRepository userRepository, @Qualifier("playerScores") PlayerScoreRepository playerScoreRepository) {
        this.userRepository = userRepository;
        this.playerScoreRepository = playerScoreRepository;
    }

    public List<User> getUsers() {
        return this.userRepository.findAll();
    }

    public User createUser(User newUser) {
        newUser.setToken(UUID.randomUUID().toString());
        newUser.setStatus(UserStatus.ONLINE);
        newUser.setCreationDate(new Date());
        newUser.setGuestUser(false);
        checkForGuestUser(newUser);
        checkIfUserExists(newUser);
        newUser = userRepository.save(newUser);
        userRepository.flush();
        log.debug("Created Information for User: {}", newUser);
        return newUser;
    }

    private User createGuestUser(String username) {
        User newGuestUser = new User();
        newGuestUser.setUsername(username);
        newGuestUser.setPassword(username + "password");
        newGuestUser.setCreationDate(new Date());
        newGuestUser.setEmail(username + "@email.com");
        newGuestUser.setToken(UUID.randomUUID().toString());
        newGuestUser.setStatus(UserStatus.ONLINE);
        newGuestUser.setBio("Hi I am a Guest User");
        newGuestUser.setGuestUser(true);
        newGuestUser = userRepository.save(newGuestUser);
        userRepository.flush();
        log.debug("Created Information for guestUser: {}", newGuestUser);
        return newGuestUser;
    }

    public User loginUser(User loginUser) {
        User userDatabase = userRepository.findByUsername(loginUser.getUsername());
        if (userDatabase == null) {
            throw new ResponseStatusException(HttpStatus.valueOf(401), "Username is not registered");
        }
        if (!(userDatabase.getPassword().equals(loginUser.getPassword()))) {
            throw new ResponseStatusException(HttpStatus.valueOf(401), "Wrong Password");
        }
        userDatabase.setStatus(UserStatus.ONLINE);
        userRepository.save(userDatabase);
        userRepository.flush();
        log.debug("Logged-in User: {}", loginUser);
        return userDatabase;
    }

    public User logoutUser(String token, Long id) {
        authenticateUser(token, id);
        User loggedOutUser = getUserById(id);
        loggedOutUser.setStatus(UserStatus.OFFLINE);
        userRepository.save(loggedOutUser);
        userRepository.flush();
        log.debug("Logged out user: {}", loggedOutUser);
        return loggedOutUser;
    }

    public void logoutGuestUser(String token, Long id) {
        User loggedOutGuestUser = logoutUser(token, id);
        guestUserStorage.removeUsername(loggedOutGuestUser.getUsername());

    }

    public User getUserById(Long id) {
        Optional<User> OptionalUser = userRepository.findById(id);
        return OptionalUser.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                String.format("user with userid " + id + " was not found")));
    }

    public User loginGuestUser() {
        String username = guestUserStorage.createNewGuestUser();
        User guestUser = userRepository.findByUsername(username);
        if (guestUser == null) {
            guestUser = createGuestUser(username);
        } else {
            guestUser.setStatus(UserStatus.ONLINE);
            userRepository.save(guestUser);
            userRepository.flush();
        }
        return guestUser;
    }



    public void authenticateUser(String token, long idCurrentUser) {
        User user = getUserById(idCurrentUser);
        if (!user.getToken().equals(token)) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED,
                    "You are not authorized to perform this action");
        }
    }

    public void verifyUser(String token, long idCurrentUser) {
        authenticateUser(token, idCurrentUser);
    }

    public User updateUser(User userWithUpdateInformation, String token, long idCurrentUser, String oldPassword) {
        User user = userRepository.findById(idCurrentUser);
        if(!oldPassword.equals(user.getPassword())){
            throw new ResponseStatusException(HttpStatus.valueOf(404),
                    "Wrong old Password");
        }

        // make sure, that no information is null
        if (userWithUpdateInformation.getPassword() == null) {
            userWithUpdateInformation.setPassword(user.getPassword());
        }
        if (userWithUpdateInformation.getEmail() == null) {
            userWithUpdateInformation.setEmail(user.getEmail());
        }
        if (userWithUpdateInformation.getUsername() == null) {
            userWithUpdateInformation.setUsername(user.getUsername());
        }
        if (userWithUpdateInformation.getBio() == null) {
            userWithUpdateInformation.setBio(user.getBio());
        }
        // check if user is authorized to change its data
        authenticateUser(token, idCurrentUser);
        User userSameName = userRepository.findByUsername(userWithUpdateInformation.getUsername());
        if (userSameName != null) {
            if (!user.getId().equals(userSameName.getId())) {
                throw new ResponseStatusException(HttpStatus.valueOf(404),
                        "You can't pick the same username as somebody else!");
            }
        }
        User userSameEmail = userRepository.findByEmail(userWithUpdateInformation.getEmail());
        if (userSameEmail != null) {
            if (!user.getId().equals(userSameEmail.getId())) {
                throw new ResponseStatusException(HttpStatus.valueOf(404),
                        "You can't pick the same mail as somebody else!");
            }
        }
        user.setUsername(userWithUpdateInformation.getUsername());
        user.setEmail(userWithUpdateInformation.getEmail());
        user.setBio(userWithUpdateInformation.getBio());
        user.setPassword(userWithUpdateInformation.getPassword());
        userRepository.save(user);
        userRepository.flush();
        return user;
    }


    public void updateScores(Scores scores){
        if (scores.getPlacement().keySet().size() > 1) {
        for (String userName : scores.getPlacement().keySet()){
            User user = userRepository.findByUsername(userName);
            if (!user.isGuestUser()) {
                Long userId = user.getId();
                PlayerScore playerScore = new PlayerScore();
                playerScore.setPlayer_id(userId);
                int maxScore = scores.getPlacement().values().stream().max(Double::compare).orElseThrow(
                        () -> new NoSuchElementException("No maximum value found in the placement scores."));
                boolean winner = scores.getPlacement().get(userName) == maxScore;
                playerScore.setWinner(winner);
                playerScore.setScore(scores.getPlacement().get(userName));
                playerScoreRepository.save(playerScore);
            }
            userRepository.flush();
        }
        }
    }

    public List<LeaderBoard> getTotalScores(Long id, String token){
        authenticateUser(token,id);
        List<LeaderBoard> playerScores = playerScoreRepository.getGlobalLeaderboard();
        for (LeaderBoard playerScore : playerScores){
            Long playerId = playerScore.getUserId();
            playerScore.setUsername(getUserById(playerId).getUsername());
        }
        return playerScores;
    }

    public PlayerStatistics getStatistics(Long id, String token, Long authenticationId){
        authenticateUser(token, authenticationId);
        PlayerStatistics playerStatistics = playerScoreRepository.getPlayerStatistics(id);
        if (playerStatistics == null){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                    "Player not found");
        }
        return playerStatistics;
    }

    private void checkForGuestUser(User userToBeCreated) {
        List<String> usernames = guestUserStorage.getUsernamesString();
        String username = userToBeCreated.getUsername();
        for (String s : usernames) {
            if (userToBeCreated.getUsername().startsWith(s)) {
                throw new ResponseStatusException(HttpStatus.CONFLICT,
                        "add User failed because username is reserved for guests");
            }

        }
        if (username.substring(0, 5).equalsIgnoreCase("guest")) {
            throw new ResponseStatusException(HttpStatus.CONFLICT,
                    "add User failed because username is reserved for guests");
        }
    }

    /**
     * This is a helper method that will check the uniqueness criteria of the
     * username and the name
     * defined in the User entity. The method will do nothing if the input is unique
     * and throw an error otherwise.
     *
     * @param userToBeCreated
     * @throws org.springframework.web.server.ResponseStatusException
     * @see User
     */

    private void checkIfUserExists(User userToBeCreated) {
        User userByUsername = userRepository.findByUsername(userToBeCreated.getUsername());
        User userByEmail = userRepository.findByEmail(userToBeCreated.getEmail());
        if (userByUsername != null) {
            throw new ResponseStatusException(HttpStatus.CONFLICT,
                    "add User failed because username is already used");
        }
        if (userByEmail != null) {
            throw new ResponseStatusException(HttpStatus.CONFLICT,
                    "add User failed because email is already used");
        }
    }
}
