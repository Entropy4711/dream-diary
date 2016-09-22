package dreamdiary.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import dreamdiary.domain.DiaryEntry;
import dreamdiary.dto.DiaryEntryDTO;
import dreamdiary.dto.FileUploadResponse;
import dreamdiary.dto.FileUploadResultItem;
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
	
	@RequestMapping(value = "/image/{imageName:.+}", method = RequestMethod.GET)
	public ResponseEntity<byte[]> getImage(@PathVariable String imageName, @RequestParam(required = false) Integer width, @RequestParam(required = false) Integer height, HttpServletResponse resp) {
		return diaryEntryService.getImage(imageName, width, height, resp);
	}
	
	@RequestMapping(value = "/image", method = RequestMethod.POST, produces = "application/json")
	public FileUploadResponse uploadImage(@RequestParam MultipartFile file) {
		diaryEntryService.saveImage(file);
		
		List<FileUploadResultItem> files = new ArrayList<>(1);
		files.add(new FileUploadResultItem(file.getOriginalFilename()));
		return new FileUploadResponse(files);
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