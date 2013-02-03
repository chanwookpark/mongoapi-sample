package info.chanwook.mongoapi.op;

import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection="group_test_collection")
public class XObject {

	private float x;
	private float count;

	public XObject() {
	}

	public XObject(float x) {
		super();
		this.x = x;
	}

	public float getCount() {
		return count;
	}

	public float getX() {
		return x;
	}

	public void setCount(float count) {
		this.count = count;
	}

	public void setX(float x) {
		this.x = x;
	}

	@Override
	public String toString() {
		return "XObject [x=" + x + ", count=" + count + "]";
	}

}
