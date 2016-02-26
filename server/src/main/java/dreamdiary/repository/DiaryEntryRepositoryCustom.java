package dreamdiary.repository;

import org.springframework.data.domain.Page;

import dreamdiary.domain.DiaryEntry;
import dreamdiary.dto.DiaryEntrySearchRequest;

public interface DiaryEntryRepositoryCustom {
	
	Page<DiaryEntry> search(DiaryEntrySearchRequest request);
}