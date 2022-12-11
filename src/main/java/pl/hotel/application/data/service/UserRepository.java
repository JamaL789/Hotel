package pl.hotel.application.data.service;

import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import pl.hotel.application.data.entity.User;

public interface UserRepository extends JpaRepository<User, Integer> {

 //   User findByUsername(String username);
//	@Query("SELECT u FROM App_User u WHERE u.username = ?1")
	User findByUsername(String username);
}