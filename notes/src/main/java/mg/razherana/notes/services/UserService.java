package mg.razherana.notes.services;

import mg.razherana.notes.repositories.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import mg.razherana.notes.entities.User;

@Service
public class UserService {

  @Autowired
  private UserRepository userRepository;

  public User getUserByUsername(String username) {
    return userRepository.findByUsername(username).orElse(null);
  }
}
