package me.vurt.nms.core.jms;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 异步消息处理器，等待消息时不阻塞，接收到消息后会调用{@link #handle(Object)}
 * @param <T> 消息处理器要处理的消息类型，如果为{@link Object}则表示要处理所有接受到的消息
 * @author yanyl
 */
public interface MessageHandler<T> {
	public static final Logger LOGGER=LoggerFactory.getLogger(MessageHandler.class);
	/**
	 * 返回处理器ID
	 * @return
	 */
	String getId();

	/**
	 * 处理消息的类型
	 * @return
	 */
	//TODO:能不能直接从实现Class中读取<T>的真实类型
	Class<T> getMessageType();
	/**
	 * 处理消息
	 * @param msg 要处理的消息
	 * @return 处理消息的响应结果，如果为null则表示不需要响应
	 */
	Map<String, Object> handle(T msg) throws Exception;
}
