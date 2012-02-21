package me.vurt.nms.core.jms.impl;

import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;

import me.vurt.nms.core.jms.MessageProducer;
import me.vurt.nms.core.node.util.NMSConfigReader;
import me.vurt.nms.core.node.util.NodeConstants;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessagePostProcessor;

/**
 * 消息生产者实现类
 * 
 * @author yanyl
 * 
 */
public class MessageProducerImpl implements MessageProducer {
	private static final Logger LOGGER=LoggerFactory.getLogger(MessageProducer.class);

	private JmsTemplate jmsTemplate;

	private MessagePostProcessor processor = new AddNodeInfoProcessor();

	/**
	 * 发送消息至默认{@link Destination 目的地}
	 * 
	 * @param message
	 */
	public void send(Object message) {
		send(null, message);
	}
	
	/**
	 * 发送消息到指定目的地
	 */
	public void send(Destination destination, Object message) {
		if (destination == null) {
			jmsTemplate.convertAndSend(message, processor);
		} else {
			jmsTemplate.convertAndSend(destination, message, processor);
		}
	}

	public void setJmsTemplate(JmsTemplate jmsTemplate) {
		this.jmsTemplate = jmsTemplate;
	}

	/**
	 * 发送消息前在消息头中附加当前节点信息的处理器，只有节点类型为client时才会执行添加操作
	 * 
	 * @author yanyl
	 * 
	 */
	class AddNodeInfoProcessor implements MessagePostProcessor {

		/*
		 * (non-Javadoc)
		 * 
		 * @see
		 * org.springframework.jms.core.MessagePostProcessor#postProcessMessage
		 * (javax.jms.Message)
		 */
		@Override
		public Message postProcessMessage(Message message) throws JMSException {
			// 只有客户端发送消息前需要添加额外信息
			if(!NMSConfigReader.isClient()){
				return message;
			}
			String group = NMSConfigReader.getNodeGroup();
			String id = NMSConfigReader.getNodeID();
			message.setStringProperty(NodeConstants.PROPERTY_NODE_GROUP, group);
			message.setStringProperty(NodeConstants.PROPERTY_NODE_ID, id);
			LOGGER.debug("向消息头添加属性:"+NodeConstants.PROPERTY_NODE_GROUP+"="+group);
			LOGGER.debug("向消息头添加属性:"+NodeConstants.PROPERTY_NODE_ID+"="+id);
			return message;
		}

	}
}
