package me.vurt.nms.core.node.server.handler;

import java.util.Map;

import me.vurt.nms.core.jms.MessageHandler;

/**
 * @author yanyl
 *
 */
public class HeartBeatHandler implements MessageHandler {
	public static final String ID="heartBeatHandler";

	/* (non-Javadoc)
	 * @see me.vurt.nms.core.jms.MessageHandler#getId()
	 */
	@Override
	public String getId() {
		return ID;
	}

	/* (non-Javadoc)
	 * @see me.vurt.nms.core.jms.MessageHandler#handle(java.lang.Object)
	 */
	@Override
	public Map<String, Object> handle(Object msg) {
		LOGGER.debug("处理了一条心跳信息:"+msg);
		return null;
	}

}
