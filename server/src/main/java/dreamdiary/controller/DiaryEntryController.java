package dreamdiary.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import dreamdiary.domain.DiaryEntry;
import dreamdiary.dto.DiaryEntryDTO;
import dreamdiary.mapper.DiaryEntryMapper;
import dreamdiary.service.DiaryEntryService;

@RestController
public class DiaryEntryController {
	
	private DiaryEntryService diaryEntryService;
	private DiaryEntryMapper mapper;
	
	@RequestMapping(value = "/entry", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
	@ResponseBody
	public DiaryEntryDTO saveEntry(@RequestBody DiaryEntryDTO entry) {
		DiaryEntry bo = diaryEntryService.saveDiaryEntry(mapper.entryToBO(entry));
		return mapper.entryToDTO(bo);
	}
	
	@Autowired
	public void setDiaryEntryService(DiaryEntryService diaryEntryService) {
		this.diaryEntryService = diaryEntryService;
	}
	
	@Autowired
	public void setMapper(DiaryEntryMapper mapper) {
		this.mapper = mapper;
	}
}