package dreamdiary.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import com.mongodb.Mongo;

@Configuration
@EnableMongoRepositories(basePackages = "dreamdiary.repository")
@EnableWebMvc
@ComponentScan(basePackages = "dreamdiary")
public class MongoConfig {
	
	public @Bean Mongo mongo() throws Exception {
		return new Mongo("localhost");
	}
	
	public @Bean MongoTemplate mongoTemplate() throws Exception {
		return new MongoTemplate(mongo(), "dream_diary");
	}
}
