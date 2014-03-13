package cc.pp.service.tencent.model;

import java.util.ArrayList;
import java.util.List;

public class ExceptionModel<T> {

	private String message;

	private List<T> errors;

	public ExceptionModel() {
	}

	public ExceptionModel(String message) {
		super();
		this.message = message;
		errors = new ArrayList<>();
	}

	public ExceptionModel<T> addError(T error) {
		errors.add(error);
		return this;
	}

	public List<T> getErrors() {
		return errors;
	}

	public String getMessage() {
		return message;
	}

	public void setErrors(List<T> errors) {
		this.errors = errors;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	@Override
	public String toString() {
		return "ExceptionModel [message=" + message + ", errors=" + errors + "]";
	}

}
