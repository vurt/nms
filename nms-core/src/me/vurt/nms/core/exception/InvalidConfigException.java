package me.vurt.nms.core.exception;

/**
 * 配置错误
 * @author Vurt
 *
 */
public class InvalidConfigException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5859458400894534431L;

	public InvalidConfigException() {
		super();
	}

	public InvalidConfigException(String message) {
		super(message);
	}

	
}
