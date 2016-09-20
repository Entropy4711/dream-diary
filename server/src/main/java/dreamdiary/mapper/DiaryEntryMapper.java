package dreamdiary.mapper;

import org.mapstruct.Mapper;

import dreamdiary.domain.DiaryEntry;
import dreamdiary.dto.DiaryEntryDTO;

@Mapper(componentModel = "spring")
public interface DiaryEntryMapper {
	
	DiaryEntryDTO entryToDTO(DiaryEntry bo);
	
	DiaryEntry entryToBO(DiaryEntryDTO dto);
}