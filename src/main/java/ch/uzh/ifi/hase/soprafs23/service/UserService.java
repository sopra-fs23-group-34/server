package ch.uzh.ifi.hase.soprafs23.service;

import ch.uzh.ifi.hase.soprafs23.constant.UserStatus;
import ch.uzh.ifi.hase.soprafs23.entity.User;
import ch.uzh.ifi.hase.soprafs23.repository.UserRepository;
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
@Service
@Transactional
public class UserService {

    private final Logger log = LoggerFactory.getLogger(UserService.class);

    private final UserRepository userRepository;

    @Autowired
    public UserService(@Qualifier("userRepository") UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<User> getUsers() {
        return this.userRepository.findAll();
    }


    public User createUser(User newUser) {
        newUser.setToken(UUID.randomUUID().toString());
        newUser.setStatus(UserStatus.ONLINE);
        newUser.setCreationDate(new Date());
        checkIfUserExists(newUser);
        // saves the given entity but data is only persisted in the database once
        // flush() is called
        newUser = userRepository.save(newUser);
        userRepository.flush();

        log.debug("Created Information for User: {}", newUser);
        return newUser;
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
        // saves the given entity but data is only persisted in the database once
        // flush() is called
        userRepository.save(userDatabase);
        userRepository.flush();



        log.debug("Logged-in User: {}", loginUser);
        return userDatabase;
    }

    public User logoutUser(Long id) {

        User loggedOutUser = getUserById(id);
        loggedOutUser.setStatus(UserStatus.OFFLINE);
        // saves the given entity but data is only persisted in the database once
        // flush() is called
        userRepository.save(loggedOutUser);
        userRepository.flush();

        log.debug("Logged out user: {}", loggedOutUser);
        return loggedOutUser;

    }

    public User getUserById(Long id) {
        Optional<User> OptionalUser = userRepository.findById(id);
        User user = OptionalUser.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                String.format("user with userid " + id + " was not found")));
        return user;
    }

    private void authenticateUser(String token, long idCurrentUser) {
        User user = getUserById(idCurrentUser);
        if (!user.getToken().equals(token)) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED,
                    String.format("You are not authorized to perform this action"));
        }
    }

    public void verifyUser(String token, long idCurrentUser) {
        authenticateUser(token, idCurrentUser);
    }


    //Todo
    //implement updateUser function
    public User updateUser(User userWithUpdateInformation, String token, long idCurrentUser) {
        // check if user is authorized to change its data
        authenticateUser(token, idCurrentUser);

        // get User by Username
        // update Data
        // safe new user in DB


        User updatedUser = new User();

        return updatedUser;
    }

    public User getUserWithId(Long id) {
        return getUserById(id);
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
          String.format("add User failed because username is already used"));
    }
    if (userByEmail != null) {
        throw new ResponseStatusException(HttpStatus.CONFLICT,
                String.format("add User failed because email is already used"));
      }
  }
}
