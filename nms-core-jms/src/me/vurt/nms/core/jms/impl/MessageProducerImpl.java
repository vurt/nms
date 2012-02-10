package me.vurt.nms.core.jms.impl;

import javax.jms.Destination;

import me.vurt.nms.core.jms.JMSFactory;
import me.vurt.nms.core.jms.MessageProducer;

import org.springframework.jms.core.JmsTemplate;

/**
 * ��Ϣ������
 * <p>
 * ��Ҫֱ���½������ͨ��{@link JMSFactory}���������Spring�д�����Ĭ�������ļ�·��conf/spring-jms.xml��Ĭ��bean name: producer
 * </p>
 * 
 * @author yanyl
 * 
 */
public class MessageProducerImpl implements MessageProducer{

	private JmsTemplate jmsTemplate;

	/**
	 * ������Ϣ��Ĭ��{@link Destination Ŀ�ĵ�}
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
