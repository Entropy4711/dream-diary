package dreamdiary.controller;

import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import dreamdiary.domain.DiaryEntry;
import dreamdiary.domain.ListEntry;
import dreamdiary.dto.DiaryEntryListResult;
import dreamdiary.dto.DiaryEntrySearchRequest;
import dreamdiary.dto.DiaryEntrySearchResponse;
import dreamdiary.repository.DiaryEntryRepository;

@RestController
public class MongoRESTController {
	
	private static final Logger log = LoggerFactory.getLogger(MongoRESTController.class);
	
	@Value("${images.path}")
	private String imagesPath;
	
	private DiaryEntryRepository diaryEntryRepository;
	
	@PostConstruct
	public void init() throws IOException {
		File imagesDirectory = new File(imagesPath);
		
		if (!imagesDirectory.exists()) {
			imagesDirectory.mkdirs();
		}
	}
	
	@RequestMapping(value = "/entries", method = RequestMethod.POST)
	public DiaryEntry saveDiaryEntry(@RequestBody DiaryEntry entry) {
		if (entry.getId() == null) {
			entry.setCreatedDate(new Date());
		}
		
		if (entry.getTags() == null) {
			entry.setTags(new ArrayList<>(0));
		}
		
		if (entry.getImages() == null) {
			entry.setImages(new ArrayList<>(0));
		} else if (entry.getId() != null) {
			DiaryEntry original = diaryEntryRepository.findOne(entry.getId());
			
			if (original != null) {
				List<ListEntry> newImages = entry.getImages();
				List<ListEntry> oldImages = original.getImages();
				
				for (ListEntry oldImage : oldImages) {
					if (!newImages.contains(oldImage)) {
						String completeFilePath = imagesPath + oldImage.getName();
						File file = new File(completeFilePath);
						file.delete();
					}
				}
			}
		}
		
		return diaryEntryRepository.save(entry);
	}
	
	@RequestMapping(value = "/entries/{id}", method = RequestMethod.GET)
	public DiaryEntry getEntry(@PathVariable String id) {
		return diaryEntryRepository.findOne(id);
	}
	
	@RequestMapping(value = "/entries/{id}", method = RequestMethod.DELETE)
	public void deleteEntry(@PathVariable String id) {
		diaryEntryRepository.delete(id);
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
		
		Page<DiaryEntryListResult> entries = diaryEntryRepository.search(request);
		
		DiaryEntrySearchResponse response = new DiaryEntrySearchResponse();
		response.setEntries(entries.getContent());
		response.setTotalElements(entries.getTotalElements());
		
		return response;
	}
	
	@RequestMapping(value = "/images/{diaryEntryId}", method = RequestMethod.POST)
	public void uploadImage(@PathVariable String diaryEntryId, @RequestParam("file") MultipartFile multiPartFile) {
		String imageName = multiPartFile.getOriginalFilename();
		String completeFilePath = imagesPath + imageName;
		
		try {
			byte[] bytes = multiPartFile.getBytes();
			
			File file = new File(completeFilePath);
			FileUtils.writeByteArrayToFile(file, bytes);
		} catch (Exception e) {
			log.error("Unable to write image '" + completeFilePath + "': ", e);
		}
	}
	
	@RequestMapping(value = "/images/{diaryEntryId}/{imageName}/", method = RequestMethod.GET)
	public ResponseEntity<byte[]> getImage(
			//@formatter:off
			@PathVariable String diaryEntryId, 
			@PathVariable String imageName, 
			@RequestParam(required = false) Integer width,
			@RequestParam(required = false) Integer height, 
			HttpServletResponse resp
			//@formatter:on
	) {
		DiaryEntry entry = diaryEntryRepository.findOne(diaryEntryId);
		
		if (entry != null) {
			List<ListEntry> images = entry.getImages();
			
			if (images != null) {
				for (ListEntry imageEntry : images) {
					String name = imageEntry.getName();
					
					if (name != null && name.equals(imageName)) {
						String completeImagePath = imagesPath + imageName;
						
						try {
							BufferedImage sourceImage = ImageIO.read(new File(completeImagePath));
							
							if (width != null && height != null) {
								double orignWidth = sourceImage.getWidth();
								double originHeight = sourceImage.getHeight();
								
								double widthScale = width / orignWidth;
								double heightScale = height / originHeight;
								
								BufferedImage targetImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
								Graphics2D graphics = targetImage.createGraphics();
								AffineTransform transform = AffineTransform.getScaleInstance(widthScale, heightScale);
								graphics.drawRenderedImage(sourceImage, transform);
								
								sourceImage = targetImage;
							}
							
							ByteArrayOutputStream outStream = new ByteArrayOutputStream();
							ImageIO.write(sourceImage, "jpeg", outStream);
							
							HttpHeaders headers = new HttpHeaders();
							headers.setContentType(MediaType.IMAGE_JPEG);
							
							byte[] byteArray = outStream.toByteArray();
							return new ResponseEntity<byte[]>(byteArray, headers, HttpStatus.CREATED);
						} catch (Exception e) {
							log.error("Unable to read image '" + completeImagePath + "': ", e);
						}
					}
				}
			}
		}
		
		resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
		
		return null;
	}
	
	@Autowired
	public void setDiaryEntryRepository(DiaryEntryRepository diaryEntryRepository) {
		this.diaryEntryRepository = diaryEntryRepository;
	}
}