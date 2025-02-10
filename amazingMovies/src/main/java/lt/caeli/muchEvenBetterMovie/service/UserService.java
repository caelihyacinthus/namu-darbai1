package lt.caeli.muchEvenBetterMovie.service;

import lt.caeli.muchEvenBetterMovie.model.User;
import lt.caeli.muchEvenBetterMovie.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {
  private UserRepository userRepository;

  @Autowired
  public UserService(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  public User saveUser(User user) {
    return userRepository.save(user);
  }

  public List<User> findAllUsers() {
    return userRepository.findAll();
  }

  public Optional<User> findUserByUsername(String username) {
    return userRepository.findByUsername(username);
  }
}
