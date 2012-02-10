package me.vurt.nms.core.jms;

import javax.jms.Destination;

import me.vurt.nms.core.jms.exception.InvalidJMSConfigException;
import me.vurt.nms.core.jms.impl.MessageProducerImpl;
import me.vurt.nms.core.jms.util.EmptyDestination;
import me.vurt.nms.core.jms.util.JMSUtil;
import me.vurt.nms.core.jms.util.MessageListenerCache;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.jms.listener.DefaultMessageListenerContainer;

/**
 * NMS JMS���߹���
 * 
 * @author yanyl
 * 
 */
public class JMSFactory {
	private static final ApplicationContext context = new ClassPathXmlApplicationContext(
			"conf/spring-jms.xml");

	/**
	 * ��Ϣ������bean����
	 */
	public static final String PRODUCER_BEAN_NAME = "producer";
	/**
	 * ��Ϣ������bean����
	 */
	public static final String LISTENER_CONTAINER_BEAN_NAME = "listenerContainer";

	/**
	 * �½���Ϣ������ʵ��
	 * 
	 * @return
	 */
	public static MessageProducerImpl createProducer() {
		MessageProducerImpl producer = (MessageProducerImpl) context
				.getBean("producer");
		if (producer == null)
			throw new InvalidJMSConfigException("jms producer���ô���");
		return producer;
	}

	/**
	 * ��ȡ��Ϣ��������һ���������ÿ��Ŀ�ĵ�ֻ��һ��MessageListener�������Ŀ�ĵػ�û�б�ļ��������������½�֮������У���ֱ�ӷ������е�
	 * ��
	 * 
	 * @param destination
	 *            Ҫ������Ŀ�ĵأ����Ϊnull���Ǽ���spring-jms.xml�ж����defaultDestination
	 * @return �����ض�Destination����Ϣ������
	 */
	public static MessageListener getMessageListener(Destination destination) {
		if (destination == null) {
			destination = EmptyDestination.INSTANCE;
		}
		if (MessageListenerCache.hasListener(destination)) {
			return MessageListenerCache.getCachedMessageListener(destination);
		} else {
			DefaultMessageListenerContainer listenerContainer = (DefaultMessageListenerContainer) context
					.getBean(LISTENER_CONTAINER_BEAN_NAME);
			if (listenerContainer == null)
				throw new InvalidJMSConfigException("messageListener���ô���");

			if (!EmptyDestination.INSTANCE.equals(destination))
				listenerContainer.setDestination(destination);

			MessageListener listener = JMSUtil
					.getInnerListener(listenerContainer);
			listener.setDestination(listenerContainer.getDestination());
			
			MessageListenerCache.put(listenerContainer);
			
			return listener;
		}
	}
}
