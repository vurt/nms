package me.vurt.nms.core.jms.exception;

/**
 * 接收信息超时
 * @author yanyl
 *
 */
public class MessageReceiveFailedException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1474348813160463146L;

	/**
	 * 
	 */
	public MessageReceiveFailedException() {
		super();
	}

	/**
	 * @param message
	 * @param cause
	 */
	public MessageReceiveFailedException(String message, Throwable cause) {
		super(message, cause);
	}

	/**
	 * @param message
	 */
	public MessageReceiveFailedException(String message) {
		super(message);
	}

	/**
	 * @param cause
	 */
	public MessageReceiveFailedException(Throwable cause) {
		super(cause);
	}

	
}
