package me.vurt.nms.core.jms.exception;

/**
 * @author yanyl
 * 
 */
public class MessageHandleException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5255112799084714604L;

	public MessageHandleException(String msg) {
		super(msg);
	}
	
	public MessageHandleException(Throwable cause){
		super(cause);
	}

	public MessageHandleException(String msg, Throwable throwable) {
		super(msg, throwable);
	}
}
