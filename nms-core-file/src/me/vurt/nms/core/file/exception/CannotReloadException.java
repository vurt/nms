package me.vurt.nms.core.file.exception;

/**
 * 无法重载根节点
 * @author yanyl
 *
 */
public class CannotReloadException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1788626706404148151L;

	/**
	 * @param message
	 */
	public CannotReloadException(String message) {
		super(message);
	}
}
