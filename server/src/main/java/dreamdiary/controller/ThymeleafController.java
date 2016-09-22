package dreamdiary.controller;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import dreamdiary.domain.DiaryEntry;
import dreamdiary.dto.DiaryEntrySearchResponse;
import dreamdiary.mapper.DiaryEntryMapper;
import dreamdiary.service.DiaryEntryService;

@Controller
public class ThymeleafController {
	
	private DiaryEntryService diaryEntryService;
	private DiaryEntryMapper mapper;
	
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String getIndexPage() {
		return "index";
	}
	
	@RequestMapping(value = "/dashboard", method = RequestMethod.GET)
	public String dashboard() {
		return "dashboard";
	}
	
	@RequestMapping(value = "/navigation", method = RequestMethod.GET)
	public String navigation() {
		return "fragments/navigation :: navigation";
	}
	
	@RequestMapping(value = "/standardhead", method = RequestMethod.GET)
	public String standardhead() {
		return "fragments/standardhead :: standardhead";
	}
	
	@RequestMapping(value = "/entries", method = RequestMethod.GET)
	public String entries(
			// @formatter:off
			@RequestParam(required = false) String searchTerm, 
			@RequestParam(required = false) Integer page, 
			@RequestParam(required = false) Integer pageSize, 
			@RequestParam(required = false) String sortField, 
			@RequestParam(required = false) Boolean sortAscending, 
			Model model) {
		// @formatter:on
		
		if (page == null || page < 0) {
			page = 0;
		}
		
		if (pageSize == null || pageSize <= 0 || pageSize > 100) {
			pageSize = 25;
		}
		
		DiaryEntrySearchResponse response = diaryEntryService.search(page, pageSize, searchTerm, sortField, sortAscending);
		model.addAttribute("searchResponse", response);
		
		return "fragments/entries :: entries";
	}
	
	@RequestMapping(value = "/entry", method = RequestMethod.GET)
	public String entry(@RequestParam(required = false) String id, Model model) {
		if (StringUtils.isNotBlank(id)) {
			DiaryEntry entry = diaryEntryService.getEntry(id);
			model.addAttribute("entry", mapper.entryToDTO(entry));
		} else {
			DiaryEntry entry = new DiaryEntry();
			entry.setTags(new ArrayList<>(0));
			entry.setImages(new ArrayList<>(0));
			model.addAttribute("entry", entry);
		}
		
		return "entry";
	}
	
	@RequestMapping(value = "/tags", method = RequestMethod.GET)
	public String tags(@RequestParam(required = false) List<String> tags, Model model) {
		model.addAttribute("tags", StringUtils.join(tags, ","));
		return "fragments/tags :: tags";
	}
	
	@RequestMapping(value = "/images", method = RequestMethod.GET)
	public String images(@RequestParam(required = false) List<String> images, Model model) {
		model.addAttribute("images", images);
		return "fragments/images :: images";
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