package ch.uzh.ifi.hase.soprafs23.service;

import ch.uzh.ifi.hase.soprafs23.constant.UserStatus;
import ch.uzh.ifi.hase.soprafs23.entity.GameScores;
import ch.uzh.ifi.hase.soprafs23.entity.User;
import ch.uzh.ifi.hase.soprafs23.repository.GameScoresRepository;
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

@Service
@Transactional
public class GameScoresService {

    private final Logger log = LoggerFactory.getLogger(GameScoresService.class);
    private final GameScoresRepository  gameScoresRepository;
    private GameScores game;

    @Autowired
    public GameScoresService(@Qualifier("gameScoresRepository") GameScoresRepository gameScoresRepository) {
        this.gameScoresRepository = gameScoresRepository;
    }

    public GameScores createGameScore(GameScores gameScores, Date startingDateOfGame) {
        gameScores.setGameDate(startingDateOfGame);
        gameScores = gameScoresRepository.save(gameScores);
        gameScoresRepository.flush();
        log.debug("Created gamescore: {}", gameScores);
        return gameScores;
    }

    public GameScores updateGameScore(Long id, Date startingTimeOfGame, Integer score) {
        GameScores gameScore = gameScoresRepository.findByIdAndDate(id, startingTimeOfGame);
        gameScore.setScore(gameScore.getScore()+score);
        gameScore = gameScoresRepository.save(gameScore);
        gameScoresRepository.flush();
        return gameScore;
    }

}
