package service;

import entity.User;
import jakarta.transaction.Transactional;
import dto.BanUserRequestDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import repository.UserRepository;

import java.util.List;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

  /*  private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
        this.passwordEncoder = new BCryptPasswordEncoder();
    }

    public User insertUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

     public PasswordEncoder getPasswordEncoder() {
        return passwordEncoder;
    }
    */

    @Autowired
    private EmailService emailService;

    @Autowired
    private JavaMailSender mailSender;

    public User login(String email, String password) {
        return userRepository.findByEmailAndPassword(email, password)
                .orElseThrow(() -> new RuntimeException("Invalid credentials"));
    }


    public User findUserById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + id));
    }

    public User findByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found with email: " + email));
    }

    public List<User> getAllUsers() {
        return (List<User>) userRepository.findAll();
    }



    public User updateUser(User user) {
        // opțional: encode parolă doar dacă se modifică
        return userRepository.save(user);
    }

    public String deleteUser(Long id) {
        try {
            userRepository.deleteById(id);
            return "Deletion Successfully";
        } catch (Exception e) {
            return "Failed to delete user with id " + id;
        }
    }


    public User insertUser(User user) {
        return userRepository.save(user);
    }

    @Transactional
    public User banUser(BanUserRequestDTO banRequest) {
        System.out.println("Banning user: " + banRequest);

        // Validate input
        if (banRequest.getUserId() == null) {
            throw new IllegalArgumentException("User ID cannot be null");
        }

        if (banRequest.getReason() == null || banRequest.getReason().trim().isEmpty()) {
            throw new IllegalArgumentException("Ban reason cannot be null or empty");
        }

        // Find the user by ID
        User user = userRepository.findById(banRequest.getUserId())
                .orElseThrow(() -> new IllegalArgumentException("User with ID " + banRequest.getUserId() + " not found"));

        // Check if user is already banned
        if (Boolean.TRUE.equals(user.getIsBanned())) {
            throw new IllegalArgumentException("User is already banned");
        }

        // Validate that user has an email address
        if (user.getEmail() == null || user.getEmail().trim().isEmpty()) {
            System.err.println("Warning: User " + user.getUsername() + " does not have an email address. Ban notification will not be sent.");
        }

        // Update the ban status
        user.setIsBanned(true);

        // If you have a banReason field in your User entity, uncomment this:
        // user.setBanReason(banRequest.getReason());

        // Save the updated user
        User updatedUser = userRepository.save(user);

        System.out.println("User " + user.getUsername() + " has been banned. Reason: " + banRequest.getReason());

        // Send email notification
        if (user.getEmail() != null && !user.getEmail().trim().isEmpty()) {
            try {
                EmailService emailService = new EmailService(mailSender);
                emailService.sendBanNotificationEmail(user, banRequest.getReason());
                System.out.println("Ban notification email sent to: " + user.getEmail());
            } catch (Exception e) {
                System.err.println("Failed to send ban notification email: " + e.getMessage());
                // Continue execution - don't fail the ban process if email fails
            }
        }

        return updatedUser;
    }    @Transactional
    public User unbanUser(Long userId) {
        // Find the user by ID
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User with ID " + userId + " not found"));

        // Check if user is actually banned
        if (!Boolean.TRUE.equals(user.getIsBanned())) {
            throw new IllegalArgumentException("User is not currently banned");
        }

        // Update the ban status
        user.setIsBanned(false);

        // If you have a banReason field, clear it:
        // user.setBanReason(null);

        // Save the updated user
        User updatedUser = userRepository.save(user);

        System.out.println("User " + user.getUsername() + " has been unbanned");

        return updatedUser;
    }

}
