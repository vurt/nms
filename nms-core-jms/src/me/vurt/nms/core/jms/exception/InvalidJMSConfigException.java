package me.vurt.nms.core.jms.exception;

/**
 * Spring JMS配置无效的异常
 * 
 * @author yanyl
 * 
 */
public class InvalidJMSConfigException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5755963864983092298L;

	/**
	 * 
	 */
	public InvalidJMSConfigException() {
		super();
	}

	public InvalidJMSConfigException(String msg) {
		super(msg);
	}
}
