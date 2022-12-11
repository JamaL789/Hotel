package pl.hotel.application.data.service;

import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import pl.hotel.application.data.entity.User;

public interface UserRepository extends JpaRepository<User, Integer> {

    User findByUsername(String username);
}