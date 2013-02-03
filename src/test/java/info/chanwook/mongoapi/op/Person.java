package info.chanwook.mongoapi.op;

import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "TEST_PERSON")
public class Person {

	private String name;
	private long balance;

	public Person() {
	}

	public Person(String name, long balance) {
		super();
		this.name = name;
		this.balance = balance;
	}

	public long getBalance() {
		return balance;
	}

	public void setBalance(long balance) {
		this.balance = balance;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
