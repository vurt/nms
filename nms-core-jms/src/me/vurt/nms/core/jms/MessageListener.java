package me.vurt.nms.core.jms;

import java.util.LinkedHashMap;
import java.util.Map;

import javax.jms.Destination;

import me.vurt.nms.core.jms.util.MessageListenerCache;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author yanyl
 * 
 */
public class MessageListener {
	private static final Logger LOGGER = LoggerFactory
			.getLogger(MessageListener.class);

	private static int counter = 0;

	/**
	 * 监听器编号
	 */
	private int index = 0;

	public MessageListener() {
		addCounter();
		index = counter;
	}

	/**
	 * 监听器新建个数计数器
	 */
	private synchronized void addCounter() {
		counter++;
	}

	private Destination destination = null;

	/**
	 * 设置监听的目的地，仅供读取，手动调用并不会修改监听对象
	 * 
	 * @param destination
	 */
	public void setDestination(Destination destination) {
		if (this.destination != null) {
			LOGGER.error("不支持修改监听目标");
		} else {
			this.destination = destination;
			LOGGER.info(index + " - 开始监听" + destination.toString());
		}
	}

	/**
	 * 消息处理器Map，Key是处理器id，Value是处理器实例
	 */
	private Map<String, MessageHandler> handlers = new LinkedHashMap<String, MessageHandler>();
	
	/**
	 * 消息处理方法的方法名
	 */
	public static final String MESSAGE_HANDLE_MOTHED_NAME="messageReceived";

	public void messageReceived(Object msg) {
		LOGGER.debug("Received one msg:" + msg.toString());
		if (handlers.size() == 0) {
			LOGGER.warn("没有设置任何处理器");
		}
		for (MessageHandler handler : handlers.values()) {
			handler.handle(msg);
		}
	}

	/**
	 * 添加处理器
	 * 
	 * @param handler
	 */
	public void addMessageHandler(MessageHandler handler) {
		handlers.put(handler.getId(), handler);
	}

	/**
	 * 删除处理器
	 * 
	 * @param id
	 *            处理器id
	 */
	public void removeHandler(String id) {
		if (handlers.containsKey(id)) {
			handlers.remove(id);
		}
	}

	/**
	 * 仅输出一条停止监听的日志，如果要停止监听请直接停止监听器的容器
	 */
	public void stop() {
		LOGGER.info(index + " - 已停止监听:" + destination.toString());
	}
}
