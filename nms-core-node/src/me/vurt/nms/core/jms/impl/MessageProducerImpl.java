package me.vurt.nms.core.jms.impl;

import javax.jms.DeliveryMode;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;

import me.vurt.nms.core.data.Response;
import me.vurt.nms.core.jms.JMSFactory;
import me.vurt.nms.core.jms.MessageProducer;
import me.vurt.nms.core.jms.exception.MessageReceiveFailedException;
import me.vurt.nms.core.jms.exception.MessageSendFailedException;
import me.vurt.nms.core.node.Node;
import me.vurt.nms.core.node.util.GlobalConfigReader;
import me.vurt.nms.core.node.util.NodeConstants;
import me.vurt.nms.core.node.util.PropertyNameUtil;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jms.JmsException;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessagePostProcessor;

/**
 * 消息生产者实现类，如果发送时发生异常，则自动重试，直至达到设置的最大重试次数
 * 
 * @author yanyl
 * 
 */
public class MessageProducerImpl implements MessageProducer {
	private static final Logger LOGGER = LoggerFactory
			.getLogger(MessageProducer.class);

	/**
	 * 最大重试次数
	 */
	private int retry = 5;

	private JmsTemplate jmsTemplate;
	
	/**
	 * 设置最大的发送失败重试次数，默认是5次
	 * 
	 * @param retry
	 *            最大重试次数
	 */
	public void setRetry(int retry) {
		this.retry = retry;
	}

	public void send(Object message) throws MessageSendFailedException {
		send(null, message);
	}

	private MessagePostProcessor nodeInfoProcessor = new AddNodeInfoProcessor();

	public void send(Destination destination, Object message)
			throws MessageSendFailedException {
		doSend(destination, message, nodeInfoProcessor);
	}

	private AddReplyToProcessor replyToProcessor = new AddReplyToProcessor();

	public Response sendAndReceive(Destination sendTo, Destination receiveFrom,
			Object message) throws MessageSendFailedException,
			MessageReceiveFailedException {
		replyToProcessor.setReplyTo(receiveFrom);
		doSend(sendTo, message, replyToProcessor);
		
		try {
			return (Response) getJmsTemplate().receiveSelectedAndConvert(
					receiveFrom, JMSFactory.getMessageSelector());
		} catch (JmsException e) {
			throw new MessageReceiveFailedException(e);
		}
	}

	/**
	 * 发送逻辑
	 * 
	 * @param destination 目的地
	 * @param message 消息
	 * @param processor 消息加工执行器
	 * @throws MessageSendFailedException 发送消息失败
	 */
	private void doSend(Destination destination, Object message,
			MessagePostProcessor processor) throws MessageSendFailedException {
		int failed = 0;
		do {
			try {
				if (destination == null) {
					getJmsTemplate().convertAndSend(message, processor);
				} else {
					getJmsTemplate().convertAndSend(destination, message, processor);
				}
				return;
			} catch (Exception e) {
				// 失败次数达到上限
				if (failed == retry - 1) {
					throw new MessageSendFailedException("发送消息失败次数达到上限，发送消息失败",
							e);
				}
				LOGGER.info("发送消息失败，进行第" + (++failed) + "次重试");
			}
		} while (failed < retry);
	}

	public void setJmsTemplate(JmsTemplate jmsTemplate) {
		this.jmsTemplate = jmsTemplate;
	}
	
	/**
	 * 设置消息存活时间，默认值是0，永不超时
	 * 
	 * @param time
	 */
	public void setTimeToLive(long time) {
		if (time >= 0) {
			getJmsTemplate().setTimeToLive(time);
		} else {
			throw new RuntimeException("错误的消息存活时间:" + time);
		}
	}

	/**
	 * 设置消息的分发类型，可选的值有{@link DeliveryMode#PERSISTENT}|
	 * {@link DeliveryMode#NON_PERSISTENT}，默认值是{@link DeliveryMode#PERSISTENT}
	 * 
	 * @param deliveryMode
	 */
	public void setDeliveryPersistent(boolean deliveryPersistent) {
			getJmsTemplate().setDeliveryPersistent(deliveryPersistent);
	}
	
	@Override
	public void setReceiveTimeout(long timeout) {
		getJmsTemplate().setReceiveTimeout(timeout);
	}
	
	/**
	 *获取JmsTemplate，如果未手动设置则自动新建一个，自动新建的DefaultDestination为空，这时的{@link #send(Object)}方法无效
	 */
	private JmsTemplate getJmsTemplate(){
		if(jmsTemplate==null){
			jmsTemplate=JMSFactory.createJmsTemplate();
			jmsTemplate.setExplicitQosEnabled(true);
		}
		return jmsTemplate;
	}

	/**
	 * 发送消息前在消息头中附加当前节点信息的处理器，只有节点类型为client时才会执行添加操作
	 * 
	 * @author yanyl
	 * 
	 */
	class AddNodeInfoProcessor implements MessagePostProcessor {
		@Override
		public Message postProcessMessage(Message message) throws JMSException {
			// 只有客户端发送消息前需要添加额外信息
			if (!GlobalConfigReader.isClient()) {
				return message;
			}
			message.setStringProperty(PropertyNameUtil
					.toJMSName(NodeConstants.PROPERTY_NODE_GROUP), Node.CURRENT
					.getGroup());
			message.setStringProperty(
					PropertyNameUtil.toJMSName(NodeConstants.PROPERTY_NODE_ID),
					Node.CURRENT.getId());
			LOGGER.debug("向消息头添加属性:"
					+ PropertyNameUtil
							.toJMSName(NodeConstants.PROPERTY_NODE_GROUP) + "="
					+ Node.CURRENT.getGroup());
			LOGGER.debug("向消息头添加属性:"
					+ PropertyNameUtil
							.toJMSName(NodeConstants.PROPERTY_NODE_ID) + "="
					+ Node.CURRENT.getId());
			return message;
		}

	}

	/**
	 * 在消息头中附加响应目的地信息和节点信息
	 * @author yanyl
	 *
	 */
	class AddReplyToProcessor extends AddNodeInfoProcessor {

		private Destination replyTo;

		public void setReplyTo(Destination destination) {
			this.replyTo = destination;
		}

		@Override
		public Message postProcessMessage(Message message) throws JMSException {
			Message temp = super.postProcessMessage(message);
			temp.setJMSReplyTo(replyTo);
			return temp;
		}
	}
}
