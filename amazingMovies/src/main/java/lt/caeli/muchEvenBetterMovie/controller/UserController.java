package lt.caeli.muchEvenBetterMovie.controller;

import jakarta.validation.Valid;
import lt.caeli.muchEvenBetterMovie.dto.UserMapper;
import lt.caeli.muchEvenBetterMovie.dto.UserPostDTO;
import lt.caeli.muchEvenBetterMovie.dto.UserRequestDTO;
import lt.caeli.muchEvenBetterMovie.model.User;
import lt.caeli.muchEvenBetterMovie.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class UserController {
  private final PasswordEncoder passwordEncoder;
  private final UserService userService;

  @Autowired
  public UserController(UserService userService, PasswordEncoder passwordEncoder) {
    this.userService = userService;
    this.passwordEncoder = passwordEncoder;
  }

  @GetMapping("/users")
  public ResponseEntity<List<UserRequestDTO>> getUsers() {
    return ResponseEntity.ok(UserMapper.toUserDTOList(userService.findAllUsers()));
  }

  @GetMapping("/users/{id}")
  public ResponseEntity<UserRequestDTO> getUserById(@PathVariable long id) {
    Optional<User> user = userService.findUserById(id);
    return user.map(u -> ResponseEntity.ok(UserMapper.toUserDTO(u))).orElseGet(() -> ResponseEntity.notFound().build());

  }

  @PostMapping("/users")
  public ResponseEntity<UserRequestDTO> saveUser(@Valid @RequestBody UserPostDTO userPostDTO) {
    User user = new User();

    user.setUsername(userPostDTO.username());
    user.setPassword(passwordEncoder.encode(userPostDTO.password()));
    user.setRoles(userPostDTO.roles());

    UserRequestDTO userSaved = UserMapper.toUserDTO(userService.saveUser(user));
    return ResponseEntity.created(ServletUriComponentsBuilder.fromCurrentRequest()
                    .path("/{id}")
                    .buildAndExpand(userSaved.id())
                    .toUri())
            .body(userSaved);
  }

  @PutMapping("/users/{id}")
  public ResponseEntity<?> updateUser(@PathVariable long id, @RequestBody UserPostDTO userPostDTO) {
    if (userService.existsUserById(id)) {
      User user = userService.findUserById(id).get();
      user.setUsername(userPostDTO.username());
      user.setPassword(passwordEncoder.encode(userPostDTO.password()));
      user.setRoles(userPostDTO.roles());
      return ResponseEntity.ok(UserMapper.toUserDTO(userService.saveUser(user)));
    } else {
      User user = new User(userPostDTO.username(), userPostDTO.password(), userPostDTO.roles());
      return ResponseEntity.ok(UserMapper.toUserDTO(userService.saveUser(user)));
    }
  }

  @DeleteMapping("/users/{id}")
  public ResponseEntity<Void> deleteUser(@PathVariable long id) {
    if (userService.existsUserById(id)) {
      userService.deleteUserById(id);
      return ResponseEntity.noContent().build();
    } else {
      return ResponseEntity.notFound().build();
    }
  }
}
