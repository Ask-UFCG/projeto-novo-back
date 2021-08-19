package br.com.askufcg.services.auth;

import br.com.askufcg.dtos.auth.LoginRequest;
import br.com.askufcg.dtos.auth.LoginResponse;
import br.com.askufcg.dtos.user.UserMapper;
import br.com.askufcg.exceptions.BadRequestException;
import br.com.askufcg.models.User;
import br.com.askufcg.repositories.UserRepository;
import br.com.askufcg.utils.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static br.com.askufcg.utils.Util.checkEntityNotFound;

@Service
public class AuthServiceImpl implements AuthService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private JwtUtils jwtUtils;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    @Autowired
    private UserMapper userMapper;

    @Override
    public LoginResponse auth(LoginRequest loginRequest) {
        Optional<User> optionalUser = userRepository.findByEmail(loginRequest.getEmail());
        checkEntityNotFound(optionalUser, "O email informado não foi encontrado.");

        var user = optionalUser.get();
        boolean validPassword = bCryptPasswordEncoder.matches(loginRequest.getPassword(), user.getPassword());
        if (!validPassword) {
            throw new BadRequestException("Senha inválida.");
        }

        var userResponse = userMapper.fromUserToResponse(user);
        String token = jwtUtils.generateToken(user.getEmail());
        return new LoginResponse(token, userResponse);
    }
}
