package controller;

import dto.LoginRequestDTO;
import dto.RegisterRequestDTO;
import dto.BanUserRequestDTO;
import dto.UserDTO;
import entity.User;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.server.ResponseStatusException;
import service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    /*@PostMapping("/login")
    public ResponseEntity<UserDTO> login(@RequestBody LoginRequestDTO loginRequest) {
        try {
            User user = userService.findByEmail(loginRequest.getEmail());

            if (!userService.getPasswordEncoder().matches(loginRequest.getPassword(), user.getPassword())) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
            }

            if (Boolean.TRUE.equals(user.getIsBanned())) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
            }

            return ResponseEntity.ok(convertToDTO(user));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }

    @PostMapping("/register")
    public ResponseEntity<UserDTO> register(@RequestBody RegisterRequestDTO request) {
        try {
            // verifică dacă există deja un user cu acest email
            if (userService.findByEmail(request.getEmail()) != null) {
                return ResponseEntity.status(HttpStatus.CONFLICT).build();
            }

            User newUser = new User();
            newUser.setEmail(request.getEmail());
            newUser.setUsername(request.getUsername());
            newUser.setPassword(userService.getPasswordEncoder().encode(request.getPassword()));
            newUser.setIsBanned(false);

            User savedUser = userService.insertUser(newUser);

            return ResponseEntity.status(HttpStatus.CREATED).body(convertToDTO(savedUser));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
*/

    private UserDTO convertToDTO(User user) {
        UserDTO dto = new UserDTO();
        dto.setId(user.getId());
        dto.setUsername(user.getUsername());
        dto.setEmail(user.getEmail());
        dto.setRole(user.getRole());
        dto.setIsBanned(user.getIsBanned());

        return dto;
    }

    @GetMapping("/getAll")
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }

    @PostMapping("/insert")
    public ResponseEntity<User> insertUser(@RequestBody User user) {
        //return userService.insertUser(user);
        User saved = userService.insertUser(user);
        return ResponseEntity.ok(saved);
    }

    @CrossOrigin(origins = "http://localhost:3000")
    @PostMapping("/login")
    public ResponseEntity<UserDTO> login(@RequestBody LoginRequestDTO loginRequest) {
        try {
            User user = userService.login(loginRequest.getEmail(), loginRequest.getPassword());

            if (Boolean.TRUE.equals(user.getIsBanned())) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
            }
            if (user.getIsBanned()) {
                throw new ResponseStatusException(HttpStatus.FORBIDDEN, "You are banned from this website.");
            }
            return ResponseEntity.ok(convertToDTO(user));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

    }



    @PutMapping("/update")
    public User updateUser(@RequestBody User user) {
        return userService.updateUser(user);
    }

    @DeleteMapping("/deleteById")
    public String deleteUser(@RequestParam Long id) {
        return userService.deleteUser(id);
    }

    @GetMapping("/test")
    public String test() {
        return "User controller is working!";
    }

    @PutMapping("/ban")
    public ResponseEntity<User> banUser(@RequestBody BanUserRequestDTO banRequest) {
        try {
            System.out.println("Received ban request: " + banRequest);

            User bannedUser = userService.banUser(banRequest);
            return ResponseEntity.ok(bannedUser);

        } catch (IllegalArgumentException e) {
            System.err.println("Ban request failed: " + e.getMessage());
            return ResponseEntity.badRequest().build();
        } catch (Exception e) {
            System.err.println("Unexpected error during ban: " + e.getMessage());
            return ResponseEntity.internalServerError().build();
        }
    }

    @PutMapping("/unban/{userId}")
    public ResponseEntity<User> unbanUser(@PathVariable Long userId) {
        try {
            User unbannedUser = userService.unbanUser(userId);
            return ResponseEntity.ok(unbannedUser);

        } catch (IllegalArgumentException e) {
            System.err.println("Unban request failed: " + e.getMessage());
            return ResponseEntity.badRequest().build();
        } catch (Exception e) {
            System.err.println("Unexpected error during unban: " + e.getMessage());
            return ResponseEntity.internalServerError().build();
        }
    }

}
