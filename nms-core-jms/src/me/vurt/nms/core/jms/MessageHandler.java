package me.vurt.nms.core.jms;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author yanyl
 *
 */
public interface MessageHandler {
	public static final Logger LOGGER=LoggerFactory.getLogger(MessageHandler.class);
	/**
	 * 返回处理器ID
	 * @return
	 */
	String getId();
	/**
	 * 处理消息
	 * @param msg
	 */
	void handle(Object msg);
}
