package me.vurt.nms.core.data.impl;

import me.vurt.nms.core.data.RegisterResponse;

/**
 * @author yanyl
 *
 */
public class RegisterResponseImpl extends DefaultResponse implements RegisterResponse{

	/**
	 * 
	 */
	private static final long serialVersionUID = -8221407690923587079L;

	/* (non-Javadoc)
	 * @see me.vurt.nms.core.data.RegisterResponse#isSucceed()
	 */
	@Override
	public boolean isSucceed() {
		return getErrors().size()==0;
	}
	
	/* (non-Javadoc)
	 * @see me.vurt.nms.core.data.impl.AbstractResponse#isEmpty()
	 */
	@Override
	public boolean isEmpty() {
		return false;
	}

}
