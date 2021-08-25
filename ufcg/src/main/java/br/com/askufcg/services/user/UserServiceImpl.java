package br.com.askufcg.services.user;

import br.com.askufcg.exceptions.Constants;
import br.com.askufcg.exceptions.NoContentException;
import br.com.askufcg.models.User;
import br.com.askufcg.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static br.com.askufcg.utils.Util.checkEntityAlreadyExists;
import static br.com.askufcg.utils.Util.checkEntityNotFound;

@Service
public class UserServiceImpl implements UserService{
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public List<User> getUsers() {
        List<User> users = userRepository.findAll();
        if(users.isEmpty()) {
            throw new NoContentException(Constants.DOES_NOT_EXISTS_USERS);
        }
        return users;
    }

    @Override
    public User getUserById(Long id) {
        Optional<User> optionalUser = userRepository.findById(id);
        checkEntityNotFound(optionalUser, Constants.USER_NOT_FOUND);

        return optionalUser.get();
    }

    @Override
    public User saveUser(User user) {
        Optional<User> optionalUser = userRepository.findByEmail(user.getEmail());
        checkEntityAlreadyExists(optionalUser, Constants.USER_ALREADY_EXISTS);

        var hash = bCryptPasswordEncoder.encode(user.getPassword());
        user.setPassword(hash);

        return userRepository.save(user);
    }

    @Override
    public User updateUser(Long id, User newUser) {
        var user = getUserById(id);

        var updatedUser = updateAllInformationUser(newUser, user);

        userRepository.save(updatedUser);

        return updatedUser;
    }

    private User updateAllInformationUser(User newUser, User user) {
        if(newUser.getFirstName() != null) {
            user.setFirstName(newUser.getFirstName());
        }
        if(newUser.getLastName() != null) {
            user.setLastName(newUser.getLastName());
        }
        if(newUser.getLinkAvatar() != null) {
            user.setLinkAvatar(newUser.getLinkAvatar());
        }
        if(newUser.getLinkGithub() != null) {
            user.setLinkGithub(newUser.getLinkGithub());
        }
        if(newUser.getLinkLinkedin() != null) {
            user.setLinkLinkedin(newUser.getLinkLinkedin());
        }

        return user;
    }

    @Override
    public void deleteUser(Long id) {
        var user = getUserById(id);
        userRepository.delete(user);
    }

    @Override
    public UserDetails loadUserByUsername(String email) {
        Optional<User> optionalUser = userRepository.findByEmail(email);
        checkEntityNotFound(optionalUser, Constants.USER_NOT_FOUND);
        return optionalUser.get();
    }
}
