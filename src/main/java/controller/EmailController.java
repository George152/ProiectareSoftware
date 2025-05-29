package controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.web.bind.annotation.*;
import service.EmailService;

@RestController
@RequestMapping("/api/email")
public class EmailController {

    @Autowired
    private EmailService emailService;

    @Autowired(required = false)
    private JavaMailSender mailSender;

    @Value("${spring.mail.host:NOT_SET}")
    private String mailHost;

    @Value("${spring.mail.username:NOT_SET}")
    private String mailUsername;

    @GetMapping("/debug")
    public ResponseEntity<String> debugEmailConfig() {
        StringBuilder debug = new StringBuilder();
        debug.append("Email Configuration Debug:\n");
        debug.append("Mail Host: ").append(mailHost).append("\n");
        debug.append("Mail Username: ").append(mailUsername).append("\n");
        debug.append("JavaMailSender Bean: ").append(mailSender != null ? "AVAILABLE" : "NULL").append("\n");
        debug.append("Email Service Available: ").append(emailService.isEmailServiceAvailable()).append("\n");

        return ResponseEntity.ok(debug.toString());
    }

    @PostMapping("/test")
    public ResponseEntity<String> sendTestEmail(@RequestParam String email) {
        try {
            if (!emailService.isEmailServiceAvailable()) {
                return ResponseEntity.badRequest().body("Email service is not configured. Check /api/email/debug for details.");
            }

            emailService.sendTestEmail(email);
            return ResponseEntity.ok("Test email sent successfully to: " + email);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Failed to send test email: " + e.getMessage());
        }
    }
}
