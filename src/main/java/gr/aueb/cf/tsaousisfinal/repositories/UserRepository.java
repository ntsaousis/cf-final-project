package gr.aueb.cf.tsaousisfinal.repositories;

import gr.aueb.cf.tsaousisfinal.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);

    Optional<User> findByVat(String vat);

    Optional<User> findByEmail(String email);

    Boolean existsByEmail(String email);
}
