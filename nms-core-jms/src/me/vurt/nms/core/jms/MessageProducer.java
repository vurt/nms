package me.vurt.nms.core.jms;

import javax.jms.Destination;

import org.springframework.jms.core.JmsTemplate;

/**
 * @author yanyl
 * 
 */
public interface MessageProducer {
	/**
	 * 设置jmsTemplate
	 * @param jmsTemplate
	 */
	void setJmsTemplate(JmsTemplate jmsTemplate);
	
	/**
	 * 发送消息至默认{@link Destination 目的地}，默认目的地在jmsTemplate中指定
	 * 
	 * @param message 消息内容
	 */
	void send(Object message);

	/**
	 * 发送消息
	 * @param destination 目的地
	 * @param message 消息内容
	 */
	void send(Destination destination, Object message);
}
