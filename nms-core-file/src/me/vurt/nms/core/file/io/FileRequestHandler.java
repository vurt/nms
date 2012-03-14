package me.vurt.nms.core.file.io;

import java.util.Map;

import me.vurt.nms.core.file.NMSFileSystem;
import me.vurt.nms.core.file.data.RemoteFileReadRequest;
import me.vurt.nms.core.file.data.FolderContent;
import me.vurt.nms.core.jms.MessageHandler;

/**
 * @author yanyl
 *
 */
public class FileRequestHandler implements MessageHandler<RemoteFileReadRequest>{

	/* (non-Javadoc)
	 * @see me.vurt.nms.core.jms.MessageHandler#getId()
	 */
	@Override
	public String getId() {
		return "FileRequestHandler";
	}

	/* (non-Javadoc)
	 * @see me.vurt.nms.core.jms.MessageHandler#getMessageType()
	 */
	@Override
	public Class<RemoteFileReadRequest> getMessageType() {
		return RemoteFileReadRequest.class;
	}

	/* (non-Javadoc)
	 * @see me.vurt.nms.core.jms.MessageHandler#handle(java.lang.Object)
	 */
	@Override
	public Map<String, Object> handle(RemoteFileReadRequest msg)
			throws Exception {
		FolderContent folder=NMSFileSystem.getRootFolder(msg.getRoot());
		return null;
	}

}
