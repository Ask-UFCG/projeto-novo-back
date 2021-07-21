package br.com.askufcg.services.user;

import br.com.askufcg.exceptions.BadRequestException;
import br.com.askufcg.exceptions.NoContentException;
import br.com.askufcg.exceptions.NotFoundException;
import br.com.askufcg.models.User;
import br.com.askufcg.repositories.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
public class UserServiceTest {
    @InjectMocks
    private UserService userService = new UserServiceImpl();

    @Mock
    private UserRepository userRepository;
    @Mock
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    private List<User> mockedUsers;
    private List<User> emptyList;
    private User mockedUser;
    private User mockedUser2;

    @BeforeEach
    void setUp() {
        this.mockedUsers = new ArrayList<>();
        this.emptyList = new ArrayList<>();

        this.mockedUser = new User(14L, "wellisson@mail.com", "hashed", "Wellisson", "Gomes", "", "", "");
        this.mockedUser2 = new User(2L, "cicera@mail.com", "hashed", "Cicera", "Gomes","sem avatar", "linkedin", "github");

        this.mockedUsers.add(new User(1L, "wellisson@mail.com", "hashed", "Wellisson", "Gomes", "", "", ""));
        this.mockedUsers.add(new User(2L, "lucas@mail.com", "hashed", "Lucas", "Gomes", "", "", ""));

        when(bCryptPasswordEncoder.encode(Mockito.any())).thenReturn(mockedUser.getPassword());

    }

    @Test
    public void ShouldReturnAllUsers() {
        when(userRepository.findAll()).thenReturn(mockedUsers);

        List<User> users = userService.getUsers();

        assertEquals(users, mockedUsers);
    }

    @Test
    public void ShouldThrowErroeCallingFindAllWhenDoesNotExistsUsers() {
        when(userRepository.findAll()).thenReturn(emptyList);

        assertThrows(NoContentException.class, () -> {
           userService.getUsers();
        });
    }

    @Test
    public void ShouldReturnAnUserById() {
        when(userRepository.findById(Mockito.any(Long.class))).thenReturn(Optional.of(mockedUser));

        User user = userService.getUserById(2L);

        assertEquals(user, mockedUser);
        assertEquals(user.getEmail(), mockedUser.getEmail());
        assertEquals(user.getPassword(), mockedUser.getPassword());
        assertEquals(user.getFirstName(), mockedUser.getFirstName());
        assertEquals(user.getLastName(), mockedUser.getLastName());
        assertEquals(user.getLinkAvatar(), mockedUser.getLinkAvatar());
        assertEquals(user.getLinkGithub(), mockedUser.getLinkGithub());
        assertEquals(user.getLinkLinkedin(), mockedUser.getLinkLinkedin());
    }

    @Test
    public void ShouldThrowErrorCallingGetByIdWhenIdDoesNotExists() {
        when(userRepository.findById(Mockito.any(Long.class))).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> {
            userService.getUserById(2L);
        });
    }

    @Test
    public void ShouldReturnAnUserWhenSave() {
        when(userRepository.save(Mockito.any(User.class))).thenReturn(mockedUser);

        var user = userService.saveUser(mockedUser2);
        assertEquals(user, mockedUser);
    }

    @Test
    public void shouldThrowErrorCallingSaveUserWhenEmailAlredyExists() {
        when(userRepository.findByEmail("mail@mail.com")).thenReturn(Optional.of(mockedUser));

        assertThrows(BadRequestException.class, () -> {
            var user = new User();
            user.setEmail("mail@mail.com");
            userService.saveUser(user);
        });
    }

    @Test
    public void ShouldUpdateAnUser() {
        when(userRepository.findById(Mockito.eq(1L))).thenReturn(Optional.of(mockedUser));

        var userUpdated = userService.updateUser(1L, mockedUser2);

        assertEquals(mockedUser2.getEmail(), userUpdated.getEmail());
        assertEquals(mockedUser2.getPassword(), userUpdated.getPassword());
        assertEquals(mockedUser2.getFirstName(), userUpdated.getFirstName());
        assertEquals(mockedUser2.getLastName(), userUpdated.getLastName());
        assertEquals(mockedUser2.getLinkAvatar(), userUpdated.getLinkAvatar());
        assertEquals(mockedUser2.getLinkLinkedin(), userUpdated.getLinkLinkedin());
        assertEquals(mockedUser2.getLinkGithub(), userUpdated.getLinkGithub());
    }

    @Test
    public void ShouldThrowErrorCallingUpdateUserWhenUserDoesNotExists() {
        when(userRepository.findById(Mockito.any(Long.class))).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> {
            userService.updateUser(1L, mockedUser2);
        });
    }

    @Test
    public void ShouldDeleteAnUser() {
        when(userRepository.findById(Mockito.eq(1L))).thenReturn(Optional.of(mockedUser));
        userService.deleteUser(1L);
        Mockito.verify(userRepository).delete(mockedUser);
    }

    @Test
    public void ShouldThrowErrorCallingDeleteUserWhenUserDoesNotExists() {
        when(userRepository.findById(Mockito.eq(1L))).thenReturn(Optional.empty());
        assertThrows(NotFoundException.class, () -> {
            userService.deleteUser(1L);
        });
    }
}