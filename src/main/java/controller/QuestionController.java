package controller;

import dto.QuestionRequestDTO;
import dto.QuestionResponseDTO;
import entity.Question;
import entity.User;
import org.springframework.http.ResponseEntity;
import service.QuestionService;
import service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/questions")
public class QuestionController {

    @Autowired
    private QuestionService questionService;

    @Autowired
    private UserService userService;

    @GetMapping("/getAll")
    public List<QuestionResponseDTO> getAllQuestions() {
        return questionService.getAllQuestions();
    }

    @GetMapping("/{id}")
    public ResponseEntity<QuestionResponseDTO> getQuestionById(@PathVariable Long id) {
        Optional<QuestionResponseDTO> questionOpt = questionService.getQuestionById(id);
        if (questionOpt.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(questionOpt.get());
    }

    @PostMapping("/insert")
    public ResponseEntity<QuestionResponseDTO> insertQuestion(@RequestBody QuestionRequestDTO questionDTO) {
        if (questionDTO.getAuthor() == null || questionDTO.getAuthor().getId() == null) {
            return ResponseEntity.badRequest().build();
        }

        User author = userService.findUserById(questionDTO.getAuthor().getId());
        if (author == null) {
            return ResponseEntity.badRequest().build();
        }

        QuestionResponseDTO created = questionService.insertQuestion(questionDTO, author);
        return ResponseEntity.ok(created);
    }


   /* @PostMapping("/createWithTags")
    public ResponseEntity<QuestionResponseDTO> createQuestionWithTags(@RequestBody QuestionRequestDTO questionDTO) {
        User author = userService.findUserById(questionDTO.getAuthorId());
        if (author == null) {
            return ResponseEntity.badRequest().build();
        }

        QuestionResponseDTO created = questionService.createQuestionWithTags(questionDTO, author);
        return ResponseEntity.ok(created);
    }*/

    @PutMapping("/{id}")
    public ResponseEntity<QuestionResponseDTO> updateQuestion(@PathVariable Long id, @RequestBody QuestionRequestDTO questionDTO) {
        Optional<QuestionResponseDTO> updated = questionService.updateQuestion(id, questionDTO);
        if (updated.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(updated.get());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteQuestion(@PathVariable Long id) {
        boolean deleted = questionService.deleteQuestion(id);
        if (!deleted) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.noContent().build();
    }
}
