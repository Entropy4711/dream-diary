package dreamdiary.service;

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
import javax.annotation.security.RolesAllowed;
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
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import dreamdiary.constants.Roles;
import dreamdiary.domain.DiaryEntry;
import dreamdiary.dto.DiaryEntryListResult;
import dreamdiary.dto.DiaryEntrySearchRequest;
import dreamdiary.dto.DiaryEntrySearchResponse;
import dreamdiary.dto.TagListResult;
import dreamdiary.dto.TagsSearchRequest;
import dreamdiary.dto.TagsSearchResponse;
import dreamdiary.repository.DiaryEntryRepository;

@Service
public class DiaryEntryService {
	
	private final Logger log = LoggerFactory.getLogger(getClass());
	
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
	
	@RolesAllowed({ Roles.WRITE })
	public DiaryEntry saveDiaryEntry(DiaryEntry entry) {
		String id = entry.getId();
		
		if (id == null) {
			entry.setCreatedDate(new Date());
		} else {
			DiaryEntry original = diaryEntryRepository.findOne(id);
			entry.setCreatedDate(original.getCreatedDate());
		}
		
		if (entry.getTags() == null) {
			entry.setTags(new ArrayList<>(0));
		}
		
		if (entry.getImages() == null) {
			entry.setImages(new ArrayList<>(0));
		} else if (id != null) {
			DiaryEntry original = diaryEntryRepository.findOne(id);
			
			if (original != null) {
				List<String> newImages = entry.getImages();
				List<String> oldImages = original.getImages();
				
				for (String oldImage : oldImages) {
					if (!newImages.contains(oldImage)) {
						String completeFilePath = imagesPath + oldImage;
						File file = new File(completeFilePath);
						file.delete();
					}
				}
			}
		}
		
		
		return diaryEntryRepository.save(entry);
	}
	
	@RolesAllowed({ Roles.READ })
	public DiaryEntry getEntry(String id) {
		return diaryEntryRepository.findOne(id);
	}
	
	@RolesAllowed({ Roles.WRITE })
	public void deleteEntry(String id) {
		diaryEntryRepository.delete(id);
	}
	
	@RolesAllowed({ Roles.READ })
	public DiaryEntrySearchResponse search(int page, int pageSize, String term, String sortField, Boolean sortAscending) {
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
		
		if (entries.getTotalElements() > 0) {
			response.setPageCount(entries.getTotalElements() / pageSize);
			
			if (response.getPageCount() <= 0) {
				response.setPageCount(1);
			}
		}
		
		response.setPage(page);
		
		return response;
	}
	
	@RolesAllowed({ Roles.WRITE })
	public void saveImage(MultipartFile multiPartFile) {
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
	
	@RolesAllowed({ Roles.READ })
	public ResponseEntity<byte[]> getImage(String imageName, Integer width, Integer height, HttpServletResponse resp) {
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
			return new ResponseEntity<>(byteArray, headers, HttpStatus.CREATED);
		} catch (Exception e) {
			log.error("Unable to read image '" + completeImagePath + "': ", e);
		}
		
		resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
		
		return null;
	}
	
	public TagsSearchResponse searchTags(Integer page, Integer pageSize, String searchTerm, String sortField, Boolean sortAscending) {
		TagsSearchRequest request = new TagsSearchRequest();
		request.setPage(page);
		request.setPageSize(pageSize);
		request.setTerm(searchTerm);
		
		if (StringUtils.isBlank(sortField)) {
			sortField = "createdDate";
		}
		
		if (sortAscending == null) {
			sortAscending = false;
		}
		
		request.setSortField(sortField);
		request.setSortAscending(sortAscending);
		
		Page<TagListResult> entries = diaryEntryRepository.searchTags(request);
		
		TagsSearchResponse response = new TagsSearchResponse();
		response.setTags(entries.getContent());
		response.setTotalElements(entries.getTotalElements());
		
		if (entries.getTotalElements() > 0) {
			response.setPageCount(entries.getTotalElements() / pageSize);
			
			if (response.getPageCount() <= 0) {
				response.setPageCount(1);
			}
		}
		
		response.setPage(page);
		
		return response;
	}
	
	@Autowired
	public void setDiaryEntryRepository(DiaryEntryRepository diaryEntryRepository) {
		this.diaryEntryRepository = diaryEntryRepository;
	}
}