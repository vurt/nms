package me.vurt.nms.core.data.exception;

/**
 * 注册异常
 * @author yanyl
 *
 */
public class RegisterException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5104314836268475477L;
	
	public RegisterException(String msg){
		super(msg);
	}

	public RegisterException(String message, Throwable cause) {
		super(message, cause);
	}
	
}
