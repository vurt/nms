package me.vurt.nms.core.jms.data;

import java.io.InputStream;

import me.vurt.nms.core.data.DefaultResponse;

/**
 * 大对象响应的默认实现
 * @author yanyl
 */
public class DefaultBlobResponse extends DefaultResponse implements BlobResponse{

	/**
	 * 
	 */
	private static final long serialVersionUID = -395975934961726129L;

	/* (non-Javadoc)
	 * @see me.vurt.nms.core.data.BlobResponse#getInputStream()
	 */
	@Override
	public InputStream getInputStream() {
		return is;
	}
	
	private InputStream is;

	/* (non-Javadoc)
	 * @see me.vurt.nms.core.jms.data.BlobResponse#setInputStream(java.io.InputStream)
	 */
	@Override
	public void setInputStream(InputStream is) {
		this.is=is;
	}

}
