package dreamdiary.repository;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.mongodb.core.MongoTemplate;

import com.mongodb.BasicDBList;
import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;

import dreamdiary.constants.MongoDbConstants;
import dreamdiary.domain.DiaryEntry;
import dreamdiary.dto.DiaryEntrySearchRequest;

public class DiaryEntryRepositoryImpl implements DiaryEntryRepositoryCustom {
	
	private MongoTemplate mongoTemplate;
	
	@Override
	public Page<DiaryEntry> search(DiaryEntrySearchRequest request) {
		String term = request.getTerm();
		int page = request.getPage();
		int pageSize = request.getPageSize();
		String sortField = request.getSortField();
		boolean sortAscending = request.isSortAscending();
		
		DBCollection collection = mongoTemplate.getCollection(MongoDbConstants.diary_entry);
		
		DBObject query = new BasicDBObject();
		
		if (StringUtils.isNotBlank(term)) {
			List<DBObject> conditions = new ArrayList<>(3);
			query.put("$or", conditions);
			
			Pattern pattern = Pattern.compile(term, Pattern.CASE_INSENSITIVE);
			conditions.add(new BasicDBObject("title", pattern));
			conditions.add(new BasicDBObject("content", pattern));
			conditions.add(new BasicDBObject("tags", pattern));
			conditions.add(new BasicDBObject("images", pattern));
		}
		
		List<DiaryEntry> entries = new ArrayList<>(pageSize);
		
		DBObject sort = new BasicDBObject();
		sort.put(sortField, sortAscending ? 1 : -1);
		
		try (DBCursor cursor = collection.find(query).sort(sort).skip(page * pageSize).limit(pageSize);) {
			while (cursor.hasNext()) {
				DBObject dbo = cursor.next();
				
				DiaryEntry entry = new DiaryEntry();
				entries.add(entry);
				
				entry.setId(dbo.get("_id").toString());
				entry.setTitle((String)dbo.get("title"));
				entry.setCreatedDate((Date)dbo.get("createdDate"));
				
				String content = (String)dbo.get("content");
				
				if (StringUtils.isNotBlank(content) && content.length() > 100) {
					content = content.substring(0, 100) + "...";
				}
				
				entry.setContent(content);
				
				List<String> tags = getStringList(dbo, "tags");
				entry.setTags(tags);
				
				List<String> images = getStringList(dbo, "images");
				entry.setImages(images);
			}
		}
		
		long totalElements = entries.size();
		
		if (entries.size() == pageSize) {
			totalElements = collection.count(query);
		}
		
		return new PageImpl<>(entries, new PageRequest(page, pageSize), totalElements);
	}
	
	private List<String> getStringList(DBObject dbo, String fieldName) {
		BasicDBList tags = (BasicDBList)dbo.get(fieldName);
		
		if (tags != null) {
			List<String> tagStrings = new ArrayList<>(tags.size());
			
			for (Object tag : tags) {
				tagStrings.add(tag.toString());
			}
			
			return tagStrings;
		}
		
		return null;
	}
	
	@Autowired
	public void setMongoTemplate(MongoTemplate mongoTemplate) {
		this.mongoTemplate = mongoTemplate;
	}
}