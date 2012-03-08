package me.vurt.nms.core.file.exception;

/**
 * 无效的远程文件路径
 * @author yanyl
 *
 */
public class InvalidFilePathException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1002070062504360709L;

	/**
	 * @param message
	 */
	public InvalidFilePathException(String message) {
		super(message);
	}
}
