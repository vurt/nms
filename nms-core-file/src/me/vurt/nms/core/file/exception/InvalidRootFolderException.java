package me.vurt.nms.core.file.exception;

public class InvalidRootFolderException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2912149628788895735L;

	public InvalidRootFolderException(String message, Throwable cause) {
		super(message, cause);
	}

	public InvalidRootFolderException(String message) {
		super(message);
	}
}
