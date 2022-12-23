package pl.hotel.application.data.service;

import java.util.UUID;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import pl.hotel.application.data.entity.User;

public interface UserRepository extends JpaRepository<User, Integer> {

 //   User findByUsername(String username);
//	@Query("SELECT u FROM App_User u WHERE u.username = ?1")
	User findByUsername(String username);
	
	@Modifying
	@Transactional
	@Query("UPDATE User u SET u.username = ?1, u.name = ?2, u.password = ?3, u.email = ?4 WHERE u.id = ?5")
	void updateUserInfo(String username, String name, String password, String email, int id);
}