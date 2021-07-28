package br.com.askufcg.services.answer;

import br.com.askufcg.exceptions.NotFoundException;
import br.com.askufcg.models.Answer;
import br.com.askufcg.models.Comment;
import br.com.askufcg.models.Question;
import br.com.askufcg.models.User;
import br.com.askufcg.repositories.AnswerRepository;
import br.com.askufcg.repositories.QuestionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class AnswerServiceTest {
    @InjectMocks
    private AnswerService answerService = new AnswerServiceImpl();
    @Mock
    private AnswerRepository answerRepository;
    @Mock
    private QuestionRepository questionRepository;

    private User mockedAuthor;
    private User mockedAuthor2;
    private List<Answer> mockedAnswers;
    private Answer mockedAnswer;
    private Answer mockedAnswer2;
    private Question mockedQuestion;

    @BeforeEach
    void setUp() {
        this.mockedAnswers = new ArrayList<>();

        this.mockedAuthor = new User(1L, "mail@mail.com", "hashed", "first", "last", "link", "link", "link");
        this.mockedAuthor2 = new User(2L, "mail2@mail.com", "hashed2", "first2", "last2", "link2", "link2", "link2");

        var comments = Collections.singletonList(new Comment());

        this.mockedQuestion = new Question(1L, "title", "content", 20, 10, new Date(), true, new HashSet<>(), mockedAuthor, new ArrayList<>(), new ArrayList<>());

        this.mockedAnswer = new Answer(1L, "content", 10, 5, new Date(), false, this.mockedAuthor, mockedQuestion, comments);
        this.mockedAnswer2 = new Answer(2L, "content2", 20, 10, new Date(), false, this.mockedAuthor, mockedQuestion, comments);

        this.mockedAnswers.add(mockedAnswer);
    }

    @Test
    public void ShouldReturnAllUserAnswers() {
        when(answerRepository.findAnswersByAuthor(mockedAuthor)).thenReturn(mockedAnswers);

        var answers = answerService.getUserAnswers(mockedAuthor);

        assertEquals(mockedAnswers, answers);
    }

    @Test
    public void ShouldReturnAnAnserWhenSave() {
        when(questionRepository.findById(Mockito.eq(1L))).thenReturn(Optional.of(mockedQuestion));
        when(answerRepository.save(mockedAnswer2)).thenReturn(mockedAnswer2);

        var user = new User();
        var savedAnswer = answerService.saveAnswer(mockedAnswer2, user,1L);

        assertEquals(mockedAnswer2, savedAnswer);
        assertEquals(user, savedAnswer.getAuthor());
    }

    @Test
    public void ShouldThrowAnErrorWhenCallingSaveAnswerWithInvalidQuestionId() {
        when(questionRepository.findById(Mockito.eq(1L))).thenReturn(Optional.empty());

        var user = new User();
        assertThrows(NotFoundException.class, () -> {
            answerService.saveAnswer(mockedAnswer2, user,1L);
        });
    }

    @Test
    public void ShouldUpdateAnswer() {
        when(answerRepository.findById(Mockito.eq(1L))).thenReturn(Optional.of(mockedAnswer));

        var updatedAnswer = answerService.updateAnswer(mockedAnswer2, 1L);

        assertEquals(mockedAnswer2.getContent(), updatedAnswer.getContent());
        assertEquals(mockedAnswer2.getComments(), updatedAnswer.getComments());
        assertEquals(mockedAnswer2.getQtdLikes(), updatedAnswer.getQtdLikes());
        assertEquals(mockedAnswer2.getQtdDislikes(), updatedAnswer.getQtdDislikes());
        assertEquals(mockedAnswer2.getSolution(), updatedAnswer.getSolution());
    }

    @Test
    public void ShouldThrowAnErrorWhenCallingUpdateAnswerWithInvalidId() {
        when(answerRepository.findById(Mockito.eq(1L))).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> {
           answerService.updateAnswer(mockedAnswer, 1L);
        });
    }

    @Test
    public void ShouldReturnAnAnswerById() {
        when(answerRepository.findById(Mockito.eq(1L))).thenReturn(Optional.of(mockedAnswer));

        var answer = answerService.getAnswerById(1L);

        assertEquals(mockedAnswer.getAuthor(), answer.getAuthor());
        assertEquals(mockedAnswer.getComments(), answer.getComments());
        assertEquals(mockedAnswer.getSolution(), answer.getSolution());
        assertEquals(mockedAnswer.getQtdLikes(), answer.getQtdLikes());
        assertEquals(mockedAnswer.getQtdDislikes(), answer.getQtdDislikes());
    }

    @Test
    public void ShouldThrowErrorCallingGetByIdWhenIdDoesNotExists() {
        when(answerRepository.findById(Mockito.eq(1L))).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> {
           answerService.getAnswerById(1L);
        });
    }

    @Test
    public void ShouldDeleteAnAwnser() {
        when(answerRepository.findById(Mockito.eq(1L))).thenReturn(Optional.of(mockedAnswer));
        answerService.deleteAnswerById(1L);
        Mockito.verify(answerRepository).delete(mockedAnswer);
    }

    @Test
    public void ShouldThrowErrorCallingDeleteAwnserWhenIdDoesNotExists() {
        when(answerRepository.findById(Mockito.eq(1L))).thenReturn(Optional.empty());
        assertThrows(NotFoundException.class, () -> {
            answerService.deleteAnswerById(1L);
        });
    }
}