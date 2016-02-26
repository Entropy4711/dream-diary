package dreamdiary.repository;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

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

import dreamdiary.domain.DiaryEntry;
import dreamdiary.dto.DiaryEntrySearchRequest;

public class DiaryEntryRepositoryImpl implements DiaryEntryRepositoryCustom {
	
	private MongoTemplate mongoTemplate;
	
	@Override
	public Page<DiaryEntry> search(DiaryEntrySearchRequest request) {
		DBCollection collection = mongoTemplate.getCollection("diary_entry");
		
		DBObject query = new BasicDBObject();
		
		List<DBObject> conditions = new ArrayList<>(3);
		query.put("$or", conditions);
		
		Pattern pattern = Pattern.compile(request.getText(), Pattern.CASE_INSENSITIVE);
		conditions.add(new BasicDBObject("title", pattern));
		conditions.add(new BasicDBObject("content", pattern));
		conditions.add(new BasicDBObject("tags", pattern));
		
		int page = request.getPage();
		int pageSize = request.getPageSize();
		
		List<DiaryEntry> entries = new ArrayList<>(pageSize);
		
		try (DBCursor cursor = collection.find(query).skip(page * pageSize).limit(pageSize);) {
			while (cursor.hasNext()) {
				DBObject dbo = cursor.next();
				
				DiaryEntry entry = new DiaryEntry();
				entry.setId(dbo.get("_id").toString());
				entry.setTitle((String)dbo.get("title"));
				entry.setContent((String)dbo.get("content"));
				entries.add(entry);
				
				BasicDBList tags = (BasicDBList)dbo.get("tags");
				
				if (tags != null) {
					List<String> tagStrings = new ArrayList<>(tags.size());
					entry.setTags(tagStrings);
					
					for (Object tag : tags) {
						tagStrings.add(tag.toString());
					}
				}
			}
		}
		
		long totalElements = collection.count(query);
		
		return new PageImpl<>(entries, new PageRequest(page, pageSize), totalElements);
	}
	
	@Autowired
	public void setMongoTemplate(MongoTemplate mongoTemplate) {
		this.mongoTemplate = mongoTemplate;
	}
}