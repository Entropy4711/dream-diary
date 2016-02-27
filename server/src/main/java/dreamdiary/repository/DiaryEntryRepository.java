package dreamdiary.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import dreamdiary.domain.DiaryEntry;

public interface DiaryEntryRepository extends MongoRepository<DiaryEntry, String>, DiaryEntryRepositoryCustom {

}