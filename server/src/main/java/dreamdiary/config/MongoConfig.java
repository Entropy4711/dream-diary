package dreamdiary.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.Mongo;

import dreamdiary.constants.MongoDbConstants;

@Configuration
@EnableMongoRepositories(basePackages = "dreamdiary.repository")
@EnableWebMvc
@ComponentScan(basePackages = "dreamdiary")
public class MongoConfig {
	
	@Value("${mongodb.host}")
	private String mongoDbHost;
	
	@Value("${mongodb.port}")
	private int mongoDbPort;
	
	@Value("${mongodb.database}")
	private String mongoDbDatabase;
	
	public @Bean Mongo mongo() throws Exception {
		return new Mongo(mongoDbHost, mongoDbPort);
	}
	
	public @Bean MongoTemplate mongoTemplate() throws Exception {
		MongoTemplate mongoTemplate = new MongoTemplate(mongo(), mongoDbDatabase);
		
		DBCollection collection = mongoTemplate.getCollection(MongoDbConstants.diary_entry);
		collection.createIndex(new BasicDBObject("title", 1), "idx_diary_entry_title");
		collection.createIndex(new BasicDBObject("content", 1), "idx_diary_entry_content");
		collection.createIndex(new BasicDBObject("createdDate", 1), "idx_diary_entry_createdDate");
		collection.createIndex(new BasicDBObject("tags", 1), "idx_diary_entry_tags");
		collection.createIndex(new BasicDBObject("images", 1), "idx_diary_entry_images");
		
		return mongoTemplate;
	}
}
