package service;

import entity.User;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import repository.UserRepository;

import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public List<User> getAllUsers() {
        return (List<User>) this.userRepository.findAll();
    }

    public User insertUser(User user) {
        return this.userRepository.save(user);
    }

    public User updateUser(User user) {
        return this.userRepository.save(user);
    }

    public String deleteUser(Long id) {
        try {
            this.userRepository.deleteById(id);
            return "Deletion Successfully";
        } catch (Exception e) {
            return "Failed to delete user with id " + id;
        }
    }
}
