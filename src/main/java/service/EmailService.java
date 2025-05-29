package service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Service;
import entity.User;

import jakarta.annotation.PostConstruct;

@Service
public class EmailService {

    private final JavaMailSender mailSender;

    @Value("${app.mail.from}")
    private String fromEmail;

    @Value("${app.mail.support:support@yourforum.com}")
    private String supportEmail;

    @Value("${app.forum.name:Forum}")
    private String forumName;

    @Value("${app.forum.url:https://yourforum.com}")
    private String forumUrl;

    public EmailService(@Autowired JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    @PostConstruct
    public void init() {
        if (mailSender == null) {
            System.err.println("WARNING: JavaMailSender is not configured.");
        } else {
            System.out.println("Email service initialized successfully.");
            if (mailSender instanceof JavaMailSenderImpl) {
                JavaMailSenderImpl impl = (JavaMailSenderImpl) mailSender;
                System.out.println("Mail configuration - Host: " + impl.getHost() +
                        ", Port: " + impl.getPort() +
                        ", Username: " + impl.getUsername());
            }
        }
    }

    public void sendBanNotificationEmail(User user, String banReason) {
        if (!isEmailServiceAvailable()) {
            System.err.println("Email service is not available. Cannot send ban notification.");
            return;
        }

        if (user.getEmail() == null || user.getEmail().trim().isEmpty()) {
            System.err.println("User does not have a valid email address: " + user.getUsername());
            return;
        }

        try {
            System.out.println("Attempting to send ban notification email to: " + user.getEmail());
            sendSimpleBanNotificationEmail(user, banReason);
        } catch (Exception e) {
            System.err.println("Failed to send ban notification email: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public void sendSimpleBanNotificationEmail(User user, String banReason) {
        if (!isEmailServiceAvailable()) {
            throw new IllegalStateException("Email service is not configured");
        }

        // Debug mail sender configuration
        if (mailSender instanceof JavaMailSenderImpl) {
            JavaMailSenderImpl impl = (JavaMailSenderImpl) mailSender;
            System.out.println("Sending email using - Host: " + impl.getHost() +
                    ", Port: " + impl.getPort() +
                    ", Username: " + impl.getUsername());
        }

        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom(fromEmail);
            message.setTo(user.getEmail());
            message.setSubject("Account Suspended - " + forumName);

            String textContent = createSimpleBanEmailContent(user, banReason);
            message.setText(textContent);

            System.out.println("Sending email from: " + fromEmail + " to: " + user.getEmail());
            mailSender.send(message);
            System.out.println("Ban notification email sent successfully to: " + user.getEmail());

        } catch (Exception e) {
            System.err.println("Failed to send ban notification email to: " + user.getEmail());
            System.err.println("Error details: " + e.getMessage());
            throw e;
        }
    }

    public boolean isEmailServiceAvailable() {
        return mailSender != null;
    }

    public void sendTestEmail(String toEmail) {
        if (!isEmailServiceAvailable()) {
            throw new IllegalStateException("Email service is not configured");
        }

        // Debug configuration before sending
        if (mailSender instanceof JavaMailSenderImpl) {
            JavaMailSenderImpl impl = (JavaMailSenderImpl) mailSender;
            System.out.println("Test email configuration - Host: " + impl.getHost() +
                    ", Port: " + impl.getPort() +
                    ", Username: " + impl.getUsername());
        }

        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom(fromEmail);
            message.setTo(toEmail);
            message.setSubject("Test Email - " + forumName);
            message.setText("This is a test email to verify email configuration is working properly.\n\nSent at: " + new java.util.Date());

            System.out.println("Sending test email from: " + fromEmail + " to: " + toEmail);
            mailSender.send(message);
            System.out.println("Test email sent successfully to: " + toEmail);

        } catch (Exception e) {
            System.err.println("Failed to send test email: " + e.getMessage());
            e.printStackTrace();
            throw e;
        }
    }

    private String createSimpleBanEmailContent(User user, String banReason) {
        return String.format("""
            Dear %s,
            
            We regret to inform you that your account on %s has been suspended.
            
            Reason for suspension: %s
            
            This action was taken due to a violation of our community guidelines and terms of service.
            
            What this means:
            - You will no longer be able to post questions or answers
            - You cannot vote on content
            - Your access to the forum is restricted
            
            If you believe this suspension was made in error or would like to appeal this decision, 
            please contact our support team at %s.
            
            We appreciate your understanding and encourage you to review our community guidelines.
            
            Best regards,
            The %s Team
            
            ---
            This is an automated message. Please do not reply to this email.
            Visit us at %s
            """,
                user.getUsername(),
                forumName,
                banReason,
                supportEmail,
                forumName,
                forumUrl
        );
    }
}
