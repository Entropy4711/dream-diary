package dreamdiary.controller;

import java.util.Date;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import dreamdiary.domain.DiaryEntry;
import dreamdiary.dto.DiaryEntrySearchRequest;
import dreamdiary.dto.DiaryEntrySearchResponse;
import dreamdiary.repository.DiaryEntryRepository;

@RestController
public class MongoRESTController {
	
	private DiaryEntryRepository diaryEntryRepository;
	
	@RequestMapping(value = "/entries", method = RequestMethod.POST)
	public DiaryEntry saveDiaryEntry(@RequestBody DiaryEntry entry) {
		if (entry.getId() == null) {
			entry.setCreatedDate(new Date());
		}
		
		return diaryEntryRepository.save(entry);
	}
	
	@RequestMapping(value = "/entries/{id}", method = RequestMethod.GET)
	public DiaryEntry getEntry(@PathVariable String id) {
		return diaryEntryRepository.findOne(id);
	}
	
	@RequestMapping(value = "/entries", method = RequestMethod.GET)
	public DiaryEntrySearchResponse search(
			//@formatter:off
			@RequestParam(required = true) int page, 
			@RequestParam(required = true) int pageSize, 
			@RequestParam(required = false) String term, 
			@RequestParam(required = false) String sortField, 
			@RequestParam(required = false) Boolean sortAscending
			//@formatter:on
	) {
		DiaryEntrySearchRequest request = new DiaryEntrySearchRequest();
		request.setPage(page);
		request.setPageSize(pageSize);
		request.setTerm(term);
		
		if (StringUtils.isBlank(sortField)) {
			sortField = "createdDate";
		}
		
		if (sortAscending == null) {
			sortAscending = false;
		}
		
		request.setSortField(sortField);
		request.setSortAscending(sortAscending);
		
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