// controller/AnswerController.java
package controller;

import dto.AnswerRequestDTO;
import dto.AnswerResponseDTO;
import entity.Answer;
import entity.Question;
import entity.User;
import service.AnswerService;
import service.QuestionService;
import service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/answers")
public class AnswerController {

    @Autowired
    private AnswerService answerService;

    @Autowired
    private UserService userService;

    @Autowired
    private QuestionService questionService;

    @GetMapping("/byQuestion/{questionId}")
    public List<AnswerResponseDTO> getAnswersByQuestionId(@PathVariable Long questionId) {
        return answerService.getAnswersByQuestionId(questionId).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @GetMapping("/getAll")
    public List<AnswerResponseDTO> getAllAnswers() {
        return answerService.getAllAnswers().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @PostMapping("/insert")
    public AnswerResponseDTO insertAnswer(
            @RequestBody AnswerRequestDTO answerDTO,
            @RequestParam Long authorId,
            @RequestParam Long questionId) {

        User author = userService.findUserById(authorId);
        Question question = questionService.findQuestionEntityById(questionId);

        Answer answer = new Answer();
        answer.setAuthor(author);
        answer.setQuestion(question);
        answer.setContent(answerDTO.getContent());
        answer.setPicture(answerDTO.getPicture());

        return convertToDTO(answerService.insertAnswer(answer));
    }

    @PutMapping("/update")
    public AnswerResponseDTO updateAnswer(
            @RequestBody AnswerRequestDTO answerDTO,
            @RequestParam Long answerId,
            @RequestParam Long authorId,
            @RequestParam Long questionId) {

        User author = userService.findUserById(authorId);
        Question question = questionService.findQuestionEntityById(questionId);

        Answer existingAnswer = answerService.findAnswerById(answerId);
        existingAnswer.setAuthor(author);
        existingAnswer.setQuestion(question);
        existingAnswer.setContent(answerDTO.getContent());
        existingAnswer.setPicture(answerDTO.getPicture());

        return convertToDTO(answerService.updateAnswer(existingAnswer));
    }

    @DeleteMapping("/deleteById")
    public String deleteAnswer(@RequestParam Long id) {
        return answerService.deleteAnswer(id);
    }

    private AnswerResponseDTO convertToDTO(Answer answer) {
        AnswerResponseDTO dto = new AnswerResponseDTO();
        dto.setId(answer.getId());
        dto.setContent(answer.getContent());
        dto.setPicture(answer.getPicture());
        dto.setCreatedDate(answer.getCreatedDate());
        dto.setAuthorUsername(answer.getAuthor().getUsername());
        dto.setQuestionId(answer.getQuestion().getId());
        return dto;
    }
}
