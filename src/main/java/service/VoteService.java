package service;

import dto.VoteRequestDTO;
import dto.VoteResponseDTO;
import entity.Answer;
import entity.Question;
import entity.User;
import entity.Vote;
import repository.AnswerRepository;
import repository.QuestionRepository;
import repository.UserRepository;
import repository.VoteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class VoteService {

    @Autowired
    private VoteRepository voteRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private QuestionRepository questionRepository;

    @Autowired
    private AnswerRepository answerRepository;

    public VoteResponseDTO createVote(VoteRequestDTO dto) {
        System.out.println("Creating vote: " + dto);

        Optional<User> userOpt = userRepository.findById(dto.getUserId());
        if (userOpt.isEmpty()) {
            throw new IllegalArgumentException("User not found");
        }


        if (dto.getVoteType() == null || (dto.getVoteType() != 1 && dto.getVoteType() != -1)) {
            System.out.println("Invalid vote type: " + dto.getVoteType());
            throw new IllegalArgumentException("Vote type must be 1 (upvote) or -1 (downvote).");
        }

        User user = userOpt.get();
        System.out.println("User found: " + user.getUsername());

        Question question = null;
        if (dto.getQuestionId() != null) {
            System.out.println("Processing vote for question ID: " + dto.getQuestionId());

            question = questionRepository.findById(dto.getQuestionId())
                    .orElseThrow(() -> {
                        System.out.println("Question not found: " + dto.getQuestionId());
                        return new IllegalArgumentException("Question not found");
                    });

            if (question.getAuthor().getId().equals(user.getId())) {
                System.out.println("User tried to vote own question.");
                throw new IllegalArgumentException("You cannot vote your own question.");
            }

            if (voteRepository.existsByUserAndQuestion(user, question)) {
                System.out.println("Duplicate vote on question.");
                throw new IllegalArgumentException("You have already voted this question.");
            }
        }

        Answer answer = null;
        if (dto.getAnswerId() != null) {
            System.out.println("Processing vote for answer ID: " + dto.getAnswerId());

            answer = answerRepository.findById(dto.getAnswerId())
                    .orElseThrow(() -> {
                        System.out.println("Answer not found: " + dto.getAnswerId());
                        return new IllegalArgumentException("Answer not found");
                    });

            if (answer.getAuthor().getId().equals(user.getId())) {
                System.out.println("User tried to vote own answer.");
                throw new IllegalArgumentException("You cannot vote your own answer.");
            }

            if (voteRepository.existsByUserAndAnswer(user, answer)) {
                System.out.println("Duplicate vote on answer.");
                throw new IllegalArgumentException("You have already voted this answer.");
            }
        }

        Vote vote = new Vote();
        vote.setUser(user);
        vote.setQuestion(question);
        vote.setAnswer(answer);
        vote.setVoteType(dto.getVoteType());

        vote = voteRepository.save(vote);
        System.out.println("Vote saved with ID: " + vote.getId());

        return mapToDTO(vote);
    }

    public List<VoteResponseDTO> getAllVotes() {
        return voteRepository.findAll().stream()
                .map(this::mapToDTO)
                .toList();
    }

    private VoteResponseDTO mapToDTO(Vote vote) {
        VoteResponseDTO dto = new VoteResponseDTO();
        dto.setId(vote.getId());
        dto.setUserId(vote.getUser().getId());
        dto.setQuestionId(vote.getQuestion() != null ? vote.getQuestion().getId() : null);
        dto.setAnswerId(vote.getAnswer() != null ? vote.getAnswer().getId() : null);
        dto.setVoteType(vote.getVoteType());
        return dto;
    }
}
