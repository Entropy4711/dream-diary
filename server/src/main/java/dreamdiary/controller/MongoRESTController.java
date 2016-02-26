package dreamdiary.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import dreamdiary.domain.DiaryEntry;
import dreamdiary.dto.DiaryEntrySearchRequest;
import dreamdiary.dto.DiaryEntrySearchResponse;
import dreamdiary.repository.DiaryEntryRepository;

@RestController
public class MongoRESTController {
	
	private DiaryEntryRepository diaryEntryRepository;
	
	@RequestMapping(value = "/entry", method = RequestMethod.POST)
	public DiaryEntry saveDiaryEntry(@RequestBody DiaryEntry entry) {
		return diaryEntryRepository.save(entry);
	}
	
	@RequestMapping(value = "/entries", method = RequestMethod.POST)
	public DiaryEntrySearchResponse search(@RequestBody DiaryEntrySearchRequest request) {
		Page<DiaryEntry> entries = diaryEntryRepository.search(request);
		
		DiaryEntrySearchResponse response = new DiaryEntrySearchResponse();
		response.setEntries(entries.getContent());
		response.setTotalElements(entries.getTotalElements());
		return response;
	}
	
	@Autowired
	public void setDiaryEntryRepository(DiaryEntryRepository diaryEntryRepository) {
		this.diaryEntryRepository = diaryEntryRepository;
	}
}