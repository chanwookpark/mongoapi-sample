package info.chanwook.mongoapi.op;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;
import info.chanwook.helper.FileUtils;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.mapreduce.GroupBy;
import org.springframework.data.mongodb.core.mapreduce.GroupByResults;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;

@ContextConfiguration(locations = "classpath*:/mongocontext.xml")
public class MongoAggregationTests extends AbstractJUnit4SpringContextTests {

	@Autowired
	MongoTemplate template;

	@Test
	public void config() throws Exception {
		assertNotNull(template);
	}

	@Test
	public void simpleGrouping() throws Exception {
		template.dropCollection(XObject.class);
		template.save(new XObject(1));
		template.save(new XObject(1));
		template.save(new XObject(2));
		template.save(new XObject(2));
		template.save(new XObject(2));
		template.save(new XObject(3));
		template.save(new XObject(3));
		template.save(new XObject(3));

		GroupByResults<XObject> result = template.group("group_test_collection",
				GroupBy.key("x").initialDocument("{count:0}").reduceFunction("function(doc,prev) {prev.count += 1}"),
				XObject.class);
		assertThat(result.getCount(), is(8.0D));
		assertThat(result.getKeys(), is(3));
		System.out.println(result.getRawResults().toString());
		assertThat(result.getRawResults().toString(), is(FileUtils.getContents("/info/chanwook/mongoapi/op/groupresult1.json")));
	}
}
