package me.vurt.nms.core.data;

import java.util.HashMap;
import java.util.Map;

/**
 * @author yanyl
 * 
 */
public class DefaultResponse implements Response {

	/**
	 * 
	 */
	private static final long serialVersionUID = 699565053563139183L;

	private Map<String, String> errors = new HashMap<String, String>();

	private Map<String, Object> responses = new HashMap<String, Object>();

	/*
	 * (non-Javadoc)
	 * 
	 * @see me.vurt.nms.core.data.Response#isEmpty()
	 */
	@Override
	public boolean isEmpty() {
		return errors.size() == 0 && responses.size() == 0;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see me.vurt.nms.core.data.Response#addError(java.lang.String,
	 * java.lang.String)
	 */
	@Override
	public void addError(String handlerID, String error) {
		errors.put(handlerID, error);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see me.vurt.nms.core.data.Response#getErrors()
	 */
	@Override
	public Map<String, String> getErrors() {
		return errors;
	}
	
	/* (non-Javadoc)
	 * @see me.vurt.nms.core.data.Response#addResponses(java.util.Map)
	 */
	public void addResponses(Map<String, Object> responses) {
		this.responses.putAll(responses);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see me.vurt.nms.core.data.Response#getResponses()
	 */
	@Override
	public Map<String, Object> getResponses() {
		return responses;
	}

	/* (non-Javadoc)
	 * @see me.vurt.nms.core.data.Response#getResponse(java.lang.String)
	 */
	@Override
	public Object getResponse(String key) {
		return responses.get(key);
	}
}
