package dreamdiary.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import dreamdiary.domain.Login;

public interface LoginRepository extends MongoRepository<Login, String> {
	
	Login findByUsername(String username);
}