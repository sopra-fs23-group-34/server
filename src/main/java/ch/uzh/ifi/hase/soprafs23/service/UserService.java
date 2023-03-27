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

import java.util.List;
import java.util.Optional;
import java.util.UUID;

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
    checkIfUserExists(newUser);
    // saves the given entity but data is only persisted in the database once
    // flush() is called
    newUser = userRepository.save(newUser);
    userRepository.flush();

    log.debug("Created Information for User: {}", newUser);
    return newUser;
  }
    public User loginUser(User loginUser) {
      User userDatabase = getUserById(loginUser.getId());
      if(userDatabase == null){
          throw new ResponseStatusException(HttpStatus.valueOf(401), "Username is not registered");
      }
      if(!(userDatabase.getPassword().equals(loginUser.getPassword()))){
          throw new ResponseStatusException(HttpStatus.valueOf(401), "Wrong Password");
      }
        loginUser.setStatus(UserStatus.ONLINE);
        // saves the given entity but data is only persisted in the database once
        // flush() is called
        loginUser = userRepository.save(loginUser);
        userRepository.flush();

        log.debug("Created Information for User: {}", loginUser);
        return loginUser;
    }
    public void logoutUser(User logoutUser) {

        logoutUser.setStatus(UserStatus.OFFLINE);
        // saves the given entity but data is only persisted in the database once
        // flush() is called
        logoutUser = userRepository.save(logoutUser);
        userRepository.flush();

        log.debug("Logged out user: {}", logoutUser);

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

    public void verifyUser(String token, long idCurrentUser){
      try {
          authenticateUser(token,idCurrentUser);
      }catch (ResponseStatusException){
          throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"You are not allowed to logout with somebody elses accoung");
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
    User userByName = userRepository.findByName(userToBeCreated.getName());

    String baseErrorMessage = "The %s provided %s not unique. Therefore, the user could not be created!";
    if (userByUsername != null && userByName != null) {
      throw new ResponseStatusException(HttpStatus.CONFLICT,
          String.format(baseErrorMessage, "username and the name", "are"));
    } else if (userByUsername != null) {
      throw new ResponseStatusException(HttpStatus.CONFLICT, String.format(baseErrorMessage, "username", "is"));
    } else if (userByName != null) {
      throw new ResponseStatusException(HttpStatus.CONFLICT, String.format(baseErrorMessage, "name", "is"));
    }
  }
}
