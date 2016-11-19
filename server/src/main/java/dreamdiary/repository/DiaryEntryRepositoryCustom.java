package dreamdiary.repository;

import org.springframework.data.domain.Page;

import dreamdiary.dto.DiaryEntryListResult;
import dreamdiary.dto.DiaryEntrySearchRequest;
import dreamdiary.dto.TagListResult;
import dreamdiary.dto.TagsSearchRequest;

public interface DiaryEntryRepositoryCustom {
	
	Page<DiaryEntryListResult> search(DiaryEntrySearchRequest request);
	
	Page<TagListResult> searchTags(TagsSearchRequest request);
}