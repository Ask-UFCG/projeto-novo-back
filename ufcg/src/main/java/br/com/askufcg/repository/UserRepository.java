package br.com.askufcg.repository;

import br.com.askufcg.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
