package info.chanwook.mongoapi.mapreduce;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

import java.util.List;

import info.chanwook.mongoapi.op.Person;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Order;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;

@ContextConfiguration(locations = "classpath*:/mongocontext.xml")
public class MongoMapReduceTests extends AbstractJUnit4SpringContextTests {

	@Autowired
	MongoTemplate template;

	@Test
	public void config() throws Exception {
		assertNotNull(template);
	}

	@Before
	public void opSetup() {
		template.dropCollection("TEST_PERSON");
	}

	/**
	 * http://docs.mongodb.org/manual/reference/operator/mod/
	 * 
	 * @throws Exception
	 */
	@Test
	public void mod() throws Exception {
		// given
		template.save(new Person("park", 5));
		template.save(new Person("kim", 15));
		template.save(new Person("lee", 6));

		// when
		Query query = new Query(Criteria.where("balance").mod(5, 0));
		query.sort().on("balance", Order.DESCENDING);
		List<Person> result = template.find(query, Person.class);

		// then
		assertThat(result.size(), is(2));
		assertThat(result.get(0).getName(), is("kim"));
		assertThat(result.get(1).getName(), is("park"));
	}

	/**
	 * http://docs.mongodb.org/manual/reference/operator/regex/
	 * 
	 * @throws Exception
	 */
	@Test
	public void regex() throws Exception {
		// given
		template.save(new Person("chanwook park", 0L));
		template.save(new Person("chanho park", 1L));
		template.save(new Person("chanwook kim", 2L));

		// when
		Query query = new Query(Criteria.where("name").regex("[a-z]* park"));
		query.sort().on("balance", Order.DESCENDING);
		List<Person> result = template.find(query, Person.class);

		// then
		assertThat(result.size(), is(2));
		assertThat(result.get(0).getBalance(), is(1L));
		assertThat(result.get(1).getBalance(), is(0L));

		// when
		query = new Query(Criteria.where("name").regex("chanwook [a-z]*"));
		query.sort().on("balance", Order.DESCENDING);
		result = template.find(query, Person.class);

		// then
		assertThat(result.size(), is(2));
		assertThat(result.get(0).getBalance(), is(2L));
		assertThat(result.get(1).getBalance(), is(0L));

		// Case-Insensitivity
		template.save(new Person("chanwook Park", 3L));

		// when
		query = new Query(Criteria.where("name").regex("chanwook [a-z]"));
		query.sort().on("balance", Order.DESCENDING);
		result = template.find(query, Person.class);

		// then, 기본값 regex 조회일 때는 Case-Sensitivity
		assertThat(result.size(), is(2));
		assertThat(result.get(0).getBalance(), is(2L));
		assertThat(result.get(1).getBalance(), is(0L));

		// when
		query = new Query(Criteria.where("name").regex("chanwook [a-z]", "i"));
		query.sort().on("balance", Order.DESCENDING);
		result = template.find(query, Person.class);

		// then, i 옵션을 줄 때는 Case-InSensitivity
		assertThat(result.size(), is(3));
		assertThat(result.get(0).getBalance(), is(3L));
		assertThat(result.get(1).getBalance(), is(2L));
		assertThat(result.get(2).getBalance(), is(0L));
	}
}
