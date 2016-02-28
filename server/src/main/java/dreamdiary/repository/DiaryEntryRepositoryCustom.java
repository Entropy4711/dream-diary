package dreamdiary.repository;

import org.springframework.data.domain.Page;

import dreamdiary.dto.DiaryEntryListResult;
import dreamdiary.dto.DiaryEntrySearchRequest;

public interface DiaryEntryRepositoryCustom {
	
	Page<DiaryEntryListResult> search(DiaryEntrySearchRequest request);
}