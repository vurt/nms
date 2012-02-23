package me.vurt.nms.core.node.data;

/**
 * @author yanyl
 *
 */
public class RegisterResponse {
	private boolean succeed;
	
	private String errorMessage;

	/**
	 * @return the succeed
	 */
	public boolean isSucceed() {
		return succeed;
	}

	/**
	 * @param succeed the succeed to set
	 */
	public void setSucceed(boolean succeed) {
		this.succeed = succeed;
	}

	/**
	 * @return the errorMessage
	 */
	public String getErrorMessage() {
		return errorMessage;
	}

	/**
	 * @param errorMessage the errorMessage to set
	 */
	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}
}
