package info.chanwook.mongoapi;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;

@ContextConfiguration(locations = "classpath*:/mongocontext.xml")
public class MongoTestTemplate extends AbstractJUnit4SpringContextTests {

	@Autowired
	MongoTemplate template;

	@Test
	public void config() throws Exception {
		assertNotNull(template);
	}
}
