package service;

import dto.QuestionRequestDTO;
import dto.QuestionResponseDTO;
import entity.Question;
import entity.User;
import mapper.QuestionMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import repository.AnswerRepository;
import repository.QuestionRepository;
import repository.UserRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class QuestionService {

    @Autowired
    private QuestionRepository questionRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AnswerRepository answerRepository;

    public boolean deleteQuestion(Long questionId) {
        // verifică dacă există întrebarea
        if (!questionRepository.existsById(questionId)) {
            return false; // nu există întrebarea
        }

        // șterge răspunsurile legate de întrebare
        answerRepository.deleteByQuestionId(questionId);

        // acum șterge întrebarea
        questionRepository.deleteById(questionId);

        return true; // șters cu succes
    }

    public Question findQuestionEntityById(Long id) {
        return questionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Question not found with id: " + id));
    }

    public Optional<QuestionResponseDTO> getQuestionById(Long id) {
        Optional<Question> questionOpt = questionRepository.findById(id);
        return questionOpt.map(QuestionMapper::toDTO);
    }


    public List<QuestionResponseDTO> getAllQuestions() {
        List<Question> questions = (List<Question>) questionRepository.findAll();
        return questions.stream().map(QuestionMapper::toDTO).collect(Collectors.toList());
    }

    public QuestionResponseDTO insertQuestion(QuestionRequestDTO questionDTO, User author) {
        author = userRepository.findById(questionDTO.getAuthorId())
                .orElseThrow(() -> new RuntimeException("User not found with id: " + questionDTO.getAuthorId()));

        String tagString = String.join(",", questionDTO.getTagNames());

        Question question = new Question();
        question.setTitle(questionDTO.getTitle());
        question.setContent(questionDTO.getContent());
        question.setPicture(questionDTO.getPicture());
        question.setAuthor(author);
        question.setTags(tagString); // ← aici adaugi tag-urile
        question.setStatus("received");

        Question saved = questionRepository.save(question);
        return QuestionMapper.toDTO(saved);
    }


   /* public QuestionResponseDTO createQuestionWithTags(QuestionRequestDTO questionDTO, User author) {
        author = userRepository.findById(questionDTO.getAuthorId())
                .orElseThrow(() -> new RuntimeException("User not found with id: " + questionDTO.getAuthorId()));

        Set<Tag> tags = questionDTO.getTagNames().stream()
                .map(name -> tagRepository.findByName(name)
                        .orElseGet(() -> tagRepository.save(new Tag(null, name))))
                .collect(Collectors.toSet());


        Question question = new Question();
        question.setTitle(questionDTO.getTitle());
        question.setContent(questionDTO.getContent());
        question.setPicture(questionDTO.getPicture());
        question.setAuthor(author);
        question.setTags(tags);
        question.setStatus("Received");

        Question saved = questionRepository.save(question);
        return QuestionMapper.toDTO(saved);
    }*/

    public Optional<QuestionResponseDTO> updateQuestion(Long id, QuestionRequestDTO questionDTO) {
        Optional<Question> existing = questionRepository.findById(id);
        if (existing.isEmpty()) {
            return Optional.empty();
        }
        Question question = existing.get();

        if (questionDTO.getTitle() != null) {
            question.setTitle(questionDTO.getTitle());
        }

        if (questionDTO.getContent() != null) {
            question.setContent(questionDTO.getContent());
        }

        if (questionDTO.getStatus() != null) {
            question.setStatus(questionDTO.getStatus());
        }

        Question updated = questionRepository.save(question);
        return Optional.of(QuestionMapper.toDTO(updated));
    }




}
