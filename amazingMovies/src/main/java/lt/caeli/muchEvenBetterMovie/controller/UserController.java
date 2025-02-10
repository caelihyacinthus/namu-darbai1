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

@RestController
@RequestMapping("/api")
public class UserController {
  private final PasswordEncoder passwordEncoder;
  private UserService userService;

  @Autowired
  public UserController(UserService userService, PasswordEncoder passwordEncoder) {
    this.userService = userService;
    this.passwordEncoder = passwordEncoder;
  }

  @GetMapping("/users")
  public ResponseEntity<List<UserRequestDTO>> getUsers() {
    return ResponseEntity.ok(UserMapper.toUserDTOList(userService.findAllUsers()));
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
}
