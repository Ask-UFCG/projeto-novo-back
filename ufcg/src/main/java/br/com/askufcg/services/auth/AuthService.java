package br.com.askufcg.services.auth;

import br.com.askufcg.dtos.auth.LoginRequest;
import br.com.askufcg.dtos.auth.LoginResponse;

public interface AuthService {
    public LoginResponse auth(LoginRequest loginRequest);
}
