package dreamdiary.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import dreamdiary.domain.Login;

public interface LoginRepository extends MongoRepository<Login, String> {

	List<Login> findByUsername(String username);
}