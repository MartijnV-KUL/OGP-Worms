package worms.model;

public class Type<T> {

	public Type() {
	}
	
	public Type(T value) {
		this.value = value;
	}

	private T value;
	
	public T getValue() {
		return value;
	}

}