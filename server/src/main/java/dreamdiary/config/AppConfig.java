package dreamdiary.config;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.web.WebMvcAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;

import dreamdiary.constants.MongoDbConstants;

@Configuration
@EnableMongoRepositories(basePackages = "dreamdiary.repository")
@EnableWebMvc
@ComponentScan(basePackages = "dreamdiary")
public class AppConfig extends WebMvcAutoConfiguration {

	@Autowired
	private MongoTemplate mongoTemplate;

	@PostConstruct
	public void init() throws Exception {
		DBCollection collection = mongoTemplate.getCollection(MongoDbConstants.diary_entry);
		collection.createIndex(new BasicDBObject("title", 1), "idx_diary_entry_title");
		collection.createIndex(new BasicDBObject("content", 1), "idx_diary_entry_content");
		collection.createIndex(new BasicDBObject("createdDate", 1), "idx_diary_entry_createdDate");
		collection.createIndex(new BasicDBObject("tags", 1), "idx_diary_entry_tags");
		collection.createIndex(new BasicDBObject("images", 1), "idx_diary_entry_images");

		collection = mongoTemplate.getCollection(MongoDbConstants.login);
		collection.createIndex(new BasicDBObject("username", 1), "idx_login_username");
	}
}