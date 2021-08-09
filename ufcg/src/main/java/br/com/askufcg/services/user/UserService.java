package br.com.askufcg.services.user;

import br.com.askufcg.models.User;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

public interface UserService extends UserDetailsService {
    public List<User> getUsers();
    public User getUserById(Long id);
    public User saveUser(User user);
    public User updateUser(Long id, User newUser);
    public void deleteUser(Long id);
}
