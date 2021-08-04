package br.com.askufcg.services.question;

import br.com.askufcg.exceptions.NotFoundException;
import br.com.askufcg.models.Question;
import br.com.askufcg.models.Comment;
import br.com.askufcg.models.Question;
import br.com.askufcg.models.User;
import br.com.askufcg.repositories.QuestionRepository;
import br.com.askufcg.repositories.QuestionRepository;
import br.com.askufcg.services.question.QuestionService;
import br.com.askufcg.services.question.QuestionServiceImpl;
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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class QuestionServiceTest {
    @InjectMocks
    private QuestionService questionService = new QuestionServiceImpl();
    @Mock
    private QuestionRepository questionRepository;

    private User mockedAuthor;
    private User mockedAuthor2;
    private List<Question> mockedQuestions;
    private Question mockedQuestion;
    private Question mockedQuestion2;

    @BeforeEach
    void setUp() {
        this.mockedQuestions = new ArrayList<>();

        this.mockedAuthor = new User(1L, "mail@mail.com", "hashed", "first", "last", "link", "link", "link");
        this.mockedAuthor2 = new User(2L, "mail2@mail.com", "hashed2", "first2", "last2", "link2", "link2", "link2");

        var comments = Collections.singletonList(new Comment());

        this.mockedQuestion = new Question(1L, "title", "content", 20, 10, new Date(), true, new HashSet<>(), mockedAuthor, new ArrayList<>(), new ArrayList<>());
        this.mockedQuestion2 = new Question(2L, "title", "content", 20, 10, new Date(), true, new HashSet<>(), mockedAuthor, new ArrayList<>(), new ArrayList<>());

        this.mockedQuestions.add(mockedQuestion);
    }

    @Test
    public void ShouldReturnAllUserQuestions() {
        when(questionRepository.findQuestionsByAuthor(mockedAuthor)).thenReturn(mockedQuestions);

        var questions = questionService.getUserQuestions(mockedAuthor);

        assertEquals(mockedQuestions, questions);
    }

    @Test
    public void ShouldReturnAnQuestionWhenSave() {
        when(questionRepository.findById(Mockito.eq(1L))).thenReturn(Optional.of(mockedQuestion));
        when(questionRepository.save(mockedQuestion2)).thenReturn(mockedQuestion2);

        var user = new User();
        var savedQuestion = questionService.saveQuestion(mockedQuestion2, user);

        assertEquals(mockedQuestion2, savedQuestion);
        assertEquals(user, savedQuestion.getAuthor());
    }

    @Test
    public void ShouldUpdateQuestion() {
        when(questionRepository.findById(Mockito.eq(1L))).thenReturn(Optional.of(mockedQuestion));

        var updatedQuestion = questionService.updateQuestion(1L, mockedQuestion2);

        assertEquals(mockedQuestion2.getTitle(), updatedQuestion.getTitle());
        assertEquals(mockedQuestion2.getContent(), updatedQuestion.getContent());
        assertEquals(mockedQuestion2.getComments(), updatedQuestion.getComments());
        assertEquals(mockedQuestion2.getQtdLikes(), updatedQuestion.getQtdLikes());
        assertEquals(mockedQuestion2.getQtdDislikes(), updatedQuestion.getQtdDislikes());
        assertEquals(mockedQuestion2.getTags(), updatedQuestion.getTags());
    }

    @Test
    public void ShouldThrowAnErrorWhenCallingUpdateQuestionWithInvalidId() {
        when(questionRepository.findById(Mockito.eq(1L))).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> {
           questionService.updateQuestion(1L, mockedQuestion);
        });
    }

    @Test
    public void ShouldReturnAnQuestionById() {
        when(questionRepository.findById(Mockito.eq(1L))).thenReturn(Optional.of(mockedQuestion));

        var question = questionService.getQuestionById(1L);

        assertEquals(mockedQuestion.getAuthor(), question.getAuthor());
        assertEquals(mockedQuestion.getTitle(), question.getTitle());
        assertEquals(mockedQuestion.getComments(), question.getComments());
        assertEquals(mockedQuestion.isAnswered(), question.isAnswered());
        assertEquals(mockedQuestion.getQtdLikes(), question.getQtdLikes());
        assertEquals(mockedQuestion.getQtdDislikes(), question.getQtdDislikes());
        assertEquals(mockedQuestion.getTags(), question.getTags());
        assertEquals(mockedQuestion.getAnswers(), question.getAnswers());
    }

    @Test
    public void ShouldThrowErrorCallingGetByIdWhenIdDoesNotExists() {
        when(questionRepository.findById(Mockito.eq(1L))).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> {
           questionService.getQuestionById(1L);
        });
    }

    @Test
    public void ShouldDeleteAnQuestion() {
        when(questionRepository.findById(Mockito.eq(1L))).thenReturn(Optional.of(mockedQuestion));
        questionService.deleteQuestionById(1L);
        Mockito.verify(questionRepository).delete(mockedQuestion);
    }

    @Test
    public void ShouldThrowErrorCallingDeleteQuestionWhenIdDoesNotExists() {
        when(questionRepository.findById(Mockito.eq(1L))).thenReturn(Optional.empty());
        assertThrows(NotFoundException.class, () -> {
            questionService.deleteQuestionById(1L);
        });
    }
}