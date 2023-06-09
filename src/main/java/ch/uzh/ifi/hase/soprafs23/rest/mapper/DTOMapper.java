package ch.uzh.ifi.hase.soprafs23.rest.mapper;

import ch.uzh.ifi.hase.soprafs23.constant.FoodCategory;
import ch.uzh.ifi.hase.soprafs23.entity.PlayerScore;
import ch.uzh.ifi.hase.soprafs23.entity.User;
import ch.uzh.ifi.hase.soprafs23.model.GameConfig;
import ch.uzh.ifi.hase.soprafs23.model.Player;
import ch.uzh.ifi.hase.soprafs23.rest.dto.*;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

/**
 * DTOMapper
 * This class is responsible for generating classes that will automatically
 * transform/map the internal representation
 * of an entity (e.g., the User) to the external/API representation (e.g.,
 * UserGetDTO for getting, UserPostDTO for creating)
 * and vice versa.
 * Additional mappers can be defined for new entities.
 * Always created one mapper for getting information (GET) and one mapper for
 * creating information (POST).
 */
@Mapper
public interface DTOMapper {

  DTOMapper INSTANCE = Mappers.getMapper(DTOMapper.class);

  @Mapping(source = "username", target = "username")
  @Mapping(source = "email", target = "email")
  @Mapping(source = "password", target = "password")
  User convertUserPostDTOtoEntity(UserPostDTO userPostDTO);


  @Mapping(source = "id", target = "id")
  @Mapping(source = "username", target = "username")
  @Mapping(source = "email", target = "email")
  @Mapping(source = "bio", target = "bio")
  @Mapping(source = "status", target = "status")
  @Mapping(source = "creation_date", target = "creationDate")
  @Mapping(source = "token", target = "token")
  UserGetDTO convertEntityToUserGetDTO(User user);


  @Mapping(source = "username", target = "username")
  @Mapping(source = "email", target = "email")
  @Mapping(source = "bio", target = "bio")
  @Mapping(source = "status", target = "status")
  @Mapping(source = "password", target = "password")
  User convertUserPutUpdateDTOtoEntity(UserPutDTO userPutDTO);


    @Mapping(source = "winner", target = "winner")
    @Mapping(source = "score", target = "score")
    @Mapping(source = "player_id", target = "userId")
PlayerScoreGetDTO convertPlayerScoreToPlayerScoreGetDTO(PlayerScore playerScore);

  @Mapping(source = "username", target = "username")
  @Mapping(source = "player_id", target = "id")
  @Mapping(source = "host", target = "host")
  PlayerGetDTO convertPlayerToPlayerGetDTO(Player player);

  @Mapping(source = "username", target = "username")
  UserGetRankDTO convertUserToUserGetRankDTO(User user);


    @Mapping(source = "foodCategory", target = "foodCategory", qualifiedByName = "mapFoodCategory")
    @Mapping(source = "roundLimit", target = "roundLimit")
    @Mapping(source = "timerLength", target = "timerLength")
    GameConfig convertUserInputDTOToGameConfig(UserInputDTO userInputDto);

    @Named("mapFoodCategory")
    default FoodCategory mapFoodCategory(String foodCategoryString) {
        switch (foodCategoryString) {
            case "All":
                return FoodCategory.ALL;
            case "Fruits":
                return FoodCategory.FRUITS;
            case "Vegetables":
                return FoodCategory.VEGETABLES;
            case "Meat":
                return FoodCategory.MEAT;
            case "Snacks":
                return FoodCategory.SNACKS;
            default:
                throw new IllegalArgumentException("Invalid food category");
        }
    }

}

