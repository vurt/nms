package me.vurt.nms.core.jms;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 异步消息处理器，等待消息时不阻塞，接收到消息后会调用{@link #handle(Object)}
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
	 * @param msg 要处理的消息
	 * @return 处理消息的响应结果，如果为null则表示不需要响应
	 */
	Object handle(Object msg) throws Exception;
}
