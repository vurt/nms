package me.vurt.nms.core.jms;

import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;

import me.vurt.nms.core.jms.impl.DefaultReponseMessage;
import me.vurt.nms.core.jms.util.MessageListenerCache;
import me.vurt.nms.core.node.util.NodeConstants;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 消息监听器的实现类，可以添加具体的处理实例，启动和关闭监听
 * 
 * @author yanyl
 * 
 */
public abstract class MessageListener {
	protected static final Logger LOGGER = LoggerFactory
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
	 * 设置监听的目的地，仅供读取，每个消息监听器只能被设置一次监听目标，重复设置无效
	 * 
	 * @param destination
	 */
	public void setDestination(Destination destination) {
		if (this.destination != null) {
			LOGGER.error("不支持修改监听目标");
		} else {
			this.destination = destination;
		}
	}

	/**
	 * 消息处理器Map，Key是处理器id，Value是处理器实例
	 */
	protected Map<String, MessageHandler> handlers = new LinkedHashMap<String, MessageHandler>();
	
	/**
	 * 添加处理器
	 * 
	 * @param handler
	 */
	protected void addMessageHandler(MessageHandler handler) {
		handlers.put(handler.getId(), handler);
	}

	/**
	 * 删除处理器
	 * 
	 * @param id
	 *            处理器id
	 */
	protected void removeHandler(String id) {
		if (handlers.containsKey(id)) {
			handlers.remove(id);
		}
	}

	/**
	 * 获取有效的处理器
	 * 
	 * @return
	 */
	protected abstract Collection<MessageHandler> getValidHandlers();

	/**
	 * 消息处理方法的方法名
	 */
	public static final String MESSAGE_HANDLE_MOTHED_NAME = "messageReceived";

	public Object messageReceived(Object msg) {
		LOGGER.debug("Received one msg:" + msg.toString());
		if (handlers.size() == 0) {
			LOGGER.warn("没有设置任何处理器");
		}
		ResponseMessage response = new DefaultReponseMessage();
		for (MessageHandler handler : getValidHandlers()) {
			try {
				Object result = handler.handle(msg);
				if (result != null) {
					response.addResponse(handler.getId(), result);
				}
			} catch (Exception e) {
				LOGGER.error("处理消息时发生异常，handlerID:" + handler.getId(), e);
				response.addError(handler.getId(), e.getMessage());
			}
		}
		if (!response.isEmpty()) {
			return response;
		}
		return null;
	}

	/**
	 * 发送响应消息前的处理，默认实现是将接收到的消息头中的nms.node.group和nms.node.id属性(如果存在)写入到响应消息头中
	 */
	public void postProcessResponse(Message request, Message response) {
		try {
			String group = request
					.getStringProperty(NodeConstants.PROPERTY_NODE_GROUP);
			String id = request
					.getStringProperty(NodeConstants.PROPERTY_NODE_ID);
			if (!StringUtils.isEmpty(group)) {
				response.setStringProperty(NodeConstants.PROPERTY_NODE_GROUP,
						group);
			}
			if (!StringUtils.isEmpty(id)) {
				response.setStringProperty(NodeConstants.PROPERTY_NODE_ID, id);
			}
		} catch (JMSException e) {
			LOGGER.debug("读取消息头时发生异常", e);
		}
	}

	/**
	 * 启动监听
	 */
	public void start() {
		MessageListenerCache.startContainer(destination);
		LOGGER.info(index + " - 开始监听" + destination.toString());
	}

	/**
	 * 停止监听
	 */
	public void stop() {
		MessageListenerCache.stopContainer(destination);
		LOGGER.info(index + " - 已停止监听:" + destination.toString());
	}
}
