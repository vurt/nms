package me.vurt.nms.core.jms.impl;

import java.util.HashMap;
import java.util.Map;

import me.vurt.nms.core.jms.ResponseMessage;

/**
 * @author yanyl
 * 
 */
public class DefaultReponseMessage implements ResponseMessage {
	private Map<String, String> errors = new HashMap<String, String>();
	private Map<String, Object> responses = new HashMap<String, Object>();

	/*
	 * (non-Javadoc)
	 * 
	 * @see me.vurt.nms.core.jms.ResponseMessage#getErrors()
	 */
	@Override
	public Map<String, String> getErrors() {
		return errors;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see me.vurt.nms.core.jms.ResponseMessage#addError(java.lang.String,
	 * java.lang.String)
	 */
	@Override
	public void addError(String handlerID, String error) {
		errors.put(handlerID, error);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see me.vurt.nms.core.jms.ResponseMessage#addResponse(java.lang.String,
	 * java.lang.Object)
	 */
	@Override
	public void addResponse(String handlerID, Object response) {
		responses.put(handlerID, response);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see me.vurt.nms.core.jms.ResponseMessage#getResponses()
	 */
	@Override
	public Map<String, Object> getResponses() {
		return responses;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see me.vurt.nms.core.jms.ResponseMessage#isEmpty()
	 */
	@Override
	public boolean isEmpty() {
		if ((responses==null||responses.size() == 0) && (errors==null||errors.size() == 0)) {
			return true;
		}
		return false;
	}

}
