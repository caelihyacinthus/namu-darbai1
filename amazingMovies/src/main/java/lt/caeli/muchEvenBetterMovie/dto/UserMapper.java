package lt.caeli.muchEvenBetterMovie.dto;

import lt.caeli.muchEvenBetterMovie.model.User;

import java.util.List;

public class UserMapper {
  public User toUser(UserPostDTO userPostDTO) {
    User user = new User();
    user.setUsername(userPostDTO.username());
    user.setPassword(userPostDTO.password());
    user.setRoles(userPostDTO.roles());
    return user;
  }

  public static UserRequestDTO toUserDTO(User user) {
    return new UserRequestDTO(user.getId(), user.getUsername(), user.getRoles());
  }

  public static List<UserRequestDTO> toUserDTOList(List<User> users) {
    return users.stream().map(UserMapper::toUserDTO).toList();
  }
}
