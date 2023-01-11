package pl.hotel.application.data.service;

import java.util.Optional;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import pl.hotel.application.data.entity.User;

@Service
public class UserService {

    private final UserRepository repository;

    @Autowired
    public UserService(UserRepository repository) {
        this.repository = repository;
    }
    public User getUserByNick(String username) {
    	return repository.findByUsername(username);
    }
    public Optional<User> get(int id) {
        return repository.findById(id);
    }

    public User addUser(User entity) {
        return repository.save(entity);
    }

    public void delete(int id) {
        repository.deleteById(id);
    }
  
    public void updateUserInfo(String username, String name, String password, String email, int id) {
    	repository.updateUserInfo(username, name, password, email, id);
    }
}
