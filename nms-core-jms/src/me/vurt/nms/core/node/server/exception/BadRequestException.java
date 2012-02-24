package me.vurt.nms.core.node.server.exception;

/**
 * @author yanyl
 *
 */
public class BadRequestException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5514780038718114410L;

	public BadRequestException(String msg){
		super(msg);
	}
}
