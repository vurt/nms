package me.vurt.nms.core.jms;

import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.jms.DeliveryMode;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageProducer;

import me.vurt.nms.core.data.DefaultResponse;
import me.vurt.nms.core.data.Response;
import me.vurt.nms.core.jms.util.MessageListenerCache;
import me.vurt.nms.core.node.util.NodeConstants;
import me.vurt.nms.core.node.util.PropertyNameUtil;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 异步消息监听器的实现类，可以添加具体的处理实例，启动和关闭监听<BR>
 * 消息的响应对象默认使用{@link DefaultResponse}，可以通过
 * {@link MessageListener#setResponseType(Class)}修改
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

	private long responseTimeToLive = 0;

	/**
	 * 设置响应消息存活时间，默认值是0，永不超时
	 * 
	 * @param time
	 */
	public void setResponseTimeToLive(long time) {
		if (time >= 0) {
			this.responseTimeToLive = time;
		} else {
			throw new RuntimeException("错误的消息存活时间:" + time);
		}
	}

	private int responseDeliveryMode = DeliveryMode.PERSISTENT;

	/**
	 * 设置响应的分发类型，可选的值有{@link DeliveryMode#PERSISTENT}|
	 * {@link DeliveryMode#NON_PERSISTENT}，默认值是{@link DeliveryMode#PERSISTENT}
	 * 
	 * @param deliveryMode
	 */
	public void setResponseDeliveryMode(int deliveryMode) {
		if (deliveryMode == DeliveryMode.PERSISTENT
				|| deliveryMode == DeliveryMode.NON_PERSISTENT) {
			this.responseDeliveryMode = deliveryMode;
		} else {
			throw new RuntimeException("错误的分发类型:" + deliveryMode);
		}
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

	private static final Class<? extends Response> DEFAULT_RESPONSE_CLASS = DefaultResponse.class;

	private Class<? extends Response> responseClass = DEFAULT_RESPONSE_CLASS;

	/**
	 * 设置响应消息的类型，如果不设置则默认使用{@link DefaultResponse}
	 * 
	 * @param clazz
	 */
	public void setResponseType(Class<? extends Response> clazz) {
		this.responseClass = clazz;
	}

	/**
	 * 创建响应对象
	 * 
	 * @return
	 */
	private Response createResponse() {
		try {
			return responseClass.newInstance();
		} catch (Exception e) {
			LOGGER.error("响应消息" + responseClass.getName() + "创建失败", e);
		}
		throw new RuntimeException("无法创建响应消息，消息类型：" + responseClass.getName());
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

	public final Object messageReceived(Object msg) {
		LOGGER.debug("Received one msg:" + msg.toString());
		if (handlers.size() == 0) {
			LOGGER.warn("没有设置任何处理器");
		}
		Response response = createResponse();
		doMessageReceived(msg, response);
		if (!response.isEmpty()) {
			return response;
		}
		// TODO:怎么样将响应消息以非持久化的方式发送
		return null;
	}

	/**
	 * 处理收到的消息，并构造响应
	 * 
	 * @param msg
	 *            消息
	 * @param response
	 *            响应
	 */
	protected void doMessageReceived(Object msg, Response response) {
		for (MessageHandler handler : getValidHandlers()) {
			try {
				Map<String, Object> result = handler.handle(msg);
				if (result != null) {
					response.addResponses(result);
				}
			} catch (Exception e) {
				LOGGER.error("处理消息时发生异常，handlerID:" + handler.getId(), e);
				response.addError(handler.getId(), e.getMessage());
			}
		}
	}

	/**
	 * 发送响应消息前对消息生成者的处理，默认实现是根据当前监听器的配置，设置消息生产者的分发模式和消息超时时间
	 * 
	 * @param producer
	 * @param response
	 * @throws JMSException
	 */
	public void postProcessProducer(MessageProducer producer, Message response)
			throws JMSException {
		producer.setTimeToLive(responseTimeToLive);
		producer.setDeliveryMode(responseDeliveryMode);
	}

	/**
	 * 发送响应消息前的处理，默认实现是将接收到的消息头中的nms.node.group和nms.node.id属性(如果存在)写入到响应消息头中
	 */
	public void postProcessResponse(Message request, Message response) {
		try {
			String group = request.getStringProperty(PropertyNameUtil
					.toJMSName(NodeConstants.PROPERTY_NODE_GROUP));
			String id = request.getStringProperty(PropertyNameUtil
					.toJMSName(NodeConstants.PROPERTY_NODE_ID));
			if (!StringUtils.isEmpty(group)) {
				response.setStringProperty(PropertyNameUtil
						.toJMSName(NodeConstants.PROPERTY_NODE_GROUP), group);
			}
			if (!StringUtils.isEmpty(id)) {
				response.setStringProperty(PropertyNameUtil
						.toJMSName(NodeConstants.PROPERTY_NODE_ID), id);
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
