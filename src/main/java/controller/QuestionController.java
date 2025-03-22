package controller;

import entity.Question;
import service.QuestionService;
import service.UserService;
import entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/questions")
public class QuestionController {

    @Autowired
    private QuestionService questionService;

    @GetMapping("/getAll")
    public List<Question> getAllQuestions() {
        return questionService.getAllQuestions();
    }

    @Autowired
    private UserService userService; // Injected UserService instance
    // Find a user by ID


    @PostMapping("/insert")
    public Question insertQuestion(@RequestBody Question question, @RequestParam Long authorId) {
        // Fetch the User entity by their ID
        User author = userService.findUserById(authorId);

        // Set the author for the question
        if (author != null) {
            question.setAuthor(author);
        } else {
            throw new RuntimeException("User not found with id: " + authorId);
        }

        // Insert the question
        return questionService.insertQuestion(question);
    }


    @PutMapping("/update")
    public Question updateQuestion(@RequestBody Question question) {
        return questionService.updateQuestion(question);
    }

    @DeleteMapping("/deleteById")
    public String deleteQuestion(@RequestParam Long id) {
        return questionService.deleteQuestion(id);
    }
}
