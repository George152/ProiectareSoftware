package service;

import dto.AnswerResponseDTO;
import dto.UserDTO;
import entity.Answer;
import entity.User;
import repository.AnswerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AnswerService {

    @Autowired
    private AnswerRepository answerRepository;

    public List<Answer> getAnswersByQuestionIdRaw(Long questionId) {
        return answerRepository.findByQuestionId(questionId);
    }

    public List<AnswerResponseDTO> getAnswersByQuestionId(Long questionId) {
        List<Answer> answers = answerRepository.findByQuestionId(questionId);
        return answers.stream()
                .map(this::mapToResponseDTO)
                .collect(Collectors.toList());
    }

    public Answer findAnswerById(Long id) {
        Optional<Answer> answerOptional = answerRepository.findById(id);
        return answerOptional.orElseThrow(() -> new RuntimeException("Answer not found with id: " + id));
    }

    public List<Answer> getAllAnswers() {
        return (List<Answer>) answerRepository.findAll();
    }

    public Answer insertAnswer(Answer answer) {
        return answerRepository.save(answer);
    }

    public Answer updateAnswer(Answer answer) {
        return answerRepository.save(answer);
    }

    public String deleteAnswer(Long id) {
        try {
            answerRepository.deleteById(id);
            return "Deletion Successfully";
        } catch (Exception e) {
            return "Failed to delete answer with id " + id;
        }
    }

    public AnswerResponseDTO mapToResponseDTO(Answer answer) {
        AnswerResponseDTO dto = new AnswerResponseDTO();
        dto.setId(answer.getId());
        dto.setContent(answer.getContent());
        dto.setPicture(answer.getPicture());
        dto.setCreatedDate(answer.getCreatedDate());
        dto.setQuestionId(answer.getQuestion().getId());

        User author = answer.getAuthor();
        UserDTO authorDTO = new UserDTO();
        authorDTO.setId(author.getId());
        authorDTO.setUsername(author.getUsername());
        authorDTO.setScore(author.getScore());
        authorDTO.setRole(author.getRole());

        dto.setAuthor(authorDTO);

        return dto;
    }


}
