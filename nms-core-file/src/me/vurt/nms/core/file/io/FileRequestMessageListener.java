package me.vurt.nms.core.file.io;

import me.vurt.nms.core.data.Response;
import me.vurt.nms.core.jms.StaticMessageListener;
import me.vurt.nms.core.jms.data.BlobResponse;

/**
 * @author yanyl
 *
 */
public class FileRequestMessageListener extends StaticMessageListener {
	
	public FileRequestMessageListener(){
		super();
		setResponseType(BlobResponse.class);
	}
	
	/* (non-Javadoc)
	 * @see me.vurt.nms.core.jms.MessageListener#doMessageReceived(java.lang.Object, me.vurt.nms.core.data.Response)
	 */
	@Override
	protected void doMessageReceived(Object msg, Response response) {
		super.doMessageReceived(msg, response);
	}
}
