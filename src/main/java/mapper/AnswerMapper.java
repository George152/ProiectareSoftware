package mapper;

import dto.AnswerRequestDTO;
import dto.AnswerResponseDTO;
import entity.Answer;
import entity.Question;
import entity.User;

public class AnswerMapper {
    public AnswerResponseDTO mapToDTO(Answer answer) {
        AnswerResponseDTO dto = new AnswerResponseDTO();
        dto.setId(answer.getId());
        dto.setContent(answer.getContent());
        dto.setPicture(answer.getPicture());
        dto.setCreatedDate(answer.getCreatedDate());
        dto.setAuthorUsername(answer.getAuthor().getUsername());
        dto.setQuestionId(answer.getQuestion().getId());
        return dto;
    }

    public Answer mapToEntity(AnswerRequestDTO dto, User author, Question question) {
        Answer answer = new Answer();
        answer.setContent(dto.getContent());
        answer.setPicture(dto.getPicture());
        answer.setAuthor(author);
        answer.setQuestion(question);
        return answer;
    }

}
