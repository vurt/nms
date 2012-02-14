package me.vurt.nms.core.jms.impl;

import javax.jms.Destination;

import me.vurt.nms.core.jms.JMSFactory;
import me.vurt.nms.core.jms.MessageProducer;

import org.springframework.jms.core.JmsTemplate;

/**
 * 消息生产者
 * <p>
 * 不要直接新建，最好通过{@link JMSFactory}创建，或从Spring中创建，默认配置文件路径conf/spring-jms.xml，默认bean name: producer
 * </p>
 * 
 * @author yanyl
 * 
 */
public class MessageProducerImpl implements MessageProducer{

	private JmsTemplate jmsTemplate;

	/**
	 * 发送消息至默认{@link Destination 目的地}
	 * @param message
	 */
	public void send(Object message) {
		jmsTemplate.convertAndSend(message);
	}
	
	public void send(Destination destination,Object message){
		jmsTemplate.convertAndSend(destination, message);
	}

	public JmsTemplate getJmsTemplate() {
		return jmsTemplate;
	}

	public void setJmsTemplate(JmsTemplate jmsTemplate) {
		this.jmsTemplate = jmsTemplate;
	}

}
