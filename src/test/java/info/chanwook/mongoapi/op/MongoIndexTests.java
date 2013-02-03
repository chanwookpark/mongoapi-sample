package info.chanwook.mongoapi.op;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.IndexOperations;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.index.Index;
import org.springframework.data.mongodb.core.query.Order;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;

/**
 * http://docs.mongodb.org/manual/core/indexes/
 * 
 * @author chanwook
 *
 */
@ContextConfiguration(locations = "classpath*:/mongocontext.xml")
public class MongoIndexTests extends AbstractJUnit4SpringContextTests {

	@Autowired
	MongoTemplate template;

	@Test
	public void config() throws Exception {
		assertNotNull(template);
	}

	@Test
	public void ensureIndex() throws Exception {
		Index index = new Index("balance", Order.DESCENDING);

		IndexOperations indexOps = template.indexOps(Person.class);
		indexOps.dropAllIndexes();// 테스트 목적이니 이전에 만들어 있던 인덱스는 삭제
		indexOps.ensureIndex(index);

		assertThat(indexOps.getIndexInfo().size(), is(2));

		assertThat(indexOps.getIndexInfo().get(0).getName(), is("_id_"));
		// 인덱스명에 ASC는 '_1' 이렇게 정수가, DESC에서는 '_-1' 이렇게 음수가 붙는다!
		assertThat(indexOps.getIndexInfo().get(1).getName(), is("balance_-1"));
		assertThat(indexOps.getIndexInfo().get(0).getIndexFields().size(), is(1));

		// Index Fileds 컬렉션의 key에는 정말 고유 키 값만 들어가 있음
		assertThat(indexOps.getIndexInfo().get(0).getIndexFields().get(0).getKey(), is("_id"));
		assertThat(indexOps.getIndexInfo().get(0).getIndexFields().get(0).getOrder(), is(Order.ASCENDING));
		assertThat(indexOps.getIndexInfo().get(1).getIndexFields().get(0).getKey(), is("balance"));
		assertThat(indexOps.getIndexInfo().get(1).getIndexFields().get(0).getOrder(), is(Order.DESCENDING));
	}

	@Test
	public void configurableIndex() throws Exception {
		IndexOperations indexOps = template.indexOps(Address.class);

		assertThat(indexOps.getIndexInfo().size(), is(2));

		assertThat(indexOps.getIndexInfo().get(0).getName(), is("_id_"));
		// @Indexed.name으로 직접 이름을 지정해준 경우 앞뒤 첨자가 붙지 않는다!
		assertThat(indexOps.getIndexInfo().get(1).getName(), is("idx-name"));

	}
}
